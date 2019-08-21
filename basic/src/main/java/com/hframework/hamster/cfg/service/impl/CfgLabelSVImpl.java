package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgLabelSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgLabel;
import com.hframework.hamster.cfg.domain.model.CfgLabel_Example;
import com.hframework.hamster.cfg.dao.CfgLabelMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgLabelSV;

@Service("iCfgLabelSV")
public class CfgLabelSVImpl  implements ICfgLabelSV {

	@Resource
	private CfgLabelMapper cfgLabelMapper;
  


    /**
    * 创建标签
    * @param cfgLabel
    * @return
    * @throws Exception
    */
    public int create(CfgLabel cfgLabel) throws Exception {
        return cfgLabelMapper.insertSelective(cfgLabel);
    }

    /**
    * 批量维护标签
    * @param cfgLabels
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgLabel[] cfgLabels) throws  Exception{
        int result = 0;
        if(cfgLabels != null) {
            for (CfgLabel cfgLabel : cfgLabels) {
                if(cfgLabel.getId() == null) {
                    result += this.create(cfgLabel);
                }else {
                    result += this.update(cfgLabel);
                }
            }
        }
        return result;
    }

    /**
    * 更新标签
    * @param cfgLabel
    * @return
    * @throws Exception
    */
    public int update(CfgLabel cfgLabel) throws  Exception {
        return cfgLabelMapper.updateByPrimaryKeySelective(cfgLabel);
    }

    /**
    * 通过查询对象更新标签
    * @param cfgLabel
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgLabel cfgLabel, CfgLabel_Example example) throws  Exception {
        return cfgLabelMapper.updateByExampleSelective(cfgLabel, example);
    }

    /**
    * 删除标签
    * @param cfgLabel
    * @return
    * @throws Exception
    */
    public int delete(CfgLabel cfgLabel) throws  Exception {
        return cfgLabelMapper.deleteByPrimaryKey(cfgLabel.getId());
    }

    /**
    * 删除标签
    * @param cfgLabelId
    * @return
    * @throws Exception
    */
    public int delete(long cfgLabelId) throws  Exception {
        return cfgLabelMapper.deleteByPrimaryKey(cfgLabelId);
    }

    /**
    * 查找所有标签
    * @return
    */
    public List<CfgLabel> getCfgLabelAll()  throws  Exception {
        return cfgLabelMapper.selectByExample(new CfgLabel_Example());
    }

    /**
    * 通过标签ID查询标签
    * @param cfgLabelId
    * @return
    * @throws Exception
    */
    public CfgLabel getCfgLabelByPK(long cfgLabelId)  throws  Exception {
        return cfgLabelMapper.selectByPrimaryKey(cfgLabelId);
    }


    /**
    * 通过MAP参数查询标签
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgLabel> getCfgLabelListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询标签
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgLabel> getCfgLabelListByExample(CfgLabel_Example example) throws  Exception {
        return cfgLabelMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询标签数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgLabelCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询标签数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgLabelCountByExample(CfgLabel_Example example) throws  Exception {
        return cfgLabelMapper.countByExample(example);
    }


  	//getter
 	
	public CfgLabelMapper getCfgLabelMapper(){
		return cfgLabelMapper;
	}
	//setter
	public void setCfgLabelMapper(CfgLabelMapper cfgLabelMapper){
    	this.cfgLabelMapper = cfgLabelMapper;
    }
}
