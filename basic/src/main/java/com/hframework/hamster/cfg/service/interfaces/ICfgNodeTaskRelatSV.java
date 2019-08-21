package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgNodeTaskRelat;
import com.hframework.hamster.cfg.domain.model.CfgNodeTaskRelat_Example;


public interface ICfgNodeTaskRelatSV   {

  
    /**
    * 创建任务节点部署
    * @param cfgNodeTaskRelat
    * @return
    * @throws Exception
    */
    public int create(CfgNodeTaskRelat cfgNodeTaskRelat) throws  Exception;

    /**
    * 批量维护任务节点部署
    * @param cfgNodeTaskRelats
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgNodeTaskRelat[] cfgNodeTaskRelats) throws  Exception;
    /**
    * 更新任务节点部署
    * @param cfgNodeTaskRelat
    * @return
    * @throws Exception
    */
    public int update(CfgNodeTaskRelat cfgNodeTaskRelat) throws  Exception;

    /**
    * 通过查询对象更新任务节点部署
    * @param cfgNodeTaskRelat
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgNodeTaskRelat cfgNodeTaskRelat, CfgNodeTaskRelat_Example example) throws  Exception;

    /**
    * 删除任务节点部署
    * @param cfgNodeTaskRelat
    * @return
    * @throws Exception
    */
    public int delete(CfgNodeTaskRelat cfgNodeTaskRelat) throws  Exception;


    /**
    * 删除任务节点部署
    * @param cfgNodeTaskRelatId
    * @return
    * @throws Exception
    */
    public int delete(long cfgNodeTaskRelatId) throws  Exception;


    /**
    * 查找所有任务节点部署
    * @return
    */
    public List<CfgNodeTaskRelat> getCfgNodeTaskRelatAll()  throws  Exception;

    /**
    * 通过任务节点部署ID查询任务节点部署
    * @param cfgNodeTaskRelatId
    * @return
    * @throws Exception
    */
    public CfgNodeTaskRelat getCfgNodeTaskRelatByPK(long cfgNodeTaskRelatId)  throws  Exception;

    /**
    * 通过MAP参数查询任务节点部署
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgNodeTaskRelat> getCfgNodeTaskRelatListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务节点部署
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgNodeTaskRelat> getCfgNodeTaskRelatListByExample(CfgNodeTaskRelat_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务节点部署数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgNodeTaskRelatCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务节点部署数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgNodeTaskRelatCountByExample(CfgNodeTaskRelat_Example example) throws  Exception;


 }
