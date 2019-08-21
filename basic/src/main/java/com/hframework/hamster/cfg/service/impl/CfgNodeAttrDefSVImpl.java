package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgNodeAttrDefSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgNodeAttrDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeAttrDef_Example;
import com.hframework.hamster.cfg.dao.CfgNodeAttrDefMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeAttrDefSV;

@Service("iCfgNodeAttrDefSV")
public class CfgNodeAttrDefSVImpl  implements ICfgNodeAttrDefSV {

	@Resource
	private CfgNodeAttrDefMapper cfgNodeAttrDefMapper;
  


    /**
    * 创建动态节点属性定义
    * @param cfgNodeAttrDef
    * @return
    * @throws Exception
    */
    public int create(CfgNodeAttrDef cfgNodeAttrDef) throws Exception {
        return cfgNodeAttrDefMapper.insertSelective(cfgNodeAttrDef);
    }

    /**
    * 批量维护动态节点属性定义
    * @param cfgNodeAttrDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgNodeAttrDef[] cfgNodeAttrDefs) throws  Exception{
        int result = 0;
        if(cfgNodeAttrDefs != null) {
            for (CfgNodeAttrDef cfgNodeAttrDef : cfgNodeAttrDefs) {
                if(cfgNodeAttrDef.getId() == null) {
                    result += this.create(cfgNodeAttrDef);
                }else {
                    result += this.update(cfgNodeAttrDef);
                }
            }
        }
        return result;
    }

    /**
    * 更新动态节点属性定义
    * @param cfgNodeAttrDef
    * @return
    * @throws Exception
    */
    public int update(CfgNodeAttrDef cfgNodeAttrDef) throws  Exception {
        return cfgNodeAttrDefMapper.updateByPrimaryKeySelective(cfgNodeAttrDef);
    }

    /**
    * 通过查询对象更新动态节点属性定义
    * @param cfgNodeAttrDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgNodeAttrDef cfgNodeAttrDef, CfgNodeAttrDef_Example example) throws  Exception {
        return cfgNodeAttrDefMapper.updateByExampleSelective(cfgNodeAttrDef, example);
    }

    /**
    * 删除动态节点属性定义
    * @param cfgNodeAttrDef
    * @return
    * @throws Exception
    */
    public int delete(CfgNodeAttrDef cfgNodeAttrDef) throws  Exception {
        return cfgNodeAttrDefMapper.deleteByPrimaryKey(cfgNodeAttrDef.getId());
    }

    /**
    * 删除动态节点属性定义
    * @param cfgNodeAttrDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgNodeAttrDefId) throws  Exception {
        return cfgNodeAttrDefMapper.deleteByPrimaryKey(cfgNodeAttrDefId);
    }

    /**
    * 查找所有动态节点属性定义
    * @return
    */
    public List<CfgNodeAttrDef> getCfgNodeAttrDefAll()  throws  Exception {
        return cfgNodeAttrDefMapper.selectByExample(new CfgNodeAttrDef_Example());
    }

    /**
    * 通过动态节点属性定义ID查询动态节点属性定义
    * @param cfgNodeAttrDefId
    * @return
    * @throws Exception
    */
    public CfgNodeAttrDef getCfgNodeAttrDefByPK(long cfgNodeAttrDefId)  throws  Exception {
        return cfgNodeAttrDefMapper.selectByPrimaryKey(cfgNodeAttrDefId);
    }


    /**
    * 通过MAP参数查询动态节点属性定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgNodeAttrDef> getCfgNodeAttrDefListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询动态节点属性定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgNodeAttrDef> getCfgNodeAttrDefListByExample(CfgNodeAttrDef_Example example) throws  Exception {
        return cfgNodeAttrDefMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询动态节点属性定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgNodeAttrDefCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询动态节点属性定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgNodeAttrDefCountByExample(CfgNodeAttrDef_Example example) throws  Exception {
        return cfgNodeAttrDefMapper.countByExample(example);
    }


  	//getter
 	
	public CfgNodeAttrDefMapper getCfgNodeAttrDefMapper(){
		return cfgNodeAttrDefMapper;
	}
	//setter
	public void setCfgNodeAttrDefMapper(CfgNodeAttrDefMapper cfgNodeAttrDefMapper){
    	this.cfgNodeAttrDefMapper = cfgNodeAttrDefMapper;
    }
}
