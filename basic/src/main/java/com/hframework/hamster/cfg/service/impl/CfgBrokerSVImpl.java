package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgBrokerSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgBroker;
import com.hframework.hamster.cfg.domain.model.CfgBroker_Example;
import com.hframework.hamster.cfg.dao.CfgBrokerMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgBrokerSV;

@Service("iCfgBrokerSV")
public class CfgBrokerSVImpl  implements ICfgBrokerSV {

	@Resource
	private CfgBrokerMapper cfgBrokerMapper;
  


    /**
    * 创建消息队列
    * @param cfgBroker
    * @return
    * @throws Exception
    */
    public int create(CfgBroker cfgBroker) throws Exception {
        return cfgBrokerMapper.insertSelective(cfgBroker);
    }

    /**
    * 批量维护消息队列
    * @param cfgBrokers
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgBroker[] cfgBrokers) throws  Exception{
        int result = 0;
        if(cfgBrokers != null) {
            for (CfgBroker cfgBroker : cfgBrokers) {
                if(cfgBroker.getCfgBrokerId() == null) {
                    result += this.create(cfgBroker);
                }else {
                    result += this.update(cfgBroker);
                }
            }
        }
        return result;
    }

    /**
    * 更新消息队列
    * @param cfgBroker
    * @return
    * @throws Exception
    */
    public int update(CfgBroker cfgBroker) throws  Exception {
        return cfgBrokerMapper.updateByPrimaryKeySelective(cfgBroker);
    }

    /**
    * 通过查询对象更新消息队列
    * @param cfgBroker
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgBroker cfgBroker, CfgBroker_Example example) throws  Exception {
        return cfgBrokerMapper.updateByExampleSelective(cfgBroker, example);
    }

    /**
    * 删除消息队列
    * @param cfgBroker
    * @return
    * @throws Exception
    */
    public int delete(CfgBroker cfgBroker) throws  Exception {
        return cfgBrokerMapper.deleteByPrimaryKey(cfgBroker.getCfgBrokerId());
    }

    /**
    * 删除消息队列
    * @param cfgBrokerId
    * @return
    * @throws Exception
    */
    public int delete(long cfgBrokerId) throws  Exception {
        return cfgBrokerMapper.deleteByPrimaryKey(cfgBrokerId);
    }

    /**
    * 查找所有消息队列
    * @return
    */
    public List<CfgBroker> getCfgBrokerAll()  throws  Exception {
        return cfgBrokerMapper.selectByExample(new CfgBroker_Example());
    }

    /**
    * 通过消息队列ID查询消息队列
    * @param cfgBrokerId
    * @return
    * @throws Exception
    */
    public CfgBroker getCfgBrokerByPK(long cfgBrokerId)  throws  Exception {
        return cfgBrokerMapper.selectByPrimaryKey(cfgBrokerId);
    }


    /**
    * 通过MAP参数查询消息队列
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgBroker> getCfgBrokerListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询消息队列
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgBroker> getCfgBrokerListByExample(CfgBroker_Example example) throws  Exception {
        return cfgBrokerMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询消息队列数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgBrokerCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询消息队列数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgBrokerCountByExample(CfgBroker_Example example) throws  Exception {
        return cfgBrokerMapper.countByExample(example);
    }


  	//getter
 	
	public CfgBrokerMapper getCfgBrokerMapper(){
		return cfgBrokerMapper;
	}
	//setter
	public void setCfgBrokerMapper(CfgBrokerMapper cfgBrokerMapper){
    	this.cfgBrokerMapper = cfgBrokerMapper;
    }
}
