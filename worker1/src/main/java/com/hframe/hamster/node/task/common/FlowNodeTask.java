package com.hframe.hamster.node.task.common;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public interface FlowNodeTask extends FlowTask {

    public Class<? extends FlowTask> prevTask();

    public Class<? extends FlowTask> nextTask();
}
