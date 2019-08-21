package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgDataview_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgDataview_Example() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andViewNameIsNull() {
            addCriterion("view_name is null");
            return (Criteria) this;
        }

        public Criteria andViewNameIsNotNull() {
            addCriterion("view_name is not null");
            return (Criteria) this;
        }

        public Criteria andViewNameEqualTo(String value) {
            addCriterion("view_name =", value, "viewName");
            return (Criteria) this;
        }

        public Criteria andViewNameNotEqualTo(String value) {
            addCriterion("view_name <>", value, "viewName");
            return (Criteria) this;
        }

        public Criteria andViewNameGreaterThan(String value) {
            addCriterion("view_name >", value, "viewName");
            return (Criteria) this;
        }

        public Criteria andViewNameGreaterThanOrEqualTo(String value) {
            addCriterion("view_name >=", value, "viewName");
            return (Criteria) this;
        }

        public Criteria andViewNameLessThan(String value) {
            addCriterion("view_name <", value, "viewName");
            return (Criteria) this;
        }

        public Criteria andViewNameLessThanOrEqualTo(String value) {
            addCriterion("view_name <=", value, "viewName");
            return (Criteria) this;
        }

        public Criteria andViewNameLike(String value) {
            addCriterion("view_name like", value, "viewName");
            return (Criteria) this;
        }

        public Criteria andViewNameNotLike(String value) {
            addCriterion("view_name not like", value, "viewName");
            return (Criteria) this;
        }

        public Criteria andViewNameIn(List<String> values) {
            addCriterion("view_name in", values, "viewName");
            return (Criteria) this;
        }

        public Criteria andViewNameNotIn(List<String> values) {
            addCriterion("view_name not in", values, "viewName");
            return (Criteria) this;
        }

        public Criteria andViewNameBetween(String value1, String value2) {
            addCriterion("view_name between", value1, value2, "viewName");
            return (Criteria) this;
        }

        public Criteria andViewNameNotBetween(String value1, String value2) {
            addCriterion("view_name not between", value1, value2, "viewName");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdIsNull() {
            addCriterion("cfg_datasource_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdIsNotNull() {
            addCriterion("cfg_datasource_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdEqualTo(Long value) {
            addCriterion("cfg_datasource_id =", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdNotEqualTo(Long value) {
            addCriterion("cfg_datasource_id <>", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdGreaterThan(Long value) {
            addCriterion("cfg_datasource_id >", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_datasource_id >=", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdLessThan(Long value) {
            addCriterion("cfg_datasource_id <", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_datasource_id <=", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdIn(List<Long> values) {
            addCriterion("cfg_datasource_id in", values, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdNotIn(List<Long> values) {
            addCriterion("cfg_datasource_id not in", values, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdBetween(Long value1, Long value2) {
            addCriterion("cfg_datasource_id between", value1, value2, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_datasource_id not between", value1, value2, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andViewSqlIsNull() {
            addCriterion("view_sql is null");
            return (Criteria) this;
        }

        public Criteria andViewSqlIsNotNull() {
            addCriterion("view_sql is not null");
            return (Criteria) this;
        }

        public Criteria andViewSqlEqualTo(String value) {
            addCriterion("view_sql =", value, "viewSql");
            return (Criteria) this;
        }

        public Criteria andViewSqlNotEqualTo(String value) {
            addCriterion("view_sql <>", value, "viewSql");
            return (Criteria) this;
        }

        public Criteria andViewSqlGreaterThan(String value) {
            addCriterion("view_sql >", value, "viewSql");
            return (Criteria) this;
        }

        public Criteria andViewSqlGreaterThanOrEqualTo(String value) {
            addCriterion("view_sql >=", value, "viewSql");
            return (Criteria) this;
        }

        public Criteria andViewSqlLessThan(String value) {
            addCriterion("view_sql <", value, "viewSql");
            return (Criteria) this;
        }

        public Criteria andViewSqlLessThanOrEqualTo(String value) {
            addCriterion("view_sql <=", value, "viewSql");
            return (Criteria) this;
        }

        public Criteria andViewSqlLike(String value) {
            addCriterion("view_sql like", value, "viewSql");
            return (Criteria) this;
        }

        public Criteria andViewSqlNotLike(String value) {
            addCriterion("view_sql not like", value, "viewSql");
            return (Criteria) this;
        }

        public Criteria andViewSqlIn(List<String> values) {
            addCriterion("view_sql in", values, "viewSql");
            return (Criteria) this;
        }

        public Criteria andViewSqlNotIn(List<String> values) {
            addCriterion("view_sql not in", values, "viewSql");
            return (Criteria) this;
        }

        public Criteria andViewSqlBetween(String value1, String value2) {
            addCriterion("view_sql between", value1, value2, "viewSql");
            return (Criteria) this;
        }

        public Criteria andViewSqlNotBetween(String value1, String value2) {
            addCriterion("view_sql not between", value1, value2, "viewSql");
            return (Criteria) this;
        }

        public Criteria andMainTablesIsNull() {
            addCriterion("main_tables is null");
            return (Criteria) this;
        }

        public Criteria andMainTablesIsNotNull() {
            addCriterion("main_tables is not null");
            return (Criteria) this;
        }

        public Criteria andMainTablesEqualTo(String value) {
            addCriterion("main_tables =", value, "mainTables");
            return (Criteria) this;
        }

        public Criteria andMainTablesNotEqualTo(String value) {
            addCriterion("main_tables <>", value, "mainTables");
            return (Criteria) this;
        }

        public Criteria andMainTablesGreaterThan(String value) {
            addCriterion("main_tables >", value, "mainTables");
            return (Criteria) this;
        }

        public Criteria andMainTablesGreaterThanOrEqualTo(String value) {
            addCriterion("main_tables >=", value, "mainTables");
            return (Criteria) this;
        }

        public Criteria andMainTablesLessThan(String value) {
            addCriterion("main_tables <", value, "mainTables");
            return (Criteria) this;
        }

        public Criteria andMainTablesLessThanOrEqualTo(String value) {
            addCriterion("main_tables <=", value, "mainTables");
            return (Criteria) this;
        }

        public Criteria andMainTablesLike(String value) {
            addCriterion("main_tables like", value, "mainTables");
            return (Criteria) this;
        }

        public Criteria andMainTablesNotLike(String value) {
            addCriterion("main_tables not like", value, "mainTables");
            return (Criteria) this;
        }

        public Criteria andMainTablesIn(List<String> values) {
            addCriterion("main_tables in", values, "mainTables");
            return (Criteria) this;
        }

        public Criteria andMainTablesNotIn(List<String> values) {
            addCriterion("main_tables not in", values, "mainTables");
            return (Criteria) this;
        }

        public Criteria andMainTablesBetween(String value1, String value2) {
            addCriterion("main_tables between", value1, value2, "mainTables");
            return (Criteria) this;
        }

        public Criteria andMainTablesNotBetween(String value1, String value2) {
            addCriterion("main_tables not between", value1, value2, "mainTables");
            return (Criteria) this;
        }

        public Criteria andTablesIsNull() {
            addCriterion("`tables` is null");
            return (Criteria) this;
        }

        public Criteria andTablesIsNotNull() {
            addCriterion("`tables` is not null");
            return (Criteria) this;
        }

        public Criteria andTablesEqualTo(String value) {
            addCriterion("`tables` =", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesNotEqualTo(String value) {
            addCriterion("`tables` <>", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesGreaterThan(String value) {
            addCriterion("`tables` >", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesGreaterThanOrEqualTo(String value) {
            addCriterion("`tables` >=", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesLessThan(String value) {
            addCriterion("`tables` <", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesLessThanOrEqualTo(String value) {
            addCriterion("`tables` <=", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesLike(String value) {
            addCriterion("`tables` like", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesNotLike(String value) {
            addCriterion("`tables` not like", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesIn(List<String> values) {
            addCriterion("`tables` in", values, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesNotIn(List<String> values) {
            addCriterion("`tables` not in", values, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesBetween(String value1, String value2) {
            addCriterion("`tables` between", value1, value2, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesNotBetween(String value1, String value2) {
            addCriterion("`tables` not between", value1, value2, "tables");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNull() {
            addCriterion("creator_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNotNull() {
            addCriterion("creator_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdEqualTo(Long value) {
            addCriterion("creator_id =", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotEqualTo(Long value) {
            addCriterion("creator_id <>", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThan(Long value) {
            addCriterion("creator_id >", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("creator_id >=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThan(Long value) {
            addCriterion("creator_id <", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThanOrEqualTo(Long value) {
            addCriterion("creator_id <=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIn(List<Long> values) {
            addCriterion("creator_id in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotIn(List<Long> values) {
            addCriterion("creator_id not in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdBetween(Long value1, Long value2) {
            addCriterion("creator_id between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotBetween(Long value1, Long value2) {
            addCriterion("creator_id not between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifierIdIsNull() {
            addCriterion("modifier_id is null");
            return (Criteria) this;
        }

        public Criteria andModifierIdIsNotNull() {
            addCriterion("modifier_id is not null");
            return (Criteria) this;
        }

        public Criteria andModifierIdEqualTo(Long value) {
            addCriterion("modifier_id =", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotEqualTo(Long value) {
            addCriterion("modifier_id <>", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdGreaterThan(Long value) {
            addCriterion("modifier_id >", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdGreaterThanOrEqualTo(Long value) {
            addCriterion("modifier_id >=", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdLessThan(Long value) {
            addCriterion("modifier_id <", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdLessThanOrEqualTo(Long value) {
            addCriterion("modifier_id <=", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdIn(List<Long> values) {
            addCriterion("modifier_id in", values, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotIn(List<Long> values) {
            addCriterion("modifier_id not in", values, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdBetween(Long value1, Long value2) {
            addCriterion("modifier_id between", value1, value2, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotBetween(Long value1, Long value2) {
            addCriterion("modifier_id not between", value1, value2, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}