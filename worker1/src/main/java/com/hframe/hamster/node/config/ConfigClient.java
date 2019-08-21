package com.hframe.hamster.node.config;

import com.hframe.ext.service.CfgNodeService;
import com.hframe.ext.service.CfgTaskService;
import com.hframe.hamster.node.HamsterConst;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.NodeTask;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;

import java.util.List;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public class ConfigClient {

    private static Long nid;

    public static String getNodeInfo(Long nid) throws Exception {
        return HamsterContextInitializer.getBean(CfgNodeService.class).getNodeIpInfo(String.valueOf(nid));
    }

    public static Long getCurrentNid() {
        return nid;
    }

    public static String getCurrentNodeInfo() throws Exception {
        return getNodeInfo(getCurrentNid());
    }

    static {
        String nid = System.getProperty(HamsterConst.NID_NAME);
        if(org.apache.commons.lang.StringUtils.isBlank(nid)) {
            throw new RuntimeException("nid is blank !");
        }
        ConfigClient.nid = Long.valueOf(nid);
    }



    /**
     * 获取节点任务列表
     * @param nid
     * @return
     */
    public static List<NodeTask> getNodeTaskList(Long nid) throws Exception {

        return HamsterContextInitializer.getBean(CfgTaskService.class).getTaskInstancesByNodeId(nid);
    }

    /**
     * 获取节点任务列表
     * @return
     */
    public static List<String> getNodesByFlowKeyAndPrototypeKey(String flowKey, PrototypeKey prototypeKey) throws Exception {

        return HamsterContextInitializer.getBean(CfgTaskService.class).getNodesByFlowKeyAndPrototypeKey(flowKey, prototypeKey);
    }

    public static void updateTaskNodeError(FlowKey flowKey, PrototypeKey prototypeKey, Class taskClass, String errorMessage){
        try {
            HamsterContextInitializer.getBean(CfgTaskService.class).updateTaskNodeError( flowKey, prototypeKey, taskClass,errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
