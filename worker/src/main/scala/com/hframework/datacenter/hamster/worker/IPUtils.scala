package com.hframework.datacenter.hamster.worker

import java.io.{BufferedReader, File, FileInputStream, InputStreamReader}

import com.hframework.utils.scala.Logging

import scala.collection.mutable

object IPUtils  extends Logging{

  var ipMetaOp: Option[Array[Array[Array[AnyRef]]]] = None

  val parseUnknown = Array("unknown","unknown","unknown","unknown")

  def parseCity(ip: String): Array[String] = try{
    if(ipMetaOp.isEmpty){
      this.synchronized{
        if(ipMetaOp.isEmpty){
          reloadIpMetas
        }
      }
    }
    val  ipParts = split(ip)
    val metas = ipMetaOp.get(ipParts._1)(ipParts._2)(ipParts._3)
    if(metas != null) {
      if(metas.isInstanceOf[List[(Int, Int, String, String, String, String)]]){
        val metaOp = metas.asInstanceOf[List[(Int, Int, String, String, String, String)]].find(meta => meta._1 <= ipParts._4 && ipParts._4 <= meta._2)
        if(metaOp.isEmpty){
          Array(metaOp.get._3, metaOp.get._4, metaOp.get._5, metaOp.get._6)
        }else parseUnknown
      }else {
        val meta = metas.asInstanceOf[(Int, Int, String, String, String, String)]
        if(meta._1 <= ipParts._4 && ipParts._4 <= meta._2) {
          Array(meta._3, meta._4, meta._5, meta._6)
        }else parseUnknown
      }
    }else parseUnknown
  } catch {
    case _ => parseUnknown
  }

  def reloadIpMetas(): Unit ={
    val t = System.currentTimeMillis()
    logger.info("reload ip meta ..")
    val metas : Array[Array[Array[AnyRef]]] = Array.ofDim(256,256,256)

    val path = getClass.getClassLoader.getResource("ips.rec").getPath
    logger.info(s"reload ip meta file $path!")
    println(path)
    val br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"))
    var str = br.readLine
    while ({str != null}) {
      val  rowConfig = str.split("\\|")
      val startIp = split(rowConfig(0))
      val endIp = split(rowConfig(1))
      addAllItems(startIp, endIp, metas, rowConfig)
      str = br.readLine
    }
    ipMetaOp = Some(metas)
    logger.info(s"reload ip meta ok! (cost ${(System.currentTimeMillis() - t)/1000}s)")
  }

  def addAllItems(startIp: (Int, Int, Int, Int), endIp: (Int, Int, Int, Int),
              metas: Array[Array[Array[AnyRef]]],
              rowConfig: Array[String]) = {
    val startNum: (Long, Int) = (startIp._1 * 256 * 256 + startIp._2 * 256 + startIp._3, startIp._4)
    val endNum:  (Long, Int)  = (endIp._1 * 256 * 256 + endIp._2 * 256 + endIp._3, endIp._4)
    var tmpNum: (Long, Int) = startNum
    while(tmpNum._1 < endNum._1 || (tmpNum._1 == endNum._1 &&  tmpNum._2 <= endNum._2)) {
      val start = tmpNum
      val end = {
        val tmp = (tmpNum._1, 255)
        if(tmp._1 < endNum._1 || (tmp._1 == endNum._1 &&  tmp._2 <= endNum._2)){
          tmp
        }else endNum
      }

      val ipParts: (Int, Int, Int) = ((start._1 / (256 * 256)).toInt, (start._1 / 256).toInt % 256, (start._1 % 256).toInt)

//      if(ipParts._1 == 116 && ipParts._2 == 114 && ipParts._3 == 18){
//        println(tmpNum, ipParts)
//      }

      val entry = (start._2, end._2, rowConfig(2), rowConfig(4), rowConfig(5), rowConfig(6))
      if(metas(ipParts._1)(ipParts._2)(ipParts._3) == null) {
        metas(ipParts._1)(ipParts._2)(ipParts._3) = entry
      }else {
        val oldEntry = metas(ipParts._1)(ipParts._2)(ipParts._3)
        if(oldEntry.isInstanceOf[List[(Int, Int, String, String, String, String)]]){
          metas(ipParts._1)(ipParts._2)(ipParts._3) = entry :: metas(ipParts._1)(ipParts._2)(ipParts._3).asInstanceOf[List[(Int, Int, String, String, String, String)]]
        }else {
          metas(ipParts._1)(ipParts._2)(ipParts._3) = List(metas(ipParts._1)(ipParts._2)(ipParts._3), entry)
        }

      }
//      println(ipParts, start._2, end._2, rowConfig)
      if(end._2 == 255) {
        tmpNum = (end._1 + 1, 0)
      }else {
        tmpNum = (end._1, end._2 + 1)
      }
    }
  }


  def split(ip: String):(Int, Int, Int, Int) = {
    val ipParts = ip.split("\\.").map(_.toInt)
    (ipParts(0),ipParts(1),ipParts(2),ipParts(3))
  }

}
