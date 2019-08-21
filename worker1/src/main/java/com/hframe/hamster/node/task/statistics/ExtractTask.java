package com.hframe.hamster.node.task.statistics;

import com.hframe.ext.bean.ExtractConfig;
import com.hframe.ext.service.CfgStatisticsService;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.AbstractExtractTask;
import com.hframe.hamster.node.task.common.FlowNodeTask;
import com.hframe.hamster.node.task.common.FlowTask;

import java.util.List;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public class ExtractTask extends AbstractExtractTask implements FlowNodeTask {

    public ExtractTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
    }

    @Override
    protected List<ExtractConfig> getExtractConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception {
        return HamsterContextInitializer.getBean(
                CfgStatisticsService.class).getExtractConfigs(Long.valueOf(prototypeKey.value()));
    }

    @Override
    public Class<? extends FlowTask> prevTask() {
        return SelectTask.class;
    }

    @Override
    public Class<? extends FlowTask> nextTask() {
        return StatisticsTask.class;
    }
}
