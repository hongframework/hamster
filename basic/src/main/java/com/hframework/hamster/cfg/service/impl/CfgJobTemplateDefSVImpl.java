package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgJobTemplateDefSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateDef;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateDef_Example;
import com.hframework.hamster.cfg.dao.CfgJobTemplateDefMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobTemplateDefSV;

@Service("iCfgJobTemplateDefSV")
public class CfgJobTemplateDefSVImpl  implements ICfgJobTemplateDefSV {

	@Resource
	private CfgJobTemplateDefMapper cfgJobTemplateDefMapper;
  


    /**
    * 创建任务模板定义
    * @param cfgJobTemplateDef
    * @return
    * @throws Exception
    */
    public int create(CfgJobTemplateDef cfgJobTemplateDef) throws Exception {
        return cfgJobTemplateDefMapper.insertSelective(cfgJobTemplateDef);
    }

    /**
    * 批量维护任务模板定义
    * @param cfgJobTemplateDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgJobTemplateDef[] cfgJobTemplateDefs) throws  Exception{
        int result = 0;
        if(cfgJobTemplateDefs != null) {
            for (CfgJobTemplateDef cfgJobTemplateDef : cfgJobTemplateDefs) {
                if(cfgJobTemplateDef.getId() == null) {
                    result += this.create(cfgJobTemplateDef);
                }else {
                    result += this.update(cfgJobTemplateDef);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务模板定义
    * @param cfgJobTemplateDef
    * @return
    * @throws Exception
    */
    public int update(CfgJobTemplateDef cfgJobTemplateDef) throws  Exception {
        return cfgJobTemplateDefMapper.updateByPrimaryKeySelective(cfgJobTemplateDef);
    }

    /**
    * 通过查询对象更新任务模板定义
    * @param cfgJobTemplateDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgJobTemplateDef cfgJobTemplateDef, CfgJobTemplateDef_Example example) throws  Exception {
        return cfgJobTemplateDefMapper.updateByExampleSelective(cfgJobTemplateDef, example);
    }

    /**
    * 删除任务模板定义
    * @param cfgJobTemplateDef
    * @return
    * @throws Exception
    */
    public int delete(CfgJobTemplateDef cfgJobTemplateDef) throws  Exception {
        return cfgJobTemplateDefMapper.deleteByPrimaryKey(cfgJobTemplateDef.getId());
    }

    /**
    * 删除任务模板定义
    * @param cfgJobTemplateDefId
    * @return
    * @throws Exception
    */
    public int delete(long cfgJobTemplateDefId) throws  Exception {
        return cfgJobTemplateDefMapper.deleteByPrimaryKey(cfgJobTemplateDefId);
    }

    /**
    * 查找所有任务模板定义
    * @return
    */
    public List<CfgJobTemplateDef> getCfgJobTemplateDefAll()  throws  Exception {
        return cfgJobTemplateDefMapper.selectByExample(new CfgJobTemplateDef_Example());
    }

    /**
    * 通过任务模板定义ID查询任务模板定义
    * @param cfgJobTemplateDefId
    * @return
    * @throws Exception
    */
    public CfgJobTemplateDef getCfgJobTemplateDefByPK(long cfgJobTemplateDefId)  throws  Exception {
        return cfgJobTemplateDefMapper.selectByPrimaryKey(cfgJobTemplateDefId);
    }


    /**
    * 通过MAP参数查询任务模板定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgJobTemplateDef> getCfgJobTemplateDefListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务模板定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgJobTemplateDef> getCfgJobTemplateDefListByExample(CfgJobTemplateDef_Example example) throws  Exception {
        return cfgJobTemplateDefMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务模板定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgJobTemplateDefCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务模板定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgJobTemplateDefCountByExample(CfgJobTemplateDef_Example example) throws  Exception {
        return cfgJobTemplateDefMapper.countByExample(example);
    }


  	//getter
 	
	public CfgJobTemplateDefMapper getCfgJobTemplateDefMapper(){
		return cfgJobTemplateDefMapper;
	}
	//setter
	public void setCfgJobTemplateDefMapper(CfgJobTemplateDefMapper cfgJobTemplateDefMapper){
    	this.cfgJobTemplateDefMapper = cfgJobTemplateDefMapper;
    }
}
