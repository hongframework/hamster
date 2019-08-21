package com.hframe.hamster.node.arbitrate;

import com.hframe.hamster.common.AddressUtils;
import com.hframe.hamster.common.zkclient.ZkClientx;
import com.hframe.hamster.node.zk.ZKPathUtils;
import com.hframe.hamster.node.zk.ZooKeeperClient;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
@Service
public class NodeArbitrateEvent {
    private static final Logger logger = LoggerFactory.getLogger(NodeArbitrateEvent.class);
    private ZkClientx zkClientx = ZooKeeperClient.getInstance();

    public void create(final Long nid,String ip) {
        logger.info("create zk node:path={},ip={},mode={}", ZKPathUtils.getNodePath(nid), ip, CreateMode.EPHEMERAL);
        try{
            zkClientx.create(ZKPathUtils.getNodePath(nid), ip, CreateMode.EPHEMERAL);
            //zk注册session重连创建node节点（session断掉后会自动删除临时节点）
            zkClientx.subscribeStateChanges(new IZkStateListener() {
                @Override
                public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {

                }
                @Override
                public void handleNewSession() throws Exception {
                    create(nid, AddressUtils.getHostIp());
                }
            });
        }catch (ZkNodeExistsException e) {
            //出现这种情况 主要为服务重启时间间隔小于30s，zk还没有释放临时节点，就要新增加节点
            logger.info("create node error, maybe create ephemeral node less 30 second {}",ExceptionUtils.getFullStackTrace(e));
        }catch (ZkNoNodeException e) {//上级路径不存在，连带创建
            logger.error("create node error, {}", ExceptionUtils.getFullStackTrace(e));
            zkClientx.create(ZKPathUtils.getNodeRootPath(), null, CreateMode.PERSISTENT);
            create(nid, ip);
        }

    }

    public void destroy(Long nid) {
        try{
            zkClientx.delete(ZKPathUtils.getNodePath(nid));
            logger.info("node delete from zk success !");
        }catch (ZkNodeExistsException e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
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
