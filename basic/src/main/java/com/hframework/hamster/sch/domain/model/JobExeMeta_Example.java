package com.hframework.hamster.sch.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobExeMeta_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public JobExeMeta_Example() {
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

        public Criteria andDeployIsNull() {
            addCriterion("deploy is null");
            return (Criteria) this;
        }

        public Criteria andDeployIsNotNull() {
            addCriterion("deploy is not null");
            return (Criteria) this;
        }

        public Criteria andDeployEqualTo(String value) {
            addCriterion("deploy =", value, "deploy");
            return (Criteria) this;
        }

        public Criteria andDeployNotEqualTo(String value) {
            addCriterion("deploy <>", value, "deploy");
            return (Criteria) this;
        }

        public Criteria andDeployGreaterThan(String value) {
            addCriterion("deploy >", value, "deploy");
            return (Criteria) this;
        }

        public Criteria andDeployGreaterThanOrEqualTo(String value) {
            addCriterion("deploy >=", value, "deploy");
            return (Criteria) this;
        }

        public Criteria andDeployLessThan(String value) {
            addCriterion("deploy <", value, "deploy");
            return (Criteria) this;
        }

        public Criteria andDeployLessThanOrEqualTo(String value) {
            addCriterion("deploy <=", value, "deploy");
            return (Criteria) this;
        }

        public Criteria andDeployLike(String value) {
            addCriterion("deploy like", value, "deploy");
            return (Criteria) this;
        }

        public Criteria andDeployNotLike(String value) {
            addCriterion("deploy not like", value, "deploy");
            return (Criteria) this;
        }

        public Criteria andDeployIn(List<String> values) {
            addCriterion("deploy in", values, "deploy");
            return (Criteria) this;
        }

        public Criteria andDeployNotIn(List<String> values) {
            addCriterion("deploy not in", values, "deploy");
            return (Criteria) this;
        }

        public Criteria andDeployBetween(String value1, String value2) {
            addCriterion("deploy between", value1, value2, "deploy");
            return (Criteria) this;
        }

        public Criteria andDeployNotBetween(String value1, String value2) {
            addCriterion("deploy not between", value1, value2, "deploy");
            return (Criteria) this;
        }

        public Criteria andDatastoreIsNull() {
            addCriterion("datastore is null");
            return (Criteria) this;
        }

        public Criteria andDatastoreIsNotNull() {
            addCriterion("datastore is not null");
            return (Criteria) this;
        }

        public Criteria andDatastoreEqualTo(String value) {
            addCriterion("datastore =", value, "datastore");
            return (Criteria) this;
        }

        public Criteria andDatastoreNotEqualTo(String value) {
            addCriterion("datastore <>", value, "datastore");
            return (Criteria) this;
        }

        public Criteria andDatastoreGreaterThan(String value) {
            addCriterion("datastore >", value, "datastore");
            return (Criteria) this;
        }

        public Criteria andDatastoreGreaterThanOrEqualTo(String value) {
            addCriterion("datastore >=", value, "datastore");
            return (Criteria) this;
        }

        public Criteria andDatastoreLessThan(String value) {
            addCriterion("datastore <", value, "datastore");
            return (Criteria) this;
        }

        public Criteria andDatastoreLessThanOrEqualTo(String value) {
            addCriterion("datastore <=", value, "datastore");
            return (Criteria) this;
        }

        public Criteria andDatastoreLike(String value) {
            addCriterion("datastore like", value, "datastore");
            return (Criteria) this;
        }

        public Criteria andDatastoreNotLike(String value) {
            addCriterion("datastore not like", value, "datastore");
            return (Criteria) this;
        }

        public Criteria andDatastoreIn(List<String> values) {
            addCriterion("datastore in", values, "datastore");
            return (Criteria) this;
        }

        public Criteria andDatastoreNotIn(List<String> values) {
            addCriterion("datastore not in", values, "datastore");
            return (Criteria) this;
        }

        public Criteria andDatastoreBetween(String value1, String value2) {
            addCriterion("datastore between", value1, value2, "datastore");
            return (Criteria) this;
        }

        public Criteria andDatastoreNotBetween(String value1, String value2) {
            addCriterion("datastore not between", value1, value2, "datastore");
            return (Criteria) this;
        }

        public Criteria andSourceTableIsNull() {
            addCriterion("source_table is null");
            return (Criteria) this;
        }

        public Criteria andSourceTableIsNotNull() {
            addCriterion("source_table is not null");
            return (Criteria) this;
        }

        public Criteria andSourceTableEqualTo(String value) {
            addCriterion("source_table =", value, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andSourceTableNotEqualTo(String value) {
            addCriterion("source_table <>", value, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andSourceTableGreaterThan(String value) {
            addCriterion("source_table >", value, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andSourceTableGreaterThanOrEqualTo(String value) {
            addCriterion("source_table >=", value, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andSourceTableLessThan(String value) {
            addCriterion("source_table <", value, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andSourceTableLessThanOrEqualTo(String value) {
            addCriterion("source_table <=", value, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andSourceTableLike(String value) {
            addCriterion("source_table like", value, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andSourceTableNotLike(String value) {
            addCriterion("source_table not like", value, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andSourceTableIn(List<String> values) {
            addCriterion("source_table in", values, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andSourceTableNotIn(List<String> values) {
            addCriterion("source_table not in", values, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andSourceTableBetween(String value1, String value2) {
            addCriterion("source_table between", value1, value2, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andSourceTableNotBetween(String value1, String value2) {
            addCriterion("source_table not between", value1, value2, "sourceTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableIsNull() {
            addCriterion("target_table is null");
            return (Criteria) this;
        }

        public Criteria andTargetTableIsNotNull() {
            addCriterion("target_table is not null");
            return (Criteria) this;
        }

        public Criteria andTargetTableEqualTo(String value) {
            addCriterion("target_table =", value, "targetTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableNotEqualTo(String value) {
            addCriterion("target_table <>", value, "targetTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableGreaterThan(String value) {
            addCriterion("target_table >", value, "targetTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableGreaterThanOrEqualTo(String value) {
            addCriterion("target_table >=", value, "targetTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableLessThan(String value) {
            addCriterion("target_table <", value, "targetTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableLessThanOrEqualTo(String value) {
            addCriterion("target_table <=", value, "targetTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableLike(String value) {
            addCriterion("target_table like", value, "targetTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableNotLike(String value) {
            addCriterion("target_table not like", value, "targetTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableIn(List<String> values) {
            addCriterion("target_table in", values, "targetTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableNotIn(List<String> values) {
            addCriterion("target_table not in", values, "targetTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableBetween(String value1, String value2) {
            addCriterion("target_table between", value1, value2, "targetTable");
            return (Criteria) this;
        }

        public Criteria andTargetTableNotBetween(String value1, String value2) {
            addCriterion("target_table not between", value1, value2, "targetTable");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDelayTimeIsNull() {
            addCriterion("delay_time is null");
            return (Criteria) this;
        }

        public Criteria andDelayTimeIsNotNull() {
            addCriterion("delay_time is not null");
            return (Criteria) this;
        }

        public Criteria andDelayTimeEqualTo(Long value) {
            addCriterion("delay_time =", value, "delayTime");
            return (Criteria) this;
        }

        public Criteria andDelayTimeNotEqualTo(Long value) {
            addCriterion("delay_time <>", value, "delayTime");
            return (Criteria) this;
        }

        public Criteria andDelayTimeGreaterThan(Long value) {
            addCriterion("delay_time >", value, "delayTime");
            return (Criteria) this;
        }

        public Criteria andDelayTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("delay_time >=", value, "delayTime");
            return (Criteria) this;
        }

        public Criteria andDelayTimeLessThan(Long value) {
            addCriterion("delay_time <", value, "delayTime");
            return (Criteria) this;
        }

        public Criteria andDelayTimeLessThanOrEqualTo(Long value) {
            addCriterion("delay_time <=", value, "delayTime");
            return (Criteria) this;
        }

        public Criteria andDelayTimeIn(List<Long> values) {
            addCriterion("delay_time in", values, "delayTime");
            return (Criteria) this;
        }

        public Criteria andDelayTimeNotIn(List<Long> values) {
            addCriterion("delay_time not in", values, "delayTime");
            return (Criteria) this;
        }

        public Criteria andDelayTimeBetween(Long value1, Long value2) {
            addCriterion("delay_time between", value1, value2, "delayTime");
            return (Criteria) this;
        }

        public Criteria andDelayTimeNotBetween(Long value1, Long value2) {
            addCriterion("delay_time not between", value1, value2, "delayTime");
            return (Criteria) this;
        }

        public Criteria andTotalDelayIsNull() {
            addCriterion("total_delay is null");
            return (Criteria) this;
        }

        public Criteria andTotalDelayIsNotNull() {
            addCriterion("total_delay is not null");
            return (Criteria) this;
        }

        public Criteria andTotalDelayEqualTo(Long value) {
            addCriterion("total_delay =", value, "totalDelay");
            return (Criteria) this;
        }

        public Criteria andTotalDelayNotEqualTo(Long value) {
            addCriterion("total_delay <>", value, "totalDelay");
            return (Criteria) this;
        }

        public Criteria andTotalDelayGreaterThan(Long value) {
            addCriterion("total_delay >", value, "totalDelay");
            return (Criteria) this;
        }

        public Criteria andTotalDelayGreaterThanOrEqualTo(Long value) {
            addCriterion("total_delay >=", value, "totalDelay");
            return (Criteria) this;
        }

        public Criteria andTotalDelayLessThan(Long value) {
            addCriterion("total_delay <", value, "totalDelay");
            return (Criteria) this;
        }

        public Criteria andTotalDelayLessThanOrEqualTo(Long value) {
            addCriterion("total_delay <=", value, "totalDelay");
            return (Criteria) this;
        }

        public Criteria andTotalDelayIn(List<Long> values) {
            addCriterion("total_delay in", values, "totalDelay");
            return (Criteria) this;
        }

        public Criteria andTotalDelayNotIn(List<Long> values) {
            addCriterion("total_delay not in", values, "totalDelay");
            return (Criteria) this;
        }

        public Criteria andTotalDelayBetween(Long value1, Long value2) {
            addCriterion("total_delay between", value1, value2, "totalDelay");
            return (Criteria) this;
        }

        public Criteria andTotalDelayNotBetween(Long value1, Long value2) {
            addCriterion("total_delay not between", value1, value2, "totalDelay");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeIsNull() {
            addCriterion("execute_time is null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeIsNotNull() {
            addCriterion("execute_time is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeEqualTo(Date value) {
            addCriterion("execute_time =", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeNotEqualTo(Date value) {
            addCriterion("execute_time <>", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeGreaterThan(Date value) {
            addCriterion("execute_time >", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("execute_time >=", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeLessThan(Date value) {
            addCriterion("execute_time <", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeLessThanOrEqualTo(Date value) {
            addCriterion("execute_time <=", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeIn(List<Date> values) {
            addCriterion("execute_time in", values, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeNotIn(List<Date> values) {
            addCriterion("execute_time not in", values, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeBetween(Date value1, Date value2) {
            addCriterion("execute_time between", value1, value2, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeNotBetween(Date value1, Date value2) {
            addCriterion("execute_time not between", value1, value2, "executeTime");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeIsNull() {
            addCriterion("update_node is null");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeIsNotNull() {
            addCriterion("update_node is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeEqualTo(String value) {
            addCriterion("update_node =", value, "updateNode");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeNotEqualTo(String value) {
            addCriterion("update_node <>", value, "updateNode");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeGreaterThan(String value) {
            addCriterion("update_node >", value, "updateNode");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeGreaterThanOrEqualTo(String value) {
            addCriterion("update_node >=", value, "updateNode");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeLessThan(String value) {
            addCriterion("update_node <", value, "updateNode");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeLessThanOrEqualTo(String value) {
            addCriterion("update_node <=", value, "updateNode");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeLike(String value) {
            addCriterion("update_node like", value, "updateNode");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeNotLike(String value) {
            addCriterion("update_node not like", value, "updateNode");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeIn(List<String> values) {
            addCriterion("update_node in", values, "updateNode");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeNotIn(List<String> values) {
            addCriterion("update_node not in", values, "updateNode");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeBetween(String value1, String value2) {
            addCriterion("update_node between", value1, value2, "updateNode");
            return (Criteria) this;
        }

        public Criteria andUpdateNodeNotBetween(String value1, String value2) {
            addCriterion("update_node not between", value1, value2, "updateNode");
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