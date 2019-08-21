package com.hframe.ext.bean;

/**
 * Created by zhangquanhong on 2017/3/15.
 */
public class StatisticsConfig {

    private long configId;

    private String statisticsView;

    private String statisticsValue;

    private String statisticsScript;

    private Long cfgStatisticsTopicId;

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public String getStatisticsView() {
        return statisticsView;
    }

    public void setStatisticsView(String statisticsView) {
        this.statisticsView = statisticsView;
    }

    public String getStatisticsValue() {
        return statisticsValue;
    }

    public void setStatisticsValue(String statisticsValue) {
        this.statisticsValue = statisticsValue;
    }

    public String getStatisticsScript() {
        return statisticsScript;
    }

    public void setStatisticsScript(String statisticsScript) {
        this.statisticsScript = statisticsScript;
    }

    public Long getCfgStatisticsTopicId() {
        return cfgStatisticsTopicId;
    }

    public void setCfgStatisticsTopicId(Long cfgStatisticsTopicId) {
        this.cfgStatisticsTopicId = cfgStatisticsTopicId;
    }
}
