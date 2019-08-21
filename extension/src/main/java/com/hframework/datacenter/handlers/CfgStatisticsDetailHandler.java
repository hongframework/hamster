package com.hframework.datacenter.handlers;



import com.hframework.beans.exceptions.BusinessException;
import com.hframework.common.dyncompile.FrameworkClassLoader;
import com.hframework.common.frame.ServiceFactory;
import com.hframework.common.util.RegexUtils;
import com.hframework.common.util.SpelExpressionUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.web.extension.AbstractBusinessHandler;
import com.hframework.web.extension.annotation.BeforeCreateHandler;
import com.hframework.web.extension.annotation.BeforeUpdateHandler;
import com.hframework.datacenter.dynscript.Script;
import com.hframework.datacenter.dynscript.ScriptGenerator;
import com.hframework.datacenter.utils.DBUtils;
import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import com.hframework.hamster.cfg.domain.model.CfgStatistics;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsDetail;
import com.hframework.hamster.cfg.service.interfaces.ICfgDatasourceSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgStatisticsSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangquanhong on 2016/9/23.
 */
@Service
public class CfgStatisticsDetailHandler extends AbstractBusinessHandler<CfgStatisticsDetail> {

    private static final Logger logger = LoggerFactory.getLogger(CfgStatisticsDetailHandler.class);

    @Resource
    private ICfgStatisticsSV cfgStatisticsSV;

    @Resource
    private ICfgDatasourceSV cfgDatasourceSV;

    @BeforeCreateHandler
    @BeforeUpdateHandler
    public boolean checkDataExtractExpression(CfgStatisticsDetail cfgStatisticsDetail) throws Exception {

        CfgStatistics cfgStatistics = cfgStatisticsSV.getCfgStatisticsByPK(cfgStatisticsDetail.getCfgStatisticsId());

        String expression = cfgStatisticsDetail.getDataFilterExpression();
        if (StringUtils.isNotBlank(expression) && cfgStatistics.getCfgDatasourceId() > 0) {
            String[] vars = RegexUtils.find(expression.replaceAll("'[^']*'", ""), "[a-zA-Z]+[a-zA-Z0-9._]*");

            CfgDatasource cfgDatasource = null;
            List<Map> tables = null;
            try {
                cfgDatasource = cfgDatasourceSV.getCfgDatasourceByPK(cfgStatistics.getCfgDatasourceId());

                String dbObjectName = cfgStatisticsDetail.getDbObjectName();
                if(dbObjectName.endsWith("_.*")) {//表明为分表
                    List tabless = DBUtils.query(cfgDatasource, "show tables");
                    for (Object table : tabless) {
                        String tableName = (String) ((Map) table).values().iterator().next();
                        String tableNameTarget = getSplitTableName(tableName);
                        if(StringUtils.isNotBlank(tableNameTarget) && dbObjectName.equals(tableNameTarget)) {
                            dbObjectName = tableName;
                            break;
                        }
                    }
                }
                tables = DBUtils.query(cfgDatasource, "show columns from " + dbObjectName);
            } catch (Exception e) {
                throw new BusinessException("数据源查询失败，请检查！");
            }
            if (cfgDatasource == null) {
                throw new BusinessException("数据源不存在，请检查！");
            }

            Map<String, Object> testData = new HashMap<>();
            if(vars != null) {
                flag :for (String var : vars) {
                    String fieldName = var;
                    if(var.startsWith("orig.")) {
                        fieldName = var.substring(5);
                    }

                    for (Map table : tables) {
                        if(fieldName.equals(table.get("field"))) {
                            testData.put(var, getTestValueByType((String) table.get("type")));
                            continue flag;
                        }
                    }
                    throw new BusinessException("数据过滤表达式中字段【" + fieldName + "】不存在，请检查！");
                }
            }
            try{
                SpelExpressionUtils.check(expression, testData);
            }catch (Exception e) {
                throw new BusinessException("数据过滤表达式不能计算出结果，请检查(" + e.getMessage() + ")！");
            }
        }

        return true;
    }

    @BeforeCreateHandler
    @BeforeUpdateHandler
    public boolean createStatisticsExpression(CfgStatisticsDetail cfgStatisticsDetail) throws Exception {
        if(StringUtils.isNotBlank(cfgStatisticsDetail.getStatisticsScript())) {
            try{
                String scriptContent = cfgStatisticsDetail.getStatisticsScript();
                String dynScriptClassName = "StatisticsScript" + cfgStatisticsDetail.getCfgStatisticsDetailId();
                Class<?> scriptService = new FrameworkClassLoader().compileClass(
                        "com.hframe.dynscript." + dynScriptClassName,
                         ScriptGenerator.produceScriptBodyForTest(dynScriptClassName, scriptContent)
                        );

                Script script = (Script)scriptService.newInstance();
                ServiceFactory.getWebAppContext().getAutowireCapableBeanFactory().autowireBean(script);
                Double execute = script.execute(new HashMap<String, Object>());
                System.out.println(execute);
            }catch (Exception e) {
                e.printStackTrace();
                if(e instanceof BusinessException) {
                    throw  e;
                }
                throw new BusinessException(e.getMessage());
            }

        }
        return true;
    }

    private String getSplitTableName(String tableName) {
        String[] strings = RegexUtils.find(tableName, "_[0-9]+");
        if(strings != null && strings.length > 0) {
            String endChars = strings[strings.length-1];
            tableName = tableName.substring(0, tableName.length() - endChars.length()) + "_.*";
            return tableName;
        }
        return null;
    }

    private Object getTestValueByType(String type) {
        if(type.toLowerCase().startsWith("bigint")
                || type.toLowerCase().startsWith("smallint")
                || type.toLowerCase().startsWith("tinyint")
                || type.toLowerCase().startsWith("int")) {
            return 5;
        }else if(type.toLowerCase().startsWith("datetime")) {
            return "2016-10-21 12:12:12";
        }else if(type.toLowerCase().startsWith("date")) {
            return "2016-10-21";
        }else if(type.toLowerCase().startsWith("varchar")
                || type.toLowerCase().startsWith("char")) {
            return "hello world";
        }
        return "unkown";
    }



}
