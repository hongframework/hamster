package com.hframe.hamster.node.task;

import com.google.common.collect.Lists;
import com.hframe.ext.bean.StatisticsConfig;
import com.hframe.ext.bean.StatisticsData;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.cannal.bean.EventColumn;
import com.hframe.hamster.node.cannal.bean.EventData;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.common.AbstractPrototypeFlowNodeTask;
import com.hframe.hamster.node.task.common.FlowNodeTask;
import com.hframe.hamster.node.task.common.TaskData;
import com.hframework.common.dyncompile.FrameworkClassLoader;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.DateUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.common.util.collect.bean.Mapper;
import com.hframework.datacenter.dynscript.Script;
import com.hframework.datacenter.dynscript.ScriptGenerator;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class AbstractStatisticsTask extends AbstractPrototypeFlowNodeTask implements FlowNodeTask {
    private static final Logger logger = LoggerFactory.getLogger(AbstractStatisticsTask.class);

    private Map<Long, StatisticsConfig> statisticsConfigMap;


    public AbstractStatisticsTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
        try{
            statisticsConfigMap = CollectionUtils.convert(getStatisticsConfigs(flowKey, prototypeKey), new Mapper<Long, StatisticsConfig>() {
                @Override
                public Long getKey(StatisticsConfig statisticsConfig) {
                    return statisticsConfig.getConfigId();
                }
            });

        } catch (Exception e) {
            logger.error("statistics task init error ! {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    protected abstract List<StatisticsConfig> getStatisticsConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception;

    @Override
    public void process(TaskData taskData) throws InterruptedException {

        if(!isEmpty(taskData)) {
            Message message = (Message) taskData.getMessage();
            List<EventData> datas = message.getDatas();
            Map<String, StatisticsData> statisticsDataMap =new HashMap<>();
            for (EventData eventData : datas) {
                long configId = eventData.getPairId();
                StatisticsConfig statisticsConfig = statisticsConfigMap.get(configId);
                Map<String, Object> columnValueMap = getColumnValueMap(eventData.getKeys(), eventData.getColumns());
                String statisticsView = getStatisticsViewString(columnValueMap, statisticsConfig.getStatisticsView());
                String statisticsTime = DateUtils.getDate(new Date(eventData.getExecuteTime()), "yyyyMMddHHmmss");
                String cacheKey = statisticsConfig.getCfgStatisticsTopicId() + "_" + statisticsView + "_" + statisticsTime;
                if(!statisticsDataMap.containsKey(cacheKey)) {
                    StatisticsData statisticsData = new StatisticsData();
                    statisticsData.setTopicId(statisticsConfig.getCfgStatisticsTopicId());
                    statisticsData.setStatisticsView(statisticsView);
                    statisticsData.setStatisticsTime(statisticsTime);
                    statisticsData.setStatisticsValue("0");
                    statisticsData.setExecuteTime(String.valueOf(System.currentTimeMillis()));
                    statisticsDataMap.put(cacheKey, statisticsData);
                }
                StatisticsData statisticsData = statisticsDataMap.get(cacheKey);
                Double statisticsResult = getStatisticsResult(columnValueMap, statisticsConfig.getConfigId(),
                        statisticsConfig.getStatisticsValue(), statisticsConfig.getStatisticsScript());
                statisticsData.setStatisticsValue(BigDecimal.valueOf(statisticsResult).add(
                        new BigDecimal(statisticsData.getStatisticsValue())).toPlainString());
                statisticsData.addLogItem(eventData.getBinLogPostion());
            }
            message.setStatisticsDatas(Lists.newArrayList(statisticsDataMap.values()));
        }
    }

    protected  Map<String, Object> getColumnValueMap(List<EventColumn> keys, List<EventColumn> columns) {
        Map<String, Object> properties = new HashMap<>();
        for (EventColumn eventColumn : keys) {
            properties.put(eventColumn.getColumnName(), eventColumn.getColumnValue());
        }

        for (EventColumn eventColumn : columns) {
            properties.put(eventColumn.getColumnName(), eventColumn.getColumnValue());
        }
        return properties;
    }

    protected  String getStatisticsViewString(Map<String, Object> properties, String statisticsView){

        //����statistics view�ַ���
        String statisticsViewValue = "";
        if(StringUtils.isNotBlank(statisticsView) && StringUtils.isNotBlank(statisticsView.replaceAll(",", ""))) {
            String[] statisticsViewItems = statisticsView.split(",");
            for (String statisticsViewItem : statisticsViewItems) {
                if(StringUtils.isNotBlank(statisticsViewItem)) {
                    if(!properties.containsKey(statisticsViewItem)){
                        throw new RuntimeException("column '" + statisticsViewItem + "' not exists , statistics failure !");
                    }
                    String tempValue = String.valueOf(properties.get(statisticsViewItem));
                    if(StringUtils.isNotBlank(statisticsViewValue)) {
                        statisticsViewValue += "_";
                    }
                    statisticsViewValue += tempValue;
                }
            }
        }

        return statisticsViewValue;

    }


    protected  Double getStatisticsResult(Map<String, Object> properties, Long configId,
                                                 String statisticsValue, String statisticsScript){

        if(StringUtils.isNotBlank(statisticsValue)) {
            if("COUNT(*)".equals(statisticsValue)) {
                return 1d;
            }
            if(!properties.containsKey(statisticsValue)){
                throw new RuntimeException("column '" + statisticsValue + "' not exists , statistics failure !");
            }
            String tempValue = String.valueOf(properties.get(statisticsValue));
            return Double.parseDouble(tempValue);
        }else if(StringUtils.isNotBlank(statisticsScript)){
            try{
                String dynScriptClassName = "StatisticsScript" + configId;
                Class<?> scriptService = new FrameworkClassLoader().compileClass(
                        "com.hframe.dynscript." + dynScriptClassName,
                        ScriptGenerator.produceScriptBody(dynScriptClassName, statisticsScript)
                );

                Script script = (Script)scriptService.newInstance();
                HamsterContextInitializer.autowire(script);
                return  script.execute(properties);
            }catch (Exception e) {
                logger.error("statistics execute script error => {}", ExceptionUtils.getFullStackTrace(e));
                throw new RuntimeException(e);
            }

        }else {
            throw new RuntimeException("both statistic column and script are empty, statistics failure !");
        }
    }

}

