package com.hframe.hamster.node.task.statistics;

import com.hframe.ext.bean.StatisticsConfig;
import com.hframe.ext.service.CfgStatisticsService;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.AbstractStatisticsTask;
import com.hframe.hamster.node.task.common.FlowTask;

import java.util.List;

/**
 * Created by zhangquanhong on 2017/3/17.
 */
public class StatisticsTask extends AbstractStatisticsTask {
    public StatisticsTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
    }

    @Override
    protected List<StatisticsConfig> getStatisticsConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception {
        return HamsterContextInitializer.getBean(
                CfgStatisticsService.class).getStatisticsConfigs(Long.valueOf(prototypeKey.value()));
    }

    @Override
    public Class<? extends FlowTask> prevTask() {
        return ExtractTask.class;
    }

    @Override
    public Class<? extends FlowTask> nextTask() {
        return LoadTask.class;
    }
}
