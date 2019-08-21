package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgNode;
import com.hframework.hamster.cfg.domain.model.CfgNode_Example;


public interface ICfgNodeSV   {

  
    /**
    * 创建节点
    * @param cfgNode
    * @return
    * @throws Exception
    */
    public int create(CfgNode cfgNode) throws  Exception;

    /**
    * 批量维护节点
    * @param cfgNodes
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgNode[] cfgNodes) throws  Exception;
    /**
    * 更新节点
    * @param cfgNode
    * @return
    * @throws Exception
    */
    public int update(CfgNode cfgNode) throws  Exception;

    /**
    * 通过查询对象更新节点
    * @param cfgNode
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgNode cfgNode, CfgNode_Example example) throws  Exception;

    /**
    * 删除节点
    * @param cfgNode
    * @return
    * @throws Exception
    */
    public int delete(CfgNode cfgNode) throws  Exception;


    /**
    * 删除节点
    * @param cfgNodeId
    * @return
    * @throws Exception
    */
    public int delete(long cfgNodeId) throws  Exception;


    /**
    * 查找所有节点
    * @return
    */
    public List<CfgNode> getCfgNodeAll()  throws  Exception;

    /**
    * 通过节点ID查询节点
    * @param cfgNodeId
    * @return
    * @throws Exception
    */
    public CfgNode getCfgNodeByPK(long cfgNodeId)  throws  Exception;

    /**
    * 通过MAP参数查询节点
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgNode> getCfgNodeListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询节点
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgNode> getCfgNodeListByExample(CfgNode_Example example) throws  Exception;


    /**
    * 通过MAP参数查询节点数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgNodeCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询节点数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgNodeCountByExample(CfgNode_Example example) throws  Exception;


 }
