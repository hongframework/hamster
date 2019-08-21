package com.hframework.datacenter.hamster.monitor.zk

import java.util
import java.util.UUID
import java.util.concurrent.ConcurrentLinkedQueue

import com.hframe.hamster.node.HamsterConst
import com.hframework.datacenter.hamster.monitor.zk.listeners.ZKChildrenListener
import com.hframework.datacenter.hamster.zookeeper.ZKClient
import org.I0Itec.zkclient.IZkChildListener
import org.slf4j.MDC

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.util.control.Breaks.{break, breakable}

/**
  * 用于监控zk直接root path 下子节点的变化
  * ROOT -> Child-Node
  * @param prototypeKey
  */
class ZKChildrenMonitor(prototypeKey: String, rootPath: String, autoFilter: Boolean = false, ignoreNil:Boolean = true)
  extends PrototypeMonitor[ZKChildrenListener](prototypeKey, rootPath)
  with IZkChildListener
  with ZKChildrenListener {

  /** proccess列表 **/
  @volatile var childrenCache = scala.List.empty[String]
  val historyAddCntQueue = new ConcurrentLinkedQueue[Int]
  val historyAddBuffer = mutable.Buffer.empty[String]
  val historyDelCntQueue = new ConcurrentLinkedQueue[Int]
  val historyDelBuffer = mutable.Buffer.empty[String]




  /** 启动逻辑 */
  override protected def startInternal(): Unit = {
    val children:mutable.Buffer[String] = zkClientx.subscribeChildChanges(rootPath, this).asScala
    reloadChildren(children)
  }

  def removeAllChildren: Unit = {
    val children:mutable.Buffer[String] = zkClientx.getChildren(rootPath).asScala
    logger.info(s"[TASK START]: remove last cycle processes: ${rootPath}")
    for (child <- children) {
      val path = getChildPath(child)
      logger.info(s"[TASK START]: remove last cycle process: ${path}")
      zkClientx.deleteRecursive(path)
    }
  }

  /** 加载逻辑 */
  override def reload(): Unit = {} //TODO

  /** 销毁逻辑 */
  override protected  def destroyInternal(): Unit = {
    zkClientx.unsubscribeChildChanges(rootPath, this)
  }

  override def handleChildChange(parentPath: String, currentChilds: util.List[String]): Unit = reloadChildren(currentChilds.asScala)

  def canDelete(addChildren: List[String], deleteChildren: List[String], inputChild: mutable.Buffer[String]): Boolean = {
    if(deleteChildren.isDefinedAt(0)){
      childrenCache.slice(0, deleteChildren.size) == deleteChildren && //按照顺序前n项都删除才能删除
      (inputChild.toList ++ deleteChildren).sorted .slice(0, deleteChildren.size) == deleteChildren //且删除内容都小于本次输入内容，否则需要等待
    }else true

//    if(addChildren.isEmpty && deleteChildren.isDefinedAt(0)) {
//      val addAppends  = historyAddBuffer.toList.reverse
//      val addList = addAppends.slice(0, deleteChildren.size)
//      addList.find(!deleteChildren.contains(_)).isDefined//与最近添加的几个元素并不一样
//    }else true
  }

  /**
    * 针对于频发子列表变化任务（队列场景）
    * 由于网络原因和异步的原因，后变化的通知可能先到，出现不可预计问题
    * 场景一：写入有序，读取无序，导致重复加与删（3-6、1-3并行写入与删除，但是读取乱序）
    * signal dump: (1, 2, 3) -> (3, 4, 5) -> (2, 3, 4) -> (4, 5, 6)
    * ------------------------------------------------------
    * signal add:      _     ->  4, 5     ->     2     ->   5, 6
    * signal del:      _     ->  1, 2     ->     5     ->   2, 3
    * ------------------------------------------------------
    * expect add:      -     ->  4, 5     ->     _     ->   6
    * expect del:      _     ->  1, 2     ->     _     ->   3
    * ------------------------------------------------------
    * 出现问题：2, 5重复添加，2重复删除，5提前误删
    * 解决方案：a. 增加history记忆功能，将比对从本次与上次的比对调整为本次与前n次的比对,前n次中添加过不再添加；
    *          b. 增加当前有效childDump,删除后移除childDump保证不再重复删除，删除内容必须为childDump中head开始，
    *             另外所有删除内容必须小于列表(children)内容
    * 场景二：写入有序，读取无序，中途写入出现空列表,导致误删
    * 在最极端网络延迟情况，信号处理延迟极大，在children出现了为空且为空延迟下一次批次处理，则将下次未处误理解为处理结束
    * 比如：
    * 写入：batch1: [1,2,3,4,5], batch2:[], batch3:[6,7,8,9,10]
    * 接收：batch1: [1,2,3,4,5], batch3:[6,7,8,9,10], batch2:[]
    * 处理情况：  batch3[-1,-2,-3,-4,-5,+6,+7,+8,+9,+10], batch2:[-6,-7,-8,-9,-10],因而6,7,8,9,10正被处理就被误删了
    * 解决方案：如果出现batch为[]则只会有删除操作,直接丢弃本次删除处理，延迟到下次非空batch再进行删除操作
    * 场景三：写入无序，读取也无序，导致误删
    * 比如：
    * 写入：batch1: [28], batch2:[28, 29], batch3:[27, 28, 29]
    * 接收：batch1: [28], batch3:[27,28,29], batch2:[28,29]
    * 处理情况： batch1[+28],batch3[+27,+29], batch2:[-27]
    * 解决方案：
    *   由于这种情况与正常处理结束的删除即难区分，该方案并没有好的办法解决，这里提供如下几种思路：
    *   1.优化思路一：我们知道子元素每增或减都会获得一次通知，因此我们任务如果上一轮一次添加2个即以上，下一次的删除就可能是误删
    *   2.优化思路二：针对于Task不处理的Delete操作
    *   3.解决思路：误删的元素在添加时就添加了多个，因此查找是否误删的元素添加时候是多个添加，如果是手动查询一下列表，判断是否存在
    *   4.根治思路：Chlidren列表监控只监控添加，新加的子节点change监控监控删除处理，这样只有真实删除才进行删除
    *   由于系统已经上线不合适大改，考虑稳定性选择方案3.解决思路
    * @param children
    */
  def reloadChildren(children: mutable.Buffer[String]):Unit = {
    MDC.put(HamsterConst.splitTaskLogFileKey, prototypeKey)
    try {
      val uuid = UUID.randomUUID().toString
      val sortChildren = children.sorted
      val (deleteChildren, addChildren) = this.synchronized{
        val baseAddChildren = sortChildren.filterNot(childrenCache.contains(_)).toList
        val baseDelChildren = childrenCache.filterNot(sortChildren.contains(_))

        if(autoFilter) {
          //场景二增加判断分支,防止误删
          if(ignoreNil && sortChildren.isEmpty){
            logger.warn(s"[$uuid]ignore this deal => inputChildren :${sortChildren.mkString("[",", ","]")}, childrenCache:${childrenCache.mkString("[",", ","]")}")
            (List.empty[String], List.empty[String])
          }else {
            //获取添加列表
            val addChildren = baseAddChildren.filterNot(historyAddBuffer.contains(_))
            if(addChildren.length > 0) {//不能重复添加
              historyAddBuffer ++= addChildren
              historyAddCntQueue.add(addChildren.length)
              if(historyAddCntQueue.size > 15) {
                historyAddBuffer.drop(historyAddCntQueue.poll())
              }
            }

            //获取删除列表
            val deleteChildren = {
              val deleteChildren = baseDelChildren.sorted
//              val deleteChildren = baseDelChildren.filterNot(historyDelBuffer.contains(_)).sorted
              //场景一增加判断过滤逻辑
              if(canDelete(addChildren, deleteChildren, sortChildren)) {
                deleteChildren
              }else Nil
            }
            //场景三增加判断分支,防止误删,对于Selector节点进行场景删误删过滤
            val lastDeleteChildren = if(deleteChildren.nonEmpty && addChildren.isEmpty && ignoreNil){
              deleteChildren.filter(deleteElement => {
                val isMultiAdd = checkIsMultiAdd(deleteElement)
                if(isMultiAdd) {
                  val isRealDelete = !ZKClient.exists(getChildPath(deleteElement))
                  logger.warn(s"[$uuid]false delete may occur, check zk node [${getChildPath(deleteElement)}] delete status : " +
                    s"$isRealDelete , childrenCache:${childrenCache.mkString("[",", ","]")}!")
                  if(isRealDelete) true else false
                }else true
              })
            } else deleteChildren

            // 这段逻辑是多余逻辑，当第一次删除后，在一个事务内childrenCache就已经删除了processId
            // 由于baseDelChildren是基于childrenCache多余部分计算出来的，已经删除的processId并会不重复删除
            // 因此增加该判断逻辑没有意义
//            if(lastDeleteChildren.length > 0) {
//              historyDelBuffer ++= lastDeleteChildren
//              historyDelCntQueue.add(lastDeleteChildren.length)
//              if(historyDelCntQueue.size > 15) {
//                historyDelBuffer.drop(historyDelCntQueue.poll())
//              }
//            }

            childrenCache = ((childrenCache ::: addChildren).toSet -- lastDeleteChildren.toSet).toList.sorted
            (lastDeleteChildren, addChildren)
          }
        }else {
          childrenCache = sortChildren.toList
          (baseDelChildren, baseAddChildren)
        }
      }
      childrenChange(addChildren, deleteChildren, childrenCache, uuid)

    }finally MDC.remove(HamsterConst.splitFetcherLogFileKey)
  }

  def checkIsMultiAdd(element: String): Boolean = {
    val deleteIndexReverse = historyAddBuffer.size - historyAddBuffer.indexOf(element)
    val pushIterator = historyAddCntQueue.asScala.toArray.reverseIterator
    var tmpCount = 0
    while(pushIterator.hasNext){
      breakable{
        val numbers = pushIterator.next()
        tmpCount += numbers
        if(tmpCount >= deleteIndexReverse){
          return if(numbers > 1) true else false
        }
      }
    }
    return false
  }

  override def addListener(listener: ZKChildrenListener): Unit = {
    super.addListener(listener)
    if (running) {
      listener.childrenChange(childrenCache, all = childrenCache)
    }
  }

  override def childrenChange(add: List[String], del: List[String], all: List[String], versionNo: String = ""): Unit =
    notifyListener(new ListenerExecutor[ZKChildrenListener] {
      override def execute(listener: ZKChildrenListener): Unit = listener.childrenChange(add, del, all, versionNo)
    })
}
