package com.hframework.hamster.cfg.service.impl;

import java.util.*;

import com.hframework.hamster.cfg.service.interfaces.ICfgNodeSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.hamster.cfg.domain.model.CfgNode;
import com.hframework.hamster.cfg.domain.model.CfgNode_Example;
import com.hframework.hamster.cfg.dao.CfgNodeMapper;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeSV;

@Service("iCfgNodeSV")
public class CfgNodeSVImpl  implements ICfgNodeSV {

	@Resource
	private CfgNodeMapper cfgNodeMapper;
  


    /**
    * 创建节点
    * @param cfgNode
    * @return
    * @throws Exception
    */
    public int create(CfgNode cfgNode) throws Exception {
        return cfgNodeMapper.insertSelective(cfgNode);
    }

    /**
    * 批量维护节点
    * @param cfgNodes
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgNode[] cfgNodes) throws  Exception{
        int result = 0;
        if(cfgNodes != null) {
            for (CfgNode cfgNode : cfgNodes) {
                if(cfgNode.getCfgNodeId() == null) {
                    result += this.create(cfgNode);
                }else {
                    result += this.update(cfgNode);
                }
            }
        }
        return result;
    }

    /**
    * 更新节点
    * @param cfgNode
    * @return
    * @throws Exception
    */
    public int update(CfgNode cfgNode) throws  Exception {
        return cfgNodeMapper.updateByPrimaryKeySelective(cfgNode);
    }

    /**
    * 通过查询对象更新节点
    * @param cfgNode
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgNode cfgNode, CfgNode_Example example) throws  Exception {
        return cfgNodeMapper.updateByExampleSelective(cfgNode, example);
    }

    /**
    * 删除节点
    * @param cfgNode
    * @return
    * @throws Exception
    */
    public int delete(CfgNode cfgNode) throws  Exception {
        return cfgNodeMapper.deleteByPrimaryKey(cfgNode.getCfgNodeId());
    }

    /**
    * 删除节点
    * @param cfgNodeId
    * @return
    * @throws Exception
    */
    public int delete(long cfgNodeId) throws  Exception {
        return cfgNodeMapper.deleteByPrimaryKey(cfgNodeId);
    }

    /**
    * 查找所有节点
    * @return
    */
    public List<CfgNode> getCfgNodeAll()  throws  Exception {
        return cfgNodeMapper.selectByExample(new CfgNode_Example());
    }

    /**
    * 通过节点ID查询节点
    * @param cfgNodeId
    * @return
    * @throws Exception
    */
    public CfgNode getCfgNodeByPK(long cfgNodeId)  throws  Exception {
        return cfgNodeMapper.selectByPrimaryKey(cfgNodeId);
    }


    /**
    * 通过MAP参数查询节点
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgNode> getCfgNodeListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询节点
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgNode> getCfgNodeListByExample(CfgNode_Example example) throws  Exception {
        return cfgNodeMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询节点数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgNodeCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询节点数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgNodeCountByExample(CfgNode_Example example) throws  Exception {
        return cfgNodeMapper.countByExample(example);
    }


  	//getter
 	
	public CfgNodeMapper getCfgNodeMapper(){
		return cfgNodeMapper;
	}
	//setter
	public void setCfgNodeMapper(CfgNodeMapper cfgNodeMapper){
    	this.cfgNodeMapper = cfgNodeMapper;
    }
}
