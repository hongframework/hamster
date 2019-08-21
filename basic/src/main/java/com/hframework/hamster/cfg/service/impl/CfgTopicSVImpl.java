package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgTopicSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgTopic;
import com.hframework.hamster.cfg.domain.model.CfgTopic_Example;
import com.hframework.hamster.cfg.dao.CfgTopicMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgTopicSV;

@Service("iCfgTopicSV")
public class CfgTopicSVImpl  implements ICfgTopicSV {

	@Resource
	private CfgTopicMapper cfgTopicMapper;
  


    /**
    * 创建主题
    * @param cfgTopic
    * @return
    * @throws Exception
    */
    public int create(CfgTopic cfgTopic) throws Exception {
        return cfgTopicMapper.insertSelective(cfgTopic);
    }

    /**
    * 批量维护主题
    * @param cfgTopics
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgTopic[] cfgTopics) throws  Exception{
        int result = 0;
        if(cfgTopics != null) {
            for (CfgTopic cfgTopic : cfgTopics) {
                if(cfgTopic.getCfgTopicId() == null) {
                    result += this.create(cfgTopic);
                }else {
                    result += this.update(cfgTopic);
                }
            }
        }
        return result;
    }

    /**
    * 更新主题
    * @param cfgTopic
    * @return
    * @throws Exception
    */
    public int update(CfgTopic cfgTopic) throws  Exception {
        return cfgTopicMapper.updateByPrimaryKeySelective(cfgTopic);
    }

    /**
    * 通过查询对象更新主题
    * @param cfgTopic
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgTopic cfgTopic, CfgTopic_Example example) throws  Exception {
        return cfgTopicMapper.updateByExampleSelective(cfgTopic, example);
    }

    /**
    * 删除主题
    * @param cfgTopic
    * @return
    * @throws Exception
    */
    public int delete(CfgTopic cfgTopic) throws  Exception {
        return cfgTopicMapper.deleteByPrimaryKey(cfgTopic.getCfgTopicId());
    }

    /**
    * 删除主题
    * @param cfgTopicId
    * @return
    * @throws Exception
    */
    public int delete(long cfgTopicId) throws  Exception {
        return cfgTopicMapper.deleteByPrimaryKey(cfgTopicId);
    }

    /**
    * 查找所有主题
    * @return
    */
    public List<CfgTopic> getCfgTopicAll()  throws  Exception {
        return cfgTopicMapper.selectByExample(new CfgTopic_Example());
    }

    /**
    * 通过主题ID查询主题
    * @param cfgTopicId
    * @return
    * @throws Exception
    */
    public CfgTopic getCfgTopicByPK(long cfgTopicId)  throws  Exception {
        return cfgTopicMapper.selectByPrimaryKey(cfgTopicId);
    }


    /**
    * 通过MAP参数查询主题
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgTopic> getCfgTopicListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询主题
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgTopic> getCfgTopicListByExample(CfgTopic_Example example) throws  Exception {
        return cfgTopicMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询主题数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgTopicCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询主题数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgTopicCountByExample(CfgTopic_Example example) throws  Exception {
        return cfgTopicMapper.countByExample(example);
    }


  	//getter
 	
	public CfgTopicMapper getCfgTopicMapper(){
		return cfgTopicMapper;
	}
	//setter
	public void setCfgTopicMapper(CfgTopicMapper cfgTopicMapper){
    	this.cfgTopicMapper = cfgTopicMapper;
    }
}
