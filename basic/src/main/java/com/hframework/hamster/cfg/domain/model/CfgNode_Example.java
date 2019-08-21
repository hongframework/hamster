package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgNode_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgNode_Example() {
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

        public Criteria andCfgNodeIdIsNull() {
            addCriterion("cfg_node_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgNodeIdIsNotNull() {
            addCriterion("cfg_node_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgNodeIdEqualTo(Long value) {
            addCriterion("cfg_node_id =", value, "cfgNodeId");
            return (Criteria) this;
        }

        public Criteria andCfgNodeIdNotEqualTo(Long value) {
            addCriterion("cfg_node_id <>", value, "cfgNodeId");
            return (Criteria) this;
        }

        public Criteria andCfgNodeIdGreaterThan(Long value) {
            addCriterion("cfg_node_id >", value, "cfgNodeId");
            return (Criteria) this;
        }

        public Criteria andCfgNodeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_node_id >=", value, "cfgNodeId");
            return (Criteria) this;
        }

        public Criteria andCfgNodeIdLessThan(Long value) {
            addCriterion("cfg_node_id <", value, "cfgNodeId");
            return (Criteria) this;
        }

        public Criteria andCfgNodeIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_node_id <=", value, "cfgNodeId");
            return (Criteria) this;
        }

        public Criteria andCfgNodeIdIn(List<Long> values) {
            addCriterion("cfg_node_id in", values, "cfgNodeId");
            return (Criteria) this;
        }

        public Criteria andCfgNodeIdNotIn(List<Long> values) {
            addCriterion("cfg_node_id not in", values, "cfgNodeId");
            return (Criteria) this;
        }

        public Criteria andCfgNodeIdBetween(Long value1, Long value2) {
            addCriterion("cfg_node_id between", value1, value2, "cfgNodeId");
            return (Criteria) this;
        }

        public Criteria andCfgNodeIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_node_id not between", value1, value2, "cfgNodeId");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeIsNull() {
            addCriterion("cfg_node_code is null");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeIsNotNull() {
            addCriterion("cfg_node_code is not null");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeEqualTo(String value) {
            addCriterion("cfg_node_code =", value, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeNotEqualTo(String value) {
            addCriterion("cfg_node_code <>", value, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeGreaterThan(String value) {
            addCriterion("cfg_node_code >", value, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_node_code >=", value, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeLessThan(String value) {
            addCriterion("cfg_node_code <", value, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeLessThanOrEqualTo(String value) {
            addCriterion("cfg_node_code <=", value, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeLike(String value) {
            addCriterion("cfg_node_code like", value, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeNotLike(String value) {
            addCriterion("cfg_node_code not like", value, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeIn(List<String> values) {
            addCriterion("cfg_node_code in", values, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeNotIn(List<String> values) {
            addCriterion("cfg_node_code not in", values, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeBetween(String value1, String value2) {
            addCriterion("cfg_node_code between", value1, value2, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeCodeNotBetween(String value1, String value2) {
            addCriterion("cfg_node_code not between", value1, value2, "cfgNodeCode");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameIsNull() {
            addCriterion("cfg_node_name is null");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameIsNotNull() {
            addCriterion("cfg_node_name is not null");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameEqualTo(String value) {
            addCriterion("cfg_node_name =", value, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameNotEqualTo(String value) {
            addCriterion("cfg_node_name <>", value, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameGreaterThan(String value) {
            addCriterion("cfg_node_name >", value, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_node_name >=", value, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameLessThan(String value) {
            addCriterion("cfg_node_name <", value, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameLessThanOrEqualTo(String value) {
            addCriterion("cfg_node_name <=", value, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameLike(String value) {
            addCriterion("cfg_node_name like", value, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameNotLike(String value) {
            addCriterion("cfg_node_name not like", value, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameIn(List<String> values) {
            addCriterion("cfg_node_name in", values, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameNotIn(List<String> values) {
            addCriterion("cfg_node_name not in", values, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameBetween(String value1, String value2) {
            addCriterion("cfg_node_name between", value1, value2, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andCfgNodeNameNotBetween(String value1, String value2) {
            addCriterion("cfg_node_name not between", value1, value2, "cfgNodeName");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
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

        public Criteria andLabelsIsNull() {
            addCriterion("labels is null");
            return (Criteria) this;
        }

        public Criteria andLabelsIsNotNull() {
            addCriterion("labels is not null");
            return (Criteria) this;
        }

        public Criteria andLabelsEqualTo(String value) {
            addCriterion("labels =", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsNotEqualTo(String value) {
            addCriterion("labels <>", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsGreaterThan(String value) {
            addCriterion("labels >", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsGreaterThanOrEqualTo(String value) {
            addCriterion("labels >=", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsLessThan(String value) {
            addCriterion("labels <", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsLessThanOrEqualTo(String value) {
            addCriterion("labels <=", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsLike(String value) {
            addCriterion("labels like", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsNotLike(String value) {
            addCriterion("labels not like", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsIn(List<String> values) {
            addCriterion("labels in", values, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsNotIn(List<String> values) {
            addCriterion("labels not in", values, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsBetween(String value1, String value2) {
            addCriterion("labels between", value1, value2, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsNotBetween(String value1, String value2) {
            addCriterion("labels not between", value1, value2, "labels");
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