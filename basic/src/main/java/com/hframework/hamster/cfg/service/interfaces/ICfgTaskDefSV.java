package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgTaskDef;
import com.hframework.hamster.cfg.domain.model.CfgTaskDef_Example;


public interface ICfgTaskDefSV   {

  
    /**
    * 创建任务定义
    * @param cfgTaskDef
    * @return
    * @throws Exception
    */
    public int create(CfgTaskDef cfgTaskDef) throws  Exception;

    /**
    * 批量维护任务定义
    * @param cfgTaskDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgTaskDef[] cfgTaskDefs) throws  Exception;
    /**
    * 更新任务定义
    * @param cfgTaskDef
    * @return
    * @throws Exception
    */
    public int update(CfgTaskDef cfgTaskDef) throws  Exception;

    /**
    * 通过查询对象更新任务定义
    * @param cfgTaskDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgTaskDef cfgTaskDef, CfgTaskDef_Example example) throws  Exception;

    /**
    * 删除任务定义
    * @param cfgTaskDef
    * @return
    * @throws Exception
    */
    public int delete(CfgTaskDef cfgTaskDef) throws  Exception;


    /**
    * 删除任务定义
    * @param cfgTaskDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgTaskDefId) throws  Exception;


    /**
    * 查找所有任务定义
    * @return
    */
    public List<CfgTaskDef> getCfgTaskDefAll()  throws  Exception;

    /**
    * 通过任务定义ID查询任务定义
    * @param cfgTaskDefId
    * @return
    * @throws Exception
    */
    public CfgTaskDef getCfgTaskDefByPK(long cfgTaskDefId)  throws  Exception;

    /**
    * 通过MAP参数查询任务定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgTaskDef> getCfgTaskDefListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgTaskDef> getCfgTaskDefListByExample(CfgTaskDef_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgTaskDefCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgTaskDefCountByExample(CfgTaskDef_Example example) throws  Exception;


 }
