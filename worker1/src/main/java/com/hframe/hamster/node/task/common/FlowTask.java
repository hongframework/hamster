package com.hframe.hamster.node.task.common;

import com.hframe.hamster.node.monitor.bean.FlowKey;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public interface FlowTask extends Task{

    public FlowKey getFlowKey();

    public void process(TaskData taskData) throws Exception;
}
