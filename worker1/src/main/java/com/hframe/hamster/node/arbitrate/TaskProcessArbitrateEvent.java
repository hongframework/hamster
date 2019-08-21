package com.hframe.hamster.node.arbitrate;

import com.alibaba.otter.canal.common.utils.JsonUtils;
import com.hframe.hamster.common.zkclient.ZkClientx;
import com.hframe.hamster.node.config.ConfigClient;
import com.hframe.hamster.node.monitor.bean.ProcessNodeEventData;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.SharableEventData;
import com.hframe.hamster.node.zk.ZKPathUtils;
import com.hframe.hamster.node.zk.ZooKeeperClient;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
@Service
public class TaskProcessArbitrateEvent {
    private static final Logger logger = LoggerFactory.getLogger(TaskProcessArbitrateEvent.class);
    private ZkClientx zkClientx = ZooKeeperClient.getInstance();

    public void createProcess(String taskStory, PrototypeKey prototypeKey) {
        try{
            ProcessNodeEventData nodeData = new ProcessNodeEventData();
            nodeData.setStatus(ProcessNodeEventData.Status.UNUSED);// 标记为未使用
            nodeData.setNid(ConfigClient.getCurrentNid());
            byte[] nodeBytes = JsonUtils.marshalToByte(nodeData);

            String processPath = zkClientx.create(ZKPathUtils.getProcessRootPath(taskStory, prototypeKey.value()) + "/",
                    nodeBytes, CreateMode.PERSISTENT_SEQUENTIAL);
            // 创建为顺序的节点
            String processNode = StringUtils.substringAfterLast(processPath, "/");
            logger.info("create zk process : root path = {}, mode={} success, process id = {} !",
                    ZKPathUtils.getProcessRootPath(taskStory, prototypeKey.value()), CreateMode.PERSISTENT, processNode);
        }catch (ZkNodeExistsException e) {
            logger.error("create zk process error: root path = {}, mode={} success",
                    ZKPathUtils.getProcessRootPath(taskStory, prototypeKey.value()), CreateMode.PERSISTENT);
        }
    }

    public void createProcessStage(String taskStory, PrototypeKey prototypeKey, Long processId, Class taskNodeClass, SharableEventData data) {
        logger.info("create zk process task: root path = {}, mode={}, data={}",
                ZKPathUtils.getProcessStagePath(taskStory, prototypeKey.value(), processId, taskNodeClass.getSimpleName()), CreateMode.PERSISTENT, data);
        try{
            byte[] nodeBytes = JsonUtils.marshalToByte(data);
            zkClientx.create(
                    ZKPathUtils.getProcessStagePath(taskStory, prototypeKey.value(), processId, taskNodeClass.getSimpleName()),
                    nodeBytes, CreateMode.PERSISTENT);
            logger.debug("create node successful !");
        }catch (ZkNodeExistsException e) {
            logger.error("create node error, {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    public void createProcessTermin(String taskStory, PrototypeKey prototypeKey, Long processId) {
        logger.info("create zk termin : root path = {}, mode={}, data={}",
                ZKPathUtils.getTerminPath(taskStory, prototypeKey.value(), processId), CreateMode.PERSISTENT, null);
        try{
            zkClientx.create(
                    ZKPathUtils.getTerminPath(taskStory, prototypeKey.value(), processId),
                    null, CreateMode.PERSISTENT);
            logger.debug("create node successful !");
        }catch (ZkNodeExistsException e) {
            logger.error("create node error, {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    public void markUsed(String taskStory, PrototypeKey prototypeKey, Long processId) {
        logger.info("mark used zk process : path = {}", ZKPathUtils.getProcessPath(taskStory, prototypeKey.value(), processId));
        try{

            ProcessNodeEventData nodeData = new ProcessNodeEventData();
            nodeData.setStatus(ProcessNodeEventData.Status.USED);// 标记为已使用
            nodeData.setNid(ConfigClient.getCurrentNid());
            byte[] nodeBytes = JsonUtils.marshalToByte(nodeData);
            zkClientx.writeData(ZKPathUtils.getProcessPath(taskStory, prototypeKey.value(), processId), nodeBytes);
            // 创建为顺序的节点
        }catch (ZkNodeExistsException e) {
            logger.error("mark used  node error, {}",ExceptionUtils.getFullStackTrace(e));
        }
    }

    public void delete(String taskStory, PrototypeKey prototypeKey, Long processId) {
        logger.info("delete zk process node cascade : path = {}", ZKPathUtils.getProcessPath(taskStory,prototypeKey.value(),processId));
        try{
            zkClientx.deleteRecursive(ZKPathUtils.getProcessPath(taskStory, prototypeKey.value(), processId));
            // 创建为顺序的节点
        }catch (ZkException e) {
            logger.error("delete node error, {}, delete cascade !",ExceptionUtils.getFullStackTrace(e));
        }
    }

    public List<Long> getAliveNIds() {
        List<String> nids = zkClientx.getChildren(ZKPathUtils.getNodeRootPath());
        return CollectionUtils.fetch(nids, new Fetcher<String, Long>() {
            @Override
            public Long fetch(String s) {
                return Long.valueOf(s);
            }
        });
    }
}
