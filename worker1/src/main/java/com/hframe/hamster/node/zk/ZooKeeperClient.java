package com.hframe.hamster.node.zk;


import com.hframe.hamster.common.zkclient.ZkClientx;
import com.hframe.hamster.node.HamsterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public class ZooKeeperClient {

    private static Logger logger = LoggerFactory.getLogger(ZooKeeperClient.class);

    private static ZkClientx zkClientx;

    public static ZkClientx getInstance(){
        if(zkClientx == null) {
            synchronized (ZooKeeperClient.class) {
                if(zkClientx == null) {
                    logger.info("zk client init , servers = [{}], connectionTimeout = [{}]",
                            HamsterConfig.getInstance().getZkServers(),
                            Integer.valueOf(HamsterConfig.getInstance().getZkSessionTimeout()));
                    zkClientx = new ZkClientx(HamsterConfig.getInstance().getZkServers(),
                            Integer.valueOf(HamsterConfig.getInstance().getZkSessionTimeout()));
                }
            }
        }
        return zkClientx;
    }
}