package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgJobSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgJob;
import com.hframework.hamster.cfg.domain.model.CfgJob_Example;
import com.hframework.hamster.cfg.dao.CfgJobMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobSV;

@Service("iCfgJobSV")
public class CfgJobSVImpl  implements ICfgJobSV {

	@Resource
	private CfgJobMapper cfgJobMapper;
  


    /**
    * 创建任务定义
    * @param cfgJob
    * @return
    * @throws Exception
    */
    public int create(CfgJob cfgJob) throws Exception {
        return cfgJobMapper.insertSelective(cfgJob);
    }

    /**
    * 批量维护任务定义
    * @param cfgJobs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgJob[] cfgJobs) throws  Exception{
        int result = 0;
        if(cfgJobs != null) {
            for (CfgJob cfgJob : cfgJobs) {
                if(cfgJob.getId() == null) {
                    result += this.create(cfgJob);
                }else {
                    result += this.update(cfgJob);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务定义
    * @param cfgJob
    * @return
    * @throws Exception
    */
    public int update(CfgJob cfgJob) throws  Exception {
        return cfgJobMapper.updateByPrimaryKeySelective(cfgJob);
    }

    /**
    * 通过查询对象更新任务定义
    * @param cfgJob
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgJob cfgJob, CfgJob_Example example) throws  Exception {
        return cfgJobMapper.updateByExampleSelective(cfgJob, example);
    }

    /**
    * 删除任务定义
    * @param cfgJob
    * @return
    * @throws Exception
    */
    public int delete(CfgJob cfgJob) throws  Exception {
        return cfgJobMapper.deleteByPrimaryKey(cfgJob.getId());
    }

    /**
    * 删除任务定义
    * @param cfgJobId
    * @return
    * @throws Exception
    */
    public int delete(long cfgJobId) throws  Exception {
        return cfgJobMapper.deleteByPrimaryKey(cfgJobId);
    }

    /**
    * 查找所有任务定义
    * @return
    */
    public List<CfgJob> getCfgJobAll()  throws  Exception {
        return cfgJobMapper.selectByExample(new CfgJob_Example());
    }

    /**
    * 通过任务定义ID查询任务定义
    * @param cfgJobId
    * @return
    * @throws Exception
    */
    public CfgJob getCfgJobByPK(long cfgJobId)  throws  Exception {
        return cfgJobMapper.selectByPrimaryKey(cfgJobId);
    }


    /**
    * 通过MAP参数查询任务定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgJob> getCfgJobListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgJob> getCfgJobListByExample(CfgJob_Example example) throws  Exception {
        return cfgJobMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgJobCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgJobCountByExample(CfgJob_Example example) throws  Exception {
        return cfgJobMapper.countByExample(example);
    }


  	//getter
 	
	public CfgJobMapper getCfgJobMapper(){
		return cfgJobMapper;
	}
	//setter
	public void setCfgJobMapper(CfgJobMapper cfgJobMapper){
    	this.cfgJobMapper = cfgJobMapper;
    }
}
