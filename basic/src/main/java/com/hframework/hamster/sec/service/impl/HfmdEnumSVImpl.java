package com.hframework.hamster.sec.service.impl;

import java.util.*;

import com.hframework.hamster.sec.service.interfaces.IHfmdEnumSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.sec.domain.model.HfmdEnum;
import com.hframework.hamster.sec.domain.model.HfmdEnum_Example;
import com.hframework.hamster.sec.dao.HfmdEnumMapper;
import com.hframework.hamster.sec.service.interfaces.IHfmdEnumSV;

@Service("iHfmdEnumSV")
public class HfmdEnumSVImpl  implements IHfmdEnumSV {

	@Resource
	private HfmdEnumMapper hfmdEnumMapper;
  


    /**
    * 创建字典项
    * @param hfmdEnum
    * @return
    * @throws Exception
    */
    public int create(HfmdEnum hfmdEnum) throws Exception {
        return hfmdEnumMapper.insertSelective(hfmdEnum);
    }

    /**
    * 批量维护字典项
    * @param hfmdEnums
    * @return
    * @throws Exception
    */
    public int batchOperate(HfmdEnum[] hfmdEnums) throws  Exception{
        int result = 0;
        if(hfmdEnums != null) {
            for (HfmdEnum hfmdEnum : hfmdEnums) {
                if(hfmdEnum.getHfmdEnumId() == null) {
                    result += this.create(hfmdEnum);
                }else {
                    result += this.update(hfmdEnum);
                }
            }
        }
        return result;
    }

    /**
    * 更新字典项
    * @param hfmdEnum
    * @return
    * @throws Exception
    */
    public int update(HfmdEnum hfmdEnum) throws  Exception {
        return hfmdEnumMapper.updateByPrimaryKeySelective(hfmdEnum);
    }

    /**
    * 通过查询对象更新字典项
    * @param hfmdEnum
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(HfmdEnum hfmdEnum, HfmdEnum_Example example) throws  Exception {
        return hfmdEnumMapper.updateByExampleSelective(hfmdEnum, example);
    }

    /**
    * 删除字典项
    * @param hfmdEnum
    * @return
    * @throws Exception
    */
    public int delete(HfmdEnum hfmdEnum) throws  Exception {
        return hfmdEnumMapper.deleteByPrimaryKey(hfmdEnum.getHfmdEnumId());
    }

    /**
    * 删除字典项
    * @param hfmdEnumId
    * @return
    * @throws Exception
    */
    public int delete(long hfmdEnumId) throws  Exception {
        return hfmdEnumMapper.deleteByPrimaryKey(hfmdEnumId);
    }

    /**
    * 查找所有字典项
    * @return
    */
    public List<HfmdEnum> getHfmdEnumAll()  throws  Exception {
        return hfmdEnumMapper.selectByExample(new HfmdEnum_Example());
    }

    /**
    * 通过字典项ID查询字典项
    * @param hfmdEnumId
    * @return
    * @throws Exception
    */
    public HfmdEnum getHfmdEnumByPK(long hfmdEnumId)  throws  Exception {
        return hfmdEnumMapper.selectByPrimaryKey(hfmdEnumId);
    }


    /**
    * 通过MAP参数查询字典项
    * @param params
    * @return
    * @throws Exception
    */
    public List<HfmdEnum> getHfmdEnumListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询字典项
    * @param example
    * @return
    * @throws Exception
    */
    public List<HfmdEnum> getHfmdEnumListByExample(HfmdEnum_Example example) throws  Exception {
        return hfmdEnumMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询字典项数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getHfmdEnumCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询字典项数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getHfmdEnumCountByExample(HfmdEnum_Example example) throws  Exception {
        return hfmdEnumMapper.countByExample(example);
    }


  	//getter
 	
	public HfmdEnumMapper getHfmdEnumMapper(){
		return hfmdEnumMapper;
	}
	//setter
	public void setHfmdEnumMapper(HfmdEnumMapper hfmdEnumMapper){
    	this.hfmdEnumMapper = hfmdEnumMapper;
    }
}
