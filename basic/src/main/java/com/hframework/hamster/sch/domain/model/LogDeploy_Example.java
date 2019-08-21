package com.hframework.hamster.sch.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogDeploy_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public LogDeploy_Example() {
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

        public Criteria andDeployIdIsNull() {
            addCriterion("deploy_id is null");
            return (Criteria) this;
        }

        public Criteria andDeployIdIsNotNull() {
            addCriterion("deploy_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeployIdEqualTo(Long value) {
            addCriterion("deploy_id =", value, "deployId");
            return (Criteria) this;
        }

        public Criteria andDeployIdNotEqualTo(Long value) {
            addCriterion("deploy_id <>", value, "deployId");
            return (Criteria) this;
        }

        public Criteria andDeployIdGreaterThan(Long value) {
            addCriterion("deploy_id >", value, "deployId");
            return (Criteria) this;
        }

        public Criteria andDeployIdGreaterThanOrEqualTo(Long value) {
            addCriterion("deploy_id >=", value, "deployId");
            return (Criteria) this;
        }

        public Criteria andDeployIdLessThan(Long value) {
            addCriterion("deploy_id <", value, "deployId");
            return (Criteria) this;
        }

        public Criteria andDeployIdLessThanOrEqualTo(Long value) {
            addCriterion("deploy_id <=", value, "deployId");
            return (Criteria) this;
        }

        public Criteria andDeployIdIn(List<Long> values) {
            addCriterion("deploy_id in", values, "deployId");
            return (Criteria) this;
        }

        public Criteria andDeployIdNotIn(List<Long> values) {
            addCriterion("deploy_id not in", values, "deployId");
            return (Criteria) this;
        }

        public Criteria andDeployIdBetween(Long value1, Long value2) {
            addCriterion("deploy_id between", value1, value2, "deployId");
            return (Criteria) this;
        }

        public Criteria andDeployIdNotBetween(Long value1, Long value2) {
            addCriterion("deploy_id not between", value1, value2, "deployId");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
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

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andStartInfoIsNull() {
            addCriterion("start_info is null");
            return (Criteria) this;
        }

        public Criteria andStartInfoIsNotNull() {
            addCriterion("start_info is not null");
            return (Criteria) this;
        }

        public Criteria andStartInfoEqualTo(String value) {
            addCriterion("start_info =", value, "startInfo");
            return (Criteria) this;
        }

        public Criteria andStartInfoNotEqualTo(String value) {
            addCriterion("start_info <>", value, "startInfo");
            return (Criteria) this;
        }

        public Criteria andStartInfoGreaterThan(String value) {
            addCriterion("start_info >", value, "startInfo");
            return (Criteria) this;
        }

        public Criteria andStartInfoGreaterThanOrEqualTo(String value) {
            addCriterion("start_info >=", value, "startInfo");
            return (Criteria) this;
        }

        public Criteria andStartInfoLessThan(String value) {
            addCriterion("start_info <", value, "startInfo");
            return (Criteria) this;
        }

        public Criteria andStartInfoLessThanOrEqualTo(String value) {
            addCriterion("start_info <=", value, "startInfo");
            return (Criteria) this;
        }

        public Criteria andStartInfoLike(String value) {
            addCriterion("start_info like", value, "startInfo");
            return (Criteria) this;
        }

        public Criteria andStartInfoNotLike(String value) {
            addCriterion("start_info not like", value, "startInfo");
            return (Criteria) this;
        }

        public Criteria andStartInfoIn(List<String> values) {
            addCriterion("start_info in", values, "startInfo");
            return (Criteria) this;
        }

        public Criteria andStartInfoNotIn(List<String> values) {
            addCriterion("start_info not in", values, "startInfo");
            return (Criteria) this;
        }

        public Criteria andStartInfoBetween(String value1, String value2) {
            addCriterion("start_info between", value1, value2, "startInfo");
            return (Criteria) this;
        }

        public Criteria andStartInfoNotBetween(String value1, String value2) {
            addCriterion("start_info not between", value1, value2, "startInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoIsNull() {
            addCriterion("end_info is null");
            return (Criteria) this;
        }

        public Criteria andEndInfoIsNotNull() {
            addCriterion("end_info is not null");
            return (Criteria) this;
        }

        public Criteria andEndInfoEqualTo(String value) {
            addCriterion("end_info =", value, "endInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoNotEqualTo(String value) {
            addCriterion("end_info <>", value, "endInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoGreaterThan(String value) {
            addCriterion("end_info >", value, "endInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoGreaterThanOrEqualTo(String value) {
            addCriterion("end_info >=", value, "endInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoLessThan(String value) {
            addCriterion("end_info <", value, "endInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoLessThanOrEqualTo(String value) {
            addCriterion("end_info <=", value, "endInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoLike(String value) {
            addCriterion("end_info like", value, "endInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoNotLike(String value) {
            addCriterion("end_info not like", value, "endInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoIn(List<String> values) {
            addCriterion("end_info in", values, "endInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoNotIn(List<String> values) {
            addCriterion("end_info not in", values, "endInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoBetween(String value1, String value2) {
            addCriterion("end_info between", value1, value2, "endInfo");
            return (Criteria) this;
        }

        public Criteria andEndInfoNotBetween(String value1, String value2) {
            addCriterion("end_info not between", value1, value2, "endInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoIsNull() {
            addCriterion("error_info is null");
            return (Criteria) this;
        }

        public Criteria andErrorInfoIsNotNull() {
            addCriterion("error_info is not null");
            return (Criteria) this;
        }

        public Criteria andErrorInfoEqualTo(String value) {
            addCriterion("error_info =", value, "errorInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoNotEqualTo(String value) {
            addCriterion("error_info <>", value, "errorInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoGreaterThan(String value) {
            addCriterion("error_info >", value, "errorInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoGreaterThanOrEqualTo(String value) {
            addCriterion("error_info >=", value, "errorInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoLessThan(String value) {
            addCriterion("error_info <", value, "errorInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoLessThanOrEqualTo(String value) {
            addCriterion("error_info <=", value, "errorInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoLike(String value) {
            addCriterion("error_info like", value, "errorInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoNotLike(String value) {
            addCriterion("error_info not like", value, "errorInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoIn(List<String> values) {
            addCriterion("error_info in", values, "errorInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoNotIn(List<String> values) {
            addCriterion("error_info not in", values, "errorInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoBetween(String value1, String value2) {
            addCriterion("error_info between", value1, value2, "errorInfo");
            return (Criteria) this;
        }

        public Criteria andErrorInfoNotBetween(String value1, String value2) {
            addCriterion("error_info not between", value1, value2, "errorInfo");
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

        public Criteria andSelectKeyIsNull() {
            addCriterion("select_key is null");
            return (Criteria) this;
        }

        public Criteria andSelectKeyIsNotNull() {
            addCriterion("select_key is not null");
            return (Criteria) this;
        }

        public Criteria andSelectKeyEqualTo(String value) {
            addCriterion("select_key =", value, "selectKey");
            return (Criteria) this;
        }

        public Criteria andSelectKeyNotEqualTo(String value) {
            addCriterion("select_key <>", value, "selectKey");
            return (Criteria) this;
        }

        public Criteria andSelectKeyGreaterThan(String value) {
            addCriterion("select_key >", value, "selectKey");
            return (Criteria) this;
        }

        public Criteria andSelectKeyGreaterThanOrEqualTo(String value) {
            addCriterion("select_key >=", value, "selectKey");
            return (Criteria) this;
        }

        public Criteria andSelectKeyLessThan(String value) {
            addCriterion("select_key <", value, "selectKey");
            return (Criteria) this;
        }

        public Criteria andSelectKeyLessThanOrEqualTo(String value) {
            addCriterion("select_key <=", value, "selectKey");
            return (Criteria) this;
        }

        public Criteria andSelectKeyLike(String value) {
            addCriterion("select_key like", value, "selectKey");
            return (Criteria) this;
        }

        public Criteria andSelectKeyNotLike(String value) {
            addCriterion("select_key not like", value, "selectKey");
            return (Criteria) this;
        }

        public Criteria andSelectKeyIn(List<String> values) {
            addCriterion("select_key in", values, "selectKey");
            return (Criteria) this;
        }

        public Criteria andSelectKeyNotIn(List<String> values) {
            addCriterion("select_key not in", values, "selectKey");
            return (Criteria) this;
        }

        public Criteria andSelectKeyBetween(String value1, String value2) {
            addCriterion("select_key between", value1, value2, "selectKey");
            return (Criteria) this;
        }

        public Criteria andSelectKeyNotBetween(String value1, String value2) {
            addCriterion("select_key not between", value1, value2, "selectKey");
            return (Criteria) this;
        }

        public Criteria andDeployCodeIsNull() {
            addCriterion("deploy_code is null");
            return (Criteria) this;
        }

        public Criteria andDeployCodeIsNotNull() {
            addCriterion("deploy_code is not null");
            return (Criteria) this;
        }

        public Criteria andDeployCodeEqualTo(String value) {
            addCriterion("deploy_code =", value, "deployCode");
            return (Criteria) this;
        }

        public Criteria andDeployCodeNotEqualTo(String value) {
            addCriterion("deploy_code <>", value, "deployCode");
            return (Criteria) this;
        }

        public Criteria andDeployCodeGreaterThan(String value) {
            addCriterion("deploy_code >", value, "deployCode");
            return (Criteria) this;
        }

        public Criteria andDeployCodeGreaterThanOrEqualTo(String value) {
            addCriterion("deploy_code >=", value, "deployCode");
            return (Criteria) this;
        }

        public Criteria andDeployCodeLessThan(String value) {
            addCriterion("deploy_code <", value, "deployCode");
            return (Criteria) this;
        }

        public Criteria andDeployCodeLessThanOrEqualTo(String value) {
            addCriterion("deploy_code <=", value, "deployCode");
            return (Criteria) this;
        }

        public Criteria andDeployCodeLike(String value) {
            addCriterion("deploy_code like", value, "deployCode");
            return (Criteria) this;
        }

        public Criteria andDeployCodeNotLike(String value) {
            addCriterion("deploy_code not like", value, "deployCode");
            return (Criteria) this;
        }

        public Criteria andDeployCodeIn(List<String> values) {
            addCriterion("deploy_code in", values, "deployCode");
            return (Criteria) this;
        }

        public Criteria andDeployCodeNotIn(List<String> values) {
            addCriterion("deploy_code not in", values, "deployCode");
            return (Criteria) this;
        }

        public Criteria andDeployCodeBetween(String value1, String value2) {
            addCriterion("deploy_code between", value1, value2, "deployCode");
            return (Criteria) this;
        }

        public Criteria andDeployCodeNotBetween(String value1, String value2) {
            addCriterion("deploy_code not between", value1, value2, "deployCode");
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