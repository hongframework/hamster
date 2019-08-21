package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeDef_Example;


public interface ICfgNodeDefSV   {

  
    /**
    * 创建动态节点定义
    * @param cfgNodeDef
    * @return
    * @throws Exception
    */
    public int create(CfgNodeDef cfgNodeDef) throws  Exception;

    /**
    * 批量维护动态节点定义
    * @param cfgNodeDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgNodeDef[] cfgNodeDefs) throws  Exception;
    /**
    * 更新动态节点定义
    * @param cfgNodeDef
    * @return
    * @throws Exception
    */
    public int update(CfgNodeDef cfgNodeDef) throws  Exception;

    /**
    * 通过查询对象更新动态节点定义
    * @param cfgNodeDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgNodeDef cfgNodeDef, CfgNodeDef_Example example) throws  Exception;

    /**
    * 删除动态节点定义
    * @param cfgNodeDef
    * @return
    * @throws Exception
    */
    public int delete(CfgNodeDef cfgNodeDef) throws  Exception;


    /**
    * 删除动态节点定义
    * @param cfgNodeDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgNodeDefId) throws  Exception;


    /**
    * 查找所有动态节点定义
    * @return
    */
    public List<CfgNodeDef> getCfgNodeDefAll()  throws  Exception;

    /**
    * 通过动态节点定义ID查询动态节点定义
    * @param cfgNodeDefId
    * @return
    * @throws Exception
    */
    public CfgNodeDef getCfgNodeDefByPK(long cfgNodeDefId)  throws  Exception;

    /**
    * 通过MAP参数查询动态节点定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgNodeDef> getCfgNodeDefListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询动态节点定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgNodeDef> getCfgNodeDefListByExample(CfgNodeDef_Example example) throws  Exception;


    /**
    * 通过MAP参数查询动态节点定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgNodeDefCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询动态节点定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgNodeDefCountByExample(CfgNodeDef_Example example) throws  Exception;


 }
