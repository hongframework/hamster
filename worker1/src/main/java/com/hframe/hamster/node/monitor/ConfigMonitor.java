package com.hframe.hamster.node.monitor;

import com.hframework.common.util.message.JsonUtils;
import net.sf.ehcache.util.NamedThreadFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangquanhong on 2016/9/26.
 */
public abstract class ConfigMonitor<T> implements Monitor{
    private static final Logger logger = LoggerFactory.getLogger(ConfigMonitor.class);

    private ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("config-monitor-thread"), new ThreadPoolExecutor.CallerRunsPolicy());

    protected boolean running = false;

    private long refreshSeconds;

    private T object;

    private String objectString;

    public ConfigMonitor(long refreshSeconds) {
        this.refreshSeconds = refreshSeconds;
    }

    @Override
    public void reload() {
        try {
            T newObject = fetch();
            if(object == null || isDiff(newObject)) {
                object = newObject;
            }
        } catch (Exception e) {
            logger.error("config fetch error ! {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    private boolean isDiff(T newObject) throws IOException {
        String newObjectJson = JsonUtils.writeValueAsString(newObject);
        if(!objectString.equals(newObjectJson)) {
            logger.info("config is change ,old config is [{}], new config is [{}]", objectString, newObjectJson);
            objectString = newObjectJson;
            return true;
        }
        return false;
    }

    public abstract T fetch() throws Exception;

    @Override
    public void start() throws Exception {
        if(!running) {
            synchronized (this) {
                if(!running) {
                    running = true;
                    startInternal();
                }
            }
        }
    };

    public ConfigMonitor ok() throws Exception {
        start();
        return this;
    }

    public  void startInternal() throws Exception {
        logger.debug("start internal...");
        fetch();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.debug(" reload config begin !");
                reload();
                logger.debug(" reload config finish !");
            }
        }, refreshSeconds, refreshSeconds, TimeUnit.SECONDS);
        logger.info("start internal success!");
    }

    @Override
    public void destroy() {
        destroyInternal();
        running = false;
    }

    public void destroyInternal(){
        logger.info("destroy internal...");
        logger.info("destroy internal success!");
    }

    public T getObject() {
        return object;
    }
}
