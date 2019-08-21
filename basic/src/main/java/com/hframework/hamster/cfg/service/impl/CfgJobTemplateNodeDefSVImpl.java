package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgJobTemplateNodeDefSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateNodeDef_Example;
import com.hframework.hamster.cfg.dao.CfgJobTemplateNodeDefMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobTemplateNodeDefSV;

@Service("iCfgJobTemplateNodeDefSV")
public class CfgJobTemplateNodeDefSVImpl  implements ICfgJobTemplateNodeDefSV {

	@Resource
	private CfgJobTemplateNodeDefMapper cfgJobTemplateNodeDefMapper;
  


    /**
    * 创建任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @return
    * @throws Exception
    */
    public int create(CfgJobTemplateNodeDef cfgJobTemplateNodeDef) throws Exception {
        return cfgJobTemplateNodeDefMapper.insertSelective(cfgJobTemplateNodeDef);
    }

    /**
    * 批量维护任务模板节点定义
    * @param cfgJobTemplateNodeDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgJobTemplateNodeDef[] cfgJobTemplateNodeDefs) throws  Exception{
        int result = 0;
        if(cfgJobTemplateNodeDefs != null) {
            for (CfgJobTemplateNodeDef cfgJobTemplateNodeDef : cfgJobTemplateNodeDefs) {
                if(cfgJobTemplateNodeDef.getId() == null) {
                    result += this.create(cfgJobTemplateNodeDef);
                }else {
                    result += this.update(cfgJobTemplateNodeDef);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @return
    * @throws Exception
    */
    public int update(CfgJobTemplateNodeDef cfgJobTemplateNodeDef) throws  Exception {
        return cfgJobTemplateNodeDefMapper.updateByPrimaryKeySelective(cfgJobTemplateNodeDef);
    }

    /**
    * 通过查询对象更新任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgJobTemplateNodeDef cfgJobTemplateNodeDef, CfgJobTemplateNodeDef_Example example) throws  Exception {
        return cfgJobTemplateNodeDefMapper.updateByExampleSelective(cfgJobTemplateNodeDef, example);
    }

    /**
    * 删除任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @return
    * @throws Exception
    */
    public int delete(CfgJobTemplateNodeDef cfgJobTemplateNodeDef) throws  Exception {
        return cfgJobTemplateNodeDefMapper.deleteByPrimaryKey(cfgJobTemplateNodeDef.getId());
    }

    /**
    * 删除任务模板节点定义
    * @param cfgJobTemplateNodeDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgJobTemplateNodeDefId) throws  Exception {
        return cfgJobTemplateNodeDefMapper.deleteByPrimaryKey(cfgJobTemplateNodeDefId);
    }

    /**
    * 查找所有任务模板节点定义
    * @return
    */
    public List<CfgJobTemplateNodeDef> getCfgJobTemplateNodeDefAll()  throws  Exception {
        return cfgJobTemplateNodeDefMapper.selectByExample(new CfgJobTemplateNodeDef_Example());
    }

    /**
    * 通过任务模板节点定义ID查询任务模板节点定义
    * @param cfgJobTemplateNodeDefId
    * @return
    * @throws Exception
    */
    public CfgJobTemplateNodeDef getCfgJobTemplateNodeDefByPK(long cfgJobTemplateNodeDefId)  throws  Exception {
        return cfgJobTemplateNodeDefMapper.selectByPrimaryKey(cfgJobTemplateNodeDefId);
    }


    /**
    * 通过MAP参数查询任务模板节点定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgJobTemplateNodeDef> getCfgJobTemplateNodeDefListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务模板节点定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgJobTemplateNodeDef> getCfgJobTemplateNodeDefListByExample(CfgJobTemplateNodeDef_Example example) throws  Exception {
        return cfgJobTemplateNodeDefMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务模板节点定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgJobTemplateNodeDefCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务模板节点定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgJobTemplateNodeDefCountByExample(CfgJobTemplateNodeDef_Example example) throws  Exception {
        return cfgJobTemplateNodeDefMapper.countByExample(example);
    }


  	//getter
 	
	public CfgJobTemplateNodeDefMapper getCfgJobTemplateNodeDefMapper(){
		return cfgJobTemplateNodeDefMapper;
	}
	//setter
	public void setCfgJobTemplateNodeDefMapper(CfgJobTemplateNodeDefMapper cfgJobTemplateNodeDefMapper){
    	this.cfgJobTemplateNodeDefMapper = cfgJobTemplateNodeDefMapper;
    }
}
