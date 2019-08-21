package com.hframe.hamster.node.task.common;

import com.alibaba.fastjson.JSON;
import com.hframe.ext.bean.SubscribeData;
import com.hframe.hamster.node.HamsterConfig;
import com.hframe.hamster.node.HamsterConst;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.arbitrate.ArbitratorFactory;
import com.hframe.hamster.node.arbitrate.impl.FlowEndNodeArbitrateEventImpl;
import com.hframe.hamster.node.arbitrate.impl.FlowMiddleNodeArbitrateEventImpl;
import com.hframe.hamster.node.arbitrate.impl.FlowStartNodeArbitrateEventImpl;
import com.hframe.hamster.node.arbitrate.interfaces.FlowEndNodeArbitrateEvent;
import com.hframe.hamster.node.arbitrate.interfaces.FlowTaskNodeArbitrateEvent;
import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.cannal.bean.EventData;
import com.hframe.hamster.node.config.ConfigClient;
import com.hframe.hamster.node.monitor.MainStemMonitor;
import com.hframe.hamster.node.monitor.PermitMonitor;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.SequenceData;
import com.hframe.hamster.node.monitor.bean.SharableEventData;
import com.hframe.hamster.node.zk.ZKPathUtils;
import com.hframework.common.client.redis.RedisService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 原型流程任务的抽象实现
 * Created by zqh on 2016/9/28.
 */
public abstract class AbstractPrototypeFlowNodeTask extends AbstractFlowNodeTask implements PrototypeTask, FlowTask {
    private static final Logger logger = LoggerFactory.getLogger(AbstractPrototypeFlowNodeTask.class);
    protected PrototypeKey prototypeKey;//原型数据分组Key

    protected FlowTaskNodeArbitrateEvent taskArbitrateEvent = null;//事件处理类
    private boolean isStartNode = false;
    private boolean isEndNode = false;

    private Thread endTerminThread ;

    //忽略数据过滤
    private Map<String, List<Long[]>> ignoreDataInfo = null;

    private static Long redisDataExpiredMinutes = Long.valueOf(HamsterConfig.getInstance().getHamsterRedisProcessingDataExpiredMinutes());

    private RedisService redisService;

    public AbstractPrototypeFlowNodeTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        this(flowKey, prototypeKey, TaskOrderMode.PROCESS);
    }

    public AbstractPrototypeFlowNodeTask(FlowKey flowKey, PrototypeKey prototypeKey, TaskOrderMode taskOrderMode) {
        super(flowKey, taskOrderMode);
        this.prototypeKey = prototypeKey;

        String taskStory = flowKey.value();
        Class taskNodeClass = this.getClass();

        redisService = HamsterContextInitializer.getBean("redisService");

        if (this instanceof FlowStartNodeTask) {
            taskArbitrateEvent = ArbitratorFactory.getInstance(taskStory, prototypeKey, taskNodeClass, FlowStartNodeArbitrateEventImpl.class);
            taskArbitrateEvent.setCurTask(this.getClass());
            taskArbitrateEvent.setNextTask(((FlowStartNodeTask) this).nextTask());
            isStartNode = true;
        }else if (this instanceof FlowNodeTask) {
            taskArbitrateEvent = ArbitratorFactory.getInstance(taskStory, prototypeKey, taskNodeClass, FlowMiddleNodeArbitrateEventImpl.class);
            taskArbitrateEvent.setCurTask(this.getClass());
            taskArbitrateEvent.setPrevTask(((FlowNodeTask) this).prevTask());
            taskArbitrateEvent.setNextTask(((FlowNodeTask)this).nextTask());
        }else if (this instanceof FlowEndNodeTask) {
            taskArbitrateEvent = ArbitratorFactory.getInstance(taskStory, prototypeKey, taskNodeClass, FlowEndNodeArbitrateEventImpl.class);
            taskArbitrateEvent.setCurTask(this.getClass());
            taskArbitrateEvent.setPrevTask(((FlowEndNodeTask)this).prevTask());
            isEndNode = true;
        }

        setName(name());

    }

    @Override
    protected void shutdownInternal() {
        if (this instanceof FlowStartNodeTask) {
            MainStemMonitor mainStemMonitor = ArbitratorFactory.remove(flowKey.value(), prototypeKey, Object.class, MainStemMonitor.class);
            if(mainStemMonitor != null) mainStemMonitor.destroy();
        }else {
            PermitMonitor permitMonitor = ArbitratorFactory.remove(flowKey.value(), prototypeKey, Object.class, PermitMonitor.class);
            if(permitMonitor != null) permitMonitor.destroy();
        }

        if(isEndNode && endTerminThread != null) {
            endTerminThread.interrupt();
        }
    }

    @Override
    public String name() {
        if(prototypeKey != null && prototypeKey != null) {
            return super.name() + "-" + flowKey.value() + "(" +  prototypeKey.value() + ")";
        }
        return super.name();
    }

    @Override
    protected void startInternal() {
        MDC.put(HamsterConst.splitTaskLogFileKey, flowKey.value() + "_" + prototypeKey.value());
        createTaskZkNodeIfNotExists();
        startTerminThreadIfEndNode();

        while (taskState.isRunning()) {//当任务为暂停态或者运行态时
            try {
                SharableEventData eventData = null;//事件数据
                TaskData taskData = null;////任务数据
                if (isStartNode) {
                    MainStemMonitor mainStemMonitor = ArbitratorFactory.getInstance(flowKey.value(), prototypeKey, Object.class, MainStemMonitor.class);
                    mainStemMonitor.start();
                    mainStemMonitor.waitForPermit();//阻塞等待被激活
//                    waitForPermit();

                    FlowStartNodeTask startNodeTask = (FlowStartNodeTask) this;
                    //step.2 获取待处理数据（阻塞获取）
                    final Message newMessage = ((FlowStartNodeTask) this).select();
                    if(checkNoPass()){
                        logger.warn("check not pass , maybe main stem and task state changed!");
                        startNodeTask.rollback(newMessage);
                        continue;
                    };

                    //step.2 获取可用的process资源（阻塞获取）
                    eventData = taskArbitrateEvent.await(prototypeKey);//阻塞获取可用的processId
                    if(checkNoPass()){//对于任务暂停信号发出后，主机直接抛出异常并释放maistem，备机如果在主机执行之前（没事），如果在主机执行之后，那么将直接抢占mainstem进行业务处理。
                        logger.warn("check not pass , maybe main stem and task state changed!");
                        startNodeTask.rollback(newMessage);
                        //processId删除 TODO
                        continue;
                    };
                    eventData.setBatchId(newMessage.getMessageId());
                    List<EventData> datas = newMessage.getDatas();
                    if(datas != null && datas.size() > 0) {
                        if(ignoreDataInfo == null) {
                            synchronized (this) {
                                if(ignoreDataInfo == null) {
                                    ignoreDataInfo = ((FlowStartNodeArbitrateEventImpl) taskArbitrateEvent).getIgnoreDataInfo(prototypeKey);
                                    logger.info("ignore data info => {}", JSON.toJSONString(ignoreDataInfo));
                                }
                            }
                        }
                        if(!ignoreDataInfo.isEmpty()) {
                            filterData(datas, ignoreDataInfo);//重启后过滤已经发送成功的数据
                        }
                    }

                    if(datas != null && datas.size() > 0) {
                        eventData.setBinlogStart(datas.get(0).getBinLogPostion());
                        eventData.setBinlogEnd(datas.get(datas.size() - 1).getBinLogPostion());
                    }
                    taskData = new TaskData(newMessage, eventData);
                }else {
                    eventData = taskArbitrateEvent.await(prototypeKey);//阻塞获取可用的processId
                    if(checkNoPass()){
                        //processId删除 TODO
                    }
                }

                //step.2 将待处理process信息更新到zk中
                final SharableEventData finalEventData = eventData;
                final TaskData[] finalTaskData = {taskData};
                final Class<? extends FlowTask> curClass = this.getClass();
                final Class<? extends FlowTask> prevClass = this instanceof FlowNodeTask ?
                        ((FlowNodeTask) this).prevTask() : this instanceof FlowEndNodeTask ?
                        ((FlowEndNodeTask) this).prevTask() : null;
                final Future<?> future = executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        // 设置profiling信息
                        boolean profiling = isProfiling();
                        Long profilingStartTime = null;
                        if (profiling) {
                            profilingStartTime = System.currentTimeMillis();
                        }

                        MDC.put(HamsterConst.splitTaskLogFileKey, flowKey.value() + "_" + prototypeKey.value());
                        String currentName = Thread.currentThread().getName();
                        Thread.currentThread().setName(name() + "－Worker");

                        try {
                            logger.info("task node process meta data:{}", JSON.toJSONString(finalEventData));
                            //1.获取业务数据
                            if(finalTaskData[0] == null) {
                                if(prevClass != null) {
                                    finalTaskData[0] = redisService.get(ZKPathUtils.getRedisProcessStagePath(flowKey.value(), prototypeKey.value(),
                                            finalEventData.getProcessId(), prevClass.getSimpleName()), TaskData.class, Message.class);

                                    if(finalTaskData[0] == null) {//主要为中间数据失效或者丢失造成，这种情况下默认执行空数据完成process处理，select会从新读取该process数据并排除处理成功的数据
                                        logger.warn("current node task get last node task data from redis failed, path = {}!", flowKey.value(), prototypeKey.value(),
                                                finalEventData.getProcessId(), prevClass.getSimpleName());
                                        Message message = new Message();
                                        message.setDatas(new ArrayList<EventData>());
                                        message.setTransDatas(new ArrayList<SubscribeData>());
                                        message.setMessageId(finalEventData.getBatchId());
                                        finalTaskData[0] = new TaskData(message, finalEventData);
                                    }
                                }
                            }
                            logger.debug("task node process begin, task data : {}", JSON.toJSONString(finalTaskData[0]));
//                            if(isEmpty(finalTaskData[0])) {
                                //2.处理业务数据
                                process(finalTaskData[0]);
//                            }
                            logger.debug("task node process finish，task data : {}", JSON.toJSONString(finalTaskData[0]));

                            //3.将处理结果写入缓存服务器中(24小时后失效)
                            finalTaskData[0].setCreateTime(new Date());
                            redisService.saveOrUpdate(ZKPathUtils.getRedisProcessStagePath(flowKey.value(), prototypeKey.value(),
                                    finalEventData.getProcessId(), curClass.getSimpleName()), finalTaskData[0], redisDataExpiredMinutes * 60);

                            //4.将元数据状态进行更新至zk中
                            taskArbitrateEvent.single(finalEventData);
                            if(prevClass != null) {
                                redisService.delete(ZKPathUtils.getRedisProcessStagePath(flowKey.value(), prototypeKey.value(),
                                        finalEventData.getProcessId(), prevClass.getSimpleName()));
                            }
                        } catch (Exception e) {
                            logger.error("task node process error: {}, {}, {}, {}",
                                    flowKey, prototypeKey, this.getClass(), ExceptionUtils.getFullStackTrace(e));
                            ConfigClient.updateTaskNodeError(flowKey, prototypeKey, this.getClass(), ExceptionUtils.getFullStackTrace(e));
                            taskState = TaskState.ERROR;
                            shutdownInternal();
                        }finally {
                            Thread.currentThread().setName(currentName);
                            MDC.remove(HamsterConst.splitTaskLogFileKey);
                        }
                    }
                });

                //如果过程有序，需要每批次单独处理完成后，方可进行下批次处理 TODO 这里需要放在taskArbitrateEvent进行处理
                if(taskOrderMode.isProcess()) {
                    future.get();
                }


            } catch (InterruptedException e) {
                logger.warn("thread interrupted => {}", ExceptionUtils.getFullStackTrace(e));
                break;
                //TODO 将读出来的messge信息进行回滚处理
            } catch (ExecutionException e) {
                logger.error("thread  execution interrupted => {}", ExceptionUtils.getFullStackTrace(e));
            } catch (Exception e) {
                logger.error("unknown exception => {}", ExceptionUtils.getFullStackTrace(e));
            }
        }
    }

    protected  void filterData(List<EventData> datas, Map<String, List<Long[]>> ignoreDataBinLogInfo){
        if(datas == null || datas.size() == 0 || ignoreDataBinLogInfo == null || ignoreDataBinLogInfo.isEmpty()) return;

        String binlogStart = datas.get(0).getBinLogPostion();
        String startBinlogFileName = binlogStart.substring(0, binlogStart.indexOf("|"));
        Long startBinlogOffset = Long.parseLong(binlogStart.substring(binlogStart.indexOf("|") + 1));

        String binlogEnd = datas.get(datas.size() - 1).getBinLogPostion();
        String endBinlogFileName = binlogEnd.substring(0, binlogEnd.indexOf("|"));
        Long endBinlogOffset = Long.parseLong(binlogEnd.substring(binlogEnd.indexOf("|") + 1));

        if(endBinlogFileName.equals(startBinlogFileName)) {
            filterData(datas, startBinlogFileName, ignoreDataBinLogInfo, startBinlogOffset, endBinlogOffset);
        }else {
            filterData(datas, startBinlogFileName, ignoreDataBinLogInfo, startBinlogOffset, Long.MAX_VALUE);
            filterData(datas, endBinlogFileName, ignoreDataBinLogInfo, Long.MIN_VALUE, endBinlogOffset);
        }
    }

    private synchronized void filterData(List<EventData> datas, String ignoreBinlogFileName,
                                         Map<String, List<Long[]>> ignoreDataBinLogInfo, Long startBinlogOffset, long endBinlogOffset) {
        logger.info("data filter : ignoreBinlogFileName = {}, startOffset = {} ,endOffset = {}, " +
                "object = {}", ignoreBinlogFileName , ignoreDataBinLogInfo.get(ignoreBinlogFileName), startBinlogOffset, endBinlogOffset);
        if(ignoreDataBinLogInfo.containsKey(ignoreBinlogFileName)) {
            List<Long[]> binlogInfos = ignoreDataBinLogInfo.get(ignoreBinlogFileName);
            Iterator<Long[]> iterator = binlogInfos.iterator();
            while (iterator.hasNext()) {
                Long[] binLogInfo = iterator.next();
                //存在交集
                if(binLogInfo[1] >= startBinlogOffset && binLogInfo[0] <= endBinlogOffset) {
                    filterData(datas,ignoreBinlogFileName,
                            binLogInfo[0] < startBinlogOffset ? startBinlogOffset : binLogInfo[0],
                            binLogInfo[1] > endBinlogOffset ? endBinlogOffset : binLogInfo[1]);
                }else if(binLogInfo[1] < startBinlogOffset) {//已经成为过去的一段
                    iterator.remove();
                }
            }
            if(ignoreDataBinLogInfo.get(ignoreBinlogFileName).size() == 0) {
                ignoreDataBinLogInfo.remove(ignoreBinlogFileName);
            }
        }
    }

    private void filterData(List<EventData> datas, String ignoreBinlogFileName, Long startOffset, Long endOffset){
        Iterator<EventData> iterator = datas.iterator();
        while (iterator.hasNext()) {
            EventData next = iterator.next();
            String binLogPosition = next.getBinLogPostion();
            String binlogFileName = binLogPosition.substring(0, binLogPosition.indexOf("|"));
            Long binlogOffset = Long.parseLong(binLogPosition.substring(binLogPosition.indexOf("|") + 1));
            if(binlogFileName.equals(ignoreBinlogFileName) && binlogOffset <= endOffset && binlogOffset >= startOffset) {
                logger.info("data filter result: ignoreBinlogFileName = {}, startOffset = {} ,endOffset = {}, " +
                        "object = {}", ignoreBinlogFileName, startOffset, endOffset, JSON.toJSONString(next));
                iterator.remove();
            }
        }
    }

    private void startTerminThreadIfEndNode() {
        if(isEndNode) {

            final Class<? extends FlowTask> curClass = this.getClass();
            Runnable terminRunnable = new Runnable() {
                @Override
                public void run() {
                    logger.info("termin thread start..！");
                    while (!taskState.isClosed()) {//当任务为暂停态或者运行态时
                        try {
                            SharableEventData eventData = ((FlowEndNodeArbitrateEvent) taskArbitrateEvent).awaitTermin(prototypeKey);//阻塞获取可用的processId
                            if (checkNoPass()) {
                                //processId删除 TODO
                            }
                            //step.2 将待处理process信息更新到zk中
                            final SharableEventData finalEventData = eventData;
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    MDC.put(HamsterConst.splitTaskLogFileKey, flowKey.value() + "_" + prototypeKey.value());
                                    String currentName = Thread.currentThread().getName();
                                    Thread.currentThread().setName(name() + "-Termin－Worker");
                                    logger.info("task node process meta data:{}", JSON.toJSONString(finalEventData));
                                    try {
                                        //1.获取业务数据
                                        TaskData taskData = redisService.get(ZKPathUtils.getRedisProcessStagePath(flowKey.value(), prototypeKey.value(),
                                                finalEventData.getProcessId(), curClass.getSimpleName()), TaskData.class, Message.class);
                                        //主要为中间数据失效或者丢失造成，这种情况下默认执行空数据完成process处理，select会从新读取该process数据并排除处理成功的数据
                                        if(taskData == null) {
                                            logger.warn("current node task get current node task data from redis failed, path = {},{},{},{}!", flowKey.value(), prototypeKey.value(),
                                                    finalEventData.getProcessId(), curClass.getSimpleName());
                                            Message message = new Message();
                                            message.setDatas(new ArrayList<EventData>());
                                            message.setTransDatas(new ArrayList<SubscribeData>());
                                            message.setMessageId(finalEventData.getBatchId());
                                            taskData = new TaskData(message, finalEventData);
                                        }

                                        askIfEndNode(taskData);
                                        redisService.delete(ZKPathUtils.getRedisProcessStagePath(flowKey.value(), prototypeKey.value(),
                                                finalEventData.getProcessId(), curClass.getSimpleName()));
                                    } catch (Exception e) {
                                        logger.error("task node process error: {}, {}, {}, {}",
                                                flowKey, prototypeKey, this.getClass(), ExceptionUtils.getFullStackTrace(e));
                                        ConfigClient.updateTaskNodeError(flowKey, prototypeKey, this.getClass(), ExceptionUtils.getFullStackTrace(e));
                                        taskState = TaskState.ERROR;
                                    } finally {
                                        Thread.currentThread().setName(currentName);
                                        MDC.remove(HamsterConst.splitTaskLogFileKey);
                                    }
                                }
                            });

                        } catch (InterruptedException e) {
                            logger.error("thread interrupted => {}", ExceptionUtils.getFullStackTrace(e));
                            break;
                            //TODO 将读出来的messge信息进行回滚处理
                        }
                    }
                }
            };
            endTerminThread = new Thread(terminRunnable);
            endTerminThread.setName(name() + "-Termin");
            endTerminThread.start();
        }
    }

    protected boolean isEmpty(TaskData taskData){
        if(taskData.getMessage() == null) return true;
        if(((Message)taskData.getMessage()).getDatas() == null) return true;
        if(((Message)taskData.getMessage()).getDatas().size() == 0)return true;
        return false;
    }

    protected  void createTaskZkNodeIfNotExists() {
        String taskStory = flowKey.value();
        String taskInstance = prototypeKey.value();
        try{
            if(!zkClientx.exists(ZKPathUtils.getStoryRootPath() + "/" + taskStory))
                zkClientx.create(ZKPathUtils.getStoryRootPath() + "/" + taskStory, null, CreateMode.PERSISTENT);
        }catch (Exception e) {
            logger.warn("init zk node exception, {}", ExceptionUtils.getFullStackTrace(e));
        }
        try {
            if(!zkClientx.exists(ZKPathUtils.getStoryRootPath() + "/" + taskStory + "/" + "instance"))
                zkClientx.create(ZKPathUtils.getStoryRootPath() + "/" + taskStory + "/" + "instance", null, CreateMode.PERSISTENT);
        }catch (Exception e) {
            logger.warn("init zk node exception, {}", ExceptionUtils.getFullStackTrace(e));
        }
        try {
            if(!zkClientx.exists(ZKPathUtils.getStoryInstanceRootPath(taskStory, taskInstance)))
                zkClientx.create(ZKPathUtils.getStoryInstanceRootPath(taskStory, taskInstance) , JSON.toJSONString(PermitMonitor.PrototypeStatus.START), CreateMode.PERSISTENT);
        }catch (Exception e) {
            logger.warn("init zk node exception, {}", ExceptionUtils.getFullStackTrace(e));
        }
        try {
            if(!zkClientx.exists(ZKPathUtils.getProcessRootPath(taskStory, taskInstance))) {
                SequenceData sequenceData = new SequenceData();
                sequenceData.setLastProcessId(-1L);
                sequenceData.setLastSequence(0L);
                zkClientx.create(ZKPathUtils.getProcessRootPath(taskStory, taskInstance) , JSON.toJSONString(sequenceData), CreateMode.PERSISTENT);
            }
        }catch (Exception e) {
            logger.warn("init zk node exception, {}", ExceptionUtils.getFullStackTrace(e));
        }
        try {
            if(!zkClientx.exists(ZKPathUtils.getTerminRootPath(taskStory, taskInstance))) {
                zkClientx.create(ZKPathUtils.getTerminRootPath(taskStory, taskInstance), null, CreateMode.PERSISTENT);
            }

        }catch (Exception e) {
            logger.warn("init zk node exception, {}", ExceptionUtils.getFullStackTrace(e));
        }
    }



    public void askIfEndNode(TaskData taskData){
        if (this instanceof FlowEndNodeTask) {
            FlowEndNodeTask flowEndNodeTask = (FlowEndNodeTask) this;
            flowEndNodeTask.ask((Message) taskData.getMessage());
            //4.将元数据状态进行更新至zk中
            ((FlowEndNodeArbitrateEvent)taskArbitrateEvent).ask((SharableEventData)taskData.getEventData());
        }
    }

    private boolean checkNoPass() {
        if (this instanceof FlowStartNodeTask) {
            String taskStory = flowKey.value();
            Class taskNodeClass = this.getClass();
            return !taskState.isRunning() ||
                    !((MainStemMonitor) ArbitratorFactory.getInstance(taskStory, prototypeKey, Object.class, MainStemMonitor.class)).isPermit();
        }else {
            return !taskState.isRunning();
        }
    }

    protected  void waitForPermit() throws InterruptedException {
        String taskStory = flowKey.value();
        Class taskNodeClass = this.getClass();
        if (this instanceof FlowStartNodeTask) {
            MainStemMonitor mainStemMonitor = ArbitratorFactory.getInstance(taskStory, prototypeKey, Object.class, MainStemMonitor.class);
            mainStemMonitor.waitForPermit();
//            while (!mainStemMonitor.isPermit()) {
//                LockSupport.parkNanos(5 * 1000 * 1000L * 1000L); // 5秒钟检查一次
//            }
        }else {
            PermitMonitor permitMonitor = ArbitratorFactory.getInstance(taskStory, prototypeKey, Object.class, PermitMonitor.class);
            permitMonitor.waitPermit();
        }
    }


    @Override
    public PrototypeKey getPrototypeKey() {
        return prototypeKey;
    }

    public boolean isProfiling() {
        return true;
    }

}
