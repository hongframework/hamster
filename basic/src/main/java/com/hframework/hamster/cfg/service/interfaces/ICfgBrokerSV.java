package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgBroker;
import com.hframework.hamster.cfg.domain.model.CfgBroker_Example;


public interface ICfgBrokerSV   {

  
    /**
    * 创建消息队列
    * @param cfgBroker
    * @return
    * @throws Exception
    */
    public int create(CfgBroker cfgBroker) throws  Exception;

    /**
    * 批量维护消息队列
    * @param cfgBrokers
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgBroker[] cfgBrokers) throws  Exception;
    /**
    * 更新消息队列
    * @param cfgBroker
    * @return
    * @throws Exception
    */
    public int update(CfgBroker cfgBroker) throws  Exception;

    /**
    * 通过查询对象更新消息队列
    * @param cfgBroker
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgBroker cfgBroker, CfgBroker_Example example) throws  Exception;

    /**
    * 删除消息队列
    * @param cfgBroker
    * @return
    * @throws Exception
    */
    public int delete(CfgBroker cfgBroker) throws  Exception;


    /**
    * 删除消息队列
    * @param cfgBrokerId
    * @return
    * @throws Exception
    */
    public int delete(long cfgBrokerId) throws  Exception;


    /**
    * 查找所有消息队列
    * @return
    */
    public List<CfgBroker> getCfgBrokerAll()  throws  Exception;

    /**
    * 通过消息队列ID查询消息队列
    * @param cfgBrokerId
    * @return
    * @throws Exception
    */
    public CfgBroker getCfgBrokerByPK(long cfgBrokerId)  throws  Exception;

    /**
    * 通过MAP参数查询消息队列
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgBroker> getCfgBrokerListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询消息队列
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgBroker> getCfgBrokerListByExample(CfgBroker_Example example) throws  Exception;


    /**
    * 通过MAP参数查询消息队列数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgBrokerCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询消息队列数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgBrokerCountByExample(CfgBroker_Example example) throws  Exception;


 }
