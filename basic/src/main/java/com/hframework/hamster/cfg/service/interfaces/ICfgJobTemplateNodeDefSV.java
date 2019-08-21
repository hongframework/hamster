package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateNodeDef_Example;


public interface ICfgJobTemplateNodeDefSV   {

  
    /**
    * 创建任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @return
    * @throws Exception
    */
    public int create(CfgJobTemplateNodeDef cfgJobTemplateNodeDef) throws  Exception;

    /**
    * 批量维护任务模板节点定义
    * @param cfgJobTemplateNodeDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgJobTemplateNodeDef[] cfgJobTemplateNodeDefs) throws  Exception;
    /**
    * 更新任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @return
    * @throws Exception
    */
    public int update(CfgJobTemplateNodeDef cfgJobTemplateNodeDef) throws  Exception;

    /**
    * 通过查询对象更新任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgJobTemplateNodeDef cfgJobTemplateNodeDef, CfgJobTemplateNodeDef_Example example) throws  Exception;

    /**
    * 删除任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @return
    * @throws Exception
    */
    public int delete(CfgJobTemplateNodeDef cfgJobTemplateNodeDef) throws  Exception;


    /**
    * 删除任务模板节点定义
    * @param cfgJobTemplateNodeDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgJobTemplateNodeDefId) throws  Exception;


    /**
    * 查找所有任务模板节点定义
    * @return
    */
    public List<CfgJobTemplateNodeDef> getCfgJobTemplateNodeDefAll()  throws  Exception;

    /**
    * 通过任务模板节点定义ID查询任务模板节点定义
    * @param cfgJobTemplateNodeDefId
    * @return
    * @throws Exception
    */
    public CfgJobTemplateNodeDef getCfgJobTemplateNodeDefByPK(long cfgJobTemplateNodeDefId)  throws  Exception;

    /**
    * 通过MAP参数查询任务模板节点定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgJobTemplateNodeDef> getCfgJobTemplateNodeDefListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务模板节点定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgJobTemplateNodeDef> getCfgJobTemplateNodeDefListByExample(CfgJobTemplateNodeDef_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务模板节点定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgJobTemplateNodeDefCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务模板节点定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgJobTemplateNodeDefCountByExample(CfgJobTemplateNodeDef_Example example) throws  Exception;


 }
