package com.hframe.hamster.node.monitor.listener;

import com.hframe.hamster.common.ReplyProcessQueue;
import com.hframe.hamster.common.zkclient.ZkClientx;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.arbitrate.ArbitratorFactory;
import com.hframe.hamster.node.arbitrate.TaskProcessArbitrateEvent;
import com.hframe.hamster.node.monitor.TaskProcessMonitor;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.zk.ZooKeeperClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangquanhong on 2016/9/29.
 */
public abstract class AbstractFlowNodeProcessListener implements TaskProcessListener {
    protected static final Logger logger    = LoggerFactory.getLogger(AbstractFlowNodeProcessListener.class);
    protected ZkClientx zookeeper = ZooKeeperClient.getInstance();
    protected ReplyProcessQueue processIdsQueue; // 响应式processId队列

    private static final Integer DEFAULT_PARALLELISM_SIZE = 10;
    protected int parallelism = DEFAULT_PARALLELISM_SIZE;//process并行数
    protected int queueSize = DEFAULT_PARALLELISM_SIZE * 5;//queue 大小

    protected TaskProcessMonitor processMonitor;
    protected PrototypeKey prototypeKey;

    protected String taskStory;
    protected PrototypeKey taskInstance;
    protected Class taskNodeClass;

    public AbstractFlowNodeProcessListener(String taskStory, PrototypeKey prototypeKey, Class taskNodeClass) {
        this.taskStory = taskStory;
        this.prototypeKey = prototypeKey;
        this.taskNodeClass = taskNodeClass;

        initProcessIdsQueue(prototypeKey);
        //TaskProcessMonitor只需要一个实例有一个接口，无需每个子任务单独创建一个，因此将taskNodeClass参数传死为"Object"
        processMonitor = ArbitratorFactory.getInstance(taskStory, prototypeKey, Object.class, TaskProcessMonitor.class);
        processMonitor.start();
        processMonitor.addListener(this);

    }

    public Long waitForProcess() throws InterruptedException {
        logger.info("wait for available process..");
//        if(processMonitor == null) {
//            synchronized (this) {
//                if(processMonitor == null) {
//                    //TaskProcessMonitor只需要一个实例有一个接口，无需每个子任务单独创建一个，因此将taskNodeClass参数传死为"Object"
//                    processMonitor = ArbitratorFactory.getInstance(taskStory, prototypeKey, Object.class, TaskProcessMonitor.class);
//                    processMonitor.start();
//                    processMonitor.addListener(this);
//                }
//            }
//        }
        Long processId = processIdsQueue.take();
        logger.debug("[{}] process-queue take process [{}] success !", this.getClass().getSimpleName(), processId);
        return processId;
    }
    protected synchronized void addReply(Long processId) {
        boolean result = processIdsQueue.offer(processId);
        if(result) {
            logger.debug("[{}] process-queue add process [{}] success !", this.getClass().getSimpleName(), processId);
        }else {
            logger.debug("[{}] process-queue add process [{}] failed !", this.getClass().getSimpleName(),processId);
        }
    }


    @Override
    public void processDeleted(Long processId) {
        processIdsQueue.remove(processId);
    }

    protected void initProcessIdsQueue(PrototypeKey prototypeKey) {
        //TODO 需要根据prototypeKey来确认queueSize
        processIdsQueue = new ReplyProcessQueue(queueSize);
    }

    protected void addProcessNodeToZk() {
        HamsterContextInitializer.getBean(TaskProcessArbitrateEvent.class).createProcess(taskStory,prototypeKey);
    }
}
