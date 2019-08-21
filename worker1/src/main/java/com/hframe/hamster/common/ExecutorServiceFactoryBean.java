package com.hframe.hamster.common;

import com.alibaba.otter.canal.common.utils.NamedThreadFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * 统一任务调度线程池
 * Created by zhangquanhong on 2016/9/28.
 */
@Service("executorService")
public class ExecutorServiceFactoryBean implements FactoryBean, InitializingBean, DisposableBean {
    private static final Integer DEFAULT_POOL_SIZE = 100;
    private static final Integer DEFAULT_ACCEPT_SIZE = 100;
    private Integer poolSize = DEFAULT_POOL_SIZE;
    private Integer acceptSize = DEFAULT_ACCEPT_SIZE;
    private String name = "hamster-invoke-thread";
    private int keepAliveTime = 60;

    private ExecutorService executorService;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(executorService == null) {
            executorService = new ThreadPoolExecutor(poolSize,poolSize,keepAliveTime, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(acceptSize),
                    new NamedThreadFactory(name),
                    new ThreadPoolExecutor.CallerRunsPolicy());
        }
    }

    @Override
    public void destroy() throws Exception {
       if(executorService != null) {
           executorService.shutdown();
       }
    }

    @Override
    public Object getObject() throws Exception {
        return executorService;
    }

    @Override
    public Class<?> getObjectType() {
        return ThreadPoolExecutor.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    public Integer getAcceptSize() {
        return acceptSize;
    }

    public void setAcceptSize(Integer acceptSize) {
        this.acceptSize = acceptSize;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
