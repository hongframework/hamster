package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgJob;
import com.hframework.hamster.cfg.domain.model.CfgJob_Example;


public interface ICfgJobSV   {

  
    /**
    * 创建任务定义
    * @param cfgJob
    * @return
    * @throws Exception
    */
    public int create(CfgJob cfgJob) throws  Exception;

    /**
    * 批量维护任务定义
    * @param cfgJobs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgJob[] cfgJobs) throws  Exception;
    /**
    * 更新任务定义
    * @param cfgJob
    * @return
    * @throws Exception
    */
    public int update(CfgJob cfgJob) throws  Exception;

    /**
    * 通过查询对象更新任务定义
    * @param cfgJob
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgJob cfgJob, CfgJob_Example example) throws  Exception;

    /**
    * 删除任务定义
    * @param cfgJob
    * @return
    * @throws Exception
    */
    public int delete(CfgJob cfgJob) throws  Exception;


    /**
    * 删除任务定义
    * @param cfgJobId
    * @return
    * @throws Exception
    */
    public int delete(long cfgJobId) throws  Exception;


    /**
    * 查找所有任务定义
    * @return
    */
    public List<CfgJob> getCfgJobAll()  throws  Exception;

    /**
    * 通过任务定义ID查询任务定义
    * @param cfgJobId
    * @return
    * @throws Exception
    */
    public CfgJob getCfgJobByPK(long cfgJobId)  throws  Exception;

    /**
    * 通过MAP参数查询任务定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgJob> getCfgJobListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgJob> getCfgJobListByExample(CfgJob_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgJobCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgJobCountByExample(CfgJob_Example example) throws  Exception;


 }
