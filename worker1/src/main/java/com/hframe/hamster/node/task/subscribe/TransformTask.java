package com.hframe.hamster.node.task.subscribe;

import com.hframe.ext.bean.TransformConfig;
import com.hframe.ext.service.CfgSubscribeService;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.AbstractTransformTask;
import com.hframe.hamster.node.task.common.FlowNodeTask;
import com.hframe.hamster.node.task.common.FlowTask;

import java.util.List;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public class TransformTask extends AbstractTransformTask implements FlowNodeTask {


    public TransformTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
    }

    @Override
    protected List<TransformConfig> getTransformConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception {
        return HamsterContextInitializer.getBean(
                CfgSubscribeService.class).getTransformConfigs(Long.valueOf(prototypeKey.value()));
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
