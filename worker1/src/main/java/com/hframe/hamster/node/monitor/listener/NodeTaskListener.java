package com.hframe.hamster.node.monitor.listener;

import com.hframe.hamster.node.monitor.bean.NodeTask;

import java.util.List;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public interface NodeTaskListener {

    public boolean processChanges(List<NodeTask> incTasks);

}
