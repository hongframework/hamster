package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgTaskNodeDefSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgTaskNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgTaskNodeDef_Example;
import com.hframework.hamster.cfg.dao.CfgTaskNodeDefMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgTaskNodeDefSV;

@Service("iCfgTaskNodeDefSV")
public class CfgTaskNodeDefSVImpl  implements ICfgTaskNodeDefSV {

	@Resource
	private CfgTaskNodeDefMapper cfgTaskNodeDefMapper;
  


    /**
    * 创建任务子节点定义
    * @param cfgTaskNodeDef
    * @return
    * @throws Exception
    */
    public int create(CfgTaskNodeDef cfgTaskNodeDef) throws Exception {
        return cfgTaskNodeDefMapper.insertSelective(cfgTaskNodeDef);
    }

    /**
    * 批量维护任务子节点定义
    * @param cfgTaskNodeDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgTaskNodeDef[] cfgTaskNodeDefs) throws  Exception{
        int result = 0;
        if(cfgTaskNodeDefs != null) {
            for (CfgTaskNodeDef cfgTaskNodeDef : cfgTaskNodeDefs) {
                if(cfgTaskNodeDef.getCfgTaskNodeDefId() == null) {
                    result += this.create(cfgTaskNodeDef);
                }else {
                    result += this.update(cfgTaskNodeDef);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务子节点定义
    * @param cfgTaskNodeDef
    * @return
    * @throws Exception
    */
    public int update(CfgTaskNodeDef cfgTaskNodeDef) throws  Exception {
        return cfgTaskNodeDefMapper.updateByPrimaryKeySelective(cfgTaskNodeDef);
    }

    /**
    * 通过查询对象更新任务子节点定义
    * @param cfgTaskNodeDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgTaskNodeDef cfgTaskNodeDef, CfgTaskNodeDef_Example example) throws  Exception {
        return cfgTaskNodeDefMapper.updateByExampleSelective(cfgTaskNodeDef, example);
    }

    /**
    * 删除任务子节点定义
    * @param cfgTaskNodeDef
    * @return
    * @throws Exception
    */
    public int delete(CfgTaskNodeDef cfgTaskNodeDef) throws  Exception {
        return cfgTaskNodeDefMapper.deleteByPrimaryKey(cfgTaskNodeDef.getCfgTaskNodeDefId());
    }

    /**
    * 删除任务子节点定义
    * @param cfgTaskNodeDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgTaskNodeDefId) throws  Exception {
        return cfgTaskNodeDefMapper.deleteByPrimaryKey(cfgTaskNodeDefId);
    }

    /**
    * 查找所有任务子节点定义
    * @return
    */
    public List<CfgTaskNodeDef> getCfgTaskNodeDefAll()  throws  Exception {
        return cfgTaskNodeDefMapper.selectByExample(new CfgTaskNodeDef_Example());
    }

    /**
    * 通过任务子节点定义ID查询任务子节点定义
    * @param cfgTaskNodeDefId
    * @return
    * @throws Exception
    */
    public CfgTaskNodeDef getCfgTaskNodeDefByPK(long cfgTaskNodeDefId)  throws  Exception {
        return cfgTaskNodeDefMapper.selectByPrimaryKey(cfgTaskNodeDefId);
    }


    /**
    * 通过MAP参数查询任务子节点定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgTaskNodeDef> getCfgTaskNodeDefListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务子节点定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgTaskNodeDef> getCfgTaskNodeDefListByExample(CfgTaskNodeDef_Example example) throws  Exception {
        return cfgTaskNodeDefMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务子节点定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgTaskNodeDefCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务子节点定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgTaskNodeDefCountByExample(CfgTaskNodeDef_Example example) throws  Exception {
        return cfgTaskNodeDefMapper.countByExample(example);
    }


  	//getter
 	
	public CfgTaskNodeDefMapper getCfgTaskNodeDefMapper(){
		return cfgTaskNodeDefMapper;
	}
	//setter
	public void setCfgTaskNodeDefMapper(CfgTaskNodeDefMapper cfgTaskNodeDefMapper){
    	this.cfgTaskNodeDefMapper = cfgTaskNodeDefMapper;
    }
}
