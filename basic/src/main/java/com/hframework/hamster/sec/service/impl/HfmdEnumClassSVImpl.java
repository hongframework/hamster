package com.hframework.hamster.sec.service.impl;

import java.util.*;

import com.hframework.hamster.sec.service.interfaces.IHfmdEnumClassSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.sec.domain.model.HfmdEnumClass;
import com.hframework.hamster.sec.domain.model.HfmdEnumClass_Example;
import com.hframework.hamster.sec.dao.HfmdEnumClassMapper;
import com.hframework.hamster.sec.service.interfaces.IHfmdEnumClassSV;

@Service("iHfmdEnumClassSV")
public class HfmdEnumClassSVImpl  implements IHfmdEnumClassSV {

	@Resource
	private HfmdEnumClassMapper hfmdEnumClassMapper;
  


    /**
    * 创建字典
    * @param hfmdEnumClass
    * @return
    * @throws Exception
    */
    public int create(HfmdEnumClass hfmdEnumClass) throws Exception {
        return hfmdEnumClassMapper.insertSelective(hfmdEnumClass);
    }

    /**
    * 批量维护字典
    * @param hfmdEnumClasss
    * @return
    * @throws Exception
    */
    public int batchOperate(HfmdEnumClass[] hfmdEnumClasss) throws  Exception{
        int result = 0;
        if(hfmdEnumClasss != null) {
            for (HfmdEnumClass hfmdEnumClass : hfmdEnumClasss) {
                if(hfmdEnumClass.getHfmdEnumClassId() == null) {
                    result += this.create(hfmdEnumClass);
                }else {
                    result += this.update(hfmdEnumClass);
                }
            }
        }
        return result;
    }

    /**
    * 更新字典
    * @param hfmdEnumClass
    * @return
    * @throws Exception
    */
    public int update(HfmdEnumClass hfmdEnumClass) throws  Exception {
        return hfmdEnumClassMapper.updateByPrimaryKeySelective(hfmdEnumClass);
    }

    /**
    * 通过查询对象更新字典
    * @param hfmdEnumClass
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(HfmdEnumClass hfmdEnumClass, HfmdEnumClass_Example example) throws  Exception {
        return hfmdEnumClassMapper.updateByExampleSelective(hfmdEnumClass, example);
    }

    /**
    * 删除字典
    * @param hfmdEnumClass
    * @return
    * @throws Exception
    */
    public int delete(HfmdEnumClass hfmdEnumClass) throws  Exception {
        return hfmdEnumClassMapper.deleteByPrimaryKey(hfmdEnumClass.getHfmdEnumClassId());
    }

    /**
    * 删除字典
    * @param hfmdEnumClassId
    * @return
    * @throws Exception
    */
    public int delete(long hfmdEnumClassId) throws  Exception {
        return hfmdEnumClassMapper.deleteByPrimaryKey(hfmdEnumClassId);
    }

    /**
    * 查找所有字典
    * @return
    */
    public List<HfmdEnumClass> getHfmdEnumClassAll()  throws  Exception {
        return hfmdEnumClassMapper.selectByExample(new HfmdEnumClass_Example());
    }

    /**
    * 通过字典ID查询字典
    * @param hfmdEnumClassId
    * @return
    * @throws Exception
    */
    public HfmdEnumClass getHfmdEnumClassByPK(long hfmdEnumClassId)  throws  Exception {
        return hfmdEnumClassMapper.selectByPrimaryKey(hfmdEnumClassId);
    }


    /**
    * 通过MAP参数查询字典
    * @param params
    * @return
    * @throws Exception
    */
    public List<HfmdEnumClass> getHfmdEnumClassListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询字典
    * @param example
    * @return
    * @throws Exception
    */
    public List<HfmdEnumClass> getHfmdEnumClassListByExample(HfmdEnumClass_Example example) throws  Exception {
        return hfmdEnumClassMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询字典数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getHfmdEnumClassCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询字典数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getHfmdEnumClassCountByExample(HfmdEnumClass_Example example) throws  Exception {
        return hfmdEnumClassMapper.countByExample(example);
    }


  	//getter
 	
	public HfmdEnumClassMapper getHfmdEnumClassMapper(){
		return hfmdEnumClassMapper;
	}
	//setter
	public void setHfmdEnumClassMapper(HfmdEnumClassMapper hfmdEnumClassMapper){
    	this.hfmdEnumClassMapper = hfmdEnumClassMapper;
    }
}
