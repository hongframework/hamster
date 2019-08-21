package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsTopic;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsTopic_Example;


public interface ICfgStatisticsTopicSV   {

  
    /**
    * 创建统计主题
    * @param cfgStatisticsTopic
    * @return
    * @throws Exception
    */
    public int create(CfgStatisticsTopic cfgStatisticsTopic) throws  Exception;

    /**
    * 批量维护统计主题
    * @param cfgStatisticsTopics
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgStatisticsTopic[] cfgStatisticsTopics) throws  Exception;
    /**
    * 更新统计主题
    * @param cfgStatisticsTopic
    * @return
    * @throws Exception
    */
    public int update(CfgStatisticsTopic cfgStatisticsTopic) throws  Exception;

    /**
    * 通过查询对象更新统计主题
    * @param cfgStatisticsTopic
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgStatisticsTopic cfgStatisticsTopic, CfgStatisticsTopic_Example example) throws  Exception;

    /**
    * 删除统计主题
    * @param cfgStatisticsTopic
    * @return
    * @throws Exception
    */
    public int delete(CfgStatisticsTopic cfgStatisticsTopic) throws  Exception;


    /**
    * 删除统计主题
    * @param cfgStatisticsTopicId
    * @return
    * @throws Exception
    */
    public int delete(long cfgStatisticsTopicId) throws  Exception;


    /**
    * 查找所有统计主题
    * @return
    */
    public List<CfgStatisticsTopic> getCfgStatisticsTopicAll()  throws  Exception;

    /**
    * 通过统计主题ID查询统计主题
    * @param cfgStatisticsTopicId
    * @return
    * @throws Exception
    */
    public CfgStatisticsTopic getCfgStatisticsTopicByPK(long cfgStatisticsTopicId)  throws  Exception;

    /**
    * 通过MAP参数查询统计主题
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgStatisticsTopic> getCfgStatisticsTopicListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询统计主题
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgStatisticsTopic> getCfgStatisticsTopicListByExample(CfgStatisticsTopic_Example example) throws  Exception;


    /**
    * 通过MAP参数查询统计主题数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsTopicCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询统计主题数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsTopicCountByExample(CfgStatisticsTopic_Example example) throws  Exception;


 }
