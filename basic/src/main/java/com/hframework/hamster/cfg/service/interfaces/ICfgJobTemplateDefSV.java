package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateDef;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateDef_Example;


public interface ICfgJobTemplateDefSV   {

  
    /**
    * 创建任务模板定义
    * @param cfgJobTemplateDef
    * @return
    * @throws Exception
    */
    public int create(CfgJobTemplateDef cfgJobTemplateDef) throws  Exception;

    /**
    * 批量维护任务模板定义
    * @param cfgJobTemplateDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgJobTemplateDef[] cfgJobTemplateDefs) throws  Exception;
    /**
    * 更新任务模板定义
    * @param cfgJobTemplateDef
    * @return
    * @throws Exception
    */
    public int update(CfgJobTemplateDef cfgJobTemplateDef) throws  Exception;

    /**
    * 通过查询对象更新任务模板定义
    * @param cfgJobTemplateDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgJobTemplateDef cfgJobTemplateDef, CfgJobTemplateDef_Example example) throws  Exception;

    /**
    * 删除任务模板定义
    * @param cfgJobTemplateDef
    * @return
    * @throws Exception
    */
    public int delete(CfgJobTemplateDef cfgJobTemplateDef) throws  Exception;


    /**
    * 删除任务模板定义
    * @param cfgJobTemplateDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgJobTemplateDefId) throws  Exception;


    /**
    * 查找所有任务模板定义
    * @return
    */
    public List<CfgJobTemplateDef> getCfgJobTemplateDefAll()  throws  Exception;

    /**
    * 通过任务模板定义ID查询任务模板定义
    * @param cfgJobTemplateDefId
    * @return
    * @throws Exception
    */
    public CfgJobTemplateDef getCfgJobTemplateDefByPK(long cfgJobTemplateDefId)  throws  Exception;

    /**
    * 通过MAP参数查询任务模板定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgJobTemplateDef> getCfgJobTemplateDefListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务模板定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgJobTemplateDef> getCfgJobTemplateDefListByExample(CfgJobTemplateDef_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务模板定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgJobTemplateDefCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务模板定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgJobTemplateDefCountByExample(CfgJobTemplateDef_Example example) throws  Exception;


 }
