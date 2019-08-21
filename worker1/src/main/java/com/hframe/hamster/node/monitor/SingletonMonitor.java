package com.hframe.hamster.node.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class SingletonMonitor implements Monitor{
    private static final Logger logger = LoggerFactory.getLogger(SingletonMonitor.class);
    protected boolean running = false;

    @Override
    public void start() {
        startInternal();
        running = true;
    }

    public abstract void startInternal();

    @Override
    public void destroy() {
        destroyInternal();
        running = false;
    }

    public abstract void destroyInternal();
}
