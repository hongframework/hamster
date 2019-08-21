package com.hframe.hamster.node.cannal;

/**
 * Created by zhangquanhong on 2016/9/29.
 */
public interface IFetcher {

    public void start();

    public boolean isStart();

    public void stop();

    public Message fetch() throws InterruptedException;

    public void rollback(Long messageId);

    public void ack(Long messageId) throws InterruptedException;

    public void rollback();

}
