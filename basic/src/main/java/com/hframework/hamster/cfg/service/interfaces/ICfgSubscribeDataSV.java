package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeData;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeData_Example;


public interface ICfgSubscribeDataSV   {

  
    /**
    * 创建订阅数据
    * @param cfgSubscribeData
    * @return
    * @throws Exception
    */
    public int create(CfgSubscribeData cfgSubscribeData) throws  Exception;

    /**
    * 批量维护订阅数据
    * @param cfgSubscribeDatas
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgSubscribeData[] cfgSubscribeDatas) throws  Exception;
    /**
    * 更新订阅数据
    * @param cfgSubscribeData
    * @return
    * @throws Exception
    */
    public int update(CfgSubscribeData cfgSubscribeData) throws  Exception;

    /**
    * 通过查询对象更新订阅数据
    * @param cfgSubscribeData
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgSubscribeData cfgSubscribeData, CfgSubscribeData_Example example) throws  Exception;

    /**
    * 删除订阅数据
    * @param cfgSubscribeData
    * @return
    * @throws Exception
    */
    public int delete(CfgSubscribeData cfgSubscribeData) throws  Exception;


    /**
    * 删除订阅数据
    * @param cfgSubscribeDataId
    * @return
    * @throws Exception
    */
    public int delete(long cfgSubscribeDataId) throws  Exception;


    /**
    * 查找所有订阅数据
    * @return
    */
    public List<CfgSubscribeData> getCfgSubscribeDataAll()  throws  Exception;

    /**
    * 通过订阅数据ID查询订阅数据
    * @param cfgSubscribeDataId
    * @return
    * @throws Exception
    */
    public CfgSubscribeData getCfgSubscribeDataByPK(long cfgSubscribeDataId)  throws  Exception;

    /**
    * 通过MAP参数查询订阅数据
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgSubscribeData> getCfgSubscribeDataListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询订阅数据
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgSubscribeData> getCfgSubscribeDataListByExample(CfgSubscribeData_Example example) throws  Exception;


    /**
    * 通过MAP参数查询订阅数据数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeDataCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询订阅数据数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeDataCountByExample(CfgSubscribeData_Example example) throws  Exception;


 }
