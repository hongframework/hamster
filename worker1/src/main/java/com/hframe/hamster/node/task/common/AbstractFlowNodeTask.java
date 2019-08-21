package com.hframe.hamster.node.task.common;

import com.hframe.hamster.node.monitor.bean.FlowKey;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class AbstractFlowNodeTask extends AbstractTask implements FlowTask {
   protected FlowKey flowKey;

    public AbstractFlowNodeTask(FlowKey flowKey) {
        this(flowKey, TaskOrderMode.PROCESS);
    }

    public AbstractFlowNodeTask(FlowKey flowKey,TaskOrderMode taskOrderMode) {
        super(taskOrderMode);
        this.flowKey = flowKey;
    }

    @Override
    public FlowKey getFlowKey() {
        return flowKey;
    }
}
