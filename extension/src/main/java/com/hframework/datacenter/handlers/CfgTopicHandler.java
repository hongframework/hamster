package com.hframework.datacenter.handlers;


import com.hframework.common.client.kafka.KafkaService;
import com.hframework.web.extension.AbstractBusinessHandler;
import com.hframework.web.extension.annotation.AfterCreateHandler;
import com.hframework.web.extension.annotation.BeforeDeleteHandler;
import com.hframework.web.extension.annotation.BeforeUpdateHandler;
import com.hframework.hamster.cfg.domain.model.CfgBroker;
import com.hframework.hamster.cfg.domain.model.CfgTopic;
import com.hframework.hamster.cfg.service.interfaces.ICfgBrokerSV;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangquanhong on 2016/9/23.
 */
@Service
public class CfgTopicHandler extends AbstractBusinessHandler<CfgTopic> {

    private static final Logger logger = LoggerFactory.getLogger(CfgTopicHandler.class);

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private ICfgBrokerSV cfgBrokerSV;

    @AfterCreateHandler
    public boolean afterCreate(final CfgTopic topic) {
        logger.info("create broker topic ... ");
        try{
            final CfgBroker cfgBroker = cfgBrokerSV.getCfgBrokerByPK(topic.getCfgBrokerId());
            logger.info("create broker topic, zklist = {}, topick = {}, partitions = {}, replicas = {}",
                    cfgBroker.getZkAddrList(), topic.getCfgTopicCode(), topic.getPartitions(), topic.getReplicas());
            kafkaService.createTopic(cfgBroker.getZkAddrList(), topic.getCfgTopicCode(), topic.getPartitions(), topic.getReplicas());
            logger.info("create broker topic success ! ");
        }catch (Exception e) {
            logger.error("create broker topic failed => {}", ExceptionUtils.getFullStackTrace(e));
        }

        return false;
    }

    @BeforeUpdateHandler(attr = "status", orig = "1" , target = "2")
    public boolean topicInActive(CfgTopic topic, CfgTopic origTopic) {
        System.out.println("===> update !");
        return false;
    }

    @BeforeUpdateHandler(attr = "status", orig = "2" , target = "1")
    public boolean topicActive(CfgTopic topic, CfgTopic origTopic) {
        System.out.println("===> update !");
        return false;
    }

    @BeforeDeleteHandler
    public boolean afterDelete(CfgTopic topic) {
        System.out.println("===> delete !");
        return false;
    }

    public static void main(String[] args) {
       int x = 0, y = 0;
        x = (y = 1) + 1;
        System.out.println(x + "," + y );

    }
}
