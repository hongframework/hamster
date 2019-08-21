package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgSubscribe;
import com.hframework.hamster.cfg.domain.model.CfgSubscribe_Example;


public interface ICfgSubscribeSV   {

  
    /**
    * 创建订阅
    * @param cfgSubscribe
    * @return
    * @throws Exception
    */
    public int create(CfgSubscribe cfgSubscribe) throws  Exception;

    /**
    * 批量维护订阅
    * @param cfgSubscribes
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgSubscribe[] cfgSubscribes) throws  Exception;
    /**
    * 更新订阅
    * @param cfgSubscribe
    * @return
    * @throws Exception
    */
    public int update(CfgSubscribe cfgSubscribe) throws  Exception;

    /**
    * 通过查询对象更新订阅
    * @param cfgSubscribe
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgSubscribe cfgSubscribe, CfgSubscribe_Example example) throws  Exception;

    /**
    * 删除订阅
    * @param cfgSubscribe
    * @return
    * @throws Exception
    */
    public int delete(CfgSubscribe cfgSubscribe) throws  Exception;


    /**
    * 删除订阅
    * @param cfgSubscribeId
    * @return
    * @throws Exception
    */
    public int delete(long cfgSubscribeId) throws  Exception;


    /**
    * 查找所有订阅
    * @return
    */
    public List<CfgSubscribe> getCfgSubscribeAll()  throws  Exception;

    /**
    * 通过订阅ID查询订阅
    * @param cfgSubscribeId
    * @return
    * @throws Exception
    */
    public CfgSubscribe getCfgSubscribeByPK(long cfgSubscribeId)  throws  Exception;

    /**
    * 通过MAP参数查询订阅
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgSubscribe> getCfgSubscribeListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询订阅
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgSubscribe> getCfgSubscribeListByExample(CfgSubscribe_Example example) throws  Exception;


    /**
    * 通过MAP参数查询订阅数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询订阅数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeCountByExample(CfgSubscribe_Example example) throws  Exception;


 }
