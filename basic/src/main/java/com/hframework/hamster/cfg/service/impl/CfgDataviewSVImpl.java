package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgDataviewSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgDataview;
import com.hframework.hamster.cfg.domain.model.CfgDataview_Example;
import com.hframework.hamster.cfg.dao.CfgDataviewMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgDataviewSV;

@Service("iCfgDataviewSV")
public class CfgDataviewSVImpl  implements ICfgDataviewSV {

	@Resource
	private CfgDataviewMapper cfgDataviewMapper;
  


    /**
    * 创建数据视图
    * @param cfgDataview
    * @return
    * @throws Exception
    */
    public int create(CfgDataview cfgDataview) throws Exception {
        return cfgDataviewMapper.insertSelective(cfgDataview);
    }

    /**
    * 批量维护数据视图
    * @param cfgDataviews
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDataview[] cfgDataviews) throws  Exception{
        int result = 0;
        if(cfgDataviews != null) {
            for (CfgDataview cfgDataview : cfgDataviews) {
                if(cfgDataview.getId() == null) {
                    result += this.create(cfgDataview);
                }else {
                    result += this.update(cfgDataview);
                }
            }
        }
        return result;
    }

    /**
    * 更新数据视图
    * @param cfgDataview
    * @return
    * @throws Exception
    */
    public int update(CfgDataview cfgDataview) throws  Exception {
        return cfgDataviewMapper.updateByPrimaryKeySelective(cfgDataview);
    }

    /**
    * 通过查询对象更新数据视图
    * @param cfgDataview
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDataview cfgDataview, CfgDataview_Example example) throws  Exception {
        return cfgDataviewMapper.updateByExampleSelective(cfgDataview, example);
    }

    /**
    * 删除数据视图
    * @param cfgDataview
    * @return
    * @throws Exception
    */
    public int delete(CfgDataview cfgDataview) throws  Exception {
        return cfgDataviewMapper.deleteByPrimaryKey(cfgDataview.getId());
    }

    /**
    * 删除数据视图
    * @param cfgDataviewId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDataviewId) throws  Exception {
        return cfgDataviewMapper.deleteByPrimaryKey(cfgDataviewId);
    }

    /**
    * 查找所有数据视图
    * @return
    */
    public List<CfgDataview> getCfgDataviewAll()  throws  Exception {
        return cfgDataviewMapper.selectByExample(new CfgDataview_Example());
    }

    /**
    * 通过数据视图ID查询数据视图
    * @param cfgDataviewId
    * @return
    * @throws Exception
    */
    public CfgDataview getCfgDataviewByPK(long cfgDataviewId)  throws  Exception {
        return cfgDataviewMapper.selectByPrimaryKey(cfgDataviewId);
    }


    /**
    * 通过MAP参数查询数据视图
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDataview> getCfgDataviewListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询数据视图
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDataview> getCfgDataviewListByExample(CfgDataview_Example example) throws  Exception {
        return cfgDataviewMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询数据视图数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDataviewCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询数据视图数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDataviewCountByExample(CfgDataview_Example example) throws  Exception {
        return cfgDataviewMapper.countByExample(example);
    }


  	//getter
 	
	public CfgDataviewMapper getCfgDataviewMapper(){
		return cfgDataviewMapper;
	}
	//setter
	public void setCfgDataviewMapper(CfgDataviewMapper cfgDataviewMapper){
    	this.cfgDataviewMapper = cfgDataviewMapper;
    }
}
