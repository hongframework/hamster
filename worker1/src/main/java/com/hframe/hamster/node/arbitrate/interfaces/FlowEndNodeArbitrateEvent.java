package com.hframe.hamster.node.arbitrate.interfaces;

import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.SharableEventData;

/**
 * 调度接口
 * Created by zhangquanhong on 2016/9/28.
 */
public interface FlowEndNodeArbitrateEvent extends FlowTaskNodeArbitrateEvent{

    public void ask(SharableEventData data);

    SharableEventData awaitTermin(PrototypeKey prototypeKey) throws InterruptedException;
}
