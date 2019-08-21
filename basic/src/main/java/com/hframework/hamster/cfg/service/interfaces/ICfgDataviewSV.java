package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgDataview;
import com.hframework.hamster.cfg.domain.model.CfgDataview_Example;


public interface ICfgDataviewSV   {

  
    /**
    * 创建数据视图
    * @param cfgDataview
    * @return
    * @throws Exception
    */
    public int create(CfgDataview cfgDataview) throws  Exception;

    /**
    * 批量维护数据视图
    * @param cfgDataviews
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDataview[] cfgDataviews) throws  Exception;
    /**
    * 更新数据视图
    * @param cfgDataview
    * @return
    * @throws Exception
    */
    public int update(CfgDataview cfgDataview) throws  Exception;

    /**
    * 通过查询对象更新数据视图
    * @param cfgDataview
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDataview cfgDataview, CfgDataview_Example example) throws  Exception;

    /**
    * 删除数据视图
    * @param cfgDataview
    * @return
    * @throws Exception
    */
    public int delete(CfgDataview cfgDataview) throws  Exception;


    /**
    * 删除数据视图
    * @param cfgDataviewId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDataviewId) throws  Exception;


    /**
    * 查找所有数据视图
    * @return
    */
    public List<CfgDataview> getCfgDataviewAll()  throws  Exception;

    /**
    * 通过数据视图ID查询数据视图
    * @param cfgDataviewId
    * @return
    * @throws Exception
    */
    public CfgDataview getCfgDataviewByPK(long cfgDataviewId)  throws  Exception;

    /**
    * 通过MAP参数查询数据视图
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDataview> getCfgDataviewListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据视图
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDataview> getCfgDataviewListByExample(CfgDataview_Example example) throws  Exception;


    /**
    * 通过MAP参数查询数据视图数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDataviewCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据视图数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDataviewCountByExample(CfgDataview_Example example) throws  Exception;


 }
