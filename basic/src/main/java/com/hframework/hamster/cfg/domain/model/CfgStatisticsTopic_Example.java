package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgStatisticsTopic_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgStatisticsTopic_Example() {
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

        public Criteria andCfgStatisticsTopicIdIsNull() {
            addCriterion("cfg_statistics_topic_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicIdIsNotNull() {
            addCriterion("cfg_statistics_topic_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicIdEqualTo(Long value) {
            addCriterion("cfg_statistics_topic_id =", value, "cfgStatisticsTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicIdNotEqualTo(Long value) {
            addCriterion("cfg_statistics_topic_id <>", value, "cfgStatisticsTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicIdGreaterThan(Long value) {
            addCriterion("cfg_statistics_topic_id >", value, "cfgStatisticsTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_statistics_topic_id >=", value, "cfgStatisticsTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicIdLessThan(Long value) {
            addCriterion("cfg_statistics_topic_id <", value, "cfgStatisticsTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_statistics_topic_id <=", value, "cfgStatisticsTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicIdIn(List<Long> values) {
            addCriterion("cfg_statistics_topic_id in", values, "cfgStatisticsTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicIdNotIn(List<Long> values) {
            addCriterion("cfg_statistics_topic_id not in", values, "cfgStatisticsTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicIdBetween(Long value1, Long value2) {
            addCriterion("cfg_statistics_topic_id between", value1, value2, "cfgStatisticsTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_statistics_topic_id not between", value1, value2, "cfgStatisticsTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeIsNull() {
            addCriterion("cfg_statistics_topic_code is null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeIsNotNull() {
            addCriterion("cfg_statistics_topic_code is not null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeEqualTo(String value) {
            addCriterion("cfg_statistics_topic_code =", value, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeNotEqualTo(String value) {
            addCriterion("cfg_statistics_topic_code <>", value, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeGreaterThan(String value) {
            addCriterion("cfg_statistics_topic_code >", value, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_statistics_topic_code >=", value, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeLessThan(String value) {
            addCriterion("cfg_statistics_topic_code <", value, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeLessThanOrEqualTo(String value) {
            addCriterion("cfg_statistics_topic_code <=", value, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeLike(String value) {
            addCriterion("cfg_statistics_topic_code like", value, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeNotLike(String value) {
            addCriterion("cfg_statistics_topic_code not like", value, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeIn(List<String> values) {
            addCriterion("cfg_statistics_topic_code in", values, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeNotIn(List<String> values) {
            addCriterion("cfg_statistics_topic_code not in", values, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeBetween(String value1, String value2) {
            addCriterion("cfg_statistics_topic_code between", value1, value2, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicCodeNotBetween(String value1, String value2) {
            addCriterion("cfg_statistics_topic_code not between", value1, value2, "cfgStatisticsTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameIsNull() {
            addCriterion("cfg_statistics_topic_name is null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameIsNotNull() {
            addCriterion("cfg_statistics_topic_name is not null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameEqualTo(String value) {
            addCriterion("cfg_statistics_topic_name =", value, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameNotEqualTo(String value) {
            addCriterion("cfg_statistics_topic_name <>", value, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameGreaterThan(String value) {
            addCriterion("cfg_statistics_topic_name >", value, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_statistics_topic_name >=", value, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameLessThan(String value) {
            addCriterion("cfg_statistics_topic_name <", value, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameLessThanOrEqualTo(String value) {
            addCriterion("cfg_statistics_topic_name <=", value, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameLike(String value) {
            addCriterion("cfg_statistics_topic_name like", value, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameNotLike(String value) {
            addCriterion("cfg_statistics_topic_name not like", value, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameIn(List<String> values) {
            addCriterion("cfg_statistics_topic_name in", values, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameNotIn(List<String> values) {
            addCriterion("cfg_statistics_topic_name not in", values, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameBetween(String value1, String value2) {
            addCriterion("cfg_statistics_topic_name between", value1, value2, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicNameNotBetween(String value1, String value2) {
            addCriterion("cfg_statistics_topic_name not between", value1, value2, "cfgStatisticsTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescIsNull() {
            addCriterion("cfg_statistics_topic_desc is null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescIsNotNull() {
            addCriterion("cfg_statistics_topic_desc is not null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescEqualTo(String value) {
            addCriterion("cfg_statistics_topic_desc =", value, "cfgStatisticsTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescNotEqualTo(String value) {
            addCriterion("cfg_statistics_topic_desc <>", value, "cfgStatisticsTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescGreaterThan(String value) {
            addCriterion("cfg_statistics_topic_desc >", value, "cfgStatisticsTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_statistics_topic_desc >=", value, "cfgStatisticsTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescLessThan(String value) {
            addCriterion("cfg_statistics_topic_desc <", value, "cfgStatisticsTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescLessThanOrEqualTo(String value) {
            addCriterion("cfg_statistics_topic_desc <=", value, "cfgStatisticsTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescLike(String value) {
            addCriterion("cfg_statistics_topic_desc like", value, "cfgStatisticsTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescNotLike(String value) {
            addCriterion("cfg_statistics_topic_desc not like", value, "cfgStatisticsTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescIn(List<String> values) {
            addCriterion("cfg_statistics_topic_desc in", values, "cfgStatisticsTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescNotIn(List<String> values) {
            addCriterion("cfg_statistics_topic_desc not in", values, "cfgStatisticsTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescBetween(String value1, String value2) {
            addCriterion("cfg_statistics_topic_desc between", value1, value2, "cfgStatisticsTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsTopicDescNotBetween(String value1, String value2) {
            addCriterion("cfg_statistics_topic_desc not between", value1, value2, "cfgStatisticsTopicDesc");
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