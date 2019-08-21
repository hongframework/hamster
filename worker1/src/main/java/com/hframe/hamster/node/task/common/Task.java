package com.hframe.hamster.node.task.common;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public interface Task {
    /**为线程启动方法*/
    public void start();

    /**暂停*/
    public void pause();
    /**恢复*/
    public void recovery();
    /**关闭*/
    public void shutdown();


    public TaskOrderMode getOrderMode();

    public String name();

}
