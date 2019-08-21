package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgSubscribeDataSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgSubscribeData;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeData_Example;
import com.hframework.hamster.cfg.dao.CfgSubscribeDataMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgSubscribeDataSV;

@Service("iCfgSubscribeDataSV")
public class CfgSubscribeDataSVImpl  implements ICfgSubscribeDataSV {

	@Resource
	private CfgSubscribeDataMapper cfgSubscribeDataMapper;
  


    /**
    * 创建订阅数据
    * @param cfgSubscribeData
    * @return
    * @throws Exception
    */
    public int create(CfgSubscribeData cfgSubscribeData) throws Exception {
        return cfgSubscribeDataMapper.insertSelective(cfgSubscribeData);
    }

    /**
    * 批量维护订阅数据
    * @param cfgSubscribeDatas
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgSubscribeData[] cfgSubscribeDatas) throws  Exception{
        int result = 0;
        if(cfgSubscribeDatas != null) {
            for (CfgSubscribeData cfgSubscribeData : cfgSubscribeDatas) {
                if(cfgSubscribeData.getCfgSubscribeDataId() == null) {
                    result += this.create(cfgSubscribeData);
                }else {
                    result += this.update(cfgSubscribeData);
                }
            }
        }
        return result;
    }

    /**
    * 更新订阅数据
    * @param cfgSubscribeData
    * @return
    * @throws Exception
    */
    public int update(CfgSubscribeData cfgSubscribeData) throws  Exception {
        return cfgSubscribeDataMapper.updateByPrimaryKeySelective(cfgSubscribeData);
    }

    /**
    * 通过查询对象更新订阅数据
    * @param cfgSubscribeData
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgSubscribeData cfgSubscribeData, CfgSubscribeData_Example example) throws  Exception {
        return cfgSubscribeDataMapper.updateByExampleSelective(cfgSubscribeData, example);
    }

    /**
    * 删除订阅数据
    * @param cfgSubscribeData
    * @return
    * @throws Exception
    */
    public int delete(CfgSubscribeData cfgSubscribeData) throws  Exception {
        return cfgSubscribeDataMapper.deleteByPrimaryKey(cfgSubscribeData.getCfgSubscribeDataId());
    }

    /**
    * 删除订阅数据
    * @param cfgSubscribeDataId
    * @return
    * @throws Exception
    */
    public int delete(long cfgSubscribeDataId) throws  Exception {
        return cfgSubscribeDataMapper.deleteByPrimaryKey(cfgSubscribeDataId);
    }

    /**
    * 查找所有订阅数据
    * @return
    */
    public List<CfgSubscribeData> getCfgSubscribeDataAll()  throws  Exception {
        return cfgSubscribeDataMapper.selectByExample(new CfgSubscribeData_Example());
    }

    /**
    * 通过订阅数据ID查询订阅数据
    * @param cfgSubscribeDataId
    * @return
    * @throws Exception
    */
    public CfgSubscribeData getCfgSubscribeDataByPK(long cfgSubscribeDataId)  throws  Exception {
        return cfgSubscribeDataMapper.selectByPrimaryKey(cfgSubscribeDataId);
    }


    /**
    * 通过MAP参数查询订阅数据
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgSubscribeData> getCfgSubscribeDataListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询订阅数据
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgSubscribeData> getCfgSubscribeDataListByExample(CfgSubscribeData_Example example) throws  Exception {
        return cfgSubscribeDataMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询订阅数据数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeDataCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询订阅数据数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeDataCountByExample(CfgSubscribeData_Example example) throws  Exception {
        return cfgSubscribeDataMapper.countByExample(example);
    }


  	//getter
 	
	public CfgSubscribeDataMapper getCfgSubscribeDataMapper(){
		return cfgSubscribeDataMapper;
	}
	//setter
	public void setCfgSubscribeDataMapper(CfgSubscribeDataMapper cfgSubscribeDataMapper){
    	this.cfgSubscribeDataMapper = cfgSubscribeDataMapper;
    }
}
