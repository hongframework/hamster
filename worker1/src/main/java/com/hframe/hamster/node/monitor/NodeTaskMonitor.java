package com.hframe.hamster.node.monitor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hframe.hamster.common.zkclient.NamedThreadFactory;
import com.hframe.hamster.node.HamsterConfig;
import com.hframe.hamster.node.config.ConfigClient;
import com.hframe.hamster.node.monitor.bean.NodeTask;
import com.hframe.hamster.node.monitor.listener.NodeTaskListener;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
@Service
public class NodeTaskMonitor extends SingletonMonitor implements Monitor{
    private static final Logger logger = LoggerFactory.getLogger(NodeTaskMonitor.class);


    private List<NodeTask> allTasks = Collections.synchronizedList(new ArrayList<NodeTask>());
    private List<NodeTaskListener> listeners = Collections.synchronizedList(new ArrayList<NodeTaskListener>());

    private ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("node-task-monitor-thread"), new ThreadPoolExecutor.CallerRunsPolicy());
    //定时刷新线程执行时间（单位s)
    private static final long DEFAULT_DELAY_TIME = Long.valueOf(HamsterConfig.getInstance().getTaskRefreshPeriod());
    @Override
    public void startInternal() {
        logger.debug("start internal...");
//        JobRegistry.start();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.debug(" reload node task info begin !");
                reload();
                logger.debug(" reload node task info finish !");
            }
        }, DEFAULT_DELAY_TIME, DEFAULT_DELAY_TIME, TimeUnit.SECONDS);
        logger.info("start success!");
    }

    @Override
    public void destroyInternal() {
        logger.info("destroy internal...");
        if(allTasks != null && allTasks.size() > 0) {
            List<NodeTask> incTasks = Lists.newArrayList(allTasks);
            for (NodeTask allTask : incTasks) {
                allTask.setTaskEvent(NodeTask.TaskEvent.DELETE);
            }
            if(!notifyListener(incTasks)) {
                logger.warn("notify listener error!");
                return;
            }
            allTasks.clear();
        }

        logger.info("destroy success!");
    }

    @Override
    public void reload() {
        Long nid = ConfigClient.getCurrentNid();
        List<NodeTask> currentTasks = null;
        try {
            currentTasks = ConfigClient.getNodeTaskList(nid);
        } catch (Exception e) {
            logger.error("get node task list failed , {}", ExceptionUtils.getFullStackTrace(e));
            return;
        }
        logger.debug("current tasks => {}", JSON.toJSONString(currentTasks));
        List<NodeTask> incTasks = getNodeTaskChanges(currentTasks);
        logger.info("inc tasks => {}", JSON.toJSONString(incTasks));
        if(!notifyListener(incTasks)) {
            logger.warn("notify listener error!");
            return;
        }
        allTasks = currentTasks;
    }

    public void addListener(NodeTaskListener listener) {
        listeners.add(listener);
        if(running) {
            listener.processChanges(allTasks);
        }
    }

    private boolean notifyListener(List<NodeTask> incTasks) {
        boolean result = true;
        if(incTasks != null && incTasks.size() != 0) {
            for (NodeTaskListener listener : listeners) {
                result &= listener.processChanges(incTasks);
            }
        }
        return result;
    }

    private List<NodeTask> getNodeTaskChanges(List<NodeTask> currentTasks) {
        List<NodeTask> addTasks = minus(currentTasks, allTasks);
        List<NodeTask> deleteTasks = minus(allTasks, currentTasks);
        for (NodeTask deleteTask : deleteTasks) {
            deleteTask.setTaskEvent(NodeTask.TaskEvent.DELETE);
        }

        addTasks.addAll(deleteTasks);
        return addTasks;
    }

    private List<NodeTask> minus(List<NodeTask> currentTasks, List<NodeTask> allTasks) {

        List<NodeTask> retainLists = Lists.newArrayList(currentTasks);
        retainLists.removeAll(allTasks);
        return retainLists;
    }


}
