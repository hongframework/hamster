package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgJobAttr;
import com.hframework.hamster.cfg.domain.model.CfgJobAttr_Example;


public interface ICfgJobAttrSV   {

  
    /**
    * 创建任务属性定义
    * @param cfgJobAttr
    * @return
    * @throws Exception
    */
    public int create(CfgJobAttr cfgJobAttr) throws  Exception;

    /**
    * 批量维护任务属性定义
    * @param cfgJobAttrs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgJobAttr[] cfgJobAttrs) throws  Exception;
    /**
    * 更新任务属性定义
    * @param cfgJobAttr
    * @return
    * @throws Exception
    */
    public int update(CfgJobAttr cfgJobAttr) throws  Exception;

    /**
    * 通过查询对象更新任务属性定义
    * @param cfgJobAttr
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgJobAttr cfgJobAttr, CfgJobAttr_Example example) throws  Exception;

    /**
    * 删除任务属性定义
    * @param cfgJobAttr
    * @return
    * @throws Exception
    */
    public int delete(CfgJobAttr cfgJobAttr) throws  Exception;


    /**
    * 删除任务属性定义
    * @param cfgJobAttrId
    * @return
    * @throws Exception
    */
    public int delete(long cfgJobAttrId) throws  Exception;


    /**
    * 查找所有任务属性定义
    * @return
    */
    public List<CfgJobAttr> getCfgJobAttrAll()  throws  Exception;

    /**
    * 通过任务属性定义ID查询任务属性定义
    * @param cfgJobAttrId
    * @return
    * @throws Exception
    */
    public CfgJobAttr getCfgJobAttrByPK(long cfgJobAttrId)  throws  Exception;

    /**
    * 通过MAP参数查询任务属性定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgJobAttr> getCfgJobAttrListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务属性定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgJobAttr> getCfgJobAttrListByExample(CfgJobAttr_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务属性定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgJobAttrCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务属性定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgJobAttrCountByExample(CfgJobAttr_Example example) throws  Exception;


 }
