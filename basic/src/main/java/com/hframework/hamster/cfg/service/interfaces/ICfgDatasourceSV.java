package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import com.hframework.hamster.cfg.domain.model.CfgDatasource_Example;


public interface ICfgDatasourceSV   {

  
    /**
    * 创建数据源
    * @param cfgDatasource
    * @return
    * @throws Exception
    */
    public int create(CfgDatasource cfgDatasource) throws  Exception;

    /**
    * 批量维护数据源
    * @param cfgDatasources
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDatasource[] cfgDatasources) throws  Exception;
    /**
    * 更新数据源
    * @param cfgDatasource
    * @return
    * @throws Exception
    */
    public int update(CfgDatasource cfgDatasource) throws  Exception;

    /**
    * 通过查询对象更新数据源
    * @param cfgDatasource
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDatasource cfgDatasource, CfgDatasource_Example example) throws  Exception;

    /**
    * 删除数据源
    * @param cfgDatasource
    * @return
    * @throws Exception
    */
    public int delete(CfgDatasource cfgDatasource) throws  Exception;


    /**
    * 删除数据源
    * @param cfgDatasourceId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDatasourceId) throws  Exception;


    /**
    * 查找所有数据源
    * @return
    */
    public List<CfgDatasource> getCfgDatasourceAll()  throws  Exception;

    /**
    * 通过数据源ID查询数据源
    * @param cfgDatasourceId
    * @return
    * @throws Exception
    */
    public CfgDatasource getCfgDatasourceByPK(long cfgDatasourceId)  throws  Exception;

    /**
    * 通过MAP参数查询数据源
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDatasource> getCfgDatasourceListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据源
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDatasource> getCfgDatasourceListByExample(CfgDatasource_Example example) throws  Exception;


    /**
    * 通过MAP参数查询数据源数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDatasourceCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据源数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDatasourceCountByExample(CfgDatasource_Example example) throws  Exception;


 }
