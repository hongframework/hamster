package com.hframe.hamster.node.monitor.listener;

import com.alibaba.otter.canal.common.utils.JsonUtils;
import com.hframe.hamster.node.config.ConfigClient;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.SharableEventData;
import com.hframe.hamster.node.task.common.FlowTask;
import com.hframe.hamster.node.zk.ZKPathUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangquanhong on 2016/9/29.
 */
public class FlowMiddleNodeProcessListener extends AbstractFlowNodeProcessListener implements TaskProcessListener {
    protected static final Logger logger    = LoggerFactory.getLogger(FlowMiddleNodeProcessListener.class);


    protected Class<? extends FlowTask> prevTask;
    protected Class<? extends FlowTask> curTask;

    public FlowMiddleNodeProcessListener(String taskStory, PrototypeKey prototypeKey, Class taskNodeClass) {
        super(taskStory, prototypeKey, taskNodeClass);
    }

    //process列表变化时
    @Override
    public void processChange(List<Long> processIds) {
    }

    public SharableEventData getPrevTaskEventData(Long processId){
        // 2.2 判断是否存在了prev节点
        String path = ZKPathUtils.getProcessStagePath(taskStory, prototypeKey.value(), processId, prevTask.getSimpleName());
        // 2.2.1 获取上一个节点的next node节点信息
        byte[] data = zookeeper.readData(path);
        return JsonUtils.unmarshalFromByte(data, SharableEventData.class);
    }

    @Override
    public void processChange(Long processId, List<String> currentStages) throws Exception {

        try {
            logger.debug("[{}] processId:{}, currentStages:{}", this.getClass().getSimpleName(), processId, currentStages);
            if(currentStages == null) {
                return ;
            }
            // 2.1 判断是否存在了current节点
            if (currentStages.contains(curTask.getSimpleName())) {
                if (processIdsQueue.remove(processId)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("## remove query id [{}]", processId);
                    }
                }
                return;// 不需要监听了
            }

            if (processIdsQueue.contains(processId)) {
                logger.warn("repeat process id : {}", processId);
                return;// 避免重复处理
            }

            // 2.2 判断是否存在了prev节点
            if (currentStages.contains(prevTask.getSimpleName())) {

                    String path = ZKPathUtils.getProcessStagePath(taskStory, prototypeKey.value(), processId, prevTask.getSimpleName());
                    // 2.2.1 获取上一个节点的next node节点信息
                    byte[] data = null;
                    try {
                        data = zookeeper.readData(path);
                    }catch (Exception e) {
                        logger.error("get last node data error, sleep 1 second retry => {}", ExceptionUtils.getFullStackTrace(e));
                        Thread.sleep(1000L);
                        data = zookeeper.readData(path);
                    }
                    SharableEventData eventData = JsonUtils.unmarshalFromByte(data, SharableEventData.class);
                    if (eventData.getNextNid().equals(ConfigClient.getCurrentNid())) {
                        addReply(processId);// 添加到返回队列,唤醒wait阻塞
                    }


            }
        } catch (Exception e) {
            logger.error("notify listener error => {}", ExceptionUtils.getFullStackTrace(e));
            throw e;
        }
    }

    public Class<? extends FlowTask> getCurTask() {
        return curTask;
    }

    public void setCurTask(Class<? extends FlowTask> curTask) {
        this.curTask = curTask;
        reloadProcessIdsQueue();
    }

    public Class<? extends FlowTask> getPrevTask() {
        return prevTask;
    }

    public void setPrevTask(Class<? extends FlowTask> prevTask) {
        this.prevTask = prevTask;
        reloadProcessIdsQueue();
    }

    private void reloadProcessIdsQueue() {
        if(curTask != null && prevTask != null) {
            Map<Long, List<String>> processCache = processMonitor.getProcessCache();
            for (Long processId : processCache.keySet()) {
                try {
                    this.processChange(processId, processCache.get(processId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
