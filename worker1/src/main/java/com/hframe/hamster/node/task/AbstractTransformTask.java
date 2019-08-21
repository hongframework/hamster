package com.hframe.hamster.node.task;

import com.hframe.ext.bean.SubscribeData;
import com.hframe.ext.bean.TransformConfig;
import com.hframe.hamster.node.arbitrate.ArbitratorFactory;
import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.cannal.bean.EventColumn;
import com.hframe.hamster.node.cannal.bean.EventData;
import com.hframe.hamster.node.monitor.MultiSequenceMonitor;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.common.AbstractPrototypeFlowNodeTask;
import com.hframe.hamster.node.task.common.FlowNodeTask;
import com.hframe.hamster.node.task.common.TaskData;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.common.util.collect.bean.Mapper;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class AbstractTransformTask extends AbstractPrototypeFlowNodeTask implements FlowNodeTask {
    private static final Logger logger = LoggerFactory.getLogger(AbstractTransformTask.class);

    private MultiSequenceMonitor sequenceMonitor;

    private Map<Long, TransformConfig> transformConfigMap;


    public AbstractTransformTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
        try{
            transformConfigMap = CollectionUtils.convert(getTransformConfigs(flowKey, prototypeKey), new Mapper<Long, TransformConfig>() {
                @Override
                public Long getKey(TransformConfig transformConfig) {
                    return transformConfig.getConfigId();
                }
            });

            sequenceMonitor = ArbitratorFactory.getInstance(flowKey.value(), prototypeKey, Object.class, MultiSequenceMonitor.class);
        } catch (Exception e) {
            logger.error("transfor task init error ! {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    protected abstract List<TransformConfig> getTransformConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception;


    @Override
    public void process(TaskData taskData) throws InterruptedException {
        Map<String, Long> extSequences = sequenceMonitor.waitForMultiSequence(taskData.getEventData().getProcessId());
        if(extSequences == null) extSequences = new HashMap<>();
        if(!isEmpty(taskData)) {
            Message message = (Message) taskData.getMessage();
            List<EventData> datas = message.getDatas();
            List<SubscribeData> transDatas = new ArrayList<>();
            for (final EventData data : datas) {
                TransformConfig transformConfig = transformConfigMap.get(data.getPairId());

                SubscribeData subscribeData = new SubscribeData();
                subscribeData.setSchemaName(data.getSchemaName());
                subscribeData.setTableName(data.getTableName());
                subscribeData.setEventType(data.getEventType());
                subscribeData.setExecuteTime(String.valueOf(data.getExecuteTime()));
                subscribeData.setTablePosition(data.getBinLogPostion());
                String sequenceName = transformConfig.getDbObjectName() + "." + transformConfig.getConfigId();
                if(!extSequences.containsKey(sequenceName)){
                    extSequences.put(sequenceName, 1L);
                }else{
                    extSequences.put(sequenceName, extSequences.get(sequenceName) + 1);
                }
                subscribeData.setSubscribeId(String.valueOf(extSequences.get(sequenceName)));
                subscribeData.setSubscribeDetailId(data.getPairId());

                Map<String, SubscribeData.DataItem> columns = new HashMap<>();
                subscribeData.setColumns(columns);


                addColumns(transformConfig, columns, data.getKeys());
                addColumns(transformConfig, columns, data.getColumns());

                List<EventColumn> oldColumns = data.getOldColumns();
                for (EventColumn oldColumn : oldColumns) {
                    SubscribeData.DataItem dataItem = columns.get(oldColumn.getColumnName());
                    if(dataItem != null) dataItem.setOldColumnValue(oldColumn.getColumnValue());
                }
                transDatas.add(subscribeData);
            }
            message.setTransDatas(transDatas);
        }
        sequenceMonitor.updateSequence(taskData.getEventData().getProcessId(), extSequences);
    }

    private void addColumns(TransformConfig transformConfig , Map<String, SubscribeData.DataItem> columns, List<EventColumn> eventColumns) {
        if(eventColumns == null) return ;
        for (EventColumn eventColumn : eventColumns) {
            if(StringUtils.isBlank(transformConfig.getDbObjectDatas()) || transformConfig.getColumnsInfo().contains(eventColumn.getColumnName())) {
                SubscribeData.DataItem dataItem = new SubscribeData.DataItem();
                dataItem.setColumnName(eventColumn.getColumnName());
                dataItem.setColumnType(eventColumn.getColumnType());
                dataItem.setColumnValue(eventColumn.getColumnValue());
                dataItem.setUpdate(eventColumn.isUpdate());
                columns.put(eventColumn.getColumnName(), dataItem);
            }
        }
    }
}

