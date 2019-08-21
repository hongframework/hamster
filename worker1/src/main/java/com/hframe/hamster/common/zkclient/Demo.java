package com.hframe.hamster.common.zkclient;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangquanhong on 2016/9/26.
 */
public class Demo {

    private static final int DEFAULT_POOL_SIZE = 10;

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(DEFAULT_POOL_SIZE, new NamedThreadFactory("test-thread"), new ThreadPoolExecutor.CallerRunsPolicy());

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("do you business !");
            }
        },3*1000,3*1000, TimeUnit.MILLISECONDS);
    }
}
