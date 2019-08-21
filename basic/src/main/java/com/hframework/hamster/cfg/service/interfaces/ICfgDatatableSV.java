package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgDatatable;
import com.hframework.hamster.cfg.domain.model.CfgDatatable_Example;


public interface ICfgDatatableSV   {

  
    /**
    * 创建数据表
    * @param cfgDatatable
    * @return
    * @throws Exception
    */
    public int create(CfgDatatable cfgDatatable) throws  Exception;

    /**
    * 批量维护数据表
    * @param cfgDatatables
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDatatable[] cfgDatatables) throws  Exception;
    /**
    * 更新数据表
    * @param cfgDatatable
    * @return
    * @throws Exception
    */
    public int update(CfgDatatable cfgDatatable) throws  Exception;

    /**
    * 通过查询对象更新数据表
    * @param cfgDatatable
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDatatable cfgDatatable, CfgDatatable_Example example) throws  Exception;

    /**
    * 删除数据表
    * @param cfgDatatable
    * @return
    * @throws Exception
    */
    public int delete(CfgDatatable cfgDatatable) throws  Exception;


    /**
    * 删除数据表
    * @param cfgDatatableId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDatatableId) throws  Exception;


    /**
    * 查找所有数据表
    * @return
    */
    public List<CfgDatatable> getCfgDatatableAll()  throws  Exception;

    /**
    * 通过数据表ID查询数据表
    * @param cfgDatatableId
    * @return
    * @throws Exception
    */
    public CfgDatatable getCfgDatatableByPK(long cfgDatatableId)  throws  Exception;

    /**
    * 通过MAP参数查询数据表
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDatatable> getCfgDatatableListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据表
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDatatable> getCfgDatatableListByExample(CfgDatatable_Example example) throws  Exception;


    /**
    * 通过MAP参数查询数据表数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDatatableCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据表数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDatatableCountByExample(CfgDatatable_Example example) throws  Exception;


 }
