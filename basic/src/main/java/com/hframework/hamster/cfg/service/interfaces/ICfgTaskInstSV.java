package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgTaskInst;
import com.hframework.hamster.cfg.domain.model.CfgTaskInst_Example;


public interface ICfgTaskInstSV   {

  
    /**
    * 创建任务实例
    * @param cfgTaskInst
    * @return
    * @throws Exception
    */
    public int create(CfgTaskInst cfgTaskInst) throws  Exception;

    /**
    * 批量维护任务实例
    * @param cfgTaskInsts
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgTaskInst[] cfgTaskInsts) throws  Exception;
    /**
    * 更新任务实例
    * @param cfgTaskInst
    * @return
    * @throws Exception
    */
    public int update(CfgTaskInst cfgTaskInst) throws  Exception;

    /**
    * 通过查询对象更新任务实例
    * @param cfgTaskInst
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgTaskInst cfgTaskInst, CfgTaskInst_Example example) throws  Exception;

    /**
    * 删除任务实例
    * @param cfgTaskInst
    * @return
    * @throws Exception
    */
    public int delete(CfgTaskInst cfgTaskInst) throws  Exception;


    /**
    * 删除任务实例
    * @param cfgTaskInstId
    * @return
    * @throws Exception
    */
    public int delete(long cfgTaskInstId) throws  Exception;


    /**
    * 查找所有任务实例
    * @return
    */
    public List<CfgTaskInst> getCfgTaskInstAll()  throws  Exception;

    /**
    * 通过任务实例ID查询任务实例
    * @param cfgTaskInstId
    * @return
    * @throws Exception
    */
    public CfgTaskInst getCfgTaskInstByPK(long cfgTaskInstId)  throws  Exception;

    /**
    * 通过MAP参数查询任务实例
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgTaskInst> getCfgTaskInstListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务实例
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgTaskInst> getCfgTaskInstListByExample(CfgTaskInst_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务实例数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgTaskInstCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务实例数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgTaskInstCountByExample(CfgTaskInst_Example example) throws  Exception;


 }
