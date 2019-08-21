package com.hframe.hamster.node.arbitrate.impl;

import com.hframe.hamster.common.zkclient.ZkClientx;
import com.hframe.hamster.node.arbitrate.interfaces.FlowTaskNodeArbitrateEvent;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.common.FlowTask;
import com.hframe.hamster.node.zk.ZooKeeperClient;

/**
 * Created by zhangquanhong on 2016/10/3.
 */
public abstract class AbstractFlowTaskNodeArbitrateEvent implements FlowTaskNodeArbitrateEvent {
    protected ZkClientx zkClientx = ZooKeeperClient.getInstance();

    protected Class<? extends FlowTask> prevTask;
    protected Class<? extends FlowTask> curTask;
    protected Class<? extends FlowTask> nextTask;


    protected String taskStory;
    protected PrototypeKey taskInstance;
    protected Class taskNodeClass;

    public AbstractFlowTaskNodeArbitrateEvent(String taskStory, PrototypeKey taskInstance, Class taskNodeClass) {
        this.taskStory = taskStory;
        this.taskInstance = taskInstance;
        this.taskNodeClass = taskNodeClass;
    }


    public void setPrevTask(Class<? extends FlowTask> prevTask) {
        this.prevTask = prevTask;
    }

    public void setCurTask(Class<? extends FlowTask> curTask) {
        this.curTask = curTask;
    }

    public void setNextTask(Class<? extends FlowTask> nextTask){
        this.nextTask = nextTask;
    }

}
