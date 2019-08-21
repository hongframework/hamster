package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgDatatableSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgDatatable;
import com.hframework.hamster.cfg.domain.model.CfgDatatable_Example;
import com.hframework.hamster.cfg.dao.CfgDatatableMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgDatatableSV;

@Service("iCfgDatatableSV")
public class CfgDatatableSVImpl  implements ICfgDatatableSV {

	@Resource
	private CfgDatatableMapper cfgDatatableMapper;
  


    /**
    * 创建数据表
    * @param cfgDatatable
    * @return
    * @throws Exception
    */
    public int create(CfgDatatable cfgDatatable) throws Exception {
        return cfgDatatableMapper.insertSelective(cfgDatatable);
    }

    /**
    * 批量维护数据表
    * @param cfgDatatables
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDatatable[] cfgDatatables) throws  Exception{
        int result = 0;
        if(cfgDatatables != null) {
            for (CfgDatatable cfgDatatable : cfgDatatables) {
                if(cfgDatatable.getId() == null) {
                    result += this.create(cfgDatatable);
                }else {
                    result += this.update(cfgDatatable);
                }
            }
        }
        return result;
    }

    /**
    * 更新数据表
    * @param cfgDatatable
    * @return
    * @throws Exception
    */
    public int update(CfgDatatable cfgDatatable) throws  Exception {
        return cfgDatatableMapper.updateByPrimaryKeySelective(cfgDatatable);
    }

    /**
    * 通过查询对象更新数据表
    * @param cfgDatatable
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDatatable cfgDatatable, CfgDatatable_Example example) throws  Exception {
        return cfgDatatableMapper.updateByExampleSelective(cfgDatatable, example);
    }

    /**
    * 删除数据表
    * @param cfgDatatable
    * @return
    * @throws Exception
    */
    public int delete(CfgDatatable cfgDatatable) throws  Exception {
        return cfgDatatableMapper.deleteByPrimaryKey(cfgDatatable.getId());
    }

    /**
    * 删除数据表
    * @param cfgDatatableId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDatatableId) throws  Exception {
        return cfgDatatableMapper.deleteByPrimaryKey(cfgDatatableId);
    }

    /**
    * 查找所有数据表
    * @return
    */
    public List<CfgDatatable> getCfgDatatableAll()  throws  Exception {
        return cfgDatatableMapper.selectByExample(new CfgDatatable_Example());
    }

    /**
    * 通过数据表ID查询数据表
    * @param cfgDatatableId
    * @return
    * @throws Exception
    */
    public CfgDatatable getCfgDatatableByPK(long cfgDatatableId)  throws  Exception {
        return cfgDatatableMapper.selectByPrimaryKey(cfgDatatableId);
    }


    /**
    * 通过MAP参数查询数据表
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDatatable> getCfgDatatableListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询数据表
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDatatable> getCfgDatatableListByExample(CfgDatatable_Example example) throws  Exception {
        return cfgDatatableMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询数据表数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDatatableCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询数据表数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDatatableCountByExample(CfgDatatable_Example example) throws  Exception {
        return cfgDatatableMapper.countByExample(example);
    }


  	//getter
 	
	public CfgDatatableMapper getCfgDatatableMapper(){
		return cfgDatatableMapper;
	}
	//setter
	public void setCfgDatatableMapper(CfgDatatableMapper cfgDatatableMapper){
    	this.cfgDatatableMapper = cfgDatatableMapper;
    }
}
