package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgNodeDefSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeDef_Example;
import com.hframework.hamster.cfg.dao.CfgNodeDefMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeDefSV;

@Service("iCfgNodeDefSV")
public class CfgNodeDefSVImpl  implements ICfgNodeDefSV {

	@Resource
	private CfgNodeDefMapper cfgNodeDefMapper;
  


    /**
    * 创建动态节点定义
    * @param cfgNodeDef
    * @return
    * @throws Exception
    */
    public int create(CfgNodeDef cfgNodeDef) throws Exception {
        return cfgNodeDefMapper.insertSelective(cfgNodeDef);
    }

    /**
    * 批量维护动态节点定义
    * @param cfgNodeDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgNodeDef[] cfgNodeDefs) throws  Exception{
        int result = 0;
        if(cfgNodeDefs != null) {
            for (CfgNodeDef cfgNodeDef : cfgNodeDefs) {
                if(cfgNodeDef.getId() == null) {
                    result += this.create(cfgNodeDef);
                }else {
                    result += this.update(cfgNodeDef);
                }
            }
        }
        return result;
    }

    /**
    * 更新动态节点定义
    * @param cfgNodeDef
    * @return
    * @throws Exception
    */
    public int update(CfgNodeDef cfgNodeDef) throws  Exception {
        return cfgNodeDefMapper.updateByPrimaryKeySelective(cfgNodeDef);
    }

    /**
    * 通过查询对象更新动态节点定义
    * @param cfgNodeDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgNodeDef cfgNodeDef, CfgNodeDef_Example example) throws  Exception {
        return cfgNodeDefMapper.updateByExampleSelective(cfgNodeDef, example);
    }

    /**
    * 删除动态节点定义
    * @param cfgNodeDef
    * @return
    * @throws Exception
    */
    public int delete(CfgNodeDef cfgNodeDef) throws  Exception {
        return cfgNodeDefMapper.deleteByPrimaryKey(cfgNodeDef.getId());
    }

    /**
    * 删除动态节点定义
    * @param cfgNodeDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgNodeDefId) throws  Exception {
        return cfgNodeDefMapper.deleteByPrimaryKey(cfgNodeDefId);
    }

    /**
    * 查找所有动态节点定义
    * @return
    */
    public List<CfgNodeDef> getCfgNodeDefAll()  throws  Exception {
        return cfgNodeDefMapper.selectByExample(new CfgNodeDef_Example());
    }

    /**
    * 通过动态节点定义ID查询动态节点定义
    * @param cfgNodeDefId
    * @return
    * @throws Exception
    */
    public CfgNodeDef getCfgNodeDefByPK(long cfgNodeDefId)  throws  Exception {
        return cfgNodeDefMapper.selectByPrimaryKey(cfgNodeDefId);
    }


    /**
    * 通过MAP参数查询动态节点定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgNodeDef> getCfgNodeDefListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询动态节点定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgNodeDef> getCfgNodeDefListByExample(CfgNodeDef_Example example) throws  Exception {
        return cfgNodeDefMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询动态节点定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgNodeDefCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询动态节点定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgNodeDefCountByExample(CfgNodeDef_Example example) throws  Exception {
        return cfgNodeDefMapper.countByExample(example);
    }


  	//getter
 	
	public CfgNodeDefMapper getCfgNodeDefMapper(){
		return cfgNodeDefMapper;
	}
	//setter
	public void setCfgNodeDefMapper(CfgNodeDefMapper cfgNodeDefMapper){
    	this.cfgNodeDefMapper = cfgNodeDefMapper;
    }
}
