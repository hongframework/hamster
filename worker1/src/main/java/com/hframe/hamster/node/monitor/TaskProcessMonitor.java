package com.hframe.hamster.node.monitor;

import com.google.common.collect.Lists;
import com.hframe.hamster.common.zkclient.AsyncWatcher;
import com.hframe.hamster.common.zkclient.ZooKeeperx;
import com.hframe.hamster.node.HamsterConst;
import com.hframe.hamster.node.arbitrate.ArbitratorFactory;
import com.hframe.hamster.node.monitor.bean.ACK;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.ZkContextAware;
import com.hframe.hamster.node.monitor.listener.TaskProcessListener;
import com.hframe.hamster.node.monitor.listener.TerminListener;
import com.hframe.hamster.node.zk.ZKPathUtils;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkConnection;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * 任务处理进展监控
 * Created by zhangquanhong on 2016/9/27.
 */
public class TaskProcessMonitor extends PrototypeMonitor implements Monitor, ZkContextAware<String>, TerminListener {

    private static final Logger logger = LoggerFactory.getLogger(TaskProcessMonitor.class);

    /**proccess列表**/
    private Map<Long, List<String>> processCache = new HashMap<Long, List<String>>();

    private Set<Long> ackProcessIds = new HashSet<>();
    private List<Long> terminCache = new ArrayList<>();

    /**执行listener监听程序*/
    private ExecutorService executorService ;
    private List<TaskProcessListener> listeners = Collections.synchronizedList(new ArrayList<TaskProcessListener>());

    private TaskRunningMonitor taskRunningMonitor;
    private TerminMonitor terminMonitor;


    private IZkChildListener childChangeListener = new IZkChildListener() {
        @Override
        public void handleChildChange(String parentPath, List<String> currentProcesses) throws Exception {
            reloadProcess(currentProcesses);
        }
    };

    public TaskProcessMonitor(String taskStory, PrototypeKey prototypeKey) {
        super(taskStory, prototypeKey);
        taskRunningMonitor = new TaskRunningMonitor(taskStory,prototypeKey);
        taskRunningMonitor.setTaskProcessMonitor(this);
        terminMonitor =  ArbitratorFactory.getInstance(taskStory, prototypeKey, Object.class, TerminMonitor.class);
        terminMonitor.addListener(this);
    }


    @Override
    public void startInternal() {
        List<String> currentProcesses = zkClientx.subscribeChildChanges(getZkPath(), childChangeListener);
        reloadProcess(currentProcesses);
        terminMonitor.start();
        taskRunningMonitor.start();

    }

    @Override
    public void destroyInternal() {
        zkClientx.unsubscribeChildChanges(getZkPath(), childChangeListener);
        taskRunningMonitor.destroy();
        terminMonitor.destroy();
    }

    public void addListener(TaskProcessListener listener) {
        this.listeners.add(listener);
        if(running) {
            ArrayList<Long> processIds = Lists.newArrayList(processCache.keySet());
            Collections.sort(processIds);
            listener.processChange(processIds);
            //由于任务还没有设置cur，prev taskName，导致通知后不识别是否需要自己处理的节点，因此这里不主动通知
//            for (Long processId : processCache.keySet()) {
//                listener.processChange(processId, processCache.get(processId));
//            }
        }
    }

    public void removeListener(TaskProcessListener listener) {
        this.listeners.add(listener);
    }

    private void reloadProcess(List<String> currentProcesses) {
        try {
            MDC.put(HamsterConst.splitTaskLogFileKey, taskStory + "_" + prototypeKey.value());
            List<Long> processIds = CollectionUtils.fetch(currentProcesses, new Fetcher<String, Long>() {
                @Override
                public Long fetch(String s) {
                    return Long.valueOf(s);
                }
            });
            Collections.sort(processIds);
            List<Long> newProcessIds = Lists.newArrayList(processIds);
            synchronized (this) {

                newProcessIds.removeAll(processCache.keySet());
                List<Long> deletedProcessIds = Lists.newArrayList(processCache.keySet());
                deletedProcessIds.removeAll(processIds);

                for (Long needDeleteProcessId : deletedProcessIds) {
                    processCache.remove(needDeleteProcessId);
                }
            }
            for (Long newProcessId : newProcessIds) {
                logger.info("add watch for process [{}], current thread is [{}]{} !", newProcessId, Thread.currentThread().getId(), Thread.currentThread().getName());
                addWatchProcess(newProcessId);
            }

            ArrayList<Long> tmpProcessIds = Lists.newArrayList(processCache.keySet());
            Collections.sort(tmpProcessIds);
            fireProcessChange(tmpProcessIds);
        }finally {
            MDC.remove(HamsterConst.splitTaskLogFileKey);
        }
    }

    private void fireProcessChange(final ArrayList<Long> processIds) {
        for (final TaskProcessListener listener : listeners) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try{
                        MDC.put(HamsterConst.splitTaskLogFileKey, taskStory + "_" + prototypeKey.value());
                        listener.processChange(processIds);
                    }finally {
                        MDC.remove(HamsterConst.splitTaskLogFileKey);
                    }
                }
            });
        }
    }

    /**
     * 由于watch只会执行一次，因为在执行时循环添加watch事件，同时将watch的变化值通知对应listeners
     * @param processId
     */
    private void addWatchProcess(final Long processId) {
        try {
            String processPath = getZkPath(processId);
            IZkConnection connection = zkClientx.getConnection();
            ZooKeeper zookeeper = ((ZooKeeperx) connection).getZookeeper();
            //需要AsyncWatcher并行触发watch事件，否则zkClientx retryUntilConnected会报错
            //需要判断路径是否存在，因为在删除process时是迭代删除，可能在一边添加时间时，另外一边已经刚好删除了父节点
            List<String> newStages = zookeeper.getChildren(processPath, new AsyncWatcher() {
                    @Override
                    public void asyncProcess(WatchedEvent event) {
                        try{
                            MDC.put(HamsterConst.splitTaskLogFileKey, taskStory + "_" + prototypeKey.value());
                            if(event.getType() == Watcher.Event.EventType.NodeDeleted) {
                                logger.info("miss watch for process [{}],  because current node has deleted ! ", processId);
                                processDeleted(processId);
                            }else if(event.getType() == Watcher.Event.EventType.NodeCreated
                                    || event.getType() == Watcher.Event.EventType.NodeDataChanged
                                    || event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                                logger.info("add watch for process [{}],  because current & child node has changed ! ", processId);
                                addWatchProcess(processId);
                            }
                        }finally {
                            MDC.remove(HamsterConst.splitTaskLogFileKey);
                        }
                    }
            });

            if(terminCache.contains(processId)) {
                return ;
            }

            if(processCache.get(processId) != null && processCache.get(processId).size() >= newStages.size()) {
                //表明处理阶段没有新加，反而减少，说明这种情况已经再删除processId，那么不进行阶段变更通知，由最终的阶段删除执行
                return ;
            }

            /******如下代码在下次升级时可以删除，原因为未了兼容处理中的老数据，新数据不存在该问题*******/
            //如果存在ack节点，表明process处理完成，就差删除zk对应process节点，同时ack节点数据不再执行任务节点通知
            if(!ackProcessIds.contains(processId) && newStages.contains(ACK.class.getSimpleName())) {
                synchronized (this) {
                    if(!ackProcessIds.contains(processId) && newStages.contains(ACK.class.getSimpleName())) {
                        logger.info("delete zk process cascade begin,{}", processId);
                        ackProcessIds.add(processId);
                        zkClientx.deleteRecursive(processPath);
                        logger.info("delete zk process cascade finish,{}", processId);
                    }
                }
            }

            if(ackProcessIds.contains(processId)) {
                return;
            }
            /******如上代码在下次升级时可以删除，原因为未了兼容处理中的老数据，新数据不存在该问题*******/

            if(processCache.get(processId) == null || !processCache.get(processId).equals(newStages)) {
                fireProcessChange(processId, newStages);
            }
        } catch (KeeperException.NoNodeException e) {
            logger.warn("在循环删除process的task node节点时,watch后处理是异步的，[{}]可忽略！", e.getMessage());
            processDeleted(processId);
        } catch (KeeperException e) {
            logger.warn("unkown error！=> {}", ExceptionUtils.getFullStackTrace(e));
            try {
                Thread.sleep(1000*5L); //zookeeper 连接被重置
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            addWatchProcess(processId);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fireProcessChange(final Long processId, List<String> newStages) {
        String processPath = getZkPath(processId);
         List<String> stages = null;
        try {
            stages = zkClientx.getChildren(processPath);
        }catch (ZkNoNodeException e) {
                logger.warn("process {} watched stages was {}, but get stages real time failed [{}]！", processId, newStages, e.getMessage());
                return ;
        }
        for (final TaskProcessListener listener : listeners) {
            final List<String> finalStages = stages;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        MDC.put(HamsterConst.splitTaskLogFileKey, taskStory + "_" + prototypeKey.value());
                        listener.processChange(processId, finalStages);
                    } catch (Exception e) {
                        logger.error("fire process change error => {}", ExceptionUtils.getFullStackTrace(e));
                    }finally {
                        MDC.remove(HamsterConst.splitTaskLogFileKey);
                    }
                }
            });
        }
        processCache.put(processId, stages);
    }

    private void processDeleted(final Long processId) {
        logger.info("delete process from queue [{}]! ", processId);
        for (final TaskProcessListener listener : listeners) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    listener.processDeleted(processId);
                }
            });
        }
        processCache.remove(processId);
    }

    @Override
    public void reload() {

    }

    @Override
    public String getZkPath(Object... processId) {
        if(processId != null && processId.length > 0) {
            return ZKPathUtils.getProcessPath(taskStory, prototypeKey.value(), (Long) processId[0]);
        }else {
            return ZKPathUtils.getProcessRootPath(taskStory, prototypeKey.value());
        }
    }

    @Override
    public String getZkData() {
        return null;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Map<Long, List<String>> getProcessCache() {
        Map<Long, List<String>> processCacheClone = new HashMap<>();
        synchronized (processCache) {
            for (Long aLong : processCache.keySet()) {
                processCacheClone.put(aLong, new ArrayList<String>(processCache.get(aLong)));
            }
        }
        return processCacheClone;
    }

    @Override
    public void processChange(List<Long> processIds) {
        terminCache = processIds;
    }
}
