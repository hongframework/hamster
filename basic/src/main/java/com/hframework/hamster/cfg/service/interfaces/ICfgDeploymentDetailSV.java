package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgDeploymentDetail;
import com.hframework.hamster.cfg.domain.model.CfgDeploymentDetail_Example;


public interface ICfgDeploymentDetailSV   {

  
    /**
    * 创建发布明细
    * @param cfgDeploymentDetail
    * @return
    * @throws Exception
    */
    public int create(CfgDeploymentDetail cfgDeploymentDetail) throws  Exception;

    /**
    * 批量维护发布明细
    * @param cfgDeploymentDetails
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDeploymentDetail[] cfgDeploymentDetails) throws  Exception;
    /**
    * 更新发布明细
    * @param cfgDeploymentDetail
    * @return
    * @throws Exception
    */
    public int update(CfgDeploymentDetail cfgDeploymentDetail) throws  Exception;

    /**
    * 通过查询对象更新发布明细
    * @param cfgDeploymentDetail
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDeploymentDetail cfgDeploymentDetail, CfgDeploymentDetail_Example example) throws  Exception;

    /**
    * 删除发布明细
    * @param cfgDeploymentDetail
    * @return
    * @throws Exception
    */
    public int delete(CfgDeploymentDetail cfgDeploymentDetail) throws  Exception;


    /**
    * 删除发布明细
    * @param cfgDeploymentDetailId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDeploymentDetailId) throws  Exception;


    /**
    * 查找所有发布明细
    * @return
    */
    public List<CfgDeploymentDetail> getCfgDeploymentDetailAll()  throws  Exception;

    /**
    * 通过发布明细ID查询发布明细
    * @param cfgDeploymentDetailId
    * @return
    * @throws Exception
    */
    public CfgDeploymentDetail getCfgDeploymentDetailByPK(long cfgDeploymentDetailId)  throws  Exception;

    /**
    * 通过MAP参数查询发布明细
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDeploymentDetail> getCfgDeploymentDetailListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询发布明细
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDeploymentDetail> getCfgDeploymentDetailListByExample(CfgDeploymentDetail_Example example) throws  Exception;


    /**
    * 通过MAP参数查询发布明细数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDeploymentDetailCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询发布明细数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDeploymentDetailCountByExample(CfgDeploymentDetail_Example example) throws  Exception;


 }
