package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgStatistics;
import com.hframework.hamster.cfg.domain.model.CfgStatistics_Example;


public interface ICfgStatisticsSV   {

  
    /**
    * 创建流量统计
    * @param cfgStatistics
    * @return
    * @throws Exception
    */
    public int create(CfgStatistics cfgStatistics) throws  Exception;

    /**
    * 批量维护流量统计
    * @param cfgStatisticss
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgStatistics[] cfgStatisticss) throws  Exception;
    /**
    * 更新流量统计
    * @param cfgStatistics
    * @return
    * @throws Exception
    */
    public int update(CfgStatistics cfgStatistics) throws  Exception;

    /**
    * 通过查询对象更新流量统计
    * @param cfgStatistics
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgStatistics cfgStatistics, CfgStatistics_Example example) throws  Exception;

    /**
    * 删除流量统计
    * @param cfgStatistics
    * @return
    * @throws Exception
    */
    public int delete(CfgStatistics cfgStatistics) throws  Exception;


    /**
    * 删除流量统计
    * @param cfgStatisticsId
    * @return
    * @throws Exception
    */
    public int delete(long cfgStatisticsId) throws  Exception;


    /**
    * 查找所有流量统计
    * @return
    */
    public List<CfgStatistics> getCfgStatisticsAll()  throws  Exception;

    /**
    * 通过流量统计ID查询流量统计
    * @param cfgStatisticsId
    * @return
    * @throws Exception
    */
    public CfgStatistics getCfgStatisticsByPK(long cfgStatisticsId)  throws  Exception;

    /**
    * 通过MAP参数查询流量统计
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgStatistics> getCfgStatisticsListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询流量统计
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgStatistics> getCfgStatisticsListByExample(CfgStatistics_Example example) throws  Exception;


    /**
    * 通过MAP参数查询流量统计数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询流量统计数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsCountByExample(CfgStatistics_Example example) throws  Exception;


 }
