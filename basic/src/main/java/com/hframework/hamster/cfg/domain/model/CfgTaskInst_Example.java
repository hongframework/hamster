package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgTaskInst_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgTaskInst_Example() {
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

        public Criteria andCfgTaskInstIdIsNull() {
            addCriterion("cfg_task_inst_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstIdIsNotNull() {
            addCriterion("cfg_task_inst_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstIdEqualTo(Long value) {
            addCriterion("cfg_task_inst_id =", value, "cfgTaskInstId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstIdNotEqualTo(Long value) {
            addCriterion("cfg_task_inst_id <>", value, "cfgTaskInstId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstIdGreaterThan(Long value) {
            addCriterion("cfg_task_inst_id >", value, "cfgTaskInstId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_task_inst_id >=", value, "cfgTaskInstId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstIdLessThan(Long value) {
            addCriterion("cfg_task_inst_id <", value, "cfgTaskInstId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_task_inst_id <=", value, "cfgTaskInstId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstIdIn(List<Long> values) {
            addCriterion("cfg_task_inst_id in", values, "cfgTaskInstId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstIdNotIn(List<Long> values) {
            addCriterion("cfg_task_inst_id not in", values, "cfgTaskInstId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstIdBetween(Long value1, Long value2) {
            addCriterion("cfg_task_inst_id between", value1, value2, "cfgTaskInstId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_task_inst_id not between", value1, value2, "cfgTaskInstId");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescIsNull() {
            addCriterion("cfg_task_inst_desc is null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescIsNotNull() {
            addCriterion("cfg_task_inst_desc is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescEqualTo(String value) {
            addCriterion("cfg_task_inst_desc =", value, "cfgTaskInstDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescNotEqualTo(String value) {
            addCriterion("cfg_task_inst_desc <>", value, "cfgTaskInstDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescGreaterThan(String value) {
            addCriterion("cfg_task_inst_desc >", value, "cfgTaskInstDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_task_inst_desc >=", value, "cfgTaskInstDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescLessThan(String value) {
            addCriterion("cfg_task_inst_desc <", value, "cfgTaskInstDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescLessThanOrEqualTo(String value) {
            addCriterion("cfg_task_inst_desc <=", value, "cfgTaskInstDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescLike(String value) {
            addCriterion("cfg_task_inst_desc like", value, "cfgTaskInstDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescNotLike(String value) {
            addCriterion("cfg_task_inst_desc not like", value, "cfgTaskInstDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescIn(List<String> values) {
            addCriterion("cfg_task_inst_desc in", values, "cfgTaskInstDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescNotIn(List<String> values) {
            addCriterion("cfg_task_inst_desc not in", values, "cfgTaskInstDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescBetween(String value1, String value2) {
            addCriterion("cfg_task_inst_desc between", value1, value2, "cfgTaskInstDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstDescNotBetween(String value1, String value2) {
            addCriterion("cfg_task_inst_desc not between", value1, value2, "cfgTaskInstDesc");
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

        public Criteria andParamValue1IsNull() {
            addCriterion("param_value_1 is null");
            return (Criteria) this;
        }

        public Criteria andParamValue1IsNotNull() {
            addCriterion("param_value_1 is not null");
            return (Criteria) this;
        }

        public Criteria andParamValue1EqualTo(String value) {
            addCriterion("param_value_1 =", value, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValue1NotEqualTo(String value) {
            addCriterion("param_value_1 <>", value, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValue1GreaterThan(String value) {
            addCriterion("param_value_1 >", value, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValue1GreaterThanOrEqualTo(String value) {
            addCriterion("param_value_1 >=", value, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValue1LessThan(String value) {
            addCriterion("param_value_1 <", value, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValue1LessThanOrEqualTo(String value) {
            addCriterion("param_value_1 <=", value, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValue1Like(String value) {
            addCriterion("param_value_1 like", value, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValue1NotLike(String value) {
            addCriterion("param_value_1 not like", value, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValue1In(List<String> values) {
            addCriterion("param_value_1 in", values, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValue1NotIn(List<String> values) {
            addCriterion("param_value_1 not in", values, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValue1Between(String value1, String value2) {
            addCriterion("param_value_1 between", value1, value2, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValue1NotBetween(String value1, String value2) {
            addCriterion("param_value_1 not between", value1, value2, "paramValue1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1IsNull() {
            addCriterion("param_value_remark_1 is null");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1IsNotNull() {
            addCriterion("param_value_remark_1 is not null");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1EqualTo(String value) {
            addCriterion("param_value_remark_1 =", value, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1NotEqualTo(String value) {
            addCriterion("param_value_remark_1 <>", value, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1GreaterThan(String value) {
            addCriterion("param_value_remark_1 >", value, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1GreaterThanOrEqualTo(String value) {
            addCriterion("param_value_remark_1 >=", value, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1LessThan(String value) {
            addCriterion("param_value_remark_1 <", value, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1LessThanOrEqualTo(String value) {
            addCriterion("param_value_remark_1 <=", value, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1Like(String value) {
            addCriterion("param_value_remark_1 like", value, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1NotLike(String value) {
            addCriterion("param_value_remark_1 not like", value, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1In(List<String> values) {
            addCriterion("param_value_remark_1 in", values, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1NotIn(List<String> values) {
            addCriterion("param_value_remark_1 not in", values, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1Between(String value1, String value2) {
            addCriterion("param_value_remark_1 between", value1, value2, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark1NotBetween(String value1, String value2) {
            addCriterion("param_value_remark_1 not between", value1, value2, "paramValueRemark1");
            return (Criteria) this;
        }

        public Criteria andParamValue2IsNull() {
            addCriterion("param_value_2 is null");
            return (Criteria) this;
        }

        public Criteria andParamValue2IsNotNull() {
            addCriterion("param_value_2 is not null");
            return (Criteria) this;
        }

        public Criteria andParamValue2EqualTo(String value) {
            addCriterion("param_value_2 =", value, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValue2NotEqualTo(String value) {
            addCriterion("param_value_2 <>", value, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValue2GreaterThan(String value) {
            addCriterion("param_value_2 >", value, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValue2GreaterThanOrEqualTo(String value) {
            addCriterion("param_value_2 >=", value, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValue2LessThan(String value) {
            addCriterion("param_value_2 <", value, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValue2LessThanOrEqualTo(String value) {
            addCriterion("param_value_2 <=", value, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValue2Like(String value) {
            addCriterion("param_value_2 like", value, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValue2NotLike(String value) {
            addCriterion("param_value_2 not like", value, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValue2In(List<String> values) {
            addCriterion("param_value_2 in", values, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValue2NotIn(List<String> values) {
            addCriterion("param_value_2 not in", values, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValue2Between(String value1, String value2) {
            addCriterion("param_value_2 between", value1, value2, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValue2NotBetween(String value1, String value2) {
            addCriterion("param_value_2 not between", value1, value2, "paramValue2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2IsNull() {
            addCriterion("param_value_remark_2 is null");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2IsNotNull() {
            addCriterion("param_value_remark_2 is not null");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2EqualTo(String value) {
            addCriterion("param_value_remark_2 =", value, "paramValueRemark2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2NotEqualTo(String value) {
            addCriterion("param_value_remark_2 <>", value, "paramValueRemark2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2GreaterThan(String value) {
            addCriterion("param_value_remark_2 >", value, "paramValueRemark2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2GreaterThanOrEqualTo(String value) {
            addCriterion("param_value_remark_2 >=", value, "paramValueRemark2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2LessThan(String value) {
            addCriterion("param_value_remark_2 <", value, "paramValueRemark2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2LessThanOrEqualTo(String value) {
            addCriterion("param_value_remark_2 <=", value, "paramValueRemark2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2Like(String value) {
            addCriterion("param_value_remark_2 like", value, "paramValueRemark2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2NotLike(String value) {
            addCriterion("param_value_remark_2 not like", value, "paramValueRemark2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2In(List<String> values) {
            addCriterion("param_value_remark_2 in", values, "paramValueRemark2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2NotIn(List<String> values) {
            addCriterion("param_value_remark_2 not in", values, "paramValueRemark2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2Between(String value1, String value2) {
            addCriterion("param_value_remark_2 between", value1, value2, "paramValueRemark2");
            return (Criteria) this;
        }

        public Criteria andParamValueRemark2NotBetween(String value1, String value2) {
            addCriterion("param_value_remark_2 not between", value1, value2, "paramValueRemark2");
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