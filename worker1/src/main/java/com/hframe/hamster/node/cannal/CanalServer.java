package com.hframe.hamster.node.cannal;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.common.utils.BooleanMutex;
import com.alibaba.otter.canal.common.zookeeper.ZkClientx;
import com.alibaba.otter.canal.common.zookeeper.ZookeeperPathUtils;
import com.alibaba.otter.canal.ext.AckZookeeperLogPositionManager;
import com.alibaba.otter.canal.filter.aviater.AviaterRegexFilter;
import com.alibaba.otter.canal.instance.core.CanalInstance;
import com.alibaba.otter.canal.instance.core.CanalInstanceGenerator;
import com.alibaba.otter.canal.instance.manager.CanalInstanceWithManager;
import com.alibaba.otter.canal.instance.manager.model.Canal;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter;
import com.alibaba.otter.canal.parse.CanalEventParser;
import com.alibaba.otter.canal.parse.ha.CanalHAController;
import com.alibaba.otter.canal.parse.inbound.mysql.AbstractMysqlEventParser;
import com.alibaba.otter.canal.parse.inbound.mysql.MysqlEventParser;
import com.alibaba.otter.canal.parse.index.CanalLogPositionManager;
import com.alibaba.otter.canal.parse.index.ZooKeeperLogPositionManager;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.ClientIdentity;
import com.alibaba.otter.canal.protocol.position.LogPosition;
import com.alibaba.otter.canal.server.embedded.CanalServerWithEmbedded;
import com.hframe.ext.bean.CanalConfig;
import com.hframe.hamster.common.ReplyProcessQueue;
import com.hframe.hamster.node.HamsterConst;
import com.hframe.hamster.node.cannal.bean.EventData;
import com.hframe.hamster.node.cannal.bean.HeartBreak;
import com.hframe.hamster.node.cannal.bean.SyncMode;
import com.hframe.hamster.node.logHandlers.FetcherDumpHandler;
import com.hframe.hamster.node.logHandlers.SelectDumpHandler;
import com.hframe.hamster.node.zk.ZooKeeperClient;
import com.hframework.common.client.redis.RedisService;
import com.hframework.common.frame.ServiceFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangquanhong on 2016/9/29.
 */
public class CanalServer implements IFetcher {
    private static final Logger logger = LoggerFactory.getLogger(CanalServer.class);

    private static final String     DATE_FORMAT      = "yyyy-MM-dd HH:mm:ss";
    private static final String     SEP              = SystemUtils.LINE_SEPARATOR;
    private int                     logSplitSize     = 50;
    private CanalServerWithEmbedded canalEmbeddedServer;
    protected ReplyProcessQueue messageIdsQueue;  // 响应式processId队列
    private int queueSize = 100 * 5;//queue 大小

    private ClientIdentity clientIdentity;

    private Canal canal;

    private CanalConfig canalConfig;

    private BooleanMutex canalState = new BooleanMutex(false);

    private MessageParser messageParser;



    private String monitorRedisKey = null;

    private final HeartBreak heartBreak = new HeartBreak();

    private String selectorMonitorPath = null;

    private boolean dump = true;
    private boolean dumpDetail  = true;
    protected CanalLogPositionManager parseLogPositionManager         = null;
    protected CanalLogPositionManager ackLogPositionManager         = null;

    public CanalServer(final CanalConfig canalConfig){
        this.canalConfig = canalConfig;
        canalState = new BooleanMutex(false);
        canalEmbeddedServer = new CanalServerWithEmbedded();
        final boolean syncFull =true;

        canal = new Canal();
        canal.setId(canalConfig.getCanalId());
        canal.setName(canalConfig.getDestination());
        canal.setCanalParameter(canalConfig.getCanalParameter());

        messageIdsQueue = new ReplyProcessQueue(queueSize);
        canalEmbeddedServer.setCanalInstanceGenerator(new CanalInstanceGenerator() {
            @Override
            public CanalInstance generate(String destination) {
                return new CanalInstanceWithManager(canal, canalConfig.getFilter()) {

                    @Override
                    protected CanalLogPositionManager initLogPositionManager() {
                        return super.initLogPositionManager();
                    }

                    protected CanalHAController initHaController() {
                        CanalParameter.HAMode haMode = parameters.getHaMode();
                        if (haMode.isMedia()) {
                            return super.initHaController();
                        } else {
                            return super.initHaController();
                        }
                    }

                    protected void startEventParserInternal(CanalEventParser parser, boolean isGroup) {
                        super.startEventParserInternal(parser, isGroup);

                        if (eventParser instanceof MysqlEventParser) {
                            // 设置支持的类型
                            ((MysqlEventParser) eventParser).setSupportBinlogFormats("ROW");
                            if (syncFull) {
                                ((MysqlEventParser) eventParser).setSupportBinlogImages("FULL");
                            } else {
                                ((MysqlEventParser) eventParser).setSupportBinlogImages("FULL,MINIMAL");
                            }

                            MysqlEventParser mysqlEventParser = (MysqlEventParser) eventParser;
                            CanalHAController haController = mysqlEventParser.getHaController();

                            if (!haController.isStart()) {
                                haController.start();
                            }
                        }
                    }

                };
            }
        });
        MessageParser.MessageParseConfig messageParseConfig = new MessageParser.MessageParseConfig();
        messageParseConfig.setSyncMode(SyncMode.ROW);
        messageParser = new MessageParser(messageParseConfig);

        ackLogPositionManager = new AckZookeeperLogPositionManager();
        ((AckZookeeperLogPositionManager) ackLogPositionManager).setZkClientx(getZkclientx());

        parseLogPositionManager = new ZooKeeperLogPositionManager();
        ((ZooKeeperLogPositionManager) parseLogPositionManager).setZkClientx(getZkclientx());


    }

    public CanalServer(Long canalId){
//        canalState = new BooleanMutex(false);
//        this.canalId = canalId;
//        canalEmbeddedServer = new CanalServerWithEmbedded();
//        //TODO 如下需要根据具体的canalId进行设值
//        filter = "*.*";
//        destination = "test";
//        batchSize = 10;
//        batchTimeout = 5 * 1000L;
//        ddlSync = true;
//        slaveId = 1010L;
//        final boolean syncFull =true;
//
//        canal = new Canal();
//        canal.setId(canalId);
//        canal.setName(destination);
//        canal.setCanalParameter(new CanalParameter());
//        canal.getCanalParameter().setCanalId(canalId);
//        canal.getCanalParameter().setDdlIsolation(ddlSync);
//
//        canal.getCanalParameter().setSlaveId(slaveId);
//        canal.getCanalParameter().setDbAddresses(new ArrayList<InetSocketAddress>() {{
//            add(new InetSocketAddress("127.0.0.1", 3306));
//        }});
//        canal.getCanalParameter().setMasterAddress(new InetSocketAddress("127.0.0.1", 3306));
////        canal.getCanalParameter().setMasterTimestamp(System.currentTimeMillis());
//        canal.getCanalParameter().setDbUsername("canal");
//        canal.getCanalParameter().setDbPassword("NHY67ujm");
//        canal.getCanalParameter().setDefaultDatabaseName("bettle");
//        canal.getCanalParameter().setConnectionCharset("UTF-8");
//        canal.getCanalParameter().setRunMode(CanalParameter.RunMode.EMBEDDED);
//
//        canal.getCanalParameter().setIndexMode(CanalParameter.IndexMode.MEMORY);
//
//        canalEmbeddedServer.setCanalInstanceGenerator(new CanalInstanceGenerator() {
//            @Override
//            public CanalInstance generate(String destination) {
//                return new CanalInstanceWithManager(canal, filter) {
//                    protected CanalHAController initHaController() {
//                        CanalParameter.HAMode haMode = parameters.getHaMode();
//                        if (haMode.isMedia()) {
//                            return super.initHaController();
//                        } else {
//                            return super.initHaController();
//                        }
//                    }
//
//                    protected void startEventParserInternal(CanalEventParser parser, boolean isGroup) {
//                        super.startEventParserInternal(parser, isGroup);
//
//                        if (eventParser instanceof MysqlEventParser) {
//                            // 设置支持的类型
//                            ((MysqlEventParser) eventParser).setSupportBinlogFormats("ROW");
//                            if (syncFull) {
//                                ((MysqlEventParser) eventParser).setSupportBinlogImages("FULL");
//                            } else {
//                                ((MysqlEventParser) eventParser).setSupportBinlogImages("FULL,MINIMAL");
//                            }
//
//                            MysqlEventParser mysqlEventParser = (MysqlEventParser) eventParser;
//                            CanalHAController haController = mysqlEventParser.getHaController();
//
//                            if (!haController.isStart()) {
//                                haController.start();
//                            }
//                        }
//                    }
//
//                };
//            }
//        });
//        MessageParser.MessageParseConfig messageParseConfig = new MessageParser.MessageParseConfig();
//        messageParseConfig.setSyncMode(SyncMode.ROW);
//        messageParser = new MessageParser(messageParseConfig);

    }

    @Override
    public void start() {
        removeLastUnFinishBatches();
//        recoveryCanalParsePositionIfNecessary(); //自己做的特殊处理，现更改为解析位点与消费位点cursor模式控制
        monitorRedisKey = ("H_MONITOR_HAMSTER_SUBSCRIBE_ID_" + canalConfig.getClientId()).replaceAll("\\*","");
        canalEmbeddedServer.start();
        canalEmbeddedServer.start(canalConfig.getDestination());
        clientIdentity = new ClientIdentity(canalConfig.getDestination(), canalConfig.getClientId(), canalConfig.getFilter());
        canalEmbeddedServer.subscribe(clientIdentity);
        canalState.set(true);
    }

    public void removeLastUnFinishBatches(){
        String batchsPath = ZookeeperPathUtils.getBatchMarkPath(canalConfig.getDestination(),
                canalConfig.getClientId());
        if(getZkclientx().exists(batchsPath)) {
            List<String> nodes = getZkclientx().getChildren(batchsPath);
            if (CollectionUtils.isEmpty(nodes)) {
                // 没有batch记录
                return;
            }
            for (String batchIdString : nodes) {
                String path = ZookeeperPathUtils.getBatchMarkWithIdPath(canalConfig.getDestination(),
                        canalConfig.getClientId(),
                        Long.valueOf(batchIdString));
                logger.info("[CANAL START]: remove canal last cycle unmark batchId: {}", Long.valueOf(batchIdString));
                getZkclientx().delete(path);
            }
        }
    }

    /**
     * 启动时，需要重置canal binlog 解析位置，因为目前event store是只支持内存模式，重启后未消费数据已丢失，需要重新解析
     */
    private void recoveryCanalParsePositionIfNecessary() {
        LogPosition parseLogPosition = parseLogPositionManager.getLatestIndexBy(canalConfig.getDestination());
        LogPosition ackLogPosition = ackLogPositionManager.getLatestIndexBy(canalConfig.getDestination());
        if(parseLogPosition != null && ackLogPosition != null) {
            if(!parseLogPosition.equals(ackLogPosition)) {
                parseLogPositionManager.persistLogPosition(canalConfig.getDestination(), ackLogPosition);
                logger.info("recovery canal parse position by ack position, " +
                        " parse position : {}, ack position : {}", parseLogPosition, ackLogPosition);
            }
        }
    }

    public void waitForStarted() throws InterruptedException {
        canalState.get();
    }

    @Override
    public boolean isStart() {
         return canalEmbeddedServer.isStart();
    }

    @Override
    public void stop() {
        if(isStart()) {
            synchronized (this) {
                if(isStart()) {
                    canalEmbeddedServer.stop(canalConfig.getDestination());
                    canalEmbeddedServer.stop();
                }
            }
        }
        heartBreak.reset();
    }

    /**
     * 动态设置filter
     * @param filter
     */
    public void resetFilter(String filter) {
        if(isStart()) {
            for (CanalInstance canalInstance : this.canalEmbeddedServer.getCanalInstances().values()) {
                CanalEventParser eventParser = canalInstance.getEventParser();
                if(eventParser instanceof AbstractMysqlEventParser) {
                    AviaterRegexFilter aviaterFilter = new AviaterRegexFilter(filter);
                    ((AbstractMysqlEventParser)eventParser).setEventFilter(aviaterFilter);
                }
            }
        }else {
            logger.warn("runtime set filter {} failed, because it's  not Started !", filter);
        }

    }

    @Override
    public Message fetch() throws InterruptedException {
        com.alibaba.otter.canal.protocol.Message message = null;
        List<EventData> eventDatas = null;
        if(canalConfig.getBatchTimeout() > -1) {
            while (isStart()) {
//                logger.info("[" + Thread.currentThread().getName() + "] : canal fetch begin ..");
                message = canalEmbeddedServer.getWithoutAck(clientIdentity,canalConfig.getBatchSize(),
                        canalConfig.getBatchTimeout() , TimeUnit.MILLISECONDS);
                SelectDumpHandler.info(canalConfig.getPrototypeKey(),
                        "[" + Thread.currentThread().getName() + "] : canal fetch result : " + message);
                if(message == null || message.getId() ==-1L) {
                    updateHeartBreak(null);
//                    redisSentinelService.hIncrBy(monitorRedisKey, System.currentTimeMillis()/1000/60*60, 0L);
                    continue;
                }else {
                    messageIdsQueue.offer(message.getId());
                    eventDatas = messageParser.parse(message.getEntries());
                    if(eventDatas != null && eventDatas.size() > 0) {
                        updateHeartBreak(eventDatas.get(eventDatas.size() - 1).getExecuteTime());
                        ((RedisService)ServiceFactory.getService("redisSentinelService"))
                                .hIncrBy(monitorRedisKey, System.currentTimeMillis()/1000/60*60, Long.valueOf(eventDatas.size()));
                        break;
                    }else {
                        updateHeartBreak(null);
                        ack(message.getId());
                    }
//                    redisSentinelService.hIncrBy(monitorRedisKey, System.currentTimeMillis()/1000/60*60, 0L);

                }
            }
        }else {
            message = canalEmbeddedServer.getWithoutAck(clientIdentity,canalConfig.getBatchSize());
            eventDatas = messageParser.parse(message.getEntries());
            messageIdsQueue.offer(message.getId());
        }

        if(!isStart()) {
            throw new InterruptedException("task shutdown now !");
        }

        Message result = new Message(message.getId(), eventDatas);

        if (dump && logger.isInfoEnabled()) {
            String startPosition = null;
            String endPosition = null;
            if (!CollectionUtils.isEmpty(message.getEntries())) {
                startPosition = buildPositionForDump(message.getEntries().get(0));
                endPosition = buildPositionForDump(message.getEntries().get(message.getEntries().size() - 1));
            }

            dumpMessages(result, startPosition, endPosition, message.getEntries().size());// 记录一下，方便追查问题
        }

        return result;
    }

    private void updateHeartBreak(Long executeTimeStamp) {
        if(executeTimeStamp != null) {
            heartBreak.setExecuteTimeStamp(executeTimeStamp);
        }
        long currentTime = System.currentTimeMillis();
        long delaySeconds = (currentTime - heartBreak.getFetchTimeStamp()) / 1000;
//        logger.info("fetchTime = " + heartBreak.getFetchTimeStamp() + "; executeTime = " + heartBreak.getExecuteTimeStamp());
        if(delaySeconds >= 10) {
            heartBreak.setFetchTimeStamp(currentTime);
            logger.info("update heart break : {}, {}", selectorMonitorPath, heartBreak);
            ZooKeeperClient.getInstance().writeData(selectorMonitorPath, JSON.toJSONString(heartBreak));
//            heartBreak.setExecuteTimeStamp(-1L);
        }
    }

    private String buildPositionForDump(CanalEntry.Entry entry) {
        long time = entry.getHeader().getExecuteTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return entry.getHeader().getLogfileName() + ":" + entry.getHeader().getLogfileOffset() + ":"
                + entry.getHeader().getExecuteTime() + "(" + format.format(date) + ")";
    }


    /**
     * 记录一下message对象
     */
    private synchronized void dumpMessages(Message message, String startPosition, String endPosition, int total) {
        try {
            MDC.put(HamsterConst.splitFetcherLogFileKey, String.valueOf(canalConfig.getPrototypeKey()));
            FetcherDumpHandler.info(SEP + "****************************************************" + SEP);
            FetcherDumpHandler.info(MessageDumper.dumpMessageInfo(message, startPosition, endPosition, total));
            FetcherDumpHandler.info("****************************************************" + SEP);

            if (dumpDetail) {// 判断一下是否需要打印详细信息
                dumpEventDatas(message.getDatas());
                FetcherDumpHandler.info("****************************************************" + SEP);
            }
        } finally {
            MDC.remove(HamsterConst.splitFetcherLogFileKey);
        }
    }


    /**
     * 分批输出多个数据
     */
    private void dumpEventDatas(List<EventData> eventDatas) {
        int size = eventDatas.size();
        // 开始输出每条记录
        int index = 0;
        do {
            if (index + logSplitSize >= size) {
                logger.info(MessageDumper.dumpEventDatas(eventDatas.subList(index, size)));
            } else {
                logger.info(MessageDumper.dumpEventDatas(eventDatas.subList(index, index + logSplitSize)));
            }
            index += logSplitSize;
        } while (index < size);
    }

    @Override
    public void rollback(Long messageId) {
        canalEmbeddedServer.rollback(clientIdentity,messageId);
        messageIdsQueue.remove(messageId);
    }

    @Override
    public void ack(Long messageId){
        try {
            logger.info("ack messageId = " + messageId);
            waitForStarted();
            Long firstlyMessageId = null;
            if(!messageIdsQueue.contains(messageId)) {
                try{
                    ackInternal(messageId);
                }catch (Exception e) {
                    logger.warn("restart task message id not exists => {}", ExceptionUtils.getFullStackTrace(e));
                }
            }else {
                do{
                    if(firstlyMessageId != null){
                        if((System.currentTimeMillis()/1000) % 5 == 0) {
                            logger.warn("ack not in order, current is [{}], waiting for [{}]", messageId, firstlyMessageId);
                        }
                        Thread.sleep(1000L);
                    }
                    firstlyMessageId = messageIdsQueue.peek();
                }while (messageId > firstlyMessageId);
                //这里选择的是大于，如果小于可以直接进行回复，在异常回复后，messageIdsQueue只会有新的messageId
                ackInternal(messageId);
            }
        } catch (InterruptedException e) {
            logger.error("ack error : => {}", ExceptionUtils.getFullStackTrace(e));
        } catch (Exception e ) {
            logger.error("ack error : => {}", ExceptionUtils.getFullStackTrace(e));
            throw e;
        }

    }


    private void ackInternal(Long messageId) {
//        CanalInstance canalInstance = canalEmbeddedServer.getCanalInstances().get(clientIdentity.getDestination());
//        PositionRange positionRanges = canalInstance.getMetaManager().getBatch(clientIdentity, messageId); // 获取位置

        canalEmbeddedServer.ack(clientIdentity, messageId);
        messageIdsQueue.remove(messageId);
//
//        if (positionRanges != null) { // 说明是重复的ack/rollback
//            Position position = positionRanges.getEnd();
//            ackLogPositionManager.persistLogPosition(canalConfig.getDestination(), (LogPosition)position);
//        }

    }

    @Override
    public void rollback() {
        canalEmbeddedServer.rollback(clientIdentity);
        messageIdsQueue.clear();
    }

    public Long getLastFetchTimeStamp() {
        return heartBreak.getFetchTimeStamp();
    }

    public CanalConfig getCanalConfig() {
        return canalConfig;
    }

    public void setCanalConfig(CanalConfig canalConfig) {
        this.canalConfig = canalConfig;
    }

    private synchronized ZkClientx getZkclientx() {
        ArrayList zkClusters = new ArrayList(canalConfig.getCanalParameter().getZkClusters());
        Collections.sort(zkClusters);
        return ZkClientx.getZkClient(StringUtils.join(zkClusters, ";"));
    }

    public String getSelectorMonitorPath() {
        return selectorMonitorPath;
    }

    public void setSelectorMonitorPath(String selectorMonitorPath) {
        this.selectorMonitorPath = selectorMonitorPath;
    }
}
