package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail_Example;


public interface ICfgSubscribeDetailSV   {

  
    /**
    * 创建数据订阅明细
    * @param cfgSubscribeDetail
    * @return
    * @throws Exception
    */
    public int create(CfgSubscribeDetail cfgSubscribeDetail) throws  Exception;

    /**
    * 批量维护数据订阅明细
    * @param cfgSubscribeDetails
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgSubscribeDetail[] cfgSubscribeDetails) throws  Exception;
    /**
    * 更新数据订阅明细
    * @param cfgSubscribeDetail
    * @return
    * @throws Exception
    */
    public int update(CfgSubscribeDetail cfgSubscribeDetail) throws  Exception;

    /**
    * 通过查询对象更新数据订阅明细
    * @param cfgSubscribeDetail
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgSubscribeDetail cfgSubscribeDetail, CfgSubscribeDetail_Example example) throws  Exception;

    /**
    * 删除数据订阅明细
    * @param cfgSubscribeDetail
    * @return
    * @throws Exception
    */
    public int delete(CfgSubscribeDetail cfgSubscribeDetail) throws  Exception;


    /**
    * 删除数据订阅明细
    * @param cfgSubscribeDetailId
    * @return
    * @throws Exception
    */
    public int delete(long cfgSubscribeDetailId) throws  Exception;


    /**
    * 查找所有数据订阅明细
    * @return
    */
    public List<CfgSubscribeDetail> getCfgSubscribeDetailAll()  throws  Exception;

    /**
    * 通过数据订阅明细ID查询数据订阅明细
    * @param cfgSubscribeDetailId
    * @return
    * @throws Exception
    */
    public CfgSubscribeDetail getCfgSubscribeDetailByPK(long cfgSubscribeDetailId)  throws  Exception;

    /**
    * 通过MAP参数查询数据订阅明细
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgSubscribeDetail> getCfgSubscribeDetailListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据订阅明细
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgSubscribeDetail> getCfgSubscribeDetailListByExample(CfgSubscribeDetail_Example example) throws  Exception;


    /**
    * 通过MAP参数查询数据订阅明细数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeDetailCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据订阅明细数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeDetailCountByExample(CfgSubscribeDetail_Example example) throws  Exception;


 }
