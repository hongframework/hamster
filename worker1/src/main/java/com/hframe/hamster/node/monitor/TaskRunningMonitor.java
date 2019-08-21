package com.hframe.hamster.node.monitor;

import com.hframe.hamster.node.HamsterConfig;
import com.hframe.hamster.node.cannal.CanalServer;
import com.hframe.hamster.node.cannal.CanalServerFactory;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframework.common.client.http.HttpClientUtils;
import com.hframework.common.util.math.FibonacciUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangquanhong on 2016/11/8.
 */
public class TaskRunningMonitor  extends PrototypeMonitor implements Monitor{

    private static final Logger logger = LoggerFactory.getLogger(TaskRunningMonitor.class);

    private static final long DEFAULT_DELAY_TIME = Long.parseLong(HamsterConfig.getInstance().getHamsterTaskProcessingThreshold());
    private ScheduledExecutorService blockMonitorScheduler = Executors.newScheduledThreadPool(1);

    private TaskProcessMonitor taskProcessMonitor;

    private Map<Long, Date> runningProcess ;

    private CanalServer canalServer = null;

    public TaskRunningMonitor(String taskStory, PrototypeKey prototypeKey) {
        super(taskStory, prototypeKey);
    }

    @Override
    public void startInternal() {
        blockMonitorScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                check();
            }
        }, DEFAULT_DELAY_TIME, DEFAULT_DELAY_TIME, TimeUnit.MINUTES);
    }

    private void check() {
        //获取当前正执行的process信息
        Map<Long, List<String>> processCache = taskProcessMonitor.getProcessCache();
        if(runningProcess != null) {

            //删除已经执行完成的process
            Iterator<Map.Entry<Long, Date>> runningProcessIterator = runningProcess.entrySet().iterator();
            while (runningProcessIterator.hasNext()) {
                Map.Entry<Long, Date> next = runningProcessIterator.next();
                if(!processCache.containsKey(next.getKey())) runningProcessIterator.remove();
            }

            //对于超时的process进行报警
            for (Long processId : runningProcess.keySet()) {
                long times = (new Date().getTime() - runningProcess.get(processId).getTime())/(1000*60);
                if(processCache.containsKey(processId)) {
                    logger.warn("[{}]: process={}, stages={} deal delay about {} minutes !",
                            Thread.currentThread().getName(), processId, processCache.get(processId), times);
                }
            }
        }

        if(runningProcess == null)  runningProcess = new HashMap<>();

        //添加本次正处理的process信息
        for (Map.Entry<Long, List<String>> processStage : processCache.entrySet()) {
            Long process = processStage.getKey();
            List<String> stage = processStage.getValue();
            if(!runningProcess.containsKey(process) && stage != null && stage.size() > 0) {
                runningProcess.put(process, new Date());
            }
        }

        canalServer = CanalServerFactory.get(new FlowKey() {
            @Override
            public String value() {
                return taskStory;
            }

            //必须重写equals、hashCode方法，否者会makeComputingMap会生产重复的key
            @Override
            public boolean equals(Object obj) {
                if (obj instanceof FlowKey) {
                    FlowKey target = (FlowKey) obj;
                    return this.value().equals(target.value());
                }
                return false;
            }

            @Override
            public int hashCode() {
                return value().hashCode();
            }
        }, prototypeKey);

        if(canalServer != null && canalServer.isStart() && canalServer.getLastFetchTimeStamp() != null) {
            final String canalName = (canalServer.getCanalConfig().getFilter() + "_" + canalServer.getCanalConfig().getClientId()).replaceAll("\\*", "");
            final long delayMinutes = (System.currentTimeMillis() - canalServer.getLastFetchTimeStamp()) / 1000 / 60;
            if(delayMinutes > DEFAULT_DELAY_TIME) {
                if(FibonacciUtils.isMatch(delayMinutes/DEFAULT_DELAY_TIME)) {
                    try{
                        logger.warn("[{}]: block about {} minutes !",
                                Thread.currentThread().getName(), delayMinutes);
                        HttpClientUtils.post(HamsterConfig.getInstance().getItilAlarmPushUrl(), new ArrayList<BasicNameValuePair>() {{
                            add(new BasicNameValuePair("type", "hamster"));
                            add(new BasicNameValuePair("title", "hamster阻塞：" + canalName));
                            add(new BasicNameValuePair("content", "阻塞时间为" + delayMinutes + "分钟"));
                        }});
                    }catch (Throwable throwable) {
                        logger.error("itil alarm push error => {}", ExceptionUtils.getFullStackTrace(throwable));
                    }
                }
            }
        }
    }

    @Override
    public void destroyInternal() {
        blockMonitorScheduler.shutdownNow();
    }

    @Override
    public void reload() {

    }

    public TaskProcessMonitor getTaskProcessMonitor() {
        return taskProcessMonitor;
    }

    public void setTaskProcessMonitor(TaskProcessMonitor taskProcessMonitor) {
        this.taskProcessMonitor = taskProcessMonitor;
    }
}
