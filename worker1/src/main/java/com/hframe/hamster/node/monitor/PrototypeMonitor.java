package com.hframe.hamster.node.monitor;

import com.alibaba.otter.canal.common.utils.BooleanMutex;
import com.hframe.hamster.common.zkclient.ZkClientx;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.zk.ZooKeeperClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class PrototypeMonitor<T extends PrototypeKey> implements Monitor {
    private static final Logger logger = LoggerFactory.getLogger(PrototypeMonitor.class);
    protected boolean running = false;

    protected T prototypeKey;
    protected String taskStory;

    protected ZkClientx zkClientx = ZooKeeperClient.getInstance();

    protected BooleanMutex mutex = new BooleanMutex(false);

    public PrototypeMonitor(String taskStory, T prototypeKey) {
        this.taskStory = taskStory;
        this.prototypeKey = prototypeKey;
    }

    @Override
    public void start() {
        if(!running) {
            synchronized (this) {
                if(!running) {
                    running = true;
                    startInternal();
                }
            }
        }
    }

    public abstract void startInternal();

    @Override
    public void destroy() {
        destroyInternal();
        running = false;
    }

    public abstract void destroyInternal();
}
