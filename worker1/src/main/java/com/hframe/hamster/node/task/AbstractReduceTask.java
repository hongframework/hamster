package com.hframe.hamster.node.task;

import com.google.common.base.Joiner;
import com.hframe.ext.bean.HBaseConfig;
import com.hframe.ext.bean.HBaseData;
import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.cannal.bean.EventColumn;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.common.AbstractPrototypeFlowNodeTask;
import com.hframe.hamster.node.task.common.TaskData;
import com.hframework.common.client.hbase.HBaseClient;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.common.util.collect.bean.Grouper;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.hadoop.hbase.client.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class AbstractReduceTask extends AbstractPrototypeFlowNodeTask {
    private static final Logger logger = LoggerFactory.getLogger(AbstractReduceTask.class);



    protected List<HBaseConfig> hBaseConfigs;
    public AbstractReduceTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
        try {
            hBaseConfigs = getLoadConfigs(flowKey,prototypeKey);
        } catch (Exception e) {
            logger.error("select task init error ! {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    protected abstract List<HBaseConfig> getLoadConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception;

    protected abstract String tableName() throws Exception;

    @Override
    public void process(TaskData taskData) throws Exception {

        if(!isEmpty(taskData)) {
            Message message = (Message) taskData.getMessage();
            List<HBaseData> datas = message.getHbaseDatas();

            Map<String, List<HBaseData>> group = CollectionUtils.group(datas, new Grouper<String, HBaseData>() {
                @Override
                public <K> K groupKey(HBaseData hBaseData) {
                    String rowKey = hBaseData.getRowKey();
                    return (K) rowKey.substring(0, rowKey.length() - 2);
                }
            });
            List<HBaseData> newDatas = new ArrayList<>();
            List<HBaseData> printDatas = new ArrayList<>();
            for (String newRowKey : group.keySet()) {
                HBaseData printData = new HBaseData();
                printData.setRowKey(newRowKey);
                printDatas.add(printData);


//                printData.setRemark(Joiner.on(",").join(rowKeyInfos.get(keyInfo)));
                BigDecimal total = BigDecimal.ZERO;
                List<String> remarks = new ArrayList<>();
                for (HBaseData hBaseData : group.get(newRowKey)) {
                    String rowKey = hBaseData.getRowKey();
                    String qualifier = rowKey.substring(rowKey.length() - 2);
                    if(hBaseData.getColumn("items", "0") == null) {
                        System.out.println(1);
                    }
                    String value =  hBaseData.getColumn("items", "0").getValue();
                    printData.addColumn(
                            new HBaseData.HBaseColumn("items", qualifier, value));

                    total = total.add(new BigDecimal(value));
                    remarks.add(hBaseData.getRemark());
                }



                HBaseData newData = new HBaseData();
                newData.setRowKey(newRowKey);
                newData.addColumn(
                        new HBaseData.HBaseColumn("items", "0", total.toPlainString()));
                newData.setRemark(Joiner.on(",").join(remarks));
                newDatas.add(newData);
            }

            for (HBaseData printData : printDatas) {
                HBaseClient hBaseClient = HBaseClient.getInstance(hBaseConfigs.get(0).getZkList(), hBaseConfigs.get(0).getZkPort());
                Result result = hBaseClient.get(tableName(), printData.getRowKey());
                HBaseClient.PutContainer putContainer = hBaseClient.table(tableName())
                        .rowKey(printData.getRowKey());
                for (HBaseData.HBaseColumn hBaseColumn : printData.gethBaseColumns()) {
                    String origVal = hBaseClient.resultGet(result, hBaseColumn.getFamily(), hBaseColumn.getQualifier());

                    if(StringUtils.isNotBlank(origVal)) {
                        putContainer.column(hBaseColumn.getFamily(), hBaseColumn.getQualifier(),
                                new BigDecimal(hBaseColumn.getValue()).add(new BigDecimal(origVal)).toPlainString());
                    }else {
                        putContainer.column(hBaseColumn.getFamily(), hBaseColumn.getQualifier(), hBaseColumn.getValue());
                    }
                }
                putContainer.put();
            }

            message.setHbaseDatas(newDatas);
        }
    }

    protected boolean isEmpty(TaskData taskData){
        if(taskData.getMessage() == null) return true;
        if(((Message)taskData.getMessage()).getHbaseDatas() == null) return true;
        if(((Message)taskData.getMessage()).getHbaseDatas().size() == 0)return true;
        return false;
    }

    protected  Map<String, Object> getColumnValueMap(List<EventColumn> keys, List<EventColumn> columns) {
        Map<String, Object> properties = new HashMap<>();
        for (EventColumn eventColumn : keys) {
            properties.put(eventColumn.getColumnName(), eventColumn.getColumnValue());
        }

        for (EventColumn eventColumn : columns) {
            properties.put(eventColumn.getColumnName(), eventColumn.getColumnValue());
        }
        return properties;
    }
}

