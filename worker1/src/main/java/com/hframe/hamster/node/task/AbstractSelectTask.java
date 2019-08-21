package com.hframe.hamster.node.task;

import com.alibaba.otter.canal.common.utils.BooleanMutex;
import com.hframe.ext.bean.CanalConfig;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.cannal.CanalServer;
import com.hframe.hamster.node.cannal.CanalServerFactory;
import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.common.AbstractPrototypeFlowNodeTask;
import com.hframe.hamster.node.task.common.FlowStartNodeTask;
import com.hframe.hamster.node.task.common.TaskData;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class AbstractSelectTask extends AbstractPrototypeFlowNodeTask implements FlowStartNodeTask {
    private static final Logger logger = LoggerFactory.getLogger(AbstractSelectTask.class);

    private CanalServer canalServer ;
    private BooleanMutex canalState = new BooleanMutex(false);

    public AbstractSelectTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
        try {
            CanalConfig canalConfig = getCanalConfig(flowKey, prototypeKey);
            canalServer = new CanalServer(canalConfig);
            HamsterContextInitializer.autowire(canalServer);
            //放入全局静态变量中，方便loadTask获取进行ack
            CanalServerFactory.put(flowKey, prototypeKey, canalServer);
        } catch (Exception e) {
            logger.error("select task init error ! {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    public abstract CanalConfig getCanalConfig(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception;

    @Override
    public Message select() throws InterruptedException {
        if(!canalState.state()) {
            canalServer.start();
            canalState.set(true);
        }
        canalServer.waitForStarted();//阻塞等待
        logger.info("canal fetch begin ..");
        Message result = canalServer.fetch();//阻塞获取
        logger.info("canal fetch finish .. ");
        return result;
    }

    @Override
    public boolean rollback(Message newMessage) {
        canalServer.rollback(newMessage.getMessageId());
        return true;
    }

    @Override
    protected void shutdownInternal() {
        super.shutdownInternal();
        canalServer.stop();
    }

    @Override
    public void process(TaskData taskData) {
        if(isEmpty(taskData)) return;
    }

}
