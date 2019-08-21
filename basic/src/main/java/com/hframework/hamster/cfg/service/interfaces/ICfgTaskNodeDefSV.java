package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgTaskNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgTaskNodeDef_Example;


public interface ICfgTaskNodeDefSV   {

  
    /**
    * 创建任务子节点定义
    * @param cfgTaskNodeDef
    * @return
    * @throws Exception
    */
    public int create(CfgTaskNodeDef cfgTaskNodeDef) throws  Exception;

    /**
    * 批量维护任务子节点定义
    * @param cfgTaskNodeDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgTaskNodeDef[] cfgTaskNodeDefs) throws  Exception;
    /**
    * 更新任务子节点定义
    * @param cfgTaskNodeDef
    * @return
    * @throws Exception
    */
    public int update(CfgTaskNodeDef cfgTaskNodeDef) throws  Exception;

    /**
    * 通过查询对象更新任务子节点定义
    * @param cfgTaskNodeDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgTaskNodeDef cfgTaskNodeDef, CfgTaskNodeDef_Example example) throws  Exception;

    /**
    * 删除任务子节点定义
    * @param cfgTaskNodeDef
    * @return
    * @throws Exception
    */
    public int delete(CfgTaskNodeDef cfgTaskNodeDef) throws  Exception;


    /**
    * 删除任务子节点定义
    * @param cfgTaskNodeDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgTaskNodeDefId) throws  Exception;


    /**
    * 查找所有任务子节点定义
    * @return
    */
    public List<CfgTaskNodeDef> getCfgTaskNodeDefAll()  throws  Exception;

    /**
    * 通过任务子节点定义ID查询任务子节点定义
    * @param cfgTaskNodeDefId
    * @return
    * @throws Exception
    */
    public CfgTaskNodeDef getCfgTaskNodeDefByPK(long cfgTaskNodeDefId)  throws  Exception;

    /**
    * 通过MAP参数查询任务子节点定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgTaskNodeDef> getCfgTaskNodeDefListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务子节点定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgTaskNodeDef> getCfgTaskNodeDefListByExample(CfgTaskNodeDef_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务子节点定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgTaskNodeDefCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务子节点定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgTaskNodeDefCountByExample(CfgTaskNodeDef_Example example) throws  Exception;


 }
