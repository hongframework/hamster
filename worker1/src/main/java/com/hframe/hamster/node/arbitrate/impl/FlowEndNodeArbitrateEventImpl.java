package com.hframe.hamster.node.arbitrate.impl;

import com.hframe.hamster.node.arbitrate.ArbitratorFactory;
import com.hframe.hamster.node.arbitrate.TaskProcessArbitrateEvent;
import com.hframe.hamster.node.arbitrate.interfaces.FlowEndNodeArbitrateEvent;
import com.hframe.hamster.node.monitor.PermitMonitor;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.SharableEventData;
import com.hframe.hamster.node.monitor.listener.FlowEndNodeProcessListener;
import com.hframe.hamster.node.task.common.FlowTask;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 调度接口
 * Created by zhangquanhong on 2016/9/28.
 */
public class FlowEndNodeArbitrateEventImpl extends AbstractFlowTaskNodeArbitrateEvent implements FlowEndNodeArbitrateEvent {
    private static final Logger logger = LoggerFactory.getLogger(FlowMiddleNodeArbitrateEventImpl.class);
    private FlowEndNodeProcessListener endNodeProcessListener;
    private PermitMonitor permitMonitor;
    public FlowEndNodeArbitrateEventImpl(String taskStory, PrototypeKey taskInstance, Class taskNodeClass) {
        super(taskStory, taskInstance, taskNodeClass);
        permitMonitor = ArbitratorFactory.getInstance(taskStory,taskInstance, Object.class, PermitMonitor.class);
        endNodeProcessListener = ArbitratorFactory.getInstance(taskStory, taskInstance, taskNodeClass,  FlowEndNodeProcessListener.class);
    }


    public SharableEventData await(PrototypeKey prototypeKey) throws InterruptedException {
        //检查当前的Permit，阻塞等待其授权(解决Channel的pause状态处理)
        permitMonitor.waitPermit();// 阻塞等待授权
        Long processId = endNodeProcessListener.waitForProcess();// 阻塞等待有可处理processId，同时zk已经产生对应的process节点

        if(permitMonitor.getPrototypeStatus().isStart()) {//非阻塞查询当前主机状态
            SharableEventData eventData = endNodeProcessListener.getPrevTaskEventData(processId);
            return eventData;
        }else {
            return await(prototypeKey);
        }
    }

    public SharableEventData awaitTermin(PrototypeKey prototypeKey) throws InterruptedException {
        //检查当前的Permit，阻塞等待其授权(解决Channel的pause状态处理)
        permitMonitor.waitPermit();// 阻塞等待授权
        Long processId = endNodeProcessListener.waitForTerminProcess();// 阻塞等待有可处理processId，同时zk已经产生对应的process节点

        if(permitMonitor.getPrototypeStatus().isStart()) {//非阻塞查询当前主机状态
            SharableEventData eventData = null;
            try{
                eventData = endNodeProcessListener.getPrevTaskEventData(processId);
            }catch (Exception e) {
                try{
                    eventData = endNodeProcessListener.getPrevTaskEventData(processId);
                }catch (Exception e2) {
                    logger.warn("await termin get prev task error : {}", ExceptionUtils.getFullStackTrace(e2));
                    return awaitTermin(prototypeKey);
                }
            }

            return eventData;
        }else {
            return awaitTermin(prototypeKey);
        }
    }

    @Override
    public void setPrevTask(Class<? extends FlowTask> prevTask) {
        super.setPrevTask(prevTask);
        endNodeProcessListener.setPrevTask(prevTask);
    }

    @Override
    public void setCurTask(Class<? extends FlowTask> curTask) {
        super.setCurTask(curTask);
        endNodeProcessListener.setCurTask(curTask);
    }

    public void single(SharableEventData data){
        Assert.notNull(data);

        TaskProcessArbitrateEvent taskProcessArbitrateEvent =
                ArbitratorFactory.getInstance(taskStory,taskInstance, taskNodeClass, TaskProcessArbitrateEvent.class);
        taskProcessArbitrateEvent.createProcessStage(taskStory, taskInstance, data.getProcessId(), taskNodeClass, data);
    }

    @Override
    public void ask(SharableEventData eventData) {
        Assert.notNull(eventData);
        TaskProcessArbitrateEvent taskProcessArbitrateEvent =
                ArbitratorFactory.getInstance(taskStory,taskInstance, taskNodeClass, TaskProcessArbitrateEvent.class);

        taskProcessArbitrateEvent.createProcessTermin(taskStory, taskInstance, eventData.getProcessId());

//        //创建unWatchFlag节点
//        taskProcessArbitrateEvent.createProcessStage(taskStory, taskInstance, eventData.getProcessId(), ACK.class, null);


    }
}
