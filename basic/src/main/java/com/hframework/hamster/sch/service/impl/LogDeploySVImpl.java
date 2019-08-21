package com.hframework.hamster.sch.service.impl;

import java.util.*;

import com.hframework.hamster.sch.dao.LogDeployMapper;
import com.hframework.hamster.sch.service.interfaces.ILogDeploySV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.sch.domain.model.LogDeploy;
import com.hframework.hamster.sch.domain.model.LogDeploy_Example;
import com.hframework.hamster.sch.dao.LogDeployMapper;
import com.hframework.hamster.sch.service.interfaces.ILogDeploySV;

@Service("iLogDeploySV")
public class LogDeploySVImpl  implements ILogDeploySV {

	@Resource
	private LogDeployMapper logDeployMapper;
  


    /**
    * 创建发布日志
    * @param logDeploy
    * @return
    * @throws Exception
    */
    public int create(LogDeploy logDeploy) throws Exception {
        return logDeployMapper.insertSelective(logDeploy);
    }

    /**
    * 批量维护发布日志
    * @param logDeploys
    * @return
    * @throws Exception
    */
    public int batchOperate(LogDeploy[] logDeploys) throws  Exception{
        int result = 0;
        if(logDeploys != null) {
            for (LogDeploy logDeploy : logDeploys) {
                if(logDeploy.getId() == null) {
                    result += this.create(logDeploy);
                }else {
                    result += this.update(logDeploy);
                }
            }
        }
        return result;
    }

    /**
    * 更新发布日志
    * @param logDeploy
    * @return
    * @throws Exception
    */
    public int update(LogDeploy logDeploy) throws  Exception {
        return logDeployMapper.updateByPrimaryKeySelective(logDeploy);
    }

    /**
    * 通过查询对象更新发布日志
    * @param logDeploy
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(LogDeploy logDeploy, LogDeploy_Example example) throws  Exception {
        return logDeployMapper.updateByExampleSelective(logDeploy, example);
    }

    /**
    * 删除发布日志
    * @param logDeploy
    * @return
    * @throws Exception
    */
    public int delete(LogDeploy logDeploy) throws  Exception {
        return logDeployMapper.deleteByPrimaryKey(logDeploy.getId());
    }

    /**
    * 删除发布日志
    * @param logDeployId
    * @return
    * @throws Exception
    */
    public int delete(long logDeployId) throws  Exception {
        return logDeployMapper.deleteByPrimaryKey(logDeployId);
    }

    /**
    * 查找所有发布日志
    * @return
    */
    public List<LogDeploy> getLogDeployAll()  throws  Exception {
        return logDeployMapper.selectByExample(new LogDeploy_Example());
    }

    /**
    * 通过发布日志ID查询发布日志
    * @param logDeployId
    * @return
    * @throws Exception
    */
    public LogDeploy getLogDeployByPK(long logDeployId)  throws  Exception {
        return logDeployMapper.selectByPrimaryKey(logDeployId);
    }


    /**
    * 通过MAP参数查询发布日志
    * @param params
    * @return
    * @throws Exception
    */
    public List<LogDeploy> getLogDeployListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询发布日志
    * @param example
    * @return
    * @throws Exception
    */
    public List<LogDeploy> getLogDeployListByExample(LogDeploy_Example example) throws  Exception {
        return logDeployMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询发布日志数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getLogDeployCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询发布日志数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getLogDeployCountByExample(LogDeploy_Example example) throws  Exception {
        return logDeployMapper.countByExample(example);
    }


  	//getter
 	
	public LogDeployMapper getLogDeployMapper(){
		return logDeployMapper;
	}
	//setter
	public void setLogDeployMapper(LogDeployMapper logDeployMapper){
    	this.logDeployMapper = logDeployMapper;
    }
}
