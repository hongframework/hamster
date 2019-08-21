package com.hframe.ext.bean;

import com.hframework.hamster.cfg.domain.model.CfgBroker;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail;
import com.hframework.hamster.cfg.domain.model.CfgTopic;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangquanhong on 2016/10/9.
 */
public class SubscribeConfig {

    private CfgBroker cfgBroker;
    private Map<String, List<CfgSubscribeDetail>> cfgSubscribeDetailInfo;
    private Map<Long, CfgTopic> cfgTopicInfo;

    public CfgBroker getCfgBroker() {
        return cfgBroker;
    }

    public SubscribeConfig(CfgBroker cfgBroker, Map<String, List<CfgSubscribeDetail>> cfgSubscribeDetailInfo, Map<Long, CfgTopic> cfgTopicInfo) {
        this.cfgBroker = cfgBroker;
        this.cfgSubscribeDetailInfo = cfgSubscribeDetailInfo;
        this.cfgTopicInfo = cfgTopicInfo;
    }

    public void setCfgBroker(CfgBroker cfgBroker) {
        this.cfgBroker = cfgBroker;
    }

    public Map<String, List<CfgSubscribeDetail>> getCfgSubscribeDetailInfo() {
        return cfgSubscribeDetailInfo;
    }

    public void setCfgSubscribeDetailInfo(Map<String, List<CfgSubscribeDetail>> cfgSubscribeDetailInfo) {
        this.cfgSubscribeDetailInfo = cfgSubscribeDetailInfo;
    }

    public Map<Long, CfgTopic> getCfgTopicInfo() {
        return cfgTopicInfo;
    }

    public void setCfgTopicInfo(Map<Long, CfgTopic> cfgTopicInfo) {
        this.cfgTopicInfo = cfgTopicInfo;
    }
}
