package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgTaskInstSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgTaskInst;
import com.hframework.hamster.cfg.domain.model.CfgTaskInst_Example;
import com.hframework.hamster.cfg.dao.CfgTaskInstMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgTaskInstSV;

@Service("iCfgTaskInstSV")
public class CfgTaskInstSVImpl  implements ICfgTaskInstSV {

	@Resource
	private CfgTaskInstMapper cfgTaskInstMapper;
  


    /**
    * 创建任务实例
    * @param cfgTaskInst
    * @return
    * @throws Exception
    */
    public int create(CfgTaskInst cfgTaskInst) throws Exception {
        return cfgTaskInstMapper.insertSelective(cfgTaskInst);
    }

    /**
    * 批量维护任务实例
    * @param cfgTaskInsts
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgTaskInst[] cfgTaskInsts) throws  Exception{
        int result = 0;
        if(cfgTaskInsts != null) {
            for (CfgTaskInst cfgTaskInst : cfgTaskInsts) {
                if(cfgTaskInst.getCfgTaskInstId() == null) {
                    result += this.create(cfgTaskInst);
                }else {
                    result += this.update(cfgTaskInst);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务实例
    * @param cfgTaskInst
    * @return
    * @throws Exception
    */
    public int update(CfgTaskInst cfgTaskInst) throws  Exception {
        return cfgTaskInstMapper.updateByPrimaryKeySelective(cfgTaskInst);
    }

    /**
    * 通过查询对象更新任务实例
    * @param cfgTaskInst
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgTaskInst cfgTaskInst, CfgTaskInst_Example example) throws  Exception {
        return cfgTaskInstMapper.updateByExampleSelective(cfgTaskInst, example);
    }

    /**
    * 删除任务实例
    * @param cfgTaskInst
    * @return
    * @throws Exception
    */
    public int delete(CfgTaskInst cfgTaskInst) throws  Exception {
        return cfgTaskInstMapper.deleteByPrimaryKey(cfgTaskInst.getCfgTaskInstId());
    }

    /**
    * 删除任务实例
    * @param cfgTaskInstId
    * @return
    * @throws Exception
    */
    public int delete(long cfgTaskInstId) throws  Exception {
        return cfgTaskInstMapper.deleteByPrimaryKey(cfgTaskInstId);
    }

    /**
    * 查找所有任务实例
    * @return
    */
    public List<CfgTaskInst> getCfgTaskInstAll()  throws  Exception {
        return cfgTaskInstMapper.selectByExample(new CfgTaskInst_Example());
    }

    /**
    * 通过任务实例ID查询任务实例
    * @param cfgTaskInstId
    * @return
    * @throws Exception
    */
    public CfgTaskInst getCfgTaskInstByPK(long cfgTaskInstId)  throws  Exception {
        return cfgTaskInstMapper.selectByPrimaryKey(cfgTaskInstId);
    }


    /**
    * 通过MAP参数查询任务实例
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgTaskInst> getCfgTaskInstListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务实例
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgTaskInst> getCfgTaskInstListByExample(CfgTaskInst_Example example) throws  Exception {
        return cfgTaskInstMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务实例数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgTaskInstCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务实例数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgTaskInstCountByExample(CfgTaskInst_Example example) throws  Exception {
        return cfgTaskInstMapper.countByExample(example);
    }


  	//getter
 	
	public CfgTaskInstMapper getCfgTaskInstMapper(){
		return cfgTaskInstMapper;
	}
	//setter
	public void setCfgTaskInstMapper(CfgTaskInstMapper cfgTaskInstMapper){
    	this.cfgTaskInstMapper = cfgTaskInstMapper;
    }
}
