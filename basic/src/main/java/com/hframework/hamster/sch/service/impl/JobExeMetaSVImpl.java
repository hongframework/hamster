package com.hframework.hamster.sch.service.impl;

import java.util.*;

import com.hframework.hamster.sch.dao.JobExeMetaMapper;
import com.hframework.hamster.sch.service.interfaces.IJobExeMetaSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.sch.domain.model.JobExeMeta;
import com.hframework.hamster.sch.domain.model.JobExeMeta_Example;
import com.hframework.hamster.sch.dao.JobExeMetaMapper;
import com.hframework.hamster.sch.service.interfaces.IJobExeMetaSV;

@Service("iJobExeMetaSV")
public class JobExeMetaSVImpl  implements IJobExeMetaSV {

	@Resource
	private JobExeMetaMapper jobExeMetaMapper;
  


    /**
    * 创建任务调度元数据
    * @param jobExeMeta
    * @return
    * @throws Exception
    */
    public int create(JobExeMeta jobExeMeta) throws Exception {
        return jobExeMetaMapper.insertSelective(jobExeMeta);
    }

    /**
    * 批量维护任务调度元数据
    * @param jobExeMetas
    * @return
    * @throws Exception
    */
    public int batchOperate(JobExeMeta[] jobExeMetas) throws  Exception{
        int result = 0;
        if(jobExeMetas != null) {
            for (JobExeMeta jobExeMeta : jobExeMetas) {
                if(jobExeMeta.getId() == null) {
                    result += this.create(jobExeMeta);
                }else {
                    result += this.update(jobExeMeta);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务调度元数据
    * @param jobExeMeta
    * @return
    * @throws Exception
    */
    public int update(JobExeMeta jobExeMeta) throws  Exception {
        return jobExeMetaMapper.updateByPrimaryKeySelective(jobExeMeta);
    }

    /**
    * 通过查询对象更新任务调度元数据
    * @param jobExeMeta
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(JobExeMeta jobExeMeta, JobExeMeta_Example example) throws  Exception {
        return jobExeMetaMapper.updateByExampleSelective(jobExeMeta, example);
    }

    /**
    * 删除任务调度元数据
    * @param jobExeMeta
    * @return
    * @throws Exception
    */
    public int delete(JobExeMeta jobExeMeta) throws  Exception {
        return jobExeMetaMapper.deleteByPrimaryKey(jobExeMeta.getId());
    }

    /**
    * 删除任务调度元数据
    * @param jobExeMetaId
    * @return
    * @throws Exception
    */
    public int delete(long jobExeMetaId) throws  Exception {
        return jobExeMetaMapper.deleteByPrimaryKey(jobExeMetaId);
    }

    /**
    * 查找所有任务调度元数据
    * @return
    */
    public List<JobExeMeta> getJobExeMetaAll()  throws  Exception {
        return jobExeMetaMapper.selectByExample(new JobExeMeta_Example());
    }

    /**
    * 通过任务调度元数据ID查询任务调度元数据
    * @param jobExeMetaId
    * @return
    * @throws Exception
    */
    public JobExeMeta getJobExeMetaByPK(long jobExeMetaId)  throws  Exception {
        return jobExeMetaMapper.selectByPrimaryKey(jobExeMetaId);
    }


    /**
    * 通过MAP参数查询任务调度元数据
    * @param params
    * @return
    * @throws Exception
    */
    public List<JobExeMeta> getJobExeMetaListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务调度元数据
    * @param example
    * @return
    * @throws Exception
    */
    public List<JobExeMeta> getJobExeMetaListByExample(JobExeMeta_Example example) throws  Exception {
        return jobExeMetaMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务调度元数据数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getJobExeMetaCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务调度元数据数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getJobExeMetaCountByExample(JobExeMeta_Example example) throws  Exception {
        return jobExeMetaMapper.countByExample(example);
    }


  	//getter
 	
	public JobExeMetaMapper getJobExeMetaMapper(){
		return jobExeMetaMapper;
	}
	//setter
	public void setJobExeMetaMapper(JobExeMetaMapper jobExeMetaMapper){
    	this.jobExeMetaMapper = jobExeMetaMapper;
    }
}
