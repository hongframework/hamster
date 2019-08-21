package com.hframe.hamster.node.task.subscribe;

import com.hframe.ext.bean.MQConfig;
import com.hframe.ext.service.CfgSubscribeService;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.AbstractLoad2MQTask;
import com.hframe.hamster.node.task.common.FlowEndNodeTask;
import com.hframe.hamster.node.task.common.FlowTask;

import java.util.List;


/**
 * Created by zhangquanhong on 2016/9/28.
 */
public class LoadTask extends AbstractLoad2MQTask implements FlowEndNodeTask {

    public LoadTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
    }

    @Override
    protected List<MQConfig> getLoadConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception {
        return HamsterContextInitializer.getBean(
                CfgSubscribeService.class).getLoadConfigs(Long.valueOf(prototypeKey.value()));
    }

    @Override
    public Class<? extends FlowTask> prevTask() {
        return TransformTask.class;
    }
}
