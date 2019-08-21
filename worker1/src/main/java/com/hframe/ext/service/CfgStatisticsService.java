package com.hframe.ext.service;

import com.alibaba.otter.canal.instance.manager.model.CanalParameter;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.hframe.ext.bean.CanalConfig;
import com.hframe.ext.bean.ExtractConfig;
import com.hframe.ext.bean.HBaseConfig;
import com.hframe.ext.bean.StatisticsConfig;
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
public class CfgStatisticsService {

    private static final Logger logger = LoggerFactory.getLogger(CfgStatisticsService.class);

    @Resource
    private ICfgStatisticsSV cfgStatisticsSV;

    @Resource
    private ICfgStatisticsTopicSV cfgStatisticsTopicSV;

    @Resource
    private ICfgStatisticsDetailSV cfgStatisticsDetailSV;


    @Resource
    private ICfgDatasourceSV cfgDatasourceSV;


    @Resource
    private ICfgBrokerSV cfgBrokerSV;


    public CanalConfig getCanalConfig(Long statisticsId) throws Exception {

        //获取统计信息
        final CfgStatistics cfgStatistics = cfgStatisticsSV.getCfgStatisticsByPK(statisticsId);

        //获取数据源
        Long cfgDatasourceId = cfgStatistics.getCfgDatasourceId();
        final CfgDatasource cfgDatasource = cfgDatasourceSV.getCfgDatasourceByPK(cfgDatasourceId);

        //获取订阅明细
        CfgStatisticsDetail_Example detailExample = new CfgStatisticsDetail_Example();
        detailExample.createCriteria().andCfgStatisticsIdEqualTo(statisticsId);
        List<CfgStatisticsDetail> cfgStatisticsDetailList =
                cfgStatisticsDetailSV.getCfgStatisticsDetailListByExample(detailExample);

        String destination = "statistics-"  +  cfgDatasourceId + "-" +  statisticsId;

        List<String> filterItems = CollectionUtils.fetch(cfgStatisticsDetailList, new Fetcher<CfgStatisticsDetail, String>() {
            @Override
            public String fetch(CfgStatisticsDetail cfgStatisticsDetail) {
                return cfgDatasource.getDb() +  "." + cfgStatisticsDetail.getDbObjectName();
            }
        });
        String filter = Joiner.on(",").join(filterItems);

        String url = cfgDatasource.getUrl();
        String address = url.substring(0, url.lastIndexOf(":"));
        final int port = Integer.parseInt(url.substring(url.lastIndexOf(":") + 1));
        List<String> zkAddress = Arrays.asList(HamsterConfig.getInstance().getZkServers());

        CanalConfig canalConfig = new CanalConfig();
        canalConfig.setCanalId(Long.valueOf(cfgDatasourceId + "" + statisticsId));
        canalConfig.setDestination(destination);
        canalConfig.setFilter(filter);
        canalConfig.setClientId(Short.valueOf(statisticsId + ""));


        CanalParameter canalParameter = new CanalParameter();
        canalConfig.setCanalParameter(canalParameter);
        //设置canal基本信息
        canalParameter.setCanalId(canalConfig.getCanalId());
        canalParameter.setSlaveId(statisticsId);
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
        canalParameter.setMasterLogfileName(cfgStatistics.getLogBeginFile());
        canalParameter.setMasterLogfileOffest(cfgStatistics.getLogBeginPosition());
        if(cfgStatistics.getLogBeginTimestamp() != null){
            canalParameter.setMasterTimestamp(cfgStatistics.getLogBeginTimestamp().getTime());
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

    public List<ExtractConfig> getExtractConfigs(Long statisticsId) throws Exception {
        Assert.notNull(statisticsId);

        //获取订阅明细
        CfgStatisticsDetail_Example detailExample = new CfgStatisticsDetail_Example();
        detailExample.createCriteria().andCfgStatisticsIdEqualTo(statisticsId);
        List<CfgStatisticsDetail> cfgStatisticsDetailList =
                cfgStatisticsDetailSV.getCfgStatisticsDetailListByExample(detailExample);

        return CollectionUtils.fetch(cfgStatisticsDetailList, new Fetcher<CfgStatisticsDetail, ExtractConfig>() {
            @Override
            public ExtractConfig fetch(CfgStatisticsDetail cfgStatisticsDetail) {
                ExtractConfig extractConfig = new ExtractConfig();
                extractConfig.setConfigId(cfgStatisticsDetail.getCfgStatisticsDetailId());
                extractConfig.setDbObjectName(cfgStatisticsDetail.getDbObjectName());
                extractConfig.setDbObjectOperateType(cfgStatisticsDetail.getDbObjectOperateType());
                extractConfig.setDataFilterExpression(cfgStatisticsDetail.getDataFilterExpression());
                return extractConfig;
            }
        });
    }

    public List<StatisticsConfig> getStatisticsConfigs(Long statisticsId) throws Exception {
        Assert.notNull(statisticsId);

        //获取订阅明细
        CfgStatisticsDetail_Example detailExample = new CfgStatisticsDetail_Example();
        detailExample.createCriteria().andCfgStatisticsIdEqualTo(statisticsId);
        List<CfgStatisticsDetail> cfgStatisticsDetailList =
                cfgStatisticsDetailSV.getCfgStatisticsDetailListByExample(detailExample);

        return CollectionUtils.fetch(cfgStatisticsDetailList, new Fetcher<CfgStatisticsDetail, StatisticsConfig>() {
            @Override
            public StatisticsConfig fetch(CfgStatisticsDetail cfgStatisticsDetail) {
                StatisticsConfig statisticsConfig = new StatisticsConfig();
                statisticsConfig.setConfigId(cfgStatisticsDetail.getCfgStatisticsDetailId());
                statisticsConfig.setStatisticsView(cfgStatisticsDetail.getStatisticsView());
                statisticsConfig.setStatisticsValue(cfgStatisticsDetail.getStatisticsValue());
                statisticsConfig.setStatisticsScript(cfgStatisticsDetail.getStatisticsScript());
                statisticsConfig.setCfgStatisticsTopicId(cfgStatisticsDetail.getCfgStatisticsTopicId());
                return statisticsConfig;
            }
        });
    }



    public List<HBaseConfig> getLoadConfigs(Long statisticsId) throws Exception {
        Assert.notNull(statisticsId);
//        //获取统计信息
//        final CfgStatistics cfgStatistics = cfgStatisticsSV.getCfgStatisticsByPK(statisticsId);

        //获取订阅明细
        CfgStatisticsDetail_Example detailExample = new CfgStatisticsDetail_Example();
        detailExample.createCriteria().andCfgStatisticsIdEqualTo(statisticsId);
        List<CfgStatisticsDetail> cfgStatisticsDetailList =
                cfgStatisticsDetailSV.getCfgStatisticsDetailListByExample(detailExample);

//        Assert.notNull(cfgSubscribe.getCfgBrokerId());
//        final CfgBroker cfgBroker = cfgBrokerSV.getCfgBrokerByPK(cfgSubscribe.getCfgBrokerId());

        CfgStatisticsTopic_Example cfgTopicExample = new CfgStatisticsTopic_Example();
        cfgTopicExample.createCriteria().andCfgStatisticsTopicIdIn(CollectionUtils.fetch(cfgStatisticsDetailList, new Fetcher<CfgStatisticsDetail, Long>() {
            @Override
            public Long fetch(CfgStatisticsDetail cfgStatisticsDetail) {
                return cfgStatisticsDetail.getCfgStatisticsTopicId();
            }
        }));
        List<CfgStatisticsTopic> cfgTopicList = cfgStatisticsTopicSV.getCfgStatisticsTopicListByExample(cfgTopicExample);
        final Map<Long, CfgStatisticsTopic> cfgTopicInfo = CollectionUtils.convert(cfgTopicList, new Mapper<Long, CfgStatisticsTopic>() {
            @Override
            public <K> K getKey(CfgStatisticsTopic topic) {
                return (K) topic.getCfgStatisticsTopicId();
            }
        });

        return CollectionUtils.fetch(cfgStatisticsDetailList, new Fetcher<CfgStatisticsDetail, HBaseConfig>() {
            @Override
            public HBaseConfig fetch(CfgStatisticsDetail cfgStatisticsDetail) {
                HBaseConfig hBaseConfig = new HBaseConfig();
                hBaseConfig.setConfigId(cfgStatisticsDetail.getCfgStatisticsDetailId());
//                hBaseConfig.setZkList("node1,node2,node3");//TODO
                hBaseConfig.setTableName("hamster_statistics_runtime");//TODO
                hBaseConfig.setRowKeyword(cfgTopicInfo.get(cfgStatisticsDetail.getCfgStatisticsTopicId()).getCfgStatisticsTopicCode());
                hBaseConfig.setRowKeywordConfigId(cfgStatisticsDetail.getCfgStatisticsTopicId());
                return hBaseConfig;
            }
        });
    }

    public List<HBaseConfig> getSystemHBaseConfig() throws Exception {
        HBaseConfig hBaseConfig = new HBaseConfig();
//                hBaseConfig.setZkList("node1,node2,node3");//TODO
        hBaseConfig.setTableName("hamster_statistics_runtime");//TODO
        return Lists.newArrayList(hBaseConfig);
    }


}
