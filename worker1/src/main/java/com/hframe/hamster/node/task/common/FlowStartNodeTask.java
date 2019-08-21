package com.hframe.hamster.node.task.common;

import com.hframe.hamster.node.cannal.Message;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public interface FlowStartNodeTask extends FlowTask {

    public Class<? extends FlowTask> nextTask();

    public Message select() throws InterruptedException;

    public boolean rollback(Message newMessage);
}
