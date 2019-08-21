package com.hframe.hamster.node.arbitrate.impl;

import com.hframe.hamster.node.arbitrate.ArbitratorFactory;
import com.hframe.hamster.node.arbitrate.TaskProcessArbitrateEvent;
import com.hframe.hamster.node.arbitrate.interfaces.FlowStartNodeArbitrateEvent;
import com.hframe.hamster.node.config.ConfigClient;
import com.hframe.hamster.node.monitor.MainStemMonitor;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.SharableEventData;
import com.hframe.hamster.node.monitor.listener.FlowStartNodeProcessListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * 调度接口
 * Created by zhangquanhong on 2016/9/28.
 */
public class FlowStartNodeArbitrateEventImpl extends AbstractFlowTaskNodeArbitrateEvent implements FlowStartNodeArbitrateEvent {
    private static final Logger logger = LoggerFactory.getLogger(FlowStartNodeArbitrateEventImpl.class);

    public FlowStartNodeArbitrateEventImpl(String taskStory, PrototypeKey taskInstance, Class taskNodeClass) {
        super(taskStory, taskInstance, taskNodeClass);
    }


    public SharableEventData await(PrototypeKey prototypeKey) throws InterruptedException {
        Assert.notNull(prototypeKey);
        MainStemMonitor mainStemMonitor = ArbitratorFactory.getInstance(taskStory,prototypeKey, Object.class, MainStemMonitor.class);
        mainStemMonitor.waitForPermit();// 阻塞等待授权

        FlowStartNodeProcessListener selectProcessListener = ArbitratorFactory.getInstance(taskStory,prototypeKey, taskNodeClass, FlowStartNodeProcessListener.class);
        Long processId = selectProcessListener.waitForProcess();// 阻塞等待有可处理processId，同时zk已经产生对应的process节点

        TaskProcessArbitrateEvent taskProcessArbitrateEvent =
                ArbitratorFactory.getInstance(taskStory,prototypeKey, taskNodeClass, TaskProcessArbitrateEvent.class);

        if(mainStemMonitor.isPermit()) {//非阻塞查询当前主机状态
            SharableEventData eventData = new SharableEventData();
            eventData.setCurrNid(ConfigClient.getCurrentNid());
            eventData.setNextNid(ConfigClient.getCurrentNid());//TODO 需要通过负载算法进行选取next id
            eventData.setProcessId(processId);
            eventData.setPrototypeValue(prototypeKey.value());
            taskProcessArbitrateEvent.markUsed(taskStory, prototypeKey,processId);
            return eventData;
        }else {
            //这里选择直接删除zk对应节点，是否有新的processId产生？导致中间断了？
            taskProcessArbitrateEvent.delete(taskStory, prototypeKey, processId);
            return await(prototypeKey);
        }
    }

    public Map<String,List<Long[]>> getIgnoreDataInfo(PrototypeKey prototypeKey) {
        FlowStartNodeProcessListener selectProcessListener = ArbitratorFactory.getInstance(taskStory,prototypeKey, taskNodeClass, FlowStartNodeProcessListener.class);
        return selectProcessListener.getIgnoreDataBinLogInfo();
    }

    public void single(SharableEventData data){
        Assert.notNull(data);

        TaskProcessArbitrateEvent taskProcessArbitrateEvent =
                ArbitratorFactory.getInstance(taskStory,taskInstance, taskNodeClass, TaskProcessArbitrateEvent.class);
        taskProcessArbitrateEvent.createProcessStage(taskStory,taskInstance,data.getProcessId(),taskNodeClass,data);
    }
}
