package com.hframe.hamster.node;

import com.hframework.common.resource.ResourceWrapper;
import com.hframework.common.resource.annotation.Key;
import com.hframework.common.resource.annotation.Source;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by zhangquanhong on 2016/11/4.
 */
@Source("hamster.properties")
public class HamsterConfig {

    @Key( "hamster.env")
    private String hamsterEnv;

    @Key( "hamster.node.zkServers")
    private String zkServers;

    @Key( "hamster.node.zk.sessionTimeout")
    private String zkSessionTimeout;

    @Key( "hamster.node.task.refresh.period")
    private String taskRefreshPeriod;

    @Key( "hamster.task.processing.threshold")
    private String hamsterTaskProcessingThreshold;

    @Key( "hamster.redis.processing.data.expired.minutes")
    private String hamsterRedisProcessingDataExpiredMinutes;

    @Key( "hamster.zk.children.change.deal.delay.ms")
    private String hamsterZkChildrenChangeDealDelayMs;

    @Key( "itil.alarm.push.url")
    private String itilAlarmPushUrl;

    public String getZkServers() {
        return zkServers;
    }

    public void setZkServers(String zkServers) {
        this.zkServers = zkServers;
    }

    public String getZkSessionTimeout() {
        return zkSessionTimeout;
    }

    public void setZkSessionTimeout(String zkSessionTimeout) {
        this.zkSessionTimeout = zkSessionTimeout;
    }

    public String getTaskRefreshPeriod() {
        return taskRefreshPeriod;
    }

    public void setTaskRefreshPeriod(String taskRefreshPeriod) {
        this.taskRefreshPeriod = taskRefreshPeriod;
    }

    public String getHamsterTaskProcessingThreshold() {
        return hamsterTaskProcessingThreshold;
    }

    public void setHamsterTaskProcessingThreshold(String hamsterTaskProcessingThreshold) {
        this.hamsterTaskProcessingThreshold = hamsterTaskProcessingThreshold;
    }

    public String getHamsterRedisProcessingDataExpiredMinutes() {
        return hamsterRedisProcessingDataExpiredMinutes;
    }

    public void setHamsterRedisProcessingDataExpiredMinutes(String hamsterRedisProcessingDataExpiredMinutes) {
        this.hamsterRedisProcessingDataExpiredMinutes = hamsterRedisProcessingDataExpiredMinutes;
    }

    public String getHamsterZkChildrenChangeDealDelayMs() {
        return hamsterZkChildrenChangeDealDelayMs;
    }

    public void setHamsterZkChildrenChangeDealDelayMs(String hamsterZkChildrenChangeDealDelayMs) {
        this.hamsterZkChildrenChangeDealDelayMs = hamsterZkChildrenChangeDealDelayMs;
    }

    private static HamsterConfig instance;

    private HamsterConfig() {
        super();
    }

    public  static HamsterConfig getInstance(){
        if(instance == null) {
            synchronized (HamsterConfig.class) {
                if(instance == null) {
                    try {
                        return instance = ResourceWrapper.getResourceBean(HamsterConfig.class);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    return instance = new HamsterConfig();
                }
            }
        }
        return instance;
    }

    public String getItilAlarmPushUrl() {
        return itilAlarmPushUrl;
    }

    public void setItilAlarmPushUrl(String itilAlarmPushUrl) {
        this.itilAlarmPushUrl = itilAlarmPushUrl;
    }

    public String getHamsterEnv() {
        return hamsterEnv;
    }

    public void setHamsterEnv(String hamsterEnv) {
        this.hamsterEnv = hamsterEnv;
    }
}
