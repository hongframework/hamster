package com.hframe.hamster.node;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.hframe.hamster.common.AddressUtils;
import com.hframe.hamster.common.zkclient.ZkClientx;
import com.hframe.hamster.node.arbitrate.ArbitratorFactory;
import com.hframe.hamster.node.arbitrate.NodeArbitrateEvent;
import com.hframe.hamster.node.config.ConfigClient;
import com.hframe.hamster.node.monitor.NodeTaskMonitor;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.NodeTask;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.listener.NodeTaskListener;
import com.hframe.hamster.node.task.common.Task;
import com.hframe.hamster.node.zk.ZKPathUtils;
import com.hframe.hamster.node.zk.ZooKeeperClient;
//import com.hframework.datacenter.hamster.monitor.JobListener;
//import com.hframework.datacenter.hamster.monitor.JobRegistry;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * 控制器
 * Created by zhangquanhong on 2016/9/27.
 */
@Service
public class HamsterController implements NodeTaskListener {
    private static final Logger logger = LoggerFactory.getLogger(HamsterController.class);
    private ZkClientx zkClientx = ZooKeeperClient.getInstance();

    // taskStory - taskInstance - taskNode - taskObject
    private static Map<FlowKey, Map<PrototypeKey, Map<NodeTask.TaskType, Task>>> allTasks =
            new MapMaker().makeComputingMap(new Function<FlowKey, Map<PrototypeKey, Map<NodeTask.TaskType, Task>>>() {
        @Override
        public Map<PrototypeKey, Map<NodeTask.TaskType, Task>> apply(final FlowKey flowKey) {
            return new MapMaker().makeComputingMap(new Function<PrototypeKey, Map<NodeTask.TaskType, Task>>() {
                @Override
                public Map<NodeTask.TaskType, Task> apply(final PrototypeKey prototypeKey) {
                    return new MapMaker().makeComputingMap(new Function<NodeTask.TaskType, Task>() {
                        @Override
                        public Task apply(NodeTask.TaskType taskType) {
                            Class clazz = taskType.getClazz();
                            try {
                                Constructor constructor = clazz.getConstructor(FlowKey.class, PrototypeKey.class);
                                Task iTask = (Task) constructor.newInstance(flowKey, prototypeKey);
                                HamsterContextInitializer.autowire(iTask);
                                return iTask;
                            } catch (Exception e) {
                                logger.error("init task error => {}", ExceptionUtils.getFullStackTrace(e));
                            }
                            return null;
                        }
                    });
                }
            });
        }
    });


    public void start() throws Exception {
        initZk();
        initNid();
        NodeTaskMonitor nodeTaskMonitor = HamsterContextInitializer.getBean(NodeTaskMonitor.class);
        nodeTaskMonitor.addListener(this);
        nodeTaskMonitor.start();
        while (true) {
            Thread.sleep(1000*10L);
        }
    }

    public void initZk() {
        if(!zkClientx.exists(ZKPathUtils.getRootPath())) {
            try {
                zkClientx.create(ZKPathUtils.getRootPath(), null, CreateMode.PERSISTENT);
            }catch (Exception e ){}
        }
        if(!zkClientx.exists(ZKPathUtils.getNodeRootPath())){
            try{
                zkClientx.create(ZKPathUtils.getNodeRootPath(), null, CreateMode.PERSISTENT);
            }catch (Exception e ){}
        }
        if(!zkClientx.exists(ZKPathUtils.getStoryRootPath())){
            try{
                zkClientx.create(ZKPathUtils.getStoryRootPath(), null, CreateMode.PERSISTENT);
            }catch (Exception e ){}
        }
    }

    public void stop() {
        NodeTaskMonitor nodeTaskMonitor = HamsterContextInitializer.getBean(NodeTaskMonitor.class);
        nodeTaskMonitor.destroy();
    }

    private void initNid() throws Exception {
        String nid = System.getProperty(HamsterConst.NID_NAME);
        if(StringUtils.isBlank(nid)) {
            throw new RuntimeException("nid is blank !");
        }
        logger.info("nid = {}", nid);
        checkNidValid(Long.valueOf(nid));
        registerNidToZk(nid);
    }

    private void registerNidToZk(final String nid) {
        HamsterContextInitializer.getBean(NodeArbitrateEvent.class).create(Long.valueOf(nid), AddressUtils.getHostIp());

    }

    private void checkNidValid(Long nid) throws Exception {
        String hostIp = AddressUtils.getHostIp();
        String nodeIp = ConfigClient.getNodeInfo(nid);
        if(StringUtils.isBlank(nodeIp) || !AddressUtils.isHostIp(nodeIp)) {
            logger.error("nid ip is not right, config ip is {}, host ip is {}",nodeIp, hostIp);
            throw new RuntimeException("nid ip is not right, config ip is [" + nodeIp + "], host ip is [" + hostIp + "]");
        }
    }

    @Override
    public boolean processChanges(List<NodeTask> incTasks) {
        if(incTasks == null || incTasks.isEmpty()) return true;
        logger.info("node task listener process change => {}", JSON.toJSONString(incTasks));

        Map<FlowKey, Set<PrototypeKey>> deleteMap = new HashMap<>();
        for (NodeTask incTask : incTasks) {
            FlowKey taskStory = incTask.getFlowKey();
            PrototypeKey taskInstance = incTask.getPrototypeKey();
            NodeTask.TaskType taskType = incTask.getTaskType();
            if(incTask.isCreate()) {
                Task task = allTasks.get(taskStory).get(taskInstance).get(taskType);
                task.start();
            }else if(incTask.isDelete()) {
                if(!deleteMap.containsKey(taskStory)) deleteMap.put(taskStory,new HashSet<PrototypeKey>());
                deleteMap.get(taskStory).add(taskInstance);

                Task task = allTasks.get(taskStory).get(taskInstance).remove(taskType);
                try{
                    task.shutdown();
                }catch (Exception e) {
                    logger.error("task thread shutdown error : {}", ExceptionUtils.getFullStackTrace(e));
                }
            }
        }

        for (FlowKey flowKey : deleteMap.keySet()) {
            for (PrototypeKey prototypeKey : deleteMap.get(flowKey)) {
                ArbitratorFactory.destroy(flowKey.value(), prototypeKey);
            }
        }

        return true;
    }


}
