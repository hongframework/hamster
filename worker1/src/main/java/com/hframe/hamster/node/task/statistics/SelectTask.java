package com.hframe.hamster.node.task.statistics;

import com.hframe.ext.bean.CanalConfig;
import com.hframe.ext.service.CfgStatisticsService;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.AbstractSelectTask;
import com.hframe.hamster.node.task.common.FlowTask;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public class SelectTask extends AbstractSelectTask{

    public SelectTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
    }

    @Override
    public CanalConfig getCanalConfig(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception {
        return  HamsterContextInitializer.getBean(
                CfgStatisticsService.class).getCanalConfig(Long.valueOf(prototypeKey.value()));
    }

    @Override
    public Class<? extends FlowTask> nextTask() {
        return ExtractTask.class;
    }
}
