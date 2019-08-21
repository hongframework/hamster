package com.hframe.hamster.node.task;

import com.hframe.ext.bean.HBaseConfig;
import com.hframe.ext.bean.StatisticsData;
import com.hframe.hamster.node.HamsterConst;
import com.hframe.hamster.node.cannal.CanalServer;
import com.hframe.hamster.node.cannal.CanalServerFactory;
import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.common.AbstractPrototypeFlowNodeTask;
import com.hframe.hamster.node.task.common.FlowEndNodeTask;
import com.hframe.hamster.node.task.common.TaskData;
import com.hframe.hamster.node.task.common.TaskOrderMode;
import com.hframework.common.client.hbase.HBaseClient;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.common.util.collect.bean.Mapper;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;
import java.util.Map;


/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class AbstractLoad2HBaseTask extends AbstractPrototypeFlowNodeTask implements FlowEndNodeTask {
    private static final Logger logger = LoggerFactory.getLogger(AbstractLoad2HBaseTask.class);

    private Map<Long, HBaseConfig> loadConfigMap;

    private int KeyIndex = 0;

    public AbstractLoad2HBaseTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);

        try {
            loadConfigMap = CollectionUtils.convert(getLoadConfigs(flowKey, prototypeKey), new Mapper<Long, HBaseConfig>() {
                @Override
                public Long getKey(HBaseConfig loadConfig) {
                    return loadConfig.getRowKeywordConfigId();
                }
            });
        } catch (Exception e) {
            logger.error("select task init error ! {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    protected abstract List<HBaseConfig> getLoadConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception;


    @Override
    public void process(TaskData taskData) throws Exception {
        if(isEmpty(taskData)) return;

        Message message = (Message) taskData.getMessage();
        for (StatisticsData statisticsData : message.getStatisticsDatas()) {

            HBaseConfig loadConfig = loadConfigMap.get(statisticsData.getTopicId());

            String rowKeyword = loadConfig.getRowKeyword();;
            try {
                MDC.put(HamsterConst.splitLoaderLogFileKey, flowKey.value() + "_" + prototypeKey.value());
                String statisticsView = statisticsData.getStatisticsView();
                String statisticsTime = statisticsData.getStatisticsTime();
                String statisticsValue = statisticsData.getStatisticsValue();
                String logStart = statisticsData.getLogStart();
                String logEnd = statisticsData.getLogEnd();
                String rowKey = getRowKey(rowKeyword, statisticsView, statisticsTime);

                HBaseClient hBaseClient = HBaseClient.getInstance(loadConfig.getZkList(), loadConfig.getZkPort());
                hBaseClient.table(loadConfig.getTableName())
                        .rowKey(rowKey)
                        .column("items", "0", statisticsValue)
                        .column("infos", "logStart", logStart)
                        .column("infos", "logEnd", logEnd).put();

                logger.info("table = {} | rowKey = {} | value = {} | logStart ={} | logEnd = {}",
                        loadConfig.getTableName(), rowKey, statisticsValue, logStart, logEnd);
            } catch (Exception e) {
                logger.error("sen message to zookeeper error ! {}", ExceptionUtils.getFullStackTrace(e));
                throw e;
            }finally {
                MDC.remove(HamsterConst.splitLoaderLogFileKey);
            }
        }

    }

    protected  String getRowKey(String rowKeyword, String statisticsView, String statisticsTime){
        StringBuffer rowKey = new StringBuffer();
        rowKey.append(rowKeyword);
        if(StringUtils.isNotBlank(statisticsView)) {
            rowKey.append("_");
            rowKey.append(statisticsView);
        }
        rowKey.append("_");
        rowKey.append(statisticsTime);
        rowKey.append("_");
        rowKey.append(org.apache.commons.lang.StringUtils.rightPad(String.valueOf(System.currentTimeMillis()),14));
        rowKey.append("_");
        rowKey.append(org.apache.commons.lang.StringUtils.leftPad(String.valueOf(Math.round(Math.random() * 100000)), 5));
        return rowKey.toString();
    }

    @Override
    public boolean ask(Message message) {
        CanalServer canalServer = CanalServerFactory.get(flowKey, prototypeKey);
        canalServer.ack(message.getMessageId());
        return true;
    }

    @Override
    public TaskOrderMode getOrderMode() {
        return null;
    }

    public enum PartitionStrategy {
        ConsistentHash((byte)1, "一致性Hash"),
        RoundRobin((byte)2, "轮询"),
        Random((byte)3, "随机"),
        LeastActive((byte)4, "最小活跃数");

        private byte index;
        private String name;
        PartitionStrategy(byte index, String name) {
            this.index = index;
            this.name = name;
        }

        public static PartitionStrategy valueOf(byte index) {
            if(ConsistentHash.index == index) {
                return ConsistentHash;
            }
            if(RoundRobin.index == index) {
                return RoundRobin;
            }
            if(Random.index == index) {
                return Random;
            }

            return Random;
        }

        public static boolean isConsistentHash(byte index) {
            return ConsistentHash.index == index;
        }
        public static boolean isRoundRobin(byte index) {
            return RoundRobin.index == index;
        }
        public static boolean isRandom(byte index) {
            return Random.index == index;
        }


    }
}
