package com.hframe.hamster.node.task.common;

import com.hframe.hamster.common.zkclient.ZkClientx;
import com.hframe.hamster.node.zk.ZooKeeperClient;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class AbstractTask extends Thread implements Task {
    private  final Logger logger = LoggerFactory.getLogger(AbstractTask.class);
    //任务状态
    protected TaskState taskState = TaskState.RUNNING;

    protected TaskOrderMode taskOrderMode = TaskOrderMode.NONE;

    protected ZkClientx zkClientx = ZooKeeperClient.getInstance();

    @Resource
    protected ExecutorService executorService;

    public AbstractTask(){
        this(TaskOrderMode.PROCESS);
    }

    public AbstractTask(TaskOrderMode taskOrderMode){
        this.taskOrderMode = taskOrderMode;
        setName(name());
    }

    @Override
    public void run() {
        logger.info("task thread start !");
        taskState = TaskState.RUNNING;
        startInternal();
    }

    protected abstract void startInternal();

    @Override
    public void shutdown() {
        taskState = TaskState.CLOSED;
        try{
            shutdownInternal();
        }catch (Exception e) {
            logger.error("task thread shutdown error : {}", ExceptionUtils.getFullStackTrace(e));
        }


        this.interrupt();
        logger.info("task thread shutdown !");
    }

    protected abstract void shutdownInternal();

    @Override
    public void pause() {
        taskState = TaskState.SUSPEND;
    }

    @Override
    public void recovery() {
        taskState = TaskState.RUNNING;
    }


    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public TaskOrderMode getOrderMode() {
        return taskOrderMode;
    }

    public enum TaskState{
        /**运行中*/
        RUNNING,
        /**暂停中*/
        SUSPEND,
        /**异常阻塞*/
        ERROR,
        /**已关闭*/
        CLOSED;

        public boolean isRunning(){
            return this == RUNNING;
        }

        public boolean isSuspend(){
            return this == SUSPEND;
        }

        public boolean isClosed(){
            return this == CLOSED;
        }

        public boolean isError(){
            return this == ERROR;
        }
    }
}
