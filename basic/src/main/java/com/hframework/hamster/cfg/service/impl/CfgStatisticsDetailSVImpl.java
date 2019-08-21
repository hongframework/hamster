package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgStatisticsDetailSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgStatisticsDetail;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsDetail_Example;
import com.hframework.hamster.cfg.dao.CfgStatisticsDetailMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgStatisticsDetailSV;

@Service("iCfgStatisticsDetailSV")
public class CfgStatisticsDetailSVImpl  implements ICfgStatisticsDetailSV {

	@Resource
	private CfgStatisticsDetailMapper cfgStatisticsDetailMapper;
  


    /**
    * 创建流量统计明细
    * @param cfgStatisticsDetail
    * @return
    * @throws Exception
    */
    public int create(CfgStatisticsDetail cfgStatisticsDetail) throws Exception {
        return cfgStatisticsDetailMapper.insertSelective(cfgStatisticsDetail);
    }

    /**
    * 批量维护流量统计明细
    * @param cfgStatisticsDetails
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgStatisticsDetail[] cfgStatisticsDetails) throws  Exception{
        int result = 0;
        if(cfgStatisticsDetails != null) {
            for (CfgStatisticsDetail cfgStatisticsDetail : cfgStatisticsDetails) {
                if(cfgStatisticsDetail.getCfgStatisticsDetailId() == null) {
                    result += this.create(cfgStatisticsDetail);
                }else {
                    result += this.update(cfgStatisticsDetail);
                }
            }
        }
        return result;
    }

    /**
    * 更新流量统计明细
    * @param cfgStatisticsDetail
    * @return
    * @throws Exception
    */
    public int update(CfgStatisticsDetail cfgStatisticsDetail) throws  Exception {
        return cfgStatisticsDetailMapper.updateByPrimaryKeySelective(cfgStatisticsDetail);
    }

    /**
    * 通过查询对象更新流量统计明细
    * @param cfgStatisticsDetail
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgStatisticsDetail cfgStatisticsDetail, CfgStatisticsDetail_Example example) throws  Exception {
        return cfgStatisticsDetailMapper.updateByExampleSelective(cfgStatisticsDetail, example);
    }

    /**
    * 删除流量统计明细
    * @param cfgStatisticsDetail
    * @return
    * @throws Exception
    */
    public int delete(CfgStatisticsDetail cfgStatisticsDetail) throws  Exception {
        return cfgStatisticsDetailMapper.deleteByPrimaryKey(cfgStatisticsDetail.getCfgStatisticsDetailId());
    }

    /**
    * 删除流量统计明细
    * @param cfgStatisticsDetailId
    * @return
    * @throws Exception
    */
    public int delete(long cfgStatisticsDetailId) throws  Exception {
        return cfgStatisticsDetailMapper.deleteByPrimaryKey(cfgStatisticsDetailId);
    }

    /**
    * 查找所有流量统计明细
    * @return
    */
    public List<CfgStatisticsDetail> getCfgStatisticsDetailAll()  throws  Exception {
        return cfgStatisticsDetailMapper.selectByExample(new CfgStatisticsDetail_Example());
    }

    /**
    * 通过流量统计明细ID查询流量统计明细
    * @param cfgStatisticsDetailId
    * @return
    * @throws Exception
    */
    public CfgStatisticsDetail getCfgStatisticsDetailByPK(long cfgStatisticsDetailId)  throws  Exception {
        return cfgStatisticsDetailMapper.selectByPrimaryKey(cfgStatisticsDetailId);
    }


    /**
    * 通过MAP参数查询流量统计明细
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgStatisticsDetail> getCfgStatisticsDetailListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询流量统计明细
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgStatisticsDetail> getCfgStatisticsDetailListByExample(CfgStatisticsDetail_Example example) throws  Exception {
        return cfgStatisticsDetailMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询流量统计明细数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsDetailCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询流量统计明细数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgStatisticsDetailCountByExample(CfgStatisticsDetail_Example example) throws  Exception {
        return cfgStatisticsDetailMapper.countByExample(example);
    }


  	//getter
 	
	public CfgStatisticsDetailMapper getCfgStatisticsDetailMapper(){
		return cfgStatisticsDetailMapper;
	}
	//setter
	public void setCfgStatisticsDetailMapper(CfgStatisticsDetailMapper cfgStatisticsDetailMapper){
    	this.cfgStatisticsDetailMapper = cfgStatisticsDetailMapper;
    }
}
