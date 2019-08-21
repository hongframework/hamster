package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgDatasourceSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import com.hframework.hamster.cfg.domain.model.CfgDatasource_Example;
import com.hframework.hamster.cfg.dao.CfgDatasourceMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgDatasourceSV;

@Service("iCfgDatasourceSV")
public class CfgDatasourceSVImpl  implements ICfgDatasourceSV {

	@Resource
	private CfgDatasourceMapper cfgDatasourceMapper;
  


    /**
    * 创建数据源
    * @param cfgDatasource
    * @return
    * @throws Exception
    */
    public int create(CfgDatasource cfgDatasource) throws Exception {
        return cfgDatasourceMapper.insertSelective(cfgDatasource);
    }

    /**
    * 批量维护数据源
    * @param cfgDatasources
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDatasource[] cfgDatasources) throws  Exception{
        int result = 0;
        if(cfgDatasources != null) {
            for (CfgDatasource cfgDatasource : cfgDatasources) {
                if(cfgDatasource.getCfgDatasourceId() == null) {
                    result += this.create(cfgDatasource);
                }else {
                    result += this.update(cfgDatasource);
                }
            }
        }
        return result;
    }

    /**
    * 更新数据源
    * @param cfgDatasource
    * @return
    * @throws Exception
    */
    public int update(CfgDatasource cfgDatasource) throws  Exception {
        return cfgDatasourceMapper.updateByPrimaryKeySelective(cfgDatasource);
    }

    /**
    * 通过查询对象更新数据源
    * @param cfgDatasource
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDatasource cfgDatasource, CfgDatasource_Example example) throws  Exception {
        return cfgDatasourceMapper.updateByExampleSelective(cfgDatasource, example);
    }

    /**
    * 删除数据源
    * @param cfgDatasource
    * @return
    * @throws Exception
    */
    public int delete(CfgDatasource cfgDatasource) throws  Exception {
        return cfgDatasourceMapper.deleteByPrimaryKey(cfgDatasource.getCfgDatasourceId());
    }

    /**
    * 删除数据源
    * @param cfgDatasourceId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDatasourceId) throws  Exception {
        return cfgDatasourceMapper.deleteByPrimaryKey(cfgDatasourceId);
    }

    /**
    * 查找所有数据源
    * @return
    */
    public List<CfgDatasource> getCfgDatasourceAll()  throws  Exception {
        return cfgDatasourceMapper.selectByExample(new CfgDatasource_Example());
    }

    /**
    * 通过数据源ID查询数据源
    * @param cfgDatasourceId
    * @return
    * @throws Exception
    */
    public CfgDatasource getCfgDatasourceByPK(long cfgDatasourceId)  throws  Exception {
        return cfgDatasourceMapper.selectByPrimaryKey(cfgDatasourceId);
    }


    /**
    * 通过MAP参数查询数据源
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDatasource> getCfgDatasourceListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询数据源
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDatasource> getCfgDatasourceListByExample(CfgDatasource_Example example) throws  Exception {
        return cfgDatasourceMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询数据源数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDatasourceCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询数据源数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDatasourceCountByExample(CfgDatasource_Example example) throws  Exception {
        return cfgDatasourceMapper.countByExample(example);
    }


  	//getter
 	
	public CfgDatasourceMapper getCfgDatasourceMapper(){
		return cfgDatasourceMapper;
	}
	//setter
	public void setCfgDatasourceMapper(CfgDatasourceMapper cfgDatasourceMapper){
    	this.cfgDatasourceMapper = cfgDatasourceMapper;
    }
}
