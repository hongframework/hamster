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
public class DateReduceTask extends AbstractReduceTask implements FlowNodeTask {

    public DateReduceTask(FlowKey flowKey, PrototypeKey prototypeKey) {
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
        return "hamster_statistics_date";
    }

    @Override
    public Class<? extends FlowTask> prevTask() {
        return HourReduceTask.class;
    }

    @Override
    public Class<? extends FlowTask> nextTask() {
        return MonthReduceTask.class;
    }

}
