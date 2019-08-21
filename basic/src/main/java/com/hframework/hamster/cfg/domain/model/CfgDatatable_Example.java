package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgDatatable_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgDatatable_Example() {
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

        public Criteria andTemplateTableNameIsNull() {
            addCriterion("template_table_name is null");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameIsNotNull() {
            addCriterion("template_table_name is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameEqualTo(String value) {
            addCriterion("template_table_name =", value, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameNotEqualTo(String value) {
            addCriterion("template_table_name <>", value, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameGreaterThan(String value) {
            addCriterion("template_table_name >", value, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("template_table_name >=", value, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameLessThan(String value) {
            addCriterion("template_table_name <", value, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameLessThanOrEqualTo(String value) {
            addCriterion("template_table_name <=", value, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameLike(String value) {
            addCriterion("template_table_name like", value, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameNotLike(String value) {
            addCriterion("template_table_name not like", value, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameIn(List<String> values) {
            addCriterion("template_table_name in", values, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameNotIn(List<String> values) {
            addCriterion("template_table_name not in", values, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameBetween(String value1, String value2) {
            addCriterion("template_table_name between", value1, value2, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andTemplateTableNameNotBetween(String value1, String value2) {
            addCriterion("template_table_name not between", value1, value2, "templateTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameIsNull() {
            addCriterion("sub_table_name is null");
            return (Criteria) this;
        }

        public Criteria andSubTableNameIsNotNull() {
            addCriterion("sub_table_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubTableNameEqualTo(String value) {
            addCriterion("sub_table_name =", value, "subTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameNotEqualTo(String value) {
            addCriterion("sub_table_name <>", value, "subTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameGreaterThan(String value) {
            addCriterion("sub_table_name >", value, "subTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("sub_table_name >=", value, "subTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameLessThan(String value) {
            addCriterion("sub_table_name <", value, "subTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameLessThanOrEqualTo(String value) {
            addCriterion("sub_table_name <=", value, "subTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameLike(String value) {
            addCriterion("sub_table_name like", value, "subTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameNotLike(String value) {
            addCriterion("sub_table_name not like", value, "subTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameIn(List<String> values) {
            addCriterion("sub_table_name in", values, "subTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameNotIn(List<String> values) {
            addCriterion("sub_table_name not in", values, "subTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameBetween(String value1, String value2) {
            addCriterion("sub_table_name between", value1, value2, "subTableName");
            return (Criteria) this;
        }

        public Criteria andSubTableNameNotBetween(String value1, String value2) {
            addCriterion("sub_table_name not between", value1, value2, "subTableName");
            return (Criteria) this;
        }

        public Criteria andCreateSqlIsNull() {
            addCriterion("create_sql is null");
            return (Criteria) this;
        }

        public Criteria andCreateSqlIsNotNull() {
            addCriterion("create_sql is not null");
            return (Criteria) this;
        }

        public Criteria andCreateSqlEqualTo(String value) {
            addCriterion("create_sql =", value, "createSql");
            return (Criteria) this;
        }

        public Criteria andCreateSqlNotEqualTo(String value) {
            addCriterion("create_sql <>", value, "createSql");
            return (Criteria) this;
        }

        public Criteria andCreateSqlGreaterThan(String value) {
            addCriterion("create_sql >", value, "createSql");
            return (Criteria) this;
        }

        public Criteria andCreateSqlGreaterThanOrEqualTo(String value) {
            addCriterion("create_sql >=", value, "createSql");
            return (Criteria) this;
        }

        public Criteria andCreateSqlLessThan(String value) {
            addCriterion("create_sql <", value, "createSql");
            return (Criteria) this;
        }

        public Criteria andCreateSqlLessThanOrEqualTo(String value) {
            addCriterion("create_sql <=", value, "createSql");
            return (Criteria) this;
        }

        public Criteria andCreateSqlLike(String value) {
            addCriterion("create_sql like", value, "createSql");
            return (Criteria) this;
        }

        public Criteria andCreateSqlNotLike(String value) {
            addCriterion("create_sql not like", value, "createSql");
            return (Criteria) this;
        }

        public Criteria andCreateSqlIn(List<String> values) {
            addCriterion("create_sql in", values, "createSql");
            return (Criteria) this;
        }

        public Criteria andCreateSqlNotIn(List<String> values) {
            addCriterion("create_sql not in", values, "createSql");
            return (Criteria) this;
        }

        public Criteria andCreateSqlBetween(String value1, String value2) {
            addCriterion("create_sql between", value1, value2, "createSql");
            return (Criteria) this;
        }

        public Criteria andCreateSqlNotBetween(String value1, String value2) {
            addCriterion("create_sql not between", value1, value2, "createSql");
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