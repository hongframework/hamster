package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgStatisticsSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgStatistics;
import com.hframework.hamster.cfg.domain.model.CfgStatistics_Example;
import com.hframework.hamster.cfg.dao.CfgStatisticsMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgStatisticsSV;

@Service("iCfgStatisticsSV")
public class CfgStatisticsSVImpl  implements ICfgStatisticsSV {

	@Resource
	private CfgStatisticsMapper cfgStatisticsMapper;
  


    /**
    * 创建流量统计
    * @param cfgStatistics
    * @return
    * @throws Exception
    */
    public int create(CfgStatistics cfgStatistics) throws Exception {
        return cfgStatisticsMapper.insertSelective(cfgStatistics);
    }

    /**
    * 批量维护流量统计
    * @param cfgStatisticss
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgStatistics[] cfgStatisticss) throws  Exception{
        int result = 0;
        if(cfgStatisticss != null) {
            for (CfgStatistics cfgStatistics : cfgStatisticss) {
                if(cfgStatistics.getCfgStatisticsId() == null) {
                    result += this.create(cfgStatistics);
                }else {
                    result += this.update(cfgStatistics);
                }
            }
        }
        return result;
    }

    /**
    * 更新流量统计
    * @param cfgStatistics
    * @return
    * @throws Exception
    */
    public int update(CfgStatistics cfgStatistics) throws  Exception {
        return cfgStatisticsMapper.updateByPrimaryKeySelective(cfgStatistics);
    }

    /**
    * 通过查询对象更新流量统计
    * @param cfgStatistics
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgStatistics cfgStatistics, CfgStatistics_Example example) throws  Exception {
        return cfgStatisticsMapper.updateByExampleSelective(cfgStatistics, example);
    }

    /**
    * 删除流量统计
    * @param cfgStatistics
    * @return
    * @throws Exception
    */
    public int delete(CfgStatistics cfgStatistics) throws  Exception {
        return cfgStatisticsMapper.deleteByPrimaryKey(cfgStatistics.getCfgStatisticsId());
    }

    /**
    * 删除流量统计
    * @param cfgStatisticsId
    * @return
    * @throws Exception
    */
    public int delete(long cfgStatisticsId) throws  Exception {
        return cfgStatisticsMapper.deleteByPrimaryKey(cfgStatisticsId);
    }

    /**
    * 查找所有流量统计
    * @return
    */
    public List<CfgStatistics> getCfgStatisticsAll()  throws  Exception {
        return cfgStatisticsMapper.selectByExample(new CfgStatistics_Example());
    }

    /**
    * 通过流量统计ID查询流量统计
    * @param cfgStatisticsId
    * @return
    * @throws Exception
    */
    public CfgStatistics getCfgStatisticsByPK(long cfgStatisticsId)  throws  Exception {
        return cfgStatisticsMapper.selectByPrimaryKey(cfgStatisticsId);
    }


    /**
    * 通过MAP参数查询流量统计
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgStatistics> getCfgStatisticsListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询流量统计
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgStatistics> getCfgStatisticsListByExample(CfgStatistics_Example example) throws  Exception {
        return cfgStatisticsMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询流量统计数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询流量统计数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsCountByExample(CfgStatistics_Example example) throws  Exception {
        return cfgStatisticsMapper.countByExample(example);
    }


  	//getter
 	
	public CfgStatisticsMapper getCfgStatisticsMapper(){
		return cfgStatisticsMapper;
	}
	//setter
	public void setCfgStatisticsMapper(CfgStatisticsMapper cfgStatisticsMapper){
    	this.cfgStatisticsMapper = cfgStatisticsMapper;
    }
}
