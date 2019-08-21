package com.hframe.hamster.node.monitor;

import com.alibaba.fastjson.JSON;
import com.hframe.hamster.node.config.ConfigClient;
import com.hframe.hamster.node.monitor.bean.MainStemData;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.ZkContextAware;
import com.hframe.hamster.node.monitor.listener.MainStemListener;
import com.hframe.hamster.node.zk.ZKPathUtils;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 主备切换控制器，active的只有一位，所有的standy在进行竞争时，需要查自己“主”任务个数，最少活跃最先分配
 * Created by zhangquanhong on 2016/9/26.
 */
public class MainStemMonitor extends PrototypeMonitor implements Monitor, ZkContextAware<MainStemData> {

    private static final Logger logger = LoggerFactory.getLogger(MainStemMonitor.class);

    //当zk主机节点删除后，通过该调度线程争当主机
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    //竞争主机的时间间隔（单位s)
    private static final long DEFAULT_DELAY_TIME = 5;
    private MainStemData mainStemData;

    private List<MainStemListener> listeners = Collections.synchronizedList(new ArrayList<MainStemListener>());

    private IZkDataListener dataChangeListener = new IZkDataListener() {
        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {
            MainStemData mainStemData = JSON.parseObject((byte[]) data, MainStemData.class);
            if(isMine(mainStemData.getNodeId())) {
                if(!mainStemData.isActive()) {//表明手工设置主节点失效，因此删除主节点，然后可以重新主机竞争环境
                    releaseMainStem();
                }else {
                    mutex.set(true);
                }
            }
            setMainStemData(mainStemData);
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
            mutex.set(false);
            //delete前就是本机在运行
            if(mainStemData.isActive() && isMine(mainStemData.getNodeId())) {
                initMainStem();
            }else {
                scheduler.schedule(new Runnable() {
                    @Override
                    public void run() {
                        initMainStem();
                    }
                }, DEFAULT_DELAY_TIME, TimeUnit.SECONDS);
            }
        }
    };

    public MainStemMonitor(String taskStory, PrototypeKey prototypeKey) {
        super(taskStory, prototypeKey);
    }


    @Override
    public void startInternal() {
        zkClientx.subscribeDataChanges(getZkPath(), dataChangeListener);
        initMainStem();
        logger.info("start finish !");
    }


    @Override
    public void destroyInternal() {
        logger.info("main stem monitor destroy ..");
        zkClientx.unsubscribeDataChanges(getZkPath(), dataChangeListener);
        releaseMainStem();
        scheduler.shutdownNow();

    }

    public void waitForPermit() throws InterruptedException {
        logger.info("wait for main stem permit ..");
        mutex.get();
    }

    public boolean isPermit() {
        return mutex.state();
    }

    /**
     * 向zookeeper申请为“主”状态，如果已经存在直接更新即可
     */
    private void initMainStem() {
        if(!running) return ;
        try{

            //最小活跃数直接竞争“主”机，其他sleep双倍时间再竞争
            if(getLeastLiveTaskNodeId() != ConfigClient.getCurrentNid()) {
                Thread.sleep(2 * DEFAULT_DELAY_TIME);
            }

            MainStemData mainStemData = new MainStemData();
            mainStemData.setActive(true);
            mainStemData.setNodeId(ConfigClient.getCurrentNid());
            mainStemData.setPrototypeKey(prototypeKey);
            mainStemData.setMainStemStatus(MainStemData.MainStemStatus.TAKEING);
            mutex.set(false);
            //最近任务下的maistem节点
            zkClientx.create(getZkPath(), JSON.toJSONString(mainStemData), CreateMode.EPHEMERAL);
            //最近Node下的任务节点
            zkClientx.create(ZKPathUtils.getNodePath(ConfigClient.getCurrentNid() + "_" +
                    taskStory + "_" + prototypeKey.value()), null, CreateMode.EPHEMERAL);
            this.mainStemData = mainStemData;
            fireProcessActiveEnter();
            mutex.set(true);


        }catch (ZkNodeExistsException e) {
            MainStemData mainStem = getZkData();
            if(mainStem != null) {
                mainStemData = mainStem;
                if(mainStemData.isActive() && isMine(mainStemData.getNodeId())) {
                    mutex.set(true);
                }
            }else {
                initMainStem();
            }
        } catch (Exception e) {
            logger.error("init main stem error => {}", ExceptionUtils.getFullStackTrace(e));
        }
    }



    private void releaseMainStem() {
        if(check()) {
            mutex.set(false);
            zkClientx.delete(getZkPath());
            //最近Node下的任务节点
            zkClientx.delete(ZKPathUtils.getNodePath(ConfigClient.getCurrentNid() + "_" +
                    taskStory + "_" + prototypeKey.value()));
            fireProcessActiveExit();
        }
    }

    private boolean check() {
        mainStemData = getZkData();
        if(isMine(mainStemData.getNodeId())) {
            return true;
        }
        return false;
    }

    private void fireProcessActiveEnter() {
        for (final MainStemListener listener : listeners) {
            listener.processActiveEnter();
        }
    }

    private void fireProcessActiveExit() {
        for (final MainStemListener listener : listeners) {
            listener.processActiveExit();
        }
    }

    public void addListener(MainStemListener mainStemListener) {
        listeners.add(mainStemListener);
    }

    private boolean isMine(Long nodeId) {
        return ConfigClient.getCurrentNid().equals(nodeId);
    }


    @Override
    public void reload() {
        initMainStem();
    }

    public MainStemData getMainStemData() {
        return mainStemData;
    }

    public void setMainStemData(MainStemData mainStemData) {
        this.mainStemData = mainStemData;
    }

    @Override
    public String getZkPath(Object... args) {
        return ZKPathUtils.getMainStemPath(taskStory, prototypeKey.value());
    }

    @Override
    public MainStemData getZkData() {
        byte[] mainStem = zkClientx.readData(getZkPath());
        return JSON.parseObject(mainStem, MainStemData.class);
    }

    public Long getLeastLiveTaskNodeId() {
        try{
            List<String> cfgNodes = ConfigClient.getNodesByFlowKeyAndPrototypeKey(taskStory, prototypeKey);
            List<String> livedNodes = zkClientx.getChildren(ZKPathUtils.getNodeRootPath());

            cfgNodes.retainAll(livedNodes);

            Long leastLiveNodeId = 0L;
            int leastLiveNodeNumber = 999;
            for (String node : cfgNodes) {
                int size = getSize(node, livedNodes);
                if(size < leastLiveNodeNumber) {
                    leastLiveNodeNumber = size;
                    leastLiveNodeId = Long.valueOf(node);
                }
            }
            return leastLiveNodeId;
        }catch (Exception e) {
            logger.error("get least live node error => {}", ExceptionUtils.getFullStackTrace(e));
        }

        return -1L;
    }

    private int getSize(String node, List<String> livedNodes) {
        int count = 0;
        if(livedNodes != null) {
            for (String livedNode : livedNodes) {
                if(livedNode.startsWith(node + "_")) {
                    count ++;
                }
            }
        }
        return count;
    }
}
