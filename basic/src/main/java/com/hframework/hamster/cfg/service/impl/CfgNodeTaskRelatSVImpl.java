package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgNodeTaskRelatSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.hamster.cfg.domain.model.CfgNodeTaskRelat;
import com.hframework.hamster.cfg.domain.model.CfgNodeTaskRelat_Example;
import com.hframework.hamster.cfg.dao.CfgNodeTaskRelatMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeTaskRelatSV;

@Service("iCfgNodeTaskRelatSV")
public class CfgNodeTaskRelatSVImpl  implements ICfgNodeTaskRelatSV {

	@Resource
	private CfgNodeTaskRelatMapper cfgNodeTaskRelatMapper;
  


    /**
    * 创建任务节点部署
    * @param cfgNodeTaskRelat
    * @return
    * @throws Exception
    */
    public int create(CfgNodeTaskRelat cfgNodeTaskRelat) throws Exception {
        return cfgNodeTaskRelatMapper.insertSelective(cfgNodeTaskRelat);
    }

    /**
    * 批量维护任务节点部署
    * @param cfgNodeTaskRelats
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgNodeTaskRelat[] cfgNodeTaskRelats) throws  Exception{
        int result = 0;
        if(cfgNodeTaskRelats != null) {
            for (CfgNodeTaskRelat cfgNodeTaskRelat : cfgNodeTaskRelats) {
                if(cfgNodeTaskRelat.getCfgNodeTaskRelatId() == null) {
                    result += this.create(cfgNodeTaskRelat);
                }else {
                    result += this.update(cfgNodeTaskRelat);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务节点部署
    * @param cfgNodeTaskRelat
    * @return
    * @throws Exception
    */
    public int update(CfgNodeTaskRelat cfgNodeTaskRelat) throws  Exception {
        return cfgNodeTaskRelatMapper.updateByPrimaryKeySelective(cfgNodeTaskRelat);
    }

    /**
    * 通过查询对象更新任务节点部署
    * @param cfgNodeTaskRelat
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgNodeTaskRelat cfgNodeTaskRelat, CfgNodeTaskRelat_Example example) throws  Exception {
        return cfgNodeTaskRelatMapper.updateByExampleSelective(cfgNodeTaskRelat, example);
    }

    /**
    * 删除任务节点部署
    * @param cfgNodeTaskRelat
    * @return
    * @throws Exception
    */
    public int delete(CfgNodeTaskRelat cfgNodeTaskRelat) throws  Exception {
        return cfgNodeTaskRelatMapper.deleteByPrimaryKey(cfgNodeTaskRelat.getCfgNodeTaskRelatId());
    }

    /**
    * 删除任务节点部署
    * @param cfgNodeTaskRelatId
    * @return
    * @throws Exception
    */
    public int delete(long cfgNodeTaskRelatId) throws  Exception {
        return cfgNodeTaskRelatMapper.deleteByPrimaryKey(cfgNodeTaskRelatId);
    }

    /**
    * 查找所有任务节点部署
    * @return
    */
    public List<CfgNodeTaskRelat> getCfgNodeTaskRelatAll()  throws  Exception {
        return cfgNodeTaskRelatMapper.selectByExample(new CfgNodeTaskRelat_Example());
    }

    /**
    * 通过任务节点部署ID查询任务节点部署
    * @param cfgNodeTaskRelatId
    * @return
    * @throws Exception
    */
    public CfgNodeTaskRelat getCfgNodeTaskRelatByPK(long cfgNodeTaskRelatId)  throws  Exception {
        return cfgNodeTaskRelatMapper.selectByPrimaryKey(cfgNodeTaskRelatId);
    }


    /**
    * 通过MAP参数查询任务节点部署
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgNodeTaskRelat> getCfgNodeTaskRelatListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务节点部署
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgNodeTaskRelat> getCfgNodeTaskRelatListByExample(CfgNodeTaskRelat_Example example) throws  Exception {
        return cfgNodeTaskRelatMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务节点部署数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgNodeTaskRelatCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务节点部署数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgNodeTaskRelatCountByExample(CfgNodeTaskRelat_Example example) throws  Exception {
        return cfgNodeTaskRelatMapper.countByExample(example);
    }


  	//getter
 	
	public CfgNodeTaskRelatMapper getCfgNodeTaskRelatMapper(){
		return cfgNodeTaskRelatMapper;
	}
	//setter
	public void setCfgNodeTaskRelatMapper(CfgNodeTaskRelatMapper cfgNodeTaskRelatMapper){
    	this.cfgNodeTaskRelatMapper = cfgNodeTaskRelatMapper;
    }
}
