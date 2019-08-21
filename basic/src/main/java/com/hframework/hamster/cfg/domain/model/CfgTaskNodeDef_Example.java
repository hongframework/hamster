package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgTaskNodeDef_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgTaskNodeDef_Example() {
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

        public Criteria andCfgTaskNodeDefIdIsNull() {
            addCriterion("cfg_task_node_def_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefIdIsNotNull() {
            addCriterion("cfg_task_node_def_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefIdEqualTo(Long value) {
            addCriterion("cfg_task_node_def_id =", value, "cfgTaskNodeDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefIdNotEqualTo(Long value) {
            addCriterion("cfg_task_node_def_id <>", value, "cfgTaskNodeDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefIdGreaterThan(Long value) {
            addCriterion("cfg_task_node_def_id >", value, "cfgTaskNodeDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_task_node_def_id >=", value, "cfgTaskNodeDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefIdLessThan(Long value) {
            addCriterion("cfg_task_node_def_id <", value, "cfgTaskNodeDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_task_node_def_id <=", value, "cfgTaskNodeDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefIdIn(List<Long> values) {
            addCriterion("cfg_task_node_def_id in", values, "cfgTaskNodeDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefIdNotIn(List<Long> values) {
            addCriterion("cfg_task_node_def_id not in", values, "cfgTaskNodeDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefIdBetween(Long value1, Long value2) {
            addCriterion("cfg_task_node_def_id between", value1, value2, "cfgTaskNodeDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_task_node_def_id not between", value1, value2, "cfgTaskNodeDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameIsNull() {
            addCriterion("cfg_task_node_def_name is null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameIsNotNull() {
            addCriterion("cfg_task_node_def_name is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameEqualTo(String value) {
            addCriterion("cfg_task_node_def_name =", value, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameNotEqualTo(String value) {
            addCriterion("cfg_task_node_def_name <>", value, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameGreaterThan(String value) {
            addCriterion("cfg_task_node_def_name >", value, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_task_node_def_name >=", value, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameLessThan(String value) {
            addCriterion("cfg_task_node_def_name <", value, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameLessThanOrEqualTo(String value) {
            addCriterion("cfg_task_node_def_name <=", value, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameLike(String value) {
            addCriterion("cfg_task_node_def_name like", value, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameNotLike(String value) {
            addCriterion("cfg_task_node_def_name not like", value, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameIn(List<String> values) {
            addCriterion("cfg_task_node_def_name in", values, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameNotIn(List<String> values) {
            addCriterion("cfg_task_node_def_name not in", values, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameBetween(String value1, String value2) {
            addCriterion("cfg_task_node_def_name between", value1, value2, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefNameNotBetween(String value1, String value2) {
            addCriterion("cfg_task_node_def_name not between", value1, value2, "cfgTaskNodeDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeIsNull() {
            addCriterion("cfg_task_node_def_code is null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeIsNotNull() {
            addCriterion("cfg_task_node_def_code is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeEqualTo(String value) {
            addCriterion("cfg_task_node_def_code =", value, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeNotEqualTo(String value) {
            addCriterion("cfg_task_node_def_code <>", value, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeGreaterThan(String value) {
            addCriterion("cfg_task_node_def_code >", value, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_task_node_def_code >=", value, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeLessThan(String value) {
            addCriterion("cfg_task_node_def_code <", value, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeLessThanOrEqualTo(String value) {
            addCriterion("cfg_task_node_def_code <=", value, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeLike(String value) {
            addCriterion("cfg_task_node_def_code like", value, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeNotLike(String value) {
            addCriterion("cfg_task_node_def_code not like", value, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeIn(List<String> values) {
            addCriterion("cfg_task_node_def_code in", values, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeNotIn(List<String> values) {
            addCriterion("cfg_task_node_def_code not in", values, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeBetween(String value1, String value2) {
            addCriterion("cfg_task_node_def_code between", value1, value2, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskNodeDefCodeNotBetween(String value1, String value2) {
            addCriterion("cfg_task_node_def_code not between", value1, value2, "cfgTaskNodeDefCode");
            return (Criteria) this;
        }

        public Criteria andJavaClassIsNull() {
            addCriterion("java_class is null");
            return (Criteria) this;
        }

        public Criteria andJavaClassIsNotNull() {
            addCriterion("java_class is not null");
            return (Criteria) this;
        }

        public Criteria andJavaClassEqualTo(String value) {
            addCriterion("java_class =", value, "javaClass");
            return (Criteria) this;
        }

        public Criteria andJavaClassNotEqualTo(String value) {
            addCriterion("java_class <>", value, "javaClass");
            return (Criteria) this;
        }

        public Criteria andJavaClassGreaterThan(String value) {
            addCriterion("java_class >", value, "javaClass");
            return (Criteria) this;
        }

        public Criteria andJavaClassGreaterThanOrEqualTo(String value) {
            addCriterion("java_class >=", value, "javaClass");
            return (Criteria) this;
        }

        public Criteria andJavaClassLessThan(String value) {
            addCriterion("java_class <", value, "javaClass");
            return (Criteria) this;
        }

        public Criteria andJavaClassLessThanOrEqualTo(String value) {
            addCriterion("java_class <=", value, "javaClass");
            return (Criteria) this;
        }

        public Criteria andJavaClassLike(String value) {
            addCriterion("java_class like", value, "javaClass");
            return (Criteria) this;
        }

        public Criteria andJavaClassNotLike(String value) {
            addCriterion("java_class not like", value, "javaClass");
            return (Criteria) this;
        }

        public Criteria andJavaClassIn(List<String> values) {
            addCriterion("java_class in", values, "javaClass");
            return (Criteria) this;
        }

        public Criteria andJavaClassNotIn(List<String> values) {
            addCriterion("java_class not in", values, "javaClass");
            return (Criteria) this;
        }

        public Criteria andJavaClassBetween(String value1, String value2) {
            addCriterion("java_class between", value1, value2, "javaClass");
            return (Criteria) this;
        }

        public Criteria andJavaClassNotBetween(String value1, String value2) {
            addCriterion("java_class not between", value1, value2, "javaClass");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdIsNull() {
            addCriterion("cfg_task_def_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdIsNotNull() {
            addCriterion("cfg_task_def_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdEqualTo(Long value) {
            addCriterion("cfg_task_def_id =", value, "cfgTaskDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdNotEqualTo(Long value) {
            addCriterion("cfg_task_def_id <>", value, "cfgTaskDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdGreaterThan(Long value) {
            addCriterion("cfg_task_def_id >", value, "cfgTaskDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_task_def_id >=", value, "cfgTaskDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdLessThan(Long value) {
            addCriterion("cfg_task_def_id <", value, "cfgTaskDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_task_def_id <=", value, "cfgTaskDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdIn(List<Long> values) {
            addCriterion("cfg_task_def_id in", values, "cfgTaskDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdNotIn(List<Long> values) {
            addCriterion("cfg_task_def_id not in", values, "cfgTaskDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdBetween(Long value1, Long value2) {
            addCriterion("cfg_task_def_id between", value1, value2, "cfgTaskDefId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_task_def_id not between", value1, value2, "cfgTaskDefId");
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