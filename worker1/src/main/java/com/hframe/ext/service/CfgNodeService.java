package com.hframe.ext.service;

import com.hframework.hamster.cfg.domain.model.CfgNode;
import com.hframework.hamster.cfg.domain.model.CfgNode_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeSV;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangquanhong on 2016/11/4.
 */
@Service
public class CfgNodeService {

    @Resource
    private ICfgNodeSV cfgNodeSV;


    public String getNodeIpInfo(String nodeCode) throws Exception {
        CfgNode_Example cfgNodeExample = new CfgNode_Example();
        cfgNodeExample.createCriteria().andCfgNodeCodeEqualTo(nodeCode);
        List<CfgNode> cfgNodeList = cfgNodeSV.getCfgNodeListByExample(cfgNodeExample);
        if(cfgNodeList != null && cfgNodeList.size() > 0) {
            return cfgNodeList.get(0).getIp();
        }
        return null;
    }

}
