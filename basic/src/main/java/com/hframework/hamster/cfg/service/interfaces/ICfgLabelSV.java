package com.hframework.hamster.cfg.service.interfaces;

import java.util.*;
import com.hframework.hamster.cfg.domain.model.CfgLabel;
import com.hframework.hamster.cfg.domain.model.CfgLabel_Example;


public interface ICfgLabelSV   {

  
    /**
    * 创建标签
    * @param cfgLabel
    * @return
    * @throws Exception
    */
    public int create(CfgLabel cfgLabel) throws  Exception;

    /**
    * 批量维护标签
    * @param cfgLabels
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgLabel[] cfgLabels) throws  Exception;
    /**
    * 更新标签
    * @param cfgLabel
    * @return
    * @throws Exception
    */
    public int update(CfgLabel cfgLabel) throws  Exception;

    /**
    * 通过查询对象更新标签
    * @param cfgLabel
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgLabel cfgLabel, CfgLabel_Example example) throws  Exception;

    /**
    * 删除标签
    * @param cfgLabel
    * @return
    * @throws Exception
    */
    public int delete(CfgLabel cfgLabel) throws  Exception;


    /**
    * 删除标签
    * @param cfgLabelId
    * @return
    * @throws Exception
    */
    public int delete(long cfgLabelId) throws  Exception;


    /**
    * 查找所有标签
    * @return
    */
    public List<CfgLabel> getCfgLabelAll()  throws  Exception;

    /**
    * 通过标签ID查询标签
    * @param cfgLabelId
    * @return
    * @throws Exception
    */
    public CfgLabel getCfgLabelByPK(long cfgLabelId)  throws  Exception;

    /**
    * 通过MAP参数查询标签
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgLabel> getCfgLabelListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询标签
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgLabel> getCfgLabelListByExample(CfgLabel_Example example) throws  Exception;


    /**
    * 通过MAP参数查询标签数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgLabelCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询标签数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgLabelCountByExample(CfgLabel_Example example) throws  Exception;


 }
