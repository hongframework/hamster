package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsDetail;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsDetail_Example;


public interface ICfgStatisticsDetailSV   {

  
    /**
    * 创建流量统计明细
    * @param cfgStatisticsDetail
    * @return
    * @throws Exception
    */
    public int create(CfgStatisticsDetail cfgStatisticsDetail) throws  Exception;

    /**
    * 批量维护流量统计明细
    * @param cfgStatisticsDetails
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgStatisticsDetail[] cfgStatisticsDetails) throws  Exception;
    /**
    * 更新流量统计明细
    * @param cfgStatisticsDetail
    * @return
    * @throws Exception
    */
    public int update(CfgStatisticsDetail cfgStatisticsDetail) throws  Exception;

    /**
    * 通过查询对象更新流量统计明细
    * @param cfgStatisticsDetail
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgStatisticsDetail cfgStatisticsDetail, CfgStatisticsDetail_Example example) throws  Exception;

    /**
    * 删除流量统计明细
    * @param cfgStatisticsDetail
    * @return
    * @throws Exception
    */
    public int delete(CfgStatisticsDetail cfgStatisticsDetail) throws  Exception;


    /**
    * 删除流量统计明细
    * @param cfgStatisticsDetailId
    * @return
    * @throws Exception
    */
    public int delete(long cfgStatisticsDetailId) throws  Exception;


    /**
    * 查找所有流量统计明细
    * @return
    */
    public List<CfgStatisticsDetail> getCfgStatisticsDetailAll()  throws  Exception;

    /**
    * 通过流量统计明细ID查询流量统计明细
    * @param cfgStatisticsDetailId
    * @return
    * @throws Exception
    */
    public CfgStatisticsDetail getCfgStatisticsDetailByPK(long cfgStatisticsDetailId)  throws  Exception;

    /**
    * 通过MAP参数查询流量统计明细
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgStatisticsDetail> getCfgStatisticsDetailListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询流量统计明细
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgStatisticsDetail> getCfgStatisticsDetailListByExample(CfgStatisticsDetail_Example example) throws  Exception;


    /**
    * 通过MAP参数查询流量统计明细数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsDetailCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询流量统计明细数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsDetailCountByExample(CfgStatisticsDetail_Example example) throws  Exception;


 }
