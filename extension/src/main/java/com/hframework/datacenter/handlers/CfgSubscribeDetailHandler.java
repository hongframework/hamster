package com.hframework.datacenter.handlers;


import com.hframework.beans.exceptions.BusinessException;
import com.hframework.common.util.RegexUtils;
import com.hframework.common.util.SpelExpressionUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.web.extension.AbstractBusinessHandler;
import com.hframework.web.extension.annotation.BeforeCreateHandler;
import com.hframework.web.extension.annotation.BeforeUpdateHandler;
import com.hframework.datacenter.utils.DBUtils;
import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import com.hframework.hamster.cfg.domain.model.CfgSubscribe;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail;
import com.hframework.hamster.cfg.service.interfaces.ICfgDatasourceSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgSubscribeSV;
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
public class CfgSubscribeDetailHandler extends AbstractBusinessHandler<CfgSubscribeDetail> {

    private static final Logger logger = LoggerFactory.getLogger(CfgSubscribeDetailHandler.class);

    @Resource
    private ICfgDatasourceSV cfgDatasourceSV;

    @Resource
    private ICfgSubscribeSV cfgSubscribeSV;


    @BeforeCreateHandler
    @BeforeUpdateHandler
    public boolean check(CfgSubscribeDetail cfgSubscribeDetail) throws Exception {

        CfgSubscribe cfgSubscribe = cfgSubscribeSV.getCfgSubscribeByPK(cfgSubscribeDetail.getCfgSubscribeId());

        String expression = cfgSubscribeDetail.getDataFilterExpression();
        if (StringUtils.isNotBlank(expression) && cfgSubscribe.getCfgDatasourceId() > 0) {
            String[] vars = RegexUtils.find(expression.replaceAll("'[^']*'", ""), "[a-zA-Z]+[a-zA-Z0-9._]*");

            CfgDatasource cfgDatasource = null;
            List<Map> tables = null;
            try {
                cfgDatasource = cfgDatasourceSV.getCfgDatasourceByPK(cfgSubscribe.getCfgDatasourceId());

                String dbObjectName = cfgSubscribeDetail.getDbObjectName();
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
