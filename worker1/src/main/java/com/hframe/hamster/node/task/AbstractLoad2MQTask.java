package com.hframe.hamster.node.task;

import com.hframe.ext.bean.MQConfig;
import com.hframe.ext.bean.SubscribeData;
import com.hframe.hamster.node.HamsterConst;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.cannal.CanalServer;
import com.hframe.hamster.node.cannal.CanalServerFactory;
import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.common.AbstractPrototypeFlowNodeTask;
import com.hframe.hamster.node.task.common.FlowEndNodeTask;
import com.hframe.hamster.node.task.common.TaskData;
import com.hframe.hamster.node.task.common.TaskOrderMode;
import com.hframework.common.client.kafka.KafkaService;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.common.util.collect.bean.Mapper;
import com.hframework.common.util.message.JsonUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;
import java.util.Map;


/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class AbstractLoad2MQTask extends AbstractPrototypeFlowNodeTask implements FlowEndNodeTask {
    private static final Logger logger = LoggerFactory.getLogger(AbstractLoad2MQTask.class);

    private Map<Long, MQConfig> loadConfigMap;

    private int KeyIndex = 0;

    public AbstractLoad2MQTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);

        try {
            loadConfigMap = CollectionUtils.convert(getLoadConfigs(flowKey, prototypeKey), new Mapper<Long, MQConfig>() {
                @Override
                public Long getKey(MQConfig loadConfig) {
                    return loadConfig.getConfigId();
                }
            });
        } catch (Exception e) {
            logger.error("select task init error ! {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    protected abstract List<MQConfig> getLoadConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception;


    @Override
    public void process(TaskData taskData) throws Exception {
        if(isEmpty(taskData)) return;

        Message message = (Message) taskData.getMessage();
        for (SubscribeData subscribeData : message.getTransDatas()) {
            subscribeData.setLoadTime(System.currentTimeMillis() + "");
            MQConfig loadConfig = loadConfigMap.get(subscribeData.getSubscribeDetailId());

            String topic = loadConfig.getCfgTopicCode();;
            Byte partitions = loadConfig.getPartitions();
            subscribeData.setSubscribeDetailId(null);
            try {
                MDC.put(HamsterConst.splitLoaderLogFileKey, flowKey.value() + "_" + prototypeKey.value());
                String msg = JsonUtils.writeValueAsString(subscribeData);
                SubscribeData.DataItem dataItem = subscribeData.getColumns().get(loadConfig.getPartitionKey());
                int key = 0;
                switch (PartitionStrategy.valueOf(loadConfig.getPartitionStrategy())) {
                    case Random:
                        key = Double.valueOf(Math.random() * partitions).intValue();
                        break;
                    case RoundRobin:
                        key = KeyIndex = (KeyIndex +1) >= partitions ? 0 : (KeyIndex +1);
                        break;
                    case ConsistentHash:
                        Long keyValue = 0L;
                        if(dataItem != null && StringUtils.isNotBlank(dataItem.getColumnValue())) {
                            if(NumberUtils.isDigits(dataItem.getColumnValue().trim())) {
                                keyValue = Long.parseLong(dataItem.getColumnValue());
                            }else {
                                keyValue = Long.valueOf(dataItem.getColumnValue().hashCode());
                            }
                        }
                        if(keyValue == null){
                            key = Double.valueOf(Math.random() * partitions).intValue();
                        }else {
                            key = Long.valueOf(keyValue).intValue() % partitions;
                        }
                        break;
                }

                HamsterContextInitializer.getBean(
                        KafkaService.class).sendMessage(loadConfig.getBrokerAddrList(), topic, key, msg);
                logger.info("severs = {} | topic = {} | partition = {} | msg id ={} | msg = {}",
                        loadConfig.getBrokerAddrList(), topic, key, subscribeData.getSubscribeId(), msg);
            } catch (Exception e) {
                logger.error("sen message to zookeeper error ! {}", ExceptionUtils.getFullStackTrace(e));
                throw e;
            }finally {
                MDC.remove(HamsterConst.splitLoaderLogFileKey);
            }
        }

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
