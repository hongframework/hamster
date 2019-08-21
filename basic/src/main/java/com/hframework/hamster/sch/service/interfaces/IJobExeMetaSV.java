package com.hframework.hamster.sch.service.interfaces;

import java.util.*;
import com.hframework.hamster.sch.domain.model.JobExeMeta;
import com.hframework.hamster.sch.domain.model.JobExeMeta_Example;


public interface IJobExeMetaSV   {

  
    /**
    * 创建任务调度元数据
    * @param jobExeMeta
    * @return
    * @throws Exception
    */
    public int create(JobExeMeta jobExeMeta) throws  Exception;

    /**
    * 批量维护任务调度元数据
    * @param jobExeMetas
    * @return
    * @throws Exception
    */
    public int batchOperate(JobExeMeta[] jobExeMetas) throws  Exception;
    /**
    * 更新任务调度元数据
    * @param jobExeMeta
    * @return
    * @throws Exception
    */
    public int update(JobExeMeta jobExeMeta) throws  Exception;

    /**
    * 通过查询对象更新任务调度元数据
    * @param jobExeMeta
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(JobExeMeta jobExeMeta, JobExeMeta_Example example) throws  Exception;

    /**
    * 删除任务调度元数据
    * @param jobExeMeta
    * @return
    * @throws Exception
    */
    public int delete(JobExeMeta jobExeMeta) throws  Exception;


    /**
    * 删除任务调度元数据
    * @param jobExeMetaId
    * @return
    * @throws Exception
    */
    public int delete(long jobExeMetaId) throws  Exception;


    /**
    * 查找所有任务调度元数据
    * @return
    */
    public List<JobExeMeta> getJobExeMetaAll()  throws  Exception;

    /**
    * 通过任务调度元数据ID查询任务调度元数据
    * @param jobExeMetaId
    * @return
    * @throws Exception
    */
    public JobExeMeta getJobExeMetaByPK(long jobExeMetaId)  throws  Exception;

    /**
    * 通过MAP参数查询任务调度元数据
    * @param params
    * @return
    * @throws Exception
    */
    public List<JobExeMeta> getJobExeMetaListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务调度元数据
    * @param example
    * @return
    * @throws Exception
    */
    public List<JobExeMeta> getJobExeMetaListByExample(JobExeMeta_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务调度元数据数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getJobExeMetaCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务调度元数据数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getJobExeMetaCountByExample(JobExeMeta_Example example) throws  Exception;


 }
