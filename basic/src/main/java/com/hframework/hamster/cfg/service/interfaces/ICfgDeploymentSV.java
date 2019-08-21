package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgDeployment;
import com.hframework.hamster.cfg.domain.model.CfgDeployment_Example;


public interface ICfgDeploymentSV   {

  
    /**
    * 创建发布
    * @param cfgDeployment
    * @return
    * @throws Exception
    */
    public int create(CfgDeployment cfgDeployment) throws  Exception;

    /**
    * 批量维护发布
    * @param cfgDeployments
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDeployment[] cfgDeployments) throws  Exception;
    /**
    * 更新发布
    * @param cfgDeployment
    * @return
    * @throws Exception
    */
    public int update(CfgDeployment cfgDeployment) throws  Exception;

    /**
    * 通过查询对象更新发布
    * @param cfgDeployment
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDeployment cfgDeployment, CfgDeployment_Example example) throws  Exception;

    /**
    * 删除发布
    * @param cfgDeployment
    * @return
    * @throws Exception
    */
    public int delete(CfgDeployment cfgDeployment) throws  Exception;


    /**
    * 删除发布
    * @param cfgDeploymentId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDeploymentId) throws  Exception;


    /**
    * 查找所有发布
    * @return
    */
    public List<CfgDeployment> getCfgDeploymentAll()  throws  Exception;

    /**
    * 通过发布ID查询发布
    * @param cfgDeploymentId
    * @return
    * @throws Exception
    */
    public CfgDeployment getCfgDeploymentByPK(long cfgDeploymentId)  throws  Exception;

    /**
    * 通过MAP参数查询发布
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDeployment> getCfgDeploymentListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询发布
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDeployment> getCfgDeploymentListByExample(CfgDeployment_Example example) throws  Exception;


    /**
    * 通过MAP参数查询发布数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDeploymentCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询发布数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDeploymentCountByExample(CfgDeployment_Example example) throws  Exception;


 }
