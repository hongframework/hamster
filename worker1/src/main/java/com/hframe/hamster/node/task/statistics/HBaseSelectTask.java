package com.hframe.hamster.node.task.statistics;

import com.google.common.base.Joiner;
import com.hframe.ext.bean.HBaseConfig;
import com.hframe.ext.bean.HBaseData;
import com.hframe.ext.service.CfgStatisticsService;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.CronSelectTask;
import com.hframe.hamster.node.task.common.CronTask;
import com.hframe.hamster.node.task.common.FlowStartNodeTask;
import com.hframe.hamster.node.task.common.FlowTask;
import com.hframework.common.client.hbase.HBaseClient;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
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
public class HBaseSelectTask extends CronSelectTask implements FlowStartNodeTask,CronTask {
    private static final Logger logger = LoggerFactory.getLogger(HBaseSelectTask.class);

    private List<HBaseConfig> hBaseConfigs;
    public HBaseSelectTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
        try {
            hBaseConfigs = getHBaseConfigs(prototypeKey);
        } catch (Exception e) {
            logger.error("select task init error ! {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    private List<HBaseConfig> getHBaseConfigs( PrototypeKey prototypeKey) throws Exception {
        return HamsterContextInitializer.getBean(
                CfgStatisticsService.class).getSystemHBaseConfig();
    }

    @Override
    public String cronExpression() {
        return "0/3 * * * * ?";//每秒执行
    }

    @Override
    public Message fetch() throws Exception {
        List<HBaseData> eventDatas = new ArrayList<>();
        Message message = new Message();
        message.setHbaseDatas(eventDatas);
        for (HBaseConfig hBaseConfig : hBaseConfigs) {
            HBaseClient hBaseClient = HBaseClient.getInstance(hBaseConfig.getZkList(), hBaseConfig.getZkPort());
//            ResultScanner resultScanner = hBaseClient.scan(hBaseConfig.getTableName(), hBaseConfig.getRowKeyword() + "_");
            ResultScanner resultScanner = hBaseClient.scan(hBaseConfig.getTableName(), 99999);
            Map<String, BigDecimal> results = new HashMap<>();
            Map<String, List<String>> rowKeyInfos = new HashMap<>();
            for (Result result : resultScanner) {
                String rowkey = new String(result.getRow());
                String keyInfo = rowkey.substring(0, rowkey.lastIndexOf("_"));
                keyInfo = keyInfo.substring(0, keyInfo.lastIndexOf("_"));
                if(!results.containsKey(keyInfo)) {
                    results.put(keyInfo, BigDecimal.ZERO);
                    rowKeyInfos.put(keyInfo, new ArrayList<String>());
                }
                results.put(keyInfo, results.get(keyInfo).add(new BigDecimal(HBaseClient.resultGet(result, "items", "0")))) ;
                rowKeyInfos.get(keyInfo).add(rowkey);
            }
            for (String keyInfo : results.keySet()) {
                HBaseData hBaseData = new HBaseData();
                hBaseData.setRowKey(keyInfo);
                hBaseData.addColumn(
                        new HBaseData.HBaseColumn("items", "0", results.get(keyInfo).toPlainString()));
                hBaseData.setRemark(Joiner.on(",").join(rowKeyInfos.get(keyInfo)));
                eventDatas.add(hBaseData);
            }
        }
        return message;
    }


    @Override
    public void ack(Message newMessage) throws InterruptedException {

    }

    @Override
    public Class<? extends FlowTask> nextTask() {
        return SecondReduceTask.class;
    }
}
