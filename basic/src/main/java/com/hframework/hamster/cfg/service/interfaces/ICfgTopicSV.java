package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgTopic;
import com.hframework.hamster.cfg.domain.model.CfgTopic_Example;


public interface ICfgTopicSV   {

  
    /**
    * 创建主题
    * @param cfgTopic
    * @return
    * @throws Exception
    */
    public int create(CfgTopic cfgTopic) throws  Exception;

    /**
    * 批量维护主题
    * @param cfgTopics
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgTopic[] cfgTopics) throws  Exception;
    /**
    * 更新主题
    * @param cfgTopic
    * @return
    * @throws Exception
    */
    public int update(CfgTopic cfgTopic) throws  Exception;

    /**
    * 通过查询对象更新主题
    * @param cfgTopic
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgTopic cfgTopic, CfgTopic_Example example) throws  Exception;

    /**
    * 删除主题
    * @param cfgTopic
    * @return
    * @throws Exception
    */
    public int delete(CfgTopic cfgTopic) throws  Exception;


    /**
    * 删除主题
    * @param cfgTopicId
    * @return
    * @throws Exception
    */
    public int delete(long cfgTopicId) throws  Exception;


    /**
    * 查找所有主题
    * @return
    */
    public List<CfgTopic> getCfgTopicAll()  throws  Exception;

    /**
    * 通过主题ID查询主题
    * @param cfgTopicId
    * @return
    * @throws Exception
    */
    public CfgTopic getCfgTopicByPK(long cfgTopicId)  throws  Exception;

    /**
    * 通过MAP参数查询主题
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgTopic> getCfgTopicListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询主题
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgTopic> getCfgTopicListByExample(CfgTopic_Example example) throws  Exception;


    /**
    * 通过MAP参数查询主题数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgTopicCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询主题数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgTopicCountByExample(CfgTopic_Example example) throws  Exception;


 }
