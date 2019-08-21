package com.hframe.hamster.node.monitor;

import com.google.common.collect.Lists;
import com.hframe.hamster.node.HamsterConst;
import com.hframe.hamster.node.arbitrate.ArbitratorFactory;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.ZkContextAware;
import com.hframe.hamster.node.monitor.listener.TerminListener;
import com.hframe.hamster.node.zk.ZKPathUtils;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import org.I0Itec.zkclient.IZkChildListener;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 结束信号监控
 * Created by zhangquanhong on 2016/9/27.
 */
public class TerminMonitor extends PrototypeMonitor implements Monitor, ZkContextAware<String> {

    private static final Logger logger = LoggerFactory.getLogger(TerminMonitor.class);

    private List<Long> terminCache = new ArrayList<>();

    /**执行listener监听程序*/
    private ExecutorService executorService ;

    private List<TerminListener> listeners = Collections.synchronizedList(new ArrayList<TerminListener>());

    private IZkChildListener childChangeListener = new IZkChildListener() {
        @Override
        public void handleChildChange(String parentPath, List<String> currentProcesses) throws Exception {
            reloadTermin(currentProcesses);
        }
    };

    public TerminMonitor(String taskStory, PrototypeKey prototypeKey) {
        super(taskStory, prototypeKey);
    }


    @Override
    public void startInternal() {
        List<String> currentProcesses = zkClientx.subscribeChildChanges(getZkPath(), childChangeListener);
        reloadTermin(currentProcesses);
    }

    @Override
    public void destroyInternal() {
        zkClientx.unsubscribeChildChanges(getZkPath(), childChangeListener);
    }

    public void addListener(TerminListener listener) {
        this.listeners.add(listener);
        if(running) {
            listener.processChange(terminCache);
        }
    }

    public void removeListener(TerminListener listener) {
        this.listeners.add(listener);
    }

    private void reloadTermin(List<String> currentProcesses) {
        if(currentProcesses == null) currentProcesses = new ArrayList();
        try {
            MDC.put(HamsterConst.splitTaskLogFileKey, taskStory + "_" + prototypeKey.value());
            List<Long> processIds = CollectionUtils.fetch(currentProcesses, new Fetcher<String, Long>() {
                @Override
                public Long fetch(String s) {
                    return Long.valueOf(s);
                }
            });
            Collections.sort(processIds);
            List<Long> newProcessIds = Lists.newArrayList(processIds);
            synchronized (this) {
                newProcessIds.removeAll(terminCache);
                List<Long> deletedProcessIds = Lists.newArrayList(terminCache);
                deletedProcessIds.removeAll(processIds);

                terminCache.removeAll(deletedProcessIds);
                terminCache.addAll(newProcessIds);
            }
            for (Long newProcessId : newProcessIds) {
                logger.info("do termin for process [{}], current thread is [{}]{} !", newProcessId, Thread.currentThread().getId(), Thread.currentThread().getName());
                MainStemMonitor mainStemMonitor = ArbitratorFactory.getInstance(taskStory, prototypeKey, Object.class, MainStemMonitor.class);
                if(mainStemMonitor.isPermit()){
                    doTermin(newProcessId);
                }
            }
            fireTerminChange(terminCache);
        }finally {
            MDC.remove(HamsterConst.splitTaskLogFileKey);
        }
    }

    private void doTermin(final Long processId) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    MDC.put(HamsterConst.splitTaskLogFileKey, taskStory + "_" + prototypeKey.value());
                    logger.info("delete zk process cascade begin,{}", processId);
                    String processPath = ZKPathUtils.getProcessPath(taskStory, prototypeKey.value(), processId);
                    zkClientx.deleteRecursive(processPath);
                    zkClientx.delete(getZkPath(processId));
                    logger.info("delete zk process cascade finish,{}", processId);
                } catch (Exception e) {
                    logger.error("do termin error => {}", ExceptionUtils.getFullStackTrace(e));
                } finally {
                    MDC.remove(HamsterConst.splitTaskLogFileKey);
                }
            }
        });

    }

    private void fireTerminChange(final List<Long> processIds) {
        for (final TerminListener listener : listeners) {
            listener.processChange(processIds);
        }
    }

    @Override
    public void reload() {

    }

    @Override
    public String getZkPath(Object... processId) {
        if(processId != null && processId.length > 0) {
            return ZKPathUtils.getTerminPath(taskStory, prototypeKey.value(), (Long) processId[0]);
        }else {
            return ZKPathUtils.getTerminRootPath(taskStory, prototypeKey.value());
        }
    }

    @Override
    public String getZkData() {
        return null;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
