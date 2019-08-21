package com.hframe.ext.bean;

import com.google.common.collect.Lists;
import com.hframework.common.util.StringUtils;

import java.util.*;

/**
 * Created by zhangquanhong on 2016/10/21.
 */
public class StatisticsData {


    private Long topicId;
    //统计视图
    private String statisticsView;
    //统计时间
    private String statisticsTime;
    //统计值
    private String statisticsValue;
    //日志开始位置
    private String logStart;
    //日志结束位置
    private String logEnd;
    //执行时间
    private String executeTime;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getStatisticsView() {
        return statisticsView;
    }

    public void setStatisticsView(String statisticsView) {
        this.statisticsView = statisticsView;
    }

    public String getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(String statisticsTime) {
        this.statisticsTime = statisticsTime;
    }

    public String getStatisticsValue() {
        return statisticsValue;
    }

    public void setStatisticsValue(String statisticsValue) {
        this.statisticsValue = statisticsValue;
    }

    public String getLogStart() {
        return logStart;
    }

    public void setLogStart(String logStart) {
        this.logStart = logStart;
    }

    public String getLogEnd() {
        return logEnd;
    }

    public void setLogEnd(String logEnd) {
        this.logEnd = logEnd;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public void addLogItem(String logPosition) {
        Set<String> logPositions = new HashSet<>();
        logPositions.add(logPosition);
        if(StringUtils.isNotBlank(logStart)) {
            logPositions.add(logStart);
        }

        if(StringUtils.isNotBlank(logEnd)) {
            logPositions.add(logEnd);
        }

        if(logPositions.size() == 1) {
            logStart = logPosition;
            return ;
        }

        List<String> list = new ArrayList<>(logPositions);
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String binlogStart, String binlogEnd) {
                String startBinlogFileName = binlogStart.substring(0, binlogStart.indexOf("|"));
                Long startBinlogOffset = Long.parseLong(binlogStart.substring(binlogStart.indexOf("|") + 1));

                String endBinlogFileName = binlogEnd.substring(0, binlogEnd.indexOf("|"));
                Long endBinlogOffset = Long.parseLong(binlogEnd.substring(binlogEnd.indexOf("|") + 1));
                int fileResult = startBinlogFileName.compareTo(endBinlogFileName);
                return fileResult != 0 ?  fileResult : startBinlogOffset-endBinlogOffset > 0 ? 1 : -1;
            }
        });
        logStart = list.get(0);
        logEnd = list.get(list.size()-1);
    }

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("binlog_12_12|123456", "binlog_12_12|133456", "binlog_13_12|133456");
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String binlogStart, String binlogEnd) {
                String startBinlogFileName = binlogStart.substring(0, binlogStart.indexOf("|"));
                Long startBinlogOffset = Long.parseLong(binlogStart.substring(binlogStart.indexOf("|") + 1));

                String endBinlogFileName = binlogEnd.substring(0, binlogEnd.indexOf("|"));
                Long endBinlogOffset = Long.parseLong(binlogEnd.substring(binlogEnd.indexOf("|") + 1));
                int fileResult = startBinlogFileName.compareTo(endBinlogFileName);
                return fileResult != 0 ?  fileResult : startBinlogOffset-endBinlogOffset > 0 ? 1 : -1;
            }
        });
        for (String s : list) {
            System.out.println(s);
        }
    }
}
