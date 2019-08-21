package com.hframework.hamster.sch.service.interfaces;

import java.util.*;
import com.hframework.hamster.sch.domain.model.LogDeploy;
import com.hframework.hamster.sch.domain.model.LogDeploy_Example;


public interface ILogDeploySV   {

  
    /**
    * 创建发布日志
    * @param logDeploy
    * @return
    * @throws Exception
    */
    public int create(LogDeploy logDeploy) throws  Exception;

    /**
    * 批量维护发布日志
    * @param logDeploys
    * @return
    * @throws Exception
    */
    public int batchOperate(LogDeploy[] logDeploys) throws  Exception;
    /**
    * 更新发布日志
    * @param logDeploy
    * @return
    * @throws Exception
    */
    public int update(LogDeploy logDeploy) throws  Exception;

    /**
    * 通过查询对象更新发布日志
    * @param logDeploy
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(LogDeploy logDeploy, LogDeploy_Example example) throws  Exception;

    /**
    * 删除发布日志
    * @param logDeploy
    * @return
    * @throws Exception
    */
    public int delete(LogDeploy logDeploy) throws  Exception;


    /**
    * 删除发布日志
    * @param logDeployId
    * @return
    * @throws Exception
    */
    public int delete(long logDeployId) throws  Exception;


    /**
    * 查找所有发布日志
    * @return
    */
    public List<LogDeploy> getLogDeployAll()  throws  Exception;

    /**
    * 通过发布日志ID查询发布日志
    * @param logDeployId
    * @return
    * @throws Exception
    */
    public LogDeploy getLogDeployByPK(long logDeployId)  throws  Exception;

    /**
    * 通过MAP参数查询发布日志
    * @param params
    * @return
    * @throws Exception
    */
    public List<LogDeploy> getLogDeployListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询发布日志
    * @param example
    * @return
    * @throws Exception
    */
    public List<LogDeploy> getLogDeployListByExample(LogDeploy_Example example) throws  Exception;


    /**
    * 通过MAP参数查询发布日志数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getLogDeployCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询发布日志数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getLogDeployCountByExample(LogDeploy_Example example) throws  Exception;


 }
