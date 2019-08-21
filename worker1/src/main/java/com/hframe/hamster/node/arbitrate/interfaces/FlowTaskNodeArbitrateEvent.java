package com.hframe.hamster.node.arbitrate.interfaces;

import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.SharableEventData;
import com.hframe.hamster.node.task.common.FlowTask;

/**
 * 调度接口
 * Created by zhangquanhong on 2016/9/28.
 */
public interface FlowTaskNodeArbitrateEvent extends ArbitrateEvent {

    public SharableEventData await(PrototypeKey prototypeKey) throws InterruptedException;

    public void single(SharableEventData data);

    public void setPrevTask(Class<? extends FlowTask> prevTask);

    public void setCurTask(Class<? extends FlowTask> curTask);

    public void setNextTask(Class<? extends FlowTask> nextTask);



}
