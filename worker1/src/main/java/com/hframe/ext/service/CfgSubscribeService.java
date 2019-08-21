package com.hframe.ext.service;

import com.alibaba.otter.canal.instance.manager.model.CanalParameter;
import com.google.common.base.Joiner;
import com.hframe.ext.bean.CanalConfig;
import com.hframe.ext.bean.ExtractConfig;
import com.hframe.ext.bean.MQConfig;
import com.hframe.ext.bean.TransformConfig;
import com.hframe.hamster.node.HamsterConfig;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import com.hframework.common.util.collect.bean.Mapper;
import com.hframework.hamster.cfg.domain.model.*;
import com.hframework.hamster.cfg.service.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangquanhong on 2016/10/9.
 */
@Service
public class CfgSubscribeService {

    private static final Logger logger = LoggerFactory.getLogger(CfgSubscribeService.class);

    @Resource
    private ICfgSubscribeSV cfgSubscribeSV;

    @Resource
    private ICfgSubscribeDataSV cfgSubscribeDataSV;

    @Resource
    private ICfgDatasourceSV cfgDatasourceSV;

    @Resource
    private ICfgSubscribeDetailSV cfgSubscribeDetailSV;

    @Resource
    private ICfgTopicSV cfgTopicSV;

    @Resource
    private ICfgBrokerSV cfgBrokerSV;

    public List<ExtractConfig> getExtractConfigs(Long subscribeId) throws Exception {
        Assert.notNull(subscribeId);

        //获取订阅明细
        CfgSubscribeDetail_Example detailExample = new CfgSubscribeDetail_Example();
        detailExample.createCriteria().andCfgSubscribeIdEqualTo(subscribeId);
        List<CfgSubscribeDetail> cfgSubscribeDetailList = cfgSubscribeDetailSV.getCfgSubscribeDetailListByExample(detailExample);

        return CollectionUtils.fetch(cfgSubscribeDetailList, new Fetcher<CfgSubscribeDetail, ExtractConfig>() {
            @Override
            public ExtractConfig fetch(CfgSubscribeDetail cfgSubscribeDetail) {
                ExtractConfig extractConfig = new ExtractConfig();
                extractConfig.setConfigId(cfgSubscribeDetail.getCfgSubscribeDetailId());
                extractConfig.setDbObjectName(cfgSubscribeDetail.getDbObjectName());
                extractConfig.setDbObjectOperateType(cfgSubscribeDetail.getDbObjectOperateType());
                extractConfig.setDataFilterExpression(cfgSubscribeDetail.getDataFilterExpression());
                return extractConfig;
            }
        });
    }

    public List<TransformConfig> getTransformConfigs(Long subscribeId) throws Exception {
        Assert.notNull(subscribeId);

        //获取订阅明细
        CfgSubscribeDetail_Example detailExample = new CfgSubscribeDetail_Example();
        detailExample.createCriteria().andCfgSubscribeIdEqualTo(subscribeId);
        List<CfgSubscribeDetail> cfgSubscribeDetailList = cfgSubscribeDetailSV.getCfgSubscribeDetailListByExample(detailExample);

        return CollectionUtils.fetch(cfgSubscribeDetailList, new Fetcher<CfgSubscribeDetail, TransformConfig>() {
            @Override
            public TransformConfig fetch(CfgSubscribeDetail cfgSubscribeDetail) {
                TransformConfig transformConfig = new TransformConfig();
                transformConfig.setConfigId(cfgSubscribeDetail.getCfgSubscribeDetailId());
                transformConfig.setDbObjectName(cfgSubscribeDetail.getDbObjectName());
                transformConfig.setDbObjectDatas(cfgSubscribeDetail.getDbObjectDatas());
                return transformConfig;
            }
        });
    }

    public List<MQConfig> getLoadConfigs(Long subscribeId) throws Exception {
        Assert.notNull(subscribeId);
        //获取订阅信息
        final CfgSubscribe cfgSubscribe = cfgSubscribeSV.getCfgSubscribeByPK(subscribeId);

        //获取订阅明细
        CfgSubscribeDetail_Example detailExample = new CfgSubscribeDetail_Example();
        detailExample.createCriteria().andCfgSubscribeIdEqualTo(subscribeId);
        List<CfgSubscribeDetail> cfgSubscribeDetailList = cfgSubscribeDetailSV.getCfgSubscribeDetailListByExample(detailExample);

        Assert.notNull(cfgSubscribe.getCfgBrokerId());
        final CfgBroker cfgBroker = cfgBrokerSV.getCfgBrokerByPK(cfgSubscribe.getCfgBrokerId());


        CfgTopic_Example cfgTopicExample = new CfgTopic_Example();
        cfgTopicExample.createCriteria().andCfgTopicIdIn(CollectionUtils.fetch(cfgSubscribeDetailList, new Fetcher<CfgSubscribeDetail, Long>() {
            @Override
            public Long fetch(CfgSubscribeDetail cfgSubscribeDetail) {
                return cfgSubscribeDetail.getCfgTopicId();
            }
        }));
        List<CfgTopic> cfgTopicList = cfgTopicSV.getCfgTopicListByExample(cfgTopicExample);
        final Map<Long, CfgTopic> cfgTopicInfo = CollectionUtils.convert(cfgTopicList, new Mapper<Long, CfgTopic>() {
            @Override
            public <K> K getKey(CfgTopic topic) {
                return (K) topic.getCfgTopicId();
            }
        });

        return CollectionUtils.fetch(cfgSubscribeDetailList, new Fetcher<CfgSubscribeDetail, MQConfig>() {
            @Override
            public MQConfig fetch(CfgSubscribeDetail cfgSubscribeDetail) {
                MQConfig loadConfig = new MQConfig();
                loadConfig.setConfigId(cfgSubscribeDetail.getCfgSubscribeDetailId());
                loadConfig.setPartitionKey(cfgSubscribeDetail.getPartitionKey());
                loadConfig.setPartitionStrategy(cfgSubscribeDetail.getPartitionStrategy());

                loadConfig.setBrokerAddrList(cfgBroker.getAddrList());
                loadConfig.setCfgTopicCode(cfgTopicInfo.get(cfgSubscribeDetail.getCfgTopicId()).getCfgTopicCode());
                loadConfig.setPartitions(cfgTopicInfo.get(cfgSubscribeDetail.getCfgTopicId()).getPartitions());
                return loadConfig;
            }
        });
    }

//    public SubscribeConfig getSubscribeConfigBySubscribeId(Long subscribeId) throws Exception {
//        Assert.notNull(subscribeId);
//        //获取订阅信息
//        CfgSubscribe cfgSubscribe = cfgSubscribeSV.getCfgSubscribeByPK(subscribeId);
//
//        //获取订阅明细
//        CfgSubscribeDetail_Example detailExample = new CfgSubscribeDetail_Example();
//        detailExample.createCriteria().andCfgSubscribeIdEqualTo(subscribeId);
//        List<CfgSubscribeDetail> cfgSubscribeDetailList = cfgSubscribeDetailSV.getCfgSubscribeDetailListByExample(detailExample);
//
//        Assert.notNull(cfgSubscribe.getCfgBrokerId());
//        CfgBroker cfgBroker = cfgBrokerSV.getCfgBrokerByPK(cfgSubscribe.getCfgBrokerId());
//
//        List<Long> topicIds = new ArrayList<>();
//        Map<String, List<CfgSubscribeDetail>> cfgSubscribeDetailInfo = new HashMap<>();
//        for (CfgSubscribeDetail cfgSubscribeDetail : cfgSubscribeDetailList) {
//            String dbObjectName = cfgSubscribeDetail.getDbObjectName();
//            if(!cfgSubscribeDetailInfo.containsKey(dbObjectName)){
//                cfgSubscribeDetailInfo.put(dbObjectName, new ArrayList<CfgSubscribeDetail>());
//            }
//            cfgSubscribeDetailInfo.get(dbObjectName).add(cfgSubscribeDetail);
//            topicIds.add(cfgSubscribeDetail.getCfgTopicId());
//        }
//
//        CfgTopic_Example cfgTopicExample = new CfgTopic_Example();
//        cfgTopicExample.createCriteria().andCfgTopicIdIn(topicIds);
//        List<CfgTopic> cfgTopicList = cfgTopicSV.getCfgTopicListByExample(cfgTopicExample);
//        Map<Long, CfgTopic> cfgTopicInfo = CollectionUtils.convert(cfgTopicList, new Mapper<Long, CfgTopic>() {
//            @Override
//            public <K> K getKey(CfgTopic topic) {
//                return (K) topic.getCfgTopicId();
//            }
//        });
//
//        return new SubscribeConfig(cfgBroker, cfgSubscribeDetailInfo, cfgTopicInfo);
//    }

    public CanalConfig getCanalConfig(Long subscribeId) throws Exception {

        //获取订阅信息
        final CfgSubscribe cfgSubscribe = cfgSubscribeSV.getCfgSubscribeByPK(subscribeId);

        //获取数据源
        Long cfgDatasourceId = cfgSubscribe.getCfgDatasourceId();
        final CfgDatasource cfgDatasource = cfgDatasourceSV.getCfgDatasourceByPK(cfgDatasourceId);

        //获取订阅明细
        CfgSubscribeDetail_Example detailExample = new CfgSubscribeDetail_Example();
        detailExample.createCriteria().andCfgSubscribeIdEqualTo(subscribeId);
        List<CfgSubscribeDetail> cfgSubscribeDetailList = cfgSubscribeDetailSV.getCfgSubscribeDetailListByExample(detailExample);

        String destination = cfgDatasourceId + "-" +  cfgSubscribe.getCfgSubscribeId();

        List<String> filterItems = CollectionUtils.fetch(cfgSubscribeDetailList, new Fetcher<CfgSubscribeDetail, String>() {
            @Override
            public String fetch(CfgSubscribeDetail cfgSubscribeDetail) {
                return cfgDatasource.getDb() +  "." + cfgSubscribeDetail.getDbObjectName();
            }
        });
        String filter = Joiner.on(",").join(filterItems);

        String url = cfgDatasource.getUrl();
        String address = url.substring(0, url.lastIndexOf(":"));
        final int port = Integer.parseInt(url.substring(url.lastIndexOf(":") + 1));
        List<String> zkAddress = Arrays.asList(HamsterConfig.getInstance().getZkServers());

        CanalConfig canalConfig = new CanalConfig();
        canalConfig.setCanalId(Long.valueOf(cfgDatasourceId + "" + cfgSubscribe.getCfgSubscribeId()));
        canalConfig.setDestination(destination);
        canalConfig.setFilter(filter);
        canalConfig.setClientId(Short.valueOf(cfgSubscribe.getCfgSubscribeId() + ""));


        CanalParameter canalParameter = new CanalParameter();
        canalConfig.setCanalParameter(canalParameter);
        //设置canal基本信息
        canalParameter.setCanalId(canalConfig.getCanalId());
        canalParameter.setSlaveId(cfgSubscribe.getCfgSubscribeId());
        canalParameter.setIndexMode(CanalParameter.IndexMode.MEMORY_META_FAILBACK);
        canalParameter.setMetaMode(CanalParameter.MetaMode.ZOOKEEPER);//如果选择MIXED模式，下次重启是，是否存在一秒钟的延迟？
        canalParameter.setZkClusters(zkAddress);

        //设置数据库连接信息
        canalParameter.setMasterAddress(new InetSocketAddress(address, port));
        canalParameter.setDefaultDatabaseName(cfgDatasource.getDb());
        canalParameter.setDbUsername(cfgDatasource.getUsername());
        canalParameter.setDbPassword(cfgDatasource.getPassword());

        //设置数据过滤信息
        canalParameter.setDdlIsolation(false);
        canalParameter.setMasterLogfileName(cfgSubscribe.getLogBeginFile());
        canalParameter.setMasterLogfileOffest(cfgSubscribe.getLogBeginPosition());
        if(cfgSubscribe.getLogBeginTimestamp() != null){
            canalParameter.setMasterTimestamp(cfgSubscribe.getLogBeginTimestamp().getTime());
        }
//        final InetSocketAddress inetSocketAddress = new InetSocketAddress(address, port);
//        canalParameter.setMasterAddress(inetSocketAddress);
//        canalParameter.setDbAddresses(new ArrayList<InetSocketAddress>() {{
//            add(inetSocketAddress);
//        }});


//
//        Long logEndPosition = cfgSubscribe.getLogEndPosition();
//        Date logEndTimestamp = cfgSubscribe.getLogEndTimestamp();

        return canalConfig;
    }

}
