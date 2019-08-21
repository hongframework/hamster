package com.hframe.hamster.node.task;

import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.common.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class CronSelectTask extends AbstractPrototypeFlowNodeTask implements FlowStartNodeTask,CronTask {
    private static final Logger logger = LoggerFactory.getLogger(CronSelectTask.class);

    private TriggerHolder triggerHolder;

    public CronSelectTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
        try {
            triggerHolder = TriggerHolder.hold(cronExpression());
        } catch (Exception e) {
            logger.error("select task init error ! {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    @Override
    protected void startInternal() {
        try {
            triggerHolder.start();
        } catch (SchedulerException e) {
            logger.error("trigger start error ! {}", ExceptionUtils.getFullStackTrace(e));
        }
        super.startInternal();
    }

    @Override
    public Message select() throws InterruptedException {
        logger.info("cron fetch begin ..");
        while (true) {
            triggerHolder.waitForTrigger();

            Message result = null;//阻塞获取
            try {
                result = fetch();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(result.getHbaseDatas() != null && result.getHbaseDatas().size() > 0) {
                logger.info("cron fetch finish .. ");
                return result;
            }
        }

    }

    @Override
    public boolean rollback(Message newMessage) {
        triggerHolder.rollback();
        return true;
    }

    @Override
    protected void shutdownInternal() {
        super.shutdownInternal();
        triggerHolder.stop();

    }

    @Override
    public void process(TaskData taskData) {
        if(isEmpty(taskData)) return;
    }

}
