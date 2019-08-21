package com.hframe.hamster.node.task.common;

import com.hframe.hamster.node.cannal.Message;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public interface CronTask extends Task{

    public String cronExpression();


    public Message fetch() throws Exception;




    public void ack(Message newMessage) throws InterruptedException ;

}
