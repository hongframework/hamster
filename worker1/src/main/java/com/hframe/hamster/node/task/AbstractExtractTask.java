package com.hframe.hamster.node.task;

import com.alibaba.otter.canal.filter.aviater.AviaterRegexFilter;
import com.hframe.ext.bean.ExtractConfig;
import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.cannal.bean.EventColumn;
import com.hframe.hamster.node.cannal.bean.EventData;
import com.hframe.hamster.node.cannal.bean.EventType;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.common.AbstractPrototypeFlowNodeTask;
import com.hframe.hamster.node.task.common.FlowNodeTask;
import com.hframe.hamster.node.task.common.TaskData;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.RegexUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.common.util.collect.bean.Grouper;
import com.hframework.datacenter.enums.CfgSubscribeDataOperTypeEnum;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.InvocationTargetException;
import java.sql.Types;
import java.util.*;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public abstract class AbstractExtractTask extends AbstractPrototypeFlowNodeTask implements FlowNodeTask {
    private static final Logger logger = LoggerFactory.getLogger(AbstractExtractTask.class);

    private Map<String, List<ExtractConfig>> extractConfigMap;

    public AbstractExtractTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);

        try {
            extractConfigMap = CollectionUtils.group(
                    getExtractConfigs(flowKey, prototypeKey), new Grouper<String, ExtractConfig>() {
                @Override
                public <K> K groupKey(ExtractConfig extractConfig) {
                    return (K) extractConfig.getDbObjectName();
                }
            });

        } catch (Exception e) {
            logger.error("extract task init error ! {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    protected abstract List<ExtractConfig> getExtractConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception;

    @Override
    public void process(TaskData taskData) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(isEmpty(taskData)) return;

        Message message = (Message) taskData.getMessage();
        List<EventData> datas = message.getDatas();
        Iterator<EventData> iterator = datas.iterator();
        List<EventData> extractResult = new ArrayList<>();
        while (iterator.hasNext()) {
            EventData data = iterator.next();
            String tableName = data.getTableName();
            List<ExtractConfig> extractConfigList = extractConfigMap.get(tableName);
            if(extractConfigList == null) {//����Ϊ�ֱ�
                Set<String> tableNames = extractConfigMap.keySet();
                for (String name : tableNames) {
                    if(new AviaterRegexFilter(name).filter(tableName)) {
                        tableName = name;
                        break;
                    }
                }
                extractConfigList = extractConfigMap.get(tableName);
            }

            for (ExtractConfig extractConfig : extractConfigList) {
                if(checkEventTypePass(extractConfig.getDbObjectOperateType(), data.getEventType()) &&
                        checkFilterPass(data, extractConfig.getDataFilterExpression(), extractConfig.getDataFilterVars())){
                    EventData cloneBean = data.clone();
                    cloneBean.setPairId(extractConfig.getConfigId());
                    extractResult.add(cloneBean);
                }
            }
        }
        message.setDatas(extractResult);
    }

    private boolean checkEventTypePass(Byte dbObjectOperateType, EventType eventType) {
        if(dbObjectOperateType == CfgSubscribeDataOperTypeEnum.DML.getValue()) {
            return eventType.isDml();
        } if(dbObjectOperateType == CfgSubscribeDataOperTypeEnum.INSERT_UPDATE.getValue()) {
            return eventType.isInsert() || eventType.isUpdate();
        }else if(dbObjectOperateType == CfgSubscribeDataOperTypeEnum.INSERT.getValue()) {
            return eventType.isInsert();
        }else if(dbObjectOperateType == CfgSubscribeDataOperTypeEnum.UPDATE.getValue()) {
            return eventType.isUpdate();
        }else if(dbObjectOperateType == CfgSubscribeDataOperTypeEnum.DELETE.getValue()) {
            return eventType.isDelete();
        }
        return false;
    }

    private boolean checkFilterPass(EventData data, String filterExp, String[] vars) {
        String resultExp = filterExp;
        try{
            if(StringUtils.isBlank(filterExp)) {
                return true;
            }


            if(vars != null && vars.length > 0) {
                for (String var : vars) {
                    resultExp = resultExp.replaceAll(var,getValValueFromData(var, data));
                }
            }

            ExpressionParser parser = new SpelExpressionParser();
            return parser.parseExpression("#{" + resultExp + "}",
                    new TemplateParserContext()).getValue(Boolean.class);
        }catch (Exception e) {
            logger.error("SPEL execute failed, [filterExp = {}, resultExp = {}]", filterExp, resultExp);
            logger.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }
    }

    private String getValValueFromData(String var, EventData data) {
        List<EventColumn> columns = null;
        String columnName = var;
        if(var.startsWith("orig.")) {
            columns = data.getOldColumns();
            columnName = var.substring(5);
        }else {
            columns = data.getColumns();
        }

        for (EventColumn column : columns) {
            if(column.getColumnName().equals(columnName)) {
                if(column.getColumnType() == Types.CHAR
                        || column.getColumnType() == Types.VARCHAR
                        || column.getColumnType() == Types.LONGVARCHAR) {
                    return "'" + column.getColumnValue() + "'";
                }else {
                    return column.getColumnValue();
                }

            }
        }
        return var;
    }

    public static void main(String[] args) {

        String[] vars = {"orig.abc", "abc"};
        Arrays.sort(vars, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.contains(o2) ? -1 : 1;
            }
        });
        System.out.println(Arrays.toString(vars));
        String origExp = "add_time != orig.add_time";
        String[] strings = RegexUtils.find(origExp, "[a-zA-Z0-9._]+");
        System.out.println(Arrays.toString(strings));

//        String exp = "#{1 != 2 && 3 != 4}";
        String exp = "#{ !(1>2) && '2015-12-12 12:12:21' > '2015-12-12 12:12:12'}";
        ExpressionParser parser = new SpelExpressionParser();
        Boolean value = parser.parseExpression(exp  ,
                new TemplateParserContext()).getValue(Boolean.class);
        System.out.println(value);
    }
}
