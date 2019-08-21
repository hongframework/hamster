package com.hframe.hamster.node.monitor.bean;

/**
 * Created by zhangquanhong on 2016/9/26.
 */
public interface ZkContextAware<T> {

    public String getZkPath(Object... args);

    public T getZkData();
}
