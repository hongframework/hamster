package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgNodeAttrDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeAttrDef_Example;


public interface ICfgNodeAttrDefSV   {

  
    /**
    * 创建动态节点属性定义
    * @param cfgNodeAttrDef
    * @return
    * @throws Exception
    */
    public int create(CfgNodeAttrDef cfgNodeAttrDef) throws  Exception;

    /**
    * 批量维护动态节点属性定义
    * @param cfgNodeAttrDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgNodeAttrDef[] cfgNodeAttrDefs) throws  Exception;
    /**
    * 更新动态节点属性定义
    * @param cfgNodeAttrDef
    * @return
    * @throws Exception
    */
    public int update(CfgNodeAttrDef cfgNodeAttrDef) throws  Exception;

    /**
    * 通过查询对象更新动态节点属性定义
    * @param cfgNodeAttrDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgNodeAttrDef cfgNodeAttrDef, CfgNodeAttrDef_Example example) throws  Exception;

    /**
    * 删除动态节点属性定义
    * @param cfgNodeAttrDef
    * @return
    * @throws Exception
    */
    public int delete(CfgNodeAttrDef cfgNodeAttrDef) throws  Exception;


    /**
    * 删除动态节点属性定义
    * @param cfgNodeAttrDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgNodeAttrDefId) throws  Exception;


    /**
    * 查找所有动态节点属性定义
    * @return
    */
    public List<CfgNodeAttrDef> getCfgNodeAttrDefAll()  throws  Exception;

    /**
    * 通过动态节点属性定义ID查询动态节点属性定义
    * @param cfgNodeAttrDefId
    * @return
    * @throws Exception
    */
    public CfgNodeAttrDef getCfgNodeAttrDefByPK(long cfgNodeAttrDefId)  throws  Exception;

    /**
    * 通过MAP参数查询动态节点属性定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgNodeAttrDef> getCfgNodeAttrDefListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询动态节点属性定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgNodeAttrDef> getCfgNodeAttrDefListByExample(CfgNodeAttrDef_Example example) throws  Exception;


    /**
    * 通过MAP参数查询动态节点属性定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgNodeAttrDefCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询动态节点属性定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgNodeAttrDefCountByExample(CfgNodeAttrDef_Example example) throws  Exception;


 }
