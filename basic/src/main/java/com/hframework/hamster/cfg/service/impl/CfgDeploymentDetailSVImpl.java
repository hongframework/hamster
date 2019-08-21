package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgDeploymentDetailSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgDeploymentDetail;
import com.hframework.hamster.cfg.domain.model.CfgDeploymentDetail_Example;
import com.hframework.hamster.cfg.dao.CfgDeploymentDetailMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgDeploymentDetailSV;

@Service("iCfgDeploymentDetailSV")
public class CfgDeploymentDetailSVImpl  implements ICfgDeploymentDetailSV {

	@Resource
	private CfgDeploymentDetailMapper cfgDeploymentDetailMapper;
  


    /**
    * 创建发布明细
    * @param cfgDeploymentDetail
    * @return
    * @throws Exception
    */
    public int create(CfgDeploymentDetail cfgDeploymentDetail) throws Exception {
        return cfgDeploymentDetailMapper.insertSelective(cfgDeploymentDetail);
    }

    /**
    * 批量维护发布明细
    * @param cfgDeploymentDetails
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDeploymentDetail[] cfgDeploymentDetails) throws  Exception{
        int result = 0;
        if(cfgDeploymentDetails != null) {
            for (CfgDeploymentDetail cfgDeploymentDetail : cfgDeploymentDetails) {
                if(cfgDeploymentDetail.getId() == null) {
                    result += this.create(cfgDeploymentDetail);
                }else {
                    result += this.update(cfgDeploymentDetail);
                }
            }
        }
        return result;
    }

    /**
    * 更新发布明细
    * @param cfgDeploymentDetail
    * @return
    * @throws Exception
    */
    public int update(CfgDeploymentDetail cfgDeploymentDetail) throws  Exception {
        return cfgDeploymentDetailMapper.updateByPrimaryKeySelective(cfgDeploymentDetail);
    }

    /**
    * 通过查询对象更新发布明细
    * @param cfgDeploymentDetail
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDeploymentDetail cfgDeploymentDetail, CfgDeploymentDetail_Example example) throws  Exception {
        return cfgDeploymentDetailMapper.updateByExampleSelective(cfgDeploymentDetail, example);
    }

    /**
    * 删除发布明细
    * @param cfgDeploymentDetail
    * @return
    * @throws Exception
    */
    public int delete(CfgDeploymentDetail cfgDeploymentDetail) throws  Exception {
        return cfgDeploymentDetailMapper.deleteByPrimaryKey(cfgDeploymentDetail.getId());
    }

    /**
    * 删除发布明细
    * @param cfgDeploymentDetailId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDeploymentDetailId) throws  Exception {
        return cfgDeploymentDetailMapper.deleteByPrimaryKey(cfgDeploymentDetailId);
    }

    /**
    * 查找所有发布明细
    * @return
    */
    public List<CfgDeploymentDetail> getCfgDeploymentDetailAll()  throws  Exception {
        return cfgDeploymentDetailMapper.selectByExample(new CfgDeploymentDetail_Example());
    }

    /**
    * 通过发布明细ID查询发布明细
    * @param cfgDeploymentDetailId
    * @return
    * @throws Exception
    */
    public CfgDeploymentDetail getCfgDeploymentDetailByPK(long cfgDeploymentDetailId)  throws  Exception {
        return cfgDeploymentDetailMapper.selectByPrimaryKey(cfgDeploymentDetailId);
    }


    /**
    * 通过MAP参数查询发布明细
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDeploymentDetail> getCfgDeploymentDetailListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询发布明细
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDeploymentDetail> getCfgDeploymentDetailListByExample(CfgDeploymentDetail_Example example) throws  Exception {
        return cfgDeploymentDetailMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询发布明细数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDeploymentDetailCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询发布明细数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDeploymentDetailCountByExample(CfgDeploymentDetail_Example example) throws  Exception {
        return cfgDeploymentDetailMapper.countByExample(example);
    }


  	//getter
 	
	public CfgDeploymentDetailMapper getCfgDeploymentDetailMapper(){
		return cfgDeploymentDetailMapper;
	}
	//setter
	public void setCfgDeploymentDetailMapper(CfgDeploymentDetailMapper cfgDeploymentDetailMapper){
    	this.cfgDeploymentDetailMapper = cfgDeploymentDetailMapper;
    }
}
