package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgTaskDefSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgTaskDef;
import com.hframework.hamster.cfg.domain.model.CfgTaskDef_Example;
import com.hframework.hamster.cfg.dao.CfgTaskDefMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgTaskDefSV;

@Service("iCfgTaskDefSV")
public class CfgTaskDefSVImpl  implements ICfgTaskDefSV {

	@Resource
	private CfgTaskDefMapper cfgTaskDefMapper;
  


    /**
    * 创建任务定义
    * @param cfgTaskDef
    * @return
    * @throws Exception
    */
    public int create(CfgTaskDef cfgTaskDef) throws Exception {
        return cfgTaskDefMapper.insertSelective(cfgTaskDef);
    }

    /**
    * 批量维护任务定义
    * @param cfgTaskDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgTaskDef[] cfgTaskDefs) throws  Exception{
        int result = 0;
        if(cfgTaskDefs != null) {
            for (CfgTaskDef cfgTaskDef : cfgTaskDefs) {
                if(cfgTaskDef.getCfgTaskDefId() == null) {
                    result += this.create(cfgTaskDef);
                }else {
                    result += this.update(cfgTaskDef);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务定义
    * @param cfgTaskDef
    * @return
    * @throws Exception
    */
    public int update(CfgTaskDef cfgTaskDef) throws  Exception {
        return cfgTaskDefMapper.updateByPrimaryKeySelective(cfgTaskDef);
    }

    /**
    * 通过查询对象更新任务定义
    * @param cfgTaskDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgTaskDef cfgTaskDef, CfgTaskDef_Example example) throws  Exception {
        return cfgTaskDefMapper.updateByExampleSelective(cfgTaskDef, example);
    }

    /**
    * 删除任务定义
    * @param cfgTaskDef
    * @return
    * @throws Exception
    */
    public int delete(CfgTaskDef cfgTaskDef) throws  Exception {
        return cfgTaskDefMapper.deleteByPrimaryKey(cfgTaskDef.getCfgTaskDefId());
    }

    /**
    * 删除任务定义
    * @param cfgTaskDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgTaskDefId) throws  Exception {
        return cfgTaskDefMapper.deleteByPrimaryKey(cfgTaskDefId);
    }

    /**
    * 查找所有任务定义
    * @return
    */
    public List<CfgTaskDef> getCfgTaskDefAll()  throws  Exception {
        return cfgTaskDefMapper.selectByExample(new CfgTaskDef_Example());
    }

    /**
    * 通过任务定义ID查询任务定义
    * @param cfgTaskDefId
    * @return
    * @throws Exception
    */
    public CfgTaskDef getCfgTaskDefByPK(long cfgTaskDefId)  throws  Exception {
        return cfgTaskDefMapper.selectByPrimaryKey(cfgTaskDefId);
    }


    /**
    * 通过MAP参数查询任务定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgTaskDef> getCfgTaskDefListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgTaskDef> getCfgTaskDefListByExample(CfgTaskDef_Example example) throws  Exception {
        return cfgTaskDefMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgTaskDefCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgTaskDefCountByExample(CfgTaskDef_Example example) throws  Exception {
        return cfgTaskDefMapper.countByExample(example);
    }


  	//getter
 	
	public CfgTaskDefMapper getCfgTaskDefMapper(){
		return cfgTaskDefMapper;
	}
	//setter
	public void setCfgTaskDefMapper(CfgTaskDefMapper cfgTaskDefMapper){
    	this.cfgTaskDefMapper = cfgTaskDefMapper;
    }
}
