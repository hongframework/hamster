package com.hframe.hamster.node.task.common;

import com.hframe.hamster.node.cannal.Message;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public interface FlowEndNodeTask extends FlowTask {
    public boolean ask(Message newMessage);

    public Class<? extends FlowTask> prevTask();


}
