package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgSubscribeDetailSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail_Example;
import com.hframework.hamster.cfg.dao.CfgSubscribeDetailMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgSubscribeDetailSV;

@Service("iCfgSubscribeDetailSV")
public class CfgSubscribeDetailSVImpl  implements ICfgSubscribeDetailSV {

	@Resource
	private CfgSubscribeDetailMapper cfgSubscribeDetailMapper;
  


    /**
    * 创建数据订阅明细
    * @param cfgSubscribeDetail
    * @return
    * @throws Exception
    */
    public int create(CfgSubscribeDetail cfgSubscribeDetail) throws Exception {
        return cfgSubscribeDetailMapper.insertSelective(cfgSubscribeDetail);
    }

    /**
    * 批量维护数据订阅明细
    * @param cfgSubscribeDetails
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgSubscribeDetail[] cfgSubscribeDetails) throws  Exception{
        int result = 0;
        if(cfgSubscribeDetails != null) {
            for (CfgSubscribeDetail cfgSubscribeDetail : cfgSubscribeDetails) {
                if(cfgSubscribeDetail.getCfgSubscribeDetailId() == null) {
                    result += this.create(cfgSubscribeDetail);
                }else {
                    result += this.update(cfgSubscribeDetail);
                }
            }
        }
        return result;
    }

    /**
    * 更新数据订阅明细
    * @param cfgSubscribeDetail
    * @return
    * @throws Exception
    */
    public int update(CfgSubscribeDetail cfgSubscribeDetail) throws  Exception {
        return cfgSubscribeDetailMapper.updateByPrimaryKeySelective(cfgSubscribeDetail);
    }

    /**
    * 通过查询对象更新数据订阅明细
    * @param cfgSubscribeDetail
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgSubscribeDetail cfgSubscribeDetail, CfgSubscribeDetail_Example example) throws  Exception {
        return cfgSubscribeDetailMapper.updateByExampleSelective(cfgSubscribeDetail, example);
    }

    /**
    * 删除数据订阅明细
    * @param cfgSubscribeDetail
    * @return
    * @throws Exception
    */
    public int delete(CfgSubscribeDetail cfgSubscribeDetail) throws  Exception {
        return cfgSubscribeDetailMapper.deleteByPrimaryKey(cfgSubscribeDetail.getCfgSubscribeDetailId());
    }

    /**
    * 删除数据订阅明细
    * @param cfgSubscribeDetailId
    * @return
    * @throws Exception
    */
    public int delete(long cfgSubscribeDetailId) throws  Exception {
        return cfgSubscribeDetailMapper.deleteByPrimaryKey(cfgSubscribeDetailId);
    }

    /**
    * 查找所有数据订阅明细
    * @return
    */
    public List<CfgSubscribeDetail> getCfgSubscribeDetailAll()  throws  Exception {
        return cfgSubscribeDetailMapper.selectByExample(new CfgSubscribeDetail_Example());
    }

    /**
    * 通过数据订阅明细ID查询数据订阅明细
    * @param cfgSubscribeDetailId
    * @return
    * @throws Exception
    */
    public CfgSubscribeDetail getCfgSubscribeDetailByPK(long cfgSubscribeDetailId)  throws  Exception {
        return cfgSubscribeDetailMapper.selectByPrimaryKey(cfgSubscribeDetailId);
    }


    /**
    * 通过MAP参数查询数据订阅明细
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgSubscribeDetail> getCfgSubscribeDetailListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询数据订阅明细
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgSubscribeDetail> getCfgSubscribeDetailListByExample(CfgSubscribeDetail_Example example) throws  Exception {
        return cfgSubscribeDetailMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询数据订阅明细数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeDetailCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询数据订阅明细数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgSubscribeDetailCountByExample(CfgSubscribeDetail_Example example) throws  Exception {
        return cfgSubscribeDetailMapper.countByExample(example);
    }


  	//getter
 	
	public CfgSubscribeDetailMapper getCfgSubscribeDetailMapper(){
		return cfgSubscribeDetailMapper;
	}
	//setter
	public void setCfgSubscribeDetailMapper(CfgSubscribeDetailMapper cfgSubscribeDetailMapper){
    	this.cfgSubscribeDetailMapper = cfgSubscribeDetailMapper;
    }
}
