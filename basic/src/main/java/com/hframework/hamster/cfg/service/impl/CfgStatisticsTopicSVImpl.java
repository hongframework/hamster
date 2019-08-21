package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgStatisticsTopicSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgStatisticsTopic;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsTopic_Example;
import com.hframework.hamster.cfg.dao.CfgStatisticsTopicMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgStatisticsTopicSV;

@Service("iCfgStatisticsTopicSV")
public class CfgStatisticsTopicSVImpl  implements ICfgStatisticsTopicSV {

	@Resource
	private CfgStatisticsTopicMapper cfgStatisticsTopicMapper;
  


    /**
    * 创建统计主题
    * @param cfgStatisticsTopic
    * @return
    * @throws Exception
    */
    public int create(CfgStatisticsTopic cfgStatisticsTopic) throws Exception {
        return cfgStatisticsTopicMapper.insertSelective(cfgStatisticsTopic);
    }

    /**
    * 批量维护统计主题
    * @param cfgStatisticsTopics
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgStatisticsTopic[] cfgStatisticsTopics) throws  Exception{
        int result = 0;
        if(cfgStatisticsTopics != null) {
            for (CfgStatisticsTopic cfgStatisticsTopic : cfgStatisticsTopics) {
                if(cfgStatisticsTopic.getCfgStatisticsTopicId() == null) {
                    result += this.create(cfgStatisticsTopic);
                }else {
                    result += this.update(cfgStatisticsTopic);
                }
            }
        }
        return result;
    }

    /**
    * 更新统计主题
    * @param cfgStatisticsTopic
    * @return
    * @throws Exception
    */
    public int update(CfgStatisticsTopic cfgStatisticsTopic) throws  Exception {
        return cfgStatisticsTopicMapper.updateByPrimaryKeySelective(cfgStatisticsTopic);
    }

    /**
    * 通过查询对象更新统计主题
    * @param cfgStatisticsTopic
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgStatisticsTopic cfgStatisticsTopic, CfgStatisticsTopic_Example example) throws  Exception {
        return cfgStatisticsTopicMapper.updateByExampleSelective(cfgStatisticsTopic, example);
    }

    /**
    * 删除统计主题
    * @param cfgStatisticsTopic
    * @return
    * @throws Exception
    */
    public int delete(CfgStatisticsTopic cfgStatisticsTopic) throws  Exception {
        return cfgStatisticsTopicMapper.deleteByPrimaryKey(cfgStatisticsTopic.getCfgStatisticsTopicId());
    }

    /**
    * 删除统计主题
    * @param cfgStatisticsTopicId
    * @return
    * @throws Exception
    */
    public int delete(long cfgStatisticsTopicId) throws  Exception {
        return cfgStatisticsTopicMapper.deleteByPrimaryKey(cfgStatisticsTopicId);
    }

    /**
    * 查找所有统计主题
    * @return
    */
    public List<CfgStatisticsTopic> getCfgStatisticsTopicAll()  throws  Exception {
        return cfgStatisticsTopicMapper.selectByExample(new CfgStatisticsTopic_Example());
    }

    /**
    * 通过统计主题ID查询统计主题
    * @param cfgStatisticsTopicId
    * @return
    * @throws Exception
    */
    public CfgStatisticsTopic getCfgStatisticsTopicByPK(long cfgStatisticsTopicId)  throws  Exception {
        return cfgStatisticsTopicMapper.selectByPrimaryKey(cfgStatisticsTopicId);
    }


    /**
    * 通过MAP参数查询统计主题
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgStatisticsTopic> getCfgStatisticsTopicListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询统计主题
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgStatisticsTopic> getCfgStatisticsTopicListByExample(CfgStatisticsTopic_Example example) throws  Exception {
        return cfgStatisticsTopicMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询统计主题数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsTopicCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询统计主题数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsTopicCountByExample(CfgStatisticsTopic_Example example) throws  Exception {
        return cfgStatisticsTopicMapper.countByExample(example);
    }


  	//getter
 	
	public CfgStatisticsTopicMapper getCfgStatisticsTopicMapper(){
		return cfgStatisticsTopicMapper;
	}
	//setter
	public void setCfgStatisticsTopicMapper(CfgStatisticsTopicMapper cfgStatisticsTopicMapper){
    	this.cfgStatisticsTopicMapper = cfgStatisticsTopicMapper;
    }
}
