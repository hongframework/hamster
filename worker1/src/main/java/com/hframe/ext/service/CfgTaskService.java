package com.hframe.ext.service;

import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.NodeTask;
import com.hframe.hamster.node.monitor.bean.PipeLinePrototypeKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import com.hframework.datacenter.enums.CfgTaskInstStatusEnum;
import com.hframework.hamster.cfg.domain.model.*;
import com.hframework.hamster.cfg.service.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangquanhong on 2016/10/9.
 */
@Service
public class CfgTaskService {

    private static final Logger logger = LoggerFactory.getLogger(CfgTaskService.class);

    @Resource
    private ICfgTaskInstSV cfgTaskInstSV;

    @Resource
    private ICfgNodeSV cfgNodeSV;

    @Resource
    private ICfgTaskDefSV cfgTaskDefSV;

    @Resource
    private ICfgTaskNodeDefSV cfgTaskNodeDefSV;

    @Resource
    private ICfgNodeTaskRelatSV cfgNodeTaskRelatSV;

    public List<NodeTask> getTaskInstancesByNodeId(Long nodeId) throws Exception {
        List<NodeTask> result = new ArrayList<>();

        CfgNode_Example cfgNodeExample = new CfgNode_Example();
        cfgNodeExample.createCriteria().andCfgNodeCodeEqualTo(String.valueOf(nodeId));
        List<CfgNode> cfgNodeListByExample = cfgNodeSV.getCfgNodeListByExample(cfgNodeExample);
        if(cfgNodeListByExample == null || cfgNodeListByExample.size() != 1) {
            logger.error("节点信息不存在，或不唯一,node_code = {} !", nodeId);
            throw new RuntimeException("未找到节点信息");
        }

        CfgNodeTaskRelat_Example example = new CfgNodeTaskRelat_Example();
        example.createCriteria().andCfgNodeIdEqualTo(cfgNodeListByExample.get(0).getCfgNodeId());
        List<CfgNodeTaskRelat> cfgNodeTaskRelatList = cfgNodeTaskRelatSV.getCfgNodeTaskRelatListByExample(example);
        for (CfgNodeTaskRelat cfgNodeTaskRelat : cfgNodeTaskRelatList) {

            //任务实例
            CfgTaskInst cfgTaskInst = cfgTaskInstSV.getCfgTaskInstByPK(cfgNodeTaskRelat.getCfgTaskInstId());
            if(cfgTaskInst == null) {
                logger.error("无此任务实例, node_code = {}, cfg_taks_inst_id = {} !", nodeId, cfgNodeTaskRelat.getCfgTaskInstId());
                continue;
            }
            if(cfgTaskInst.getStatus() != (byte)1) {
                logger.info("任务实例状态不正确, node_code = {}, cfg_taks_inst_id = {} , status = {} !",
                        nodeId, cfgNodeTaskRelat.getCfgTaskInstId(), cfgTaskInst.getStatus());
                continue;
            }

            Long cfgTaskDefId = cfgTaskInst.getCfgTaskDefId();
            //任务定义
            CfgTaskDef cfgTaskDef = cfgTaskDefSV.getCfgTaskDefByPK(cfgTaskDefId);
            if(cfgTaskInst == null) {
                logger.error("任务定义查询失败,node_code = {}, cfg_taks_def_id = {} !", nodeId, cfgTaskDefId);
                continue;
            }

            //任务节点定义
            CfgTaskNodeDef_Example example1 = new CfgTaskNodeDef_Example();
            example1.createCriteria().andCfgTaskDefIdEqualTo(cfgTaskDefId);
            List<CfgTaskNodeDef> cfgTaskNodeDefList = cfgTaskNodeDefSV.getCfgTaskNodeDefListByExample(example1);

            FlowKey flowKey = getFlowKey(cfgTaskDef.getCfgTaskDefCode());
            if((byte)1 == cfgTaskDef.getCfgTaskInstanceType()) {
                cfgTaskInst.setParamValue1("-1");
            }
            PrototypeKey prototypeKey = PipeLinePrototypeKey.value(Long.valueOf(cfgTaskInst.getParamValue1()));
            for (CfgTaskNodeDef cfgTaskNodeDef : cfgTaskNodeDefList) {
                NodeTask taskNode = new NodeTask();
                taskNode.setTaskEvent(NodeTask.TaskEvent.CREATE);
                taskNode.setTaskType(new NodeTask.TaskType(Class.forName(cfgTaskNodeDef.getJavaClass())));
                taskNode.setFlowKey(flowKey);
                taskNode.setPrototypeKey(prototypeKey);
                result.add(taskNode);
            }
        }

        return result;
    }


    public List<String> getNodesByFlowKeyAndPrototypeKey(String flowKey, PrototypeKey prototypeKey) throws Exception {
        List<NodeTask> result = new ArrayList<>();
        String taskDefCode = flowKey;

        CfgTaskDef_Example cfgTaskDef_example = new CfgTaskDef_Example();
        cfgTaskDef_example.createCriteria().andCfgTaskDefCodeEqualTo(taskDefCode);
        List<CfgTaskDef> cfgTaskDefList = cfgTaskDefSV.getCfgTaskDefListByExample(cfgTaskDef_example);
        if(cfgTaskDefList == null || cfgTaskDefList.size() != 1) {
            logger.error("不能找到唯一的任务定义, flowKey = {}, prototypeKey = {} !", flowKey, prototypeKey.value());
        }

        Long cfgTaskDefId = cfgTaskDefList.get(0).getCfgTaskDefId();
        CfgTaskInst_Example cfgTaskInst_example = new CfgTaskInst_Example();
        if((byte)1 == cfgTaskDefList.get(0).getCfgTaskInstanceType()) {
            cfgTaskInst_example.createCriteria().andCfgTaskDefIdEqualTo(cfgTaskDefId);
        }else {
            cfgTaskInst_example.createCriteria().andCfgTaskDefIdEqualTo(cfgTaskDefId).andParamValue1EqualTo(prototypeKey.value());
        }

        List<CfgTaskInst> cfgTaskInstListByExample = cfgTaskInstSV.getCfgTaskInstListByExample(cfgTaskInst_example);
        if(cfgTaskInstListByExample == null || cfgTaskInstListByExample.size() != 1) {
            logger.error("不能找到唯一的任务实例, flowKey = {}, prototypeKey = {} !", flowKey, prototypeKey.value());
        }

        Long cfgTaskInstId = cfgTaskInstListByExample.get(0).getCfgTaskInstId();
        CfgNodeTaskRelat_Example cfgNodeTaskRelatExample = new CfgNodeTaskRelat_Example();
        cfgNodeTaskRelatExample.createCriteria().andCfgTaskInstIdEqualTo(cfgTaskInstId);
        List<CfgNodeTaskRelat> cfgNodeTaskRelatList =
                cfgNodeTaskRelatSV.getCfgNodeTaskRelatListByExample(cfgNodeTaskRelatExample);

        return CollectionUtils.fetch(cfgNodeTaskRelatList, new Fetcher<CfgNodeTaskRelat, String>() {
            @Override
            public String fetch(CfgNodeTaskRelat cfgNodeTaskRelat) {
                try {
                    return cfgNodeSV.getCfgNodeByPK(cfgNodeTaskRelat.getCfgNodeId()).getCfgNodeCode().trim();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    private FlowKey getFlowKey(final String value) {
        FlowKey flowKey = new FlowKey() {
            @Override
            public String value() {
                return value;
            }

            //必须重写equals、hashCode方法，否者会makeComputingMap会生产重复的key
            @Override
            public boolean equals(Object obj) {
                if (obj instanceof FlowKey) {
                    FlowKey target = (FlowKey) obj;
                    return this.value().equals(target.value());
                }
                return false;
            }

            @Override
            public int hashCode() {
                return value().hashCode();
            }
        };
        return flowKey;
    }

    public void updateTaskNodeError(FlowKey flowKey, PrototypeKey prototypeKey, Class taskClass, String errorMessage) throws Exception {
        String taskDefCode = flowKey.value();

        CfgTaskDef_Example cfgTaskDef_example = new CfgTaskDef_Example();
        cfgTaskDef_example.createCriteria().andCfgTaskDefCodeEqualTo(taskDefCode);
        List<CfgTaskDef> cfgTaskDefList = cfgTaskDefSV.getCfgTaskDefListByExample(cfgTaskDef_example);
        if(cfgTaskDefList != null && cfgTaskDefList.size() > 0) {
            Long cfgTaskDefId = cfgTaskDefList.get(0).getCfgTaskDefId();
            CfgTaskInst_Example cfgTaskInst_example = new CfgTaskInst_Example();
            cfgTaskInst_example.createCriteria().andCfgTaskDefIdEqualTo(cfgTaskDefId).andParamValue1EqualTo(prototypeKey.value());

            CfgTaskInst cfgTaskInst = new CfgTaskInst();
            cfgTaskInst.setStatus(CfgTaskInstStatusEnum.EXCEPTION.getValue());

            cfgTaskInstSV.updateByExample(cfgTaskInst, cfgTaskInst_example);
        }
    }
}
