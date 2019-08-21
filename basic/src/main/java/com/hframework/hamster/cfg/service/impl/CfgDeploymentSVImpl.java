package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgDeploymentSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgDeployment;
import com.hframework.hamster.cfg.domain.model.CfgDeployment_Example;
import com.hframework.hamster.cfg.dao.CfgDeploymentMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgDeploymentSV;

@Service("iCfgDeploymentSV")
public class CfgDeploymentSVImpl  implements ICfgDeploymentSV {

	@Resource
	private CfgDeploymentMapper cfgDeploymentMapper;
  


    /**
    * 创建发布
    * @param cfgDeployment
    * @return
    * @throws Exception
    */
    public int create(CfgDeployment cfgDeployment) throws Exception {
        return cfgDeploymentMapper.insertSelective(cfgDeployment);
    }

    /**
    * 批量维护发布
    * @param cfgDeployments
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDeployment[] cfgDeployments) throws  Exception{
        int result = 0;
        if(cfgDeployments != null) {
            for (CfgDeployment cfgDeployment : cfgDeployments) {
                if(cfgDeployment.getId() == null) {
                    result += this.create(cfgDeployment);
                }else {
                    result += this.update(cfgDeployment);
                }
            }
        }
        return result;
    }

    /**
    * 更新发布
    * @param cfgDeployment
    * @return
    * @throws Exception
    */
    public int update(CfgDeployment cfgDeployment) throws  Exception {
        return cfgDeploymentMapper.updateByPrimaryKeySelective(cfgDeployment);
    }

    /**
    * 通过查询对象更新发布
    * @param cfgDeployment
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDeployment cfgDeployment, CfgDeployment_Example example) throws  Exception {
        return cfgDeploymentMapper.updateByExampleSelective(cfgDeployment, example);
    }

    /**
    * 删除发布
    * @param cfgDeployment
    * @return
    * @throws Exception
    */
    public int delete(CfgDeployment cfgDeployment) throws  Exception {
        return cfgDeploymentMapper.deleteByPrimaryKey(cfgDeployment.getId());
    }

    /**
    * 删除发布
    * @param cfgDeploymentId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDeploymentId) throws  Exception {
        return cfgDeploymentMapper.deleteByPrimaryKey(cfgDeploymentId);
    }

    /**
    * 查找所有发布
    * @return
    */
    public List<CfgDeployment> getCfgDeploymentAll()  throws  Exception {
        return cfgDeploymentMapper.selectByExample(new CfgDeployment_Example());
    }

    /**
    * 通过发布ID查询发布
    * @param cfgDeploymentId
    * @return
    * @throws Exception
    */
    public CfgDeployment getCfgDeploymentByPK(long cfgDeploymentId)  throws  Exception {
        return cfgDeploymentMapper.selectByPrimaryKey(cfgDeploymentId);
    }


    /**
    * 通过MAP参数查询发布
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDeployment> getCfgDeploymentListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询发布
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDeployment> getCfgDeploymentListByExample(CfgDeployment_Example example) throws  Exception {
        return cfgDeploymentMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询发布数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDeploymentCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询发布数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDeploymentCountByExample(CfgDeployment_Example example) throws  Exception {
        return cfgDeploymentMapper.countByExample(example);
    }


  	//getter
 	
	public CfgDeploymentMapper getCfgDeploymentMapper(){
		return cfgDeploymentMapper;
	}
	//setter
	public void setCfgDeploymentMapper(CfgDeploymentMapper cfgDeploymentMapper){
    	this.cfgDeploymentMapper = cfgDeploymentMapper;
    }
}
