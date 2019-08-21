package com.hframe.hamster.node.arbitrate.impl;

import com.hframe.hamster.node.arbitrate.ArbitratorFactory;
import com.hframe.hamster.node.arbitrate.TaskProcessArbitrateEvent;
import com.hframe.hamster.node.arbitrate.interfaces.FlowMiddleNodeArbitrateEvent;
import com.hframe.hamster.node.config.ConfigClient;
import com.hframe.hamster.node.monitor.PermitMonitor;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.SharableEventData;
import com.hframe.hamster.node.monitor.listener.FlowMiddleNodeProcessListener;
import com.hframe.hamster.node.task.common.FlowTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 调度接口
 * Created by zhangquanhong on 2016/9/28.
 */
public class FlowMiddleNodeArbitrateEventImpl extends AbstractFlowTaskNodeArbitrateEvent implements FlowMiddleNodeArbitrateEvent {
    private static final Logger logger = LoggerFactory.getLogger(FlowMiddleNodeArbitrateEventImpl.class);

    private FlowMiddleNodeProcessListener middleNodeProcessListener;
    private PermitMonitor permitMonitor;

    public FlowMiddleNodeArbitrateEventImpl(String taskStory, PrototypeKey taskInstance, Class taskNodeClass) {
        super(taskStory, taskInstance, taskNodeClass);
        permitMonitor = ArbitratorFactory.getInstance(taskStory,taskInstance, Object.class, PermitMonitor.class);
        middleNodeProcessListener = ArbitratorFactory.getInstance(taskStory,taskInstance, taskNodeClass, FlowMiddleNodeProcessListener.class);
    }

    @Override
    public void setPrevTask(Class<? extends FlowTask> prevTask) {
        super.setPrevTask(prevTask);
        middleNodeProcessListener.setPrevTask(prevTask);
    }

    @Override
    public void setCurTask(Class<? extends FlowTask> curTask) {
        super.setCurTask(curTask);
        middleNodeProcessListener.setCurTask(curTask);
    }

    public SharableEventData await(PrototypeKey prototypeKey) throws InterruptedException {
        //检查当前的Permit，阻塞等待其授权(解决Channel的pause状态处理)
        permitMonitor.waitPermit();// 阻塞等待授权

        Long processId = middleNodeProcessListener.waitForProcess();// 阻塞等待有可处理processId，同时zk已经产生对应的process节点

        if(permitMonitor.getPrototypeStatus().isStart()) {//非阻塞查询当前主机状态
            SharableEventData eventData = middleNodeProcessListener.getPrevTaskEventData(processId);
            eventData.setNextNid(ConfigClient.getCurrentNid());//TODO 需要通过负载算法进行选取next id
            return eventData;
        }else {
            return await(prototypeKey);
        }
    }

    public void single(SharableEventData data){
        Assert.notNull(data);

        TaskProcessArbitrateEvent taskProcessArbitrateEvent =
                ArbitratorFactory.getInstance(taskStory,taskInstance, taskNodeClass, TaskProcessArbitrateEvent.class);
        taskProcessArbitrateEvent.createProcessStage(taskStory, taskInstance, data.getProcessId(), taskNodeClass, data);
    }
}
