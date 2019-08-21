package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgSubscribeSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgSubscribe;
import com.hframework.hamster.cfg.domain.model.CfgSubscribe_Example;
import com.hframework.hamster.cfg.dao.CfgSubscribeMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgSubscribeSV;

@Service("iCfgSubscribeSV")
public class CfgSubscribeSVImpl  implements ICfgSubscribeSV {

	@Resource
	private CfgSubscribeMapper cfgSubscribeMapper;
  


    /**
    * 创建订阅
    * @param cfgSubscribe
    * @return
    * @throws Exception
    */
    public int create(CfgSubscribe cfgSubscribe) throws Exception {
        return cfgSubscribeMapper.insertSelective(cfgSubscribe);
    }

    /**
    * 批量维护订阅
    * @param cfgSubscribes
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgSubscribe[] cfgSubscribes) throws  Exception{
        int result = 0;
        if(cfgSubscribes != null) {
            for (CfgSubscribe cfgSubscribe : cfgSubscribes) {
                if(cfgSubscribe.getCfgSubscribeId() == null) {
                    result += this.create(cfgSubscribe);
                }else {
                    result += this.update(cfgSubscribe);
                }
            }
        }
        return result;
    }

    /**
    * 更新订阅
    * @param cfgSubscribe
    * @return
    * @throws Exception
    */
    public int update(CfgSubscribe cfgSubscribe) throws  Exception {
        return cfgSubscribeMapper.updateByPrimaryKeySelective(cfgSubscribe);
    }

    /**
    * 通过查询对象更新订阅
    * @param cfgSubscribe
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgSubscribe cfgSubscribe, CfgSubscribe_Example example) throws  Exception {
        return cfgSubscribeMapper.updateByExampleSelective(cfgSubscribe, example);
    }

    /**
    * 删除订阅
    * @param cfgSubscribe
    * @return
    * @throws Exception
    */
    public int delete(CfgSubscribe cfgSubscribe) throws  Exception {
        return cfgSubscribeMapper.deleteByPrimaryKey(cfgSubscribe.getCfgSubscribeId());
    }

    /**
    * 删除订阅
    * @param cfgSubscribeId
    * @return
    * @throws Exception
    */
    public int delete(long cfgSubscribeId) throws  Exception {
        return cfgSubscribeMapper.deleteByPrimaryKey(cfgSubscribeId);
    }

    /**
    * 查找所有订阅
    * @return
    */
    public List<CfgSubscribe> getCfgSubscribeAll()  throws  Exception {
        return cfgSubscribeMapper.selectByExample(new CfgSubscribe_Example());
    }

    /**
    * 通过订阅ID查询订阅
    * @param cfgSubscribeId
    * @return
    * @throws Exception
    */
    public CfgSubscribe getCfgSubscribeByPK(long cfgSubscribeId)  throws  Exception {
        return cfgSubscribeMapper.selectByPrimaryKey(cfgSubscribeId);
    }


    /**
    * 通过MAP参数查询订阅
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgSubscribe> getCfgSubscribeListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询订阅
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgSubscribe> getCfgSubscribeListByExample(CfgSubscribe_Example example) throws  Exception {
        return cfgSubscribeMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询订阅数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询订阅数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeCountByExample(CfgSubscribe_Example example) throws  Exception {
        return cfgSubscribeMapper.countByExample(example);
    }


  	//getter
 	
	public CfgSubscribeMapper getCfgSubscribeMapper(){
		return cfgSubscribeMapper;
	}
	//setter
	public void setCfgSubscribeMapper(CfgSubscribeMapper cfgSubscribeMapper){
    	this.cfgSubscribeMapper = cfgSubscribeMapper;
    }
}
