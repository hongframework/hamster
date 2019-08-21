package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgJobAttrSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgJobAttr;
import com.hframework.hamster.cfg.domain.model.CfgJobAttr_Example;
import com.hframework.hamster.cfg.dao.CfgJobAttrMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobAttrSV;

@Service("iCfgJobAttrSV")
public class CfgJobAttrSVImpl  implements ICfgJobAttrSV {

	@Resource
	private CfgJobAttrMapper cfgJobAttrMapper;
  


    /**
    * 创建任务属性定义
    * @param cfgJobAttr
    * @return
    * @throws Exception
    */
    public int create(CfgJobAttr cfgJobAttr) throws Exception {
        return cfgJobAttrMapper.insertSelective(cfgJobAttr);
    }

    /**
    * 批量维护任务属性定义
    * @param cfgJobAttrs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgJobAttr[] cfgJobAttrs) throws  Exception{
        int result = 0;
        if(cfgJobAttrs != null) {
            for (CfgJobAttr cfgJobAttr : cfgJobAttrs) {
                if(cfgJobAttr.getId() == null) {
                    result += this.create(cfgJobAttr);
                }else {
                    result += this.update(cfgJobAttr);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务属性定义
    * @param cfgJobAttr
    * @return
    * @throws Exception
    */
    public int update(CfgJobAttr cfgJobAttr) throws  Exception {
        return cfgJobAttrMapper.updateByPrimaryKeySelective(cfgJobAttr);
    }

    /**
    * 通过查询对象更新任务属性定义
    * @param cfgJobAttr
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgJobAttr cfgJobAttr, CfgJobAttr_Example example) throws  Exception {
        return cfgJobAttrMapper.updateByExampleSelective(cfgJobAttr, example);
    }

    /**
    * 删除任务属性定义
    * @param cfgJobAttr
    * @return
    * @throws Exception
    */
    public int delete(CfgJobAttr cfgJobAttr) throws  Exception {
        return cfgJobAttrMapper.deleteByPrimaryKey(cfgJobAttr.getId());
    }

    /**
    * 删除任务属性定义
    * @param cfgJobAttrId
    * @return
    * @throws Exception
    */
    public int delete(long cfgJobAttrId) throws  Exception {
        return cfgJobAttrMapper.deleteByPrimaryKey(cfgJobAttrId);
    }

    /**
    * 查找所有任务属性定义
    * @return
    */
    public List<CfgJobAttr> getCfgJobAttrAll()  throws  Exception {
        return cfgJobAttrMapper.selectByExample(new CfgJobAttr_Example());
    }

    /**
    * 通过任务属性定义ID查询任务属性定义
    * @param cfgJobAttrId
    * @return
    * @throws Exception
    */
    public CfgJobAttr getCfgJobAttrByPK(long cfgJobAttrId)  throws  Exception {
        return cfgJobAttrMapper.selectByPrimaryKey(cfgJobAttrId);
    }


    /**
    * 通过MAP参数查询任务属性定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgJobAttr> getCfgJobAttrListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务属性定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgJobAttr> getCfgJobAttrListByExample(CfgJobAttr_Example example) throws  Exception {
        return cfgJobAttrMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务属性定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgJobAttrCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务属性定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgJobAttrCountByExample(CfgJobAttr_Example example) throws  Exception {
        return cfgJobAttrMapper.countByExample(example);
    }


  	//getter
 	
	public CfgJobAttrMapper getCfgJobAttrMapper(){
		return cfgJobAttrMapper;
	}
	//setter
	public void setCfgJobAttrMapper(CfgJobAttrMapper cfgJobAttrMapper){
    	this.cfgJobAttrMapper = cfgJobAttrMapper;
    }
}
