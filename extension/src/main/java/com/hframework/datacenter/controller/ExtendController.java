package com.hframework.datacenter.controller;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hframework.base.bean.KVBean;
import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.beans.exceptions.BusinessException;
import com.hframework.common.util.RegexUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import com.hframework.common.util.collect.bean.Mapping;
import com.hframework.common.util.message.JsonUtils;
import com.hframework.smartsql.client.DBClient;
import com.hframework.web.ControllerHelper;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.hframework.datacenter.utils.DBUtils;
import com.hframework.hamster.cfg.domain.model.*;
import com.hframework.hamster.cfg.service.interfaces.ICfgDatasourceSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgDatatableSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgDataviewSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobAttrSV;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * User: zhangqh6
 * Date: 2016/5/11 0:16:16
 */
@Controller
@RequestMapping(value = "/extend")
public class ExtendController {
    private static final Logger logger = LoggerFactory.getLogger(ExtendController.class);

    @Resource
    private ICfgDatasourceSV cfgDatasourceSV;

    @Resource
    private ICfgDatatableSV cfgDatatableSV;

    @Resource
    private ICfgJobAttrSV cfgJobAttrSV;

    @Resource
    private ICfgDataviewSV cfgDataviewSV;

    public static enum InputNameTypeEnum{
        db, table;
    }

    private static final Map<InputNameTypeEnum, List<String>> inputAliasNameMap = new HashMap(){{
        put(InputNameTypeEnum.db, Lists.newArrayList("cfg_datasource_id", "db", "sourceDB", "targetDB", "relationDB"));
        put(InputNameTypeEnum.table, Lists.newArrayList("db_object_name", "table", "sourceTable", "targetTable", "relationTable"));
    }};


    private Map<String, String> parseConnectInfo(String dataCondition) {
        Map<String, String> connectInfoMap = new HashMap<>();
        if(StringUtils.isNotBlank(dataCondition)) {
            String[] conditions = dataCondition.split("&&");
            for (String condition : conditions) {
                if(StringUtils.isNotBlank(condition)) {
                    String key = condition.substring(0, condition.indexOf("=")).trim();
                    String value = condition.substring(condition.indexOf("=") + 1).trim();
                    connectInfoMap.put(key, value);
                }
            }
        }

        return connectInfoMap;
    }

    public static final String getParameterValue(InputNameTypeEnum inputNameType, Map<String, String> context){
        if(context != null) {
            List<String> aliasNames = inputAliasNameMap.get(inputNameType);
            for (String aliasName : aliasNames) {
                if(StringUtils.isNotBlank(context.get(aliasName))) {
                    return context.get(aliasName).trim();
                }
            }
        }
        return null;
    }

    /**
     * 删除任务属性定义
     * @param ids
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/cfgJobAttr/delete.json")
    @ResponseBody
    public ResultData delete(@RequestParam(value = "id", required = false) Long[] ids) {
        logger.debug("request : {}", ids);

        try {
            if(ids != null) {
                for (Long id : ids) {
                    cfgJobAttrSV.delete(id);
                }
            }

        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.success();
    }

    /**
     * 获取表列表
     * @param dataCondition
     * @return
     */
    @RequestMapping(value = "/getDbObjects.json")
    @ResponseBody
    public ResultData getDbObjects(@ModelAttribute("dataCondition") final String dataCondition){
        logger.debug("request : {}", dataCondition);
        try{
            Map<String, String> connectInfoMap = parseConnectInfo(dataCondition);
            String cfgDatasourceId = getParameterValue(InputNameTypeEnum.db, connectInfoMap);
            if(StringUtils.isNotBlank(cfgDatasourceId)) {
                CfgDatasource cfgDatasource = cfgDatasourceSV.getCfgDatasourceByPK(Long.valueOf(cfgDatasourceId.trim()));
                if(cfgDatasource == null) {
                    return ResultData.error(ResultCode.get("1111", "数据库连接不存在!"));
                }

                List <KVBean> kvBeans = new ArrayList<>();
                kvBeans.addAll(getViewKVBeans(Long.valueOf(cfgDatasourceId)));
                List tables = DBUtils.query(cfgDatasource, "show tables");
                Set<String> splitTables  = new HashSet<>();
                for (Object table : tables) {
                    String tableName = (String) ((Map) table).values().iterator().next();
                    tableName = getSplitTableName(tableName);
                    if(StringUtils.isNotBlank(tableName)) {
                        splitTables.add(tableName);
                    }
                }

                kvBeans.addAll(CollectionUtils.from(tables, new Mapping() {
                    @Override
                    public KVBean from(Object table) {
                        KVBean kvBean = new KVBean();
                        String next = (String) ((Map) table).values().iterator().next();
                        kvBean.setText(next);
                        kvBean.setValue(next);
                        return kvBean;
                    }
                }));

                for (String splitTable : splitTables) {
                    KVBean kvBean = new KVBean();
                    kvBean.setText(splitTable);
                    kvBean.setValue(splitTable);
                    kvBeans.add(kvBean);
                }

                kvBeans.addAll(getCfgTableKVBeans(Long.valueOf(cfgDatasourceId)));

                return ResultData.success(kvBeans);
            }
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }

        return ResultData.success(ResultCode.UNKNOW);
    }

    public List<KVBean> getCfgTableKVBeans(Long cfgDataSourceId) throws Exception {
        CfgDatatable_Example example = new CfgDatatable_Example();
        example.createCriteria().andCfgDatasourceIdEqualTo(cfgDataSourceId);
        List<CfgDatatable> cfgDatatables = cfgDatatableSV.getCfgDatatableListByExample(example);
        return CollectionUtils.fetch(cfgDatatables, new Fetcher<CfgDatatable, KVBean>() {
            @Override
            public KVBean fetch(CfgDatatable cfgDatatable) {
                KVBean kvBean = new KVBean();
                kvBean.setText(cfgDatatable.getSubTableName());
                kvBean.setValue(cfgDatatable.getSubTableName());
                return kvBean;
            }
        });
    }

    public List<KVBean> getViewKVBeans(Long cfgDataSourceId) throws Exception {

        CfgDataview_Example viewExample = new CfgDataview_Example();
        viewExample.createCriteria()
                .andCfgDatasourceIdEqualTo(Long.valueOf(cfgDataSourceId))
                .andStatusEqualTo((byte)1);
        List<CfgDataview> views = cfgDataviewSV.getCfgDataviewListByExample(viewExample);
        List<KVBean> viewKVBeans = CollectionUtils.fetch(views, new Fetcher<CfgDataview, KVBean>() {
            @Override
            public KVBean fetch(CfgDataview cfgDataview) {
                KVBean kvBean = new KVBean();
                kvBean.setText(cfgDataview.getViewName());
                kvBean.setValue(cfgDataview.getViewName());
                return kvBean;
            }
        });
        return viewKVBeans;
    }

    /**
     * 获取表列表
     * @param dataCondition
     * @return
     */
    @RequestMapping(value = "/getDbTables.json")
    @ResponseBody
    public ResultData dictionary(@ModelAttribute("dataCondition") final String dataCondition){
        logger.debug("request : {}", dataCondition);
        try{
            Map<String, String> connectInfoMap = parseConnectInfo(dataCondition);
            String cfgDatasourceId = getParameterValue(InputNameTypeEnum.db, connectInfoMap);
            if(StringUtils.isNotBlank(cfgDatasourceId)) {
                CfgDatasource cfgDatasource = cfgDatasourceSV.getCfgDatasourceByPK(Long.valueOf(cfgDatasourceId.trim()));
                if(cfgDatasource == null) {
                    return ResultData.error(ResultCode.get("1111", "数据库连接不存在!"));
                }

                List tables = DBUtils.query(cfgDatasource, "show tables");
                Set<String> splitTables  = new HashSet<>();
                for (Object table : tables) {
                    String tableName = (String) ((Map) table).values().iterator().next();
                    tableName = getSplitTableName(tableName);
                    if(StringUtils.isNotBlank(tableName)) {
                        splitTables.add(tableName);
                    }
                }


                List <KVBean> kvBeans = CollectionUtils.from(tables, new Mapping() {
                    @Override
                    public KVBean from(Object table) {
                        KVBean kvBean = new KVBean();
                        String next = (String) ((Map) table).values().iterator().next();
                        kvBean.setText(next);
                        kvBean.setValue(next);
                        return kvBean;
                    }
                });

                for (String splitTable : splitTables) {
                    KVBean kvBean = new KVBean();
                    kvBean.setText(splitTable);
                    kvBean.setValue(splitTable);
                    kvBeans.add(kvBean);
                }

                kvBeans.addAll(getCfgTableKVBeans(Long.valueOf(cfgDatasourceId)));

                return ResultData.success(kvBeans);
            }
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }

        return ResultData.success(ResultCode.UNKNOW);
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

    /**
     * 获取表列表
     * @param dataCondition
     * @return
     */
    @RequestMapping(value = "/getDbColumns.json")
    @ResponseBody
    public ResultData getDbColumns(@ModelAttribute("dataCondition") final String dataCondition)  {
        try{
            Map<String, String> connectInfoMap = parseConnectInfo(dataCondition);
            String cfgDatasourceId = getParameterValue(InputNameTypeEnum.db, connectInfoMap);
            String dbObjectName = getParameterValue(InputNameTypeEnum.table, connectInfoMap);

            if(StringUtils.isBlank(dbObjectName)) {
                String scanSql = connectInfoMap.get("scanSQL");
                String[] tableNames = RegexUtils.find(scanSql, "(from)[ ]+[a-z0-9_]+");
                dbObjectName = tableNames[0].substring(5);
            }


            List<Map> dbColumns = getDbColumns(cfgDatasourceId, dbObjectName);
            return ResultData.success(CollectionUtils.from(dbColumns, new Mapping<Map, KVBean>() {
                @Override
                public KVBean from(Map map) {
                    KVBean kvBean = new KVBean();
                    kvBean.setText(String.valueOf(map.get("field")));
                    kvBean.setValue(String.valueOf(map.get("field")));
                    return kvBean;
                }
            }));
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
     * 获取表列和运算符信息
     * @param dataCondition
     * @return
     */
    @RequestMapping(value = "/getDbColumnsAndOperator.json")
    @ResponseBody
    public ResultData getDbColumnsAndOperator(@ModelAttribute("dataCondition") final String dataCondition)  {
        ResultData dbColumns = getDbColumns(dataCondition);

        if(dbColumns.getData() != null) {
            List kvs = (List)dbColumns.getData();
            KVBean kvBean = new KVBean();
            kvBean.setText("+");
            kvBean.setValue("+");
            kvs.add(0, kvBean);

            kvBean = new KVBean();
            kvBean.setText("-");
            kvBean.setValue("-");
            kvs.add(1, kvBean);

            kvBean = new KVBean();
            kvBean.setText("*");
            kvBean.setValue("*");
            kvs.add(2, kvBean );

            kvBean = new KVBean();
            kvBean.setText("/");
            kvBean.setValue("/");
            kvs.add(3, kvBean );
            kvBean = new KVBean();
            kvBean.setText("COUNT(*)");
            kvBean.setValue("COUNT(*)");
            kvs.add(4, kvBean );
        }
        return dbColumns;
    }

    private List<Map> getDbColumns(String cfgDatasourceId, String dbObjectName) throws Exception {

        if(StringUtils.isBlank(cfgDatasourceId) || StringUtils.isBlank(dbObjectName)) {
            return new ArrayList<>();
        }

        CfgDatasource cfgDatasource = cfgDatasourceSV.getCfgDatasourceByPK(Long.valueOf(cfgDatasourceId.trim()));
        if (cfgDatasource == null) {
            logger.error("数据库连接不存在");
            return new ArrayList<>();
//            return ResultData.error(ResultCode.get("1111", "数据库连接不存在!"));
        }

        if(dbObjectName.endsWith("_.*")) {//表明为分表
            List tables = DBUtils.query(cfgDatasource, "show tables");
            for (Object table : tables) {
                String tableName = (String) ((Map) table).values().iterator().next();
                String tableNameTarget = getSplitTableName(tableName);
                if(StringUtils.isNotBlank(tableNameTarget) && dbObjectName.equals(tableNameTarget)) {
                    dbObjectName = tableName;
                    break;
                }
            }
        }

        //分表
        if(dbObjectName.contains("\\d") || dbObjectName.contains("*")) {
            CfgDatatable_Example example = new CfgDatatable_Example();
            example.createCriteria()
                    .andCfgDatasourceIdEqualTo(Long.valueOf(cfgDatasourceId.trim()))
                    .andSubTableNameEqualTo(dbObjectName);
            List<CfgDatatable> cfgDatatables = cfgDatatableSV.getCfgDatatableListByExample(example);
            if(cfgDatatables.size() > 0) {
                dbObjectName = cfgDatatables.get(0).getTemplateTableName();
            }
        }
        List<Map> columns = null;
        try{
            columns = DBUtils.query(cfgDatasource, "show columns from " + dbObjectName);
        }catch (Exception e) {
            if(e instanceof MySQLSyntaxErrorException && e.getMessage().contains("Table")
                    && e.getMessage().contains("doesn't exist")) {
                CfgDataview_Example viewExample = new CfgDataview_Example();
                viewExample.createCriteria()
                        .andCfgDatasourceIdEqualTo(Long.valueOf(cfgDatasourceId))
                        .andViewNameEqualTo(dbObjectName)
                        .andStatusEqualTo((byte)1);
                List<CfgDataview> views = cfgDataviewSV.getCfgDataviewListByExample(viewExample);
                if(views.size() > 0) {
                    String sql = views.get(0).getViewSql();
                    DBClient.registerDatabase(cfgDatasourceId.toString(), "jdbc:mysql://" + cfgDatasource.getUrl()
                            + "/" + cfgDatasource.getDb() + "?useUnicode=true&tinyInt1isBit=false",
                            cfgDatasource.getUsername(), cfgDatasource.getPassword());
                    if(!sql.contains(" limit ") && !sql.contains(" LIMIT ")) {
                        if(sql.trim().endsWith(";")){
                            sql = sql.trim().substring(0, sql.trim().length() - 1) + " limit 1;";
                        }else{
                            sql = sql.trim() + " limit 1;";
                        }
                    }
                    Map<String, Integer> struts = DBUtils.executeQueryStruts(cfgDatasourceId.toString(), sql, null);
                    List<String> tmp = new ArrayList<>(struts.keySet());
                    List<Map> newColumns = CollectionUtils.fetch(tmp, new Fetcher<String, Map>() {
                        @Override
                        public Map fetch(final String s) {
                            return new HashMap(){{
                                put("field", s);
                            }};
                        }
                    });
                    columns = newColumns;

//                    String mainTables = views.get(0).getMainTables();
//                    String tables = views.get(0).getTables();
//                    Set<String> tableSet = new LinkedHashSet<>();
//                    tableSet.addAll(Lists.newArrayList(mainTables.split(",")));
//                    tableSet.addAll(Lists.newArrayList(tables.split(",")));
//                    columns = new ArrayList<>();
//                    for (String table : tableSet) {
//                        columns.addAll(getDbColumns(cfgDatasourceId, table));
//                    }
                }
            }
        }
        return columns;
    }

    /**
     * 获取表列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/cfg_subscribe_create.json")
    @ResponseBody
    public ResultData cfgSubscribeCreate(HttpServletRequest request){
        logger.debug("request : {}");
        try{
            final String cfgDatasourceId = request.getParameter("cfg_subscribe/cfg_datasource_id");
            final String dbObjectName = request.getParameter("cfg_subscribe/db_object_name");

            return ResultData.success(new HashMap<String,Object>(){{
                put("HELPER", getDbColumns(cfgDatasourceId, dbObjectName));

            }});
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
     * 获取表列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/cfg_subscribe_edit.json")
    @ResponseBody
    public ResultData cfgSubscribeEdit(HttpServletRequest request){
        return cfgSubscribeCreate(request);
    }


}
