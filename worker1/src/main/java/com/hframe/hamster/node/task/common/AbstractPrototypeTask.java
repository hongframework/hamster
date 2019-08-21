package com.hframe.hamster.node.task.common;

import com.hframe.hamster.node.monitor.bean.PrototypeKey;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class AbstractPrototypeTask extends AbstractTask implements PrototypeTask {
   protected PrototypeKey prototypeKey;

    public AbstractPrototypeTask(PrototypeKey prototypeKey) {
        this.prototypeKey = prototypeKey;
//        prototypeKey.setTaskKey(this.getClass().getName());
    }
}
