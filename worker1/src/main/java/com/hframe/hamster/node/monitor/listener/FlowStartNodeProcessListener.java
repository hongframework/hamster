package com.hframe.hamster.node.monitor.listener;

import com.alibaba.otter.canal.common.utils.JsonUtils;
import com.google.common.collect.Lists;
import com.hframe.hamster.node.arbitrate.ArbitratorFactory;
import com.hframe.hamster.node.monitor.MainStemMonitor;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.SharableEventData;
import com.hframe.hamster.node.zk.ZKPathUtils;
import com.hframework.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by zhangquanhong on 2016/9/29.
 */
public class FlowStartNodeProcessListener extends AbstractFlowNodeProcessListener implements TaskProcessListener, MainStemListener {
    protected static final Logger logger    = LoggerFactory.getLogger(FlowStartNodeProcessListener.class);

    private volatile Map<String, List<Long[]>> ignoreDataBinLogInfo = new HashMap<>();

    private boolean loadFirstTime = true;
    MainStemMonitor mainStemMonitor = null;

    public FlowStartNodeProcessListener(String taskStory, PrototypeKey prototypeKey, Class taskNodeClass) {
        super(taskStory, prototypeKey, taskNodeClass);
        mainStemMonitor = ArbitratorFactory.getInstance(taskStory,prototypeKey, Object.class, MainStemMonitor.class);
        mainStemMonitor.addListener(this);
    }



    @Override
    public void processChange(List<Long> processIds) {
        logger.debug("new process list : {}", Arrays.asList(processIds));

        for (Long processId : processIds) {
            if(!processIdsQueue.contains(processId)) {
                if(loadFirstTime || processIdsQueue.size() == 0) {
                    synchronized (this) {
                        if(loadFirstTime || processIdsQueue.size() == 0) {
                            String processPath = processMonitor.getZkPath(processId);
                            List<String> stages = zookeeper.getChildren(processPath);
                            if(stages !=null && stages.size() > 0) {
                                if(stages.contains("LoadTask")) {
                                    byte[] data = zookeeper.readData(ZKPathUtils.getProcessStagePath(taskStory, prototypeKey.value() ,processId, "LoadTask"));
                                    SharableEventData eventData = JsonUtils.unmarshalFromByte(data, SharableEventData.class);
                                    addIgnoreDataBinLogInfo(eventData);
                                }
                            }
                        }
                    }
                }
            }
        }
        loadFirstTime = false;


        for (Long processId : processIds) {
            if(!processIdsQueue.contains(processId)) {
                addReply(processId);
            }
        }

        int capacity = parallelism - processIds.size();
        if(capacity > 0) {
            logger.debug("create new process node  : {}, {}, {}", parallelism, processIds.size(), capacity);
            synchronized (this) {
                //二次检查
                if(parallelism - zookeeper.getChildren(processMonitor.getZkPath()).size() > 0) {
                    addProcessNodeToZk();
                }
            }
        }
    }

    private void addIgnoreDataBinLogInfo(SharableEventData eventData) {
        String binlogStart = eventData.getBinlogStart();
        String binlogEnd = eventData.getBinlogEnd();
        if(StringUtils.isBlank(binlogStart) || StringUtils.isBlank(binlogEnd)) {
            return;
        }
        String startBinlogFileName = binlogStart.substring(0, binlogStart.indexOf("|"));
        Long startBinlogOffset = Long.parseLong(binlogStart.substring(binlogStart.indexOf("|") + 1));

        String endBinlogFileName = binlogEnd.substring(0, binlogEnd.indexOf("|"));
        Long endBinlogOffset = Long.parseLong(binlogEnd.substring(binlogEnd.indexOf("|") + 1));
        if(ignoreDataBinLogInfo == null) ignoreDataBinLogInfo = new HashMap<>();
        if(!ignoreDataBinLogInfo.containsKey(startBinlogFileName)) {
            ignoreDataBinLogInfo.put(startBinlogFileName, new ArrayList<Long[]>());
        }
        if(!ignoreDataBinLogInfo.containsKey(endBinlogFileName)) {
            ignoreDataBinLogInfo.put(endBinlogFileName, new ArrayList<Long[]>());
        }

        if(endBinlogFileName.equals(startBinlogFileName)) {
            ignoreDataBinLogInfo.get(startBinlogFileName).add(new Long[]{startBinlogOffset, endBinlogOffset});
        }else {
            ignoreDataBinLogInfo.get(startBinlogFileName).add(new Long[]{startBinlogOffset, Long.MAX_VALUE});
            ignoreDataBinLogInfo.get(endBinlogFileName).add(new Long[]{Long.MIN_VALUE, endBinlogOffset});
        }
    }

    @Override
    public void processChange(Long processId, List<String> currentStages) {

    }

    @Override
    public void processActiveExit() {

    }

    @Override
    public void processActiveEnter() {
        //重新加载一下待处理process，由于在wait
        ArrayList<Long> processIds = Lists.newArrayList(processMonitor.getProcessCache().keySet());
        Collections.sort(processIds);
        processChange(processIds);
        processMonitor.reload();
    }

    public Map<String, List<Long[]>> getIgnoreDataBinLogInfo() {
        return ignoreDataBinLogInfo;
    }

    public void setIgnoreDataBinLogInfo(Map<String, List<Long[]>> ignoreDataBinLogInfo) {
        this.ignoreDataBinLogInfo = ignoreDataBinLogInfo;
    }
}
