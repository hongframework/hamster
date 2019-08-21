package com.hframe.hamster.node.task.statistics;

import com.hframe.ext.bean.HBaseConfig;
import com.hframe.ext.service.CfgStatisticsService;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.AbstractReduceTask;
import com.hframe.hamster.node.task.common.FlowNodeTask;
import com.hframe.hamster.node.task.common.FlowTask;

import java.util.List;

/**
 * Created by zhangquanhong on 2017/3/22.
 */
public class HourReduceTask extends AbstractReduceTask implements FlowNodeTask {

    public HourReduceTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
    }

    @Override
    protected List<HBaseConfig> getLoadConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception {
        return HamsterContextInitializer.getBean(
                CfgStatisticsService.class).getSystemHBaseConfig();
        //HamsterContextInitializer.getBean(
        //      CfgStatisticsService.class).getLoadConfigs(Long.valueOf(prototypeKey.value()));


    }

    @Override
    protected String tableName() throws Exception {
        return "hamster_statistics_hour";
    }

    @Override
    public Class<? extends FlowTask> prevTask() {
        return MinuteReduceTask.class;
    }

    @Override
    public Class<? extends FlowTask> nextTask() {
        return DateReduceTask.class;
    }
}
