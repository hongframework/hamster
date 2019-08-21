package com.hframework.hamster.sec.service.interfaces;

import java.util.*;
import com.hframework.hamster.sec.domain.model.HfmdEnum;
import com.hframework.hamster.sec.domain.model.HfmdEnum_Example;


public interface IHfmdEnumSV   {

  
    /**
    * 创建字典项
    * @param hfmdEnum
    * @return
    * @throws Exception
    */
    public int create(HfmdEnum hfmdEnum) throws  Exception;

    /**
    * 批量维护字典项
    * @param hfmdEnums
    * @return
    * @throws Exception
    */
    public int batchOperate(HfmdEnum[] hfmdEnums) throws  Exception;
    /**
    * 更新字典项
    * @param hfmdEnum
    * @return
    * @throws Exception
    */
    public int update(HfmdEnum hfmdEnum) throws  Exception;

    /**
    * 通过查询对象更新字典项
    * @param hfmdEnum
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(HfmdEnum hfmdEnum, HfmdEnum_Example example) throws  Exception;

    /**
    * 删除字典项
    * @param hfmdEnum
    * @return
    * @throws Exception
    */
    public int delete(HfmdEnum hfmdEnum) throws  Exception;


    /**
    * 删除字典项
    * @param hfmdEnumId
    * @return
    * @throws Exception
    */
    public int delete(long hfmdEnumId) throws  Exception;


    /**
    * 查找所有字典项
    * @return
    */
    public List<HfmdEnum> getHfmdEnumAll()  throws  Exception;

    /**
    * 通过字典项ID查询字典项
    * @param hfmdEnumId
    * @return
    * @throws Exception
    */
    public HfmdEnum getHfmdEnumByPK(long hfmdEnumId)  throws  Exception;

    /**
    * 通过MAP参数查询字典项
    * @param params
    * @return
    * @throws Exception
    */
    public List<HfmdEnum> getHfmdEnumListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询字典项
    * @param example
    * @return
    * @throws Exception
    */
    public List<HfmdEnum> getHfmdEnumListByExample(HfmdEnum_Example example) throws  Exception;


    /**
    * 通过MAP参数查询字典项数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getHfmdEnumCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询字典项数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getHfmdEnumCountByExample(HfmdEnum_Example example) throws  Exception;


 }
