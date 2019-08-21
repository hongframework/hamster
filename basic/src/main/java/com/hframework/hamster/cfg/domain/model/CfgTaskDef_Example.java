package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgTaskDef_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgTaskDef_Example() {
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

        public Criteria andCfgTaskDefCodeIsNull() {
            addCriterion("cfg_task_def_code is null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeIsNotNull() {
            addCriterion("cfg_task_def_code is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeEqualTo(String value) {
            addCriterion("cfg_task_def_code =", value, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeNotEqualTo(String value) {
            addCriterion("cfg_task_def_code <>", value, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeGreaterThan(String value) {
            addCriterion("cfg_task_def_code >", value, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_task_def_code >=", value, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeLessThan(String value) {
            addCriterion("cfg_task_def_code <", value, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeLessThanOrEqualTo(String value) {
            addCriterion("cfg_task_def_code <=", value, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeLike(String value) {
            addCriterion("cfg_task_def_code like", value, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeNotLike(String value) {
            addCriterion("cfg_task_def_code not like", value, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeIn(List<String> values) {
            addCriterion("cfg_task_def_code in", values, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeNotIn(List<String> values) {
            addCriterion("cfg_task_def_code not in", values, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeBetween(String value1, String value2) {
            addCriterion("cfg_task_def_code between", value1, value2, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefCodeNotBetween(String value1, String value2) {
            addCriterion("cfg_task_def_code not between", value1, value2, "cfgTaskDefCode");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameIsNull() {
            addCriterion("cfg_task_def_name is null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameIsNotNull() {
            addCriterion("cfg_task_def_name is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameEqualTo(String value) {
            addCriterion("cfg_task_def_name =", value, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameNotEqualTo(String value) {
            addCriterion("cfg_task_def_name <>", value, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameGreaterThan(String value) {
            addCriterion("cfg_task_def_name >", value, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_task_def_name >=", value, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameLessThan(String value) {
            addCriterion("cfg_task_def_name <", value, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameLessThanOrEqualTo(String value) {
            addCriterion("cfg_task_def_name <=", value, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameLike(String value) {
            addCriterion("cfg_task_def_name like", value, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameNotLike(String value) {
            addCriterion("cfg_task_def_name not like", value, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameIn(List<String> values) {
            addCriterion("cfg_task_def_name in", values, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameNotIn(List<String> values) {
            addCriterion("cfg_task_def_name not in", values, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameBetween(String value1, String value2) {
            addCriterion("cfg_task_def_name between", value1, value2, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefNameNotBetween(String value1, String value2) {
            addCriterion("cfg_task_def_name not between", value1, value2, "cfgTaskDefName");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeIsNull() {
            addCriterion("cfg_task_def_type is null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeIsNotNull() {
            addCriterion("cfg_task_def_type is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeEqualTo(Byte value) {
            addCriterion("cfg_task_def_type =", value, "cfgTaskDefType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeNotEqualTo(Byte value) {
            addCriterion("cfg_task_def_type <>", value, "cfgTaskDefType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeGreaterThan(Byte value) {
            addCriterion("cfg_task_def_type >", value, "cfgTaskDefType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("cfg_task_def_type >=", value, "cfgTaskDefType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeLessThan(Byte value) {
            addCriterion("cfg_task_def_type <", value, "cfgTaskDefType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeLessThanOrEqualTo(Byte value) {
            addCriterion("cfg_task_def_type <=", value, "cfgTaskDefType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeIn(List<Byte> values) {
            addCriterion("cfg_task_def_type in", values, "cfgTaskDefType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeNotIn(List<Byte> values) {
            addCriterion("cfg_task_def_type not in", values, "cfgTaskDefType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeBetween(Byte value1, Byte value2) {
            addCriterion("cfg_task_def_type between", value1, value2, "cfgTaskDefType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskDefTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("cfg_task_def_type not between", value1, value2, "cfgTaskDefType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeIsNull() {
            addCriterion("cfg_task_instance_type is null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeIsNotNull() {
            addCriterion("cfg_task_instance_type is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeEqualTo(Byte value) {
            addCriterion("cfg_task_instance_type =", value, "cfgTaskInstanceType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeNotEqualTo(Byte value) {
            addCriterion("cfg_task_instance_type <>", value, "cfgTaskInstanceType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeGreaterThan(Byte value) {
            addCriterion("cfg_task_instance_type >", value, "cfgTaskInstanceType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("cfg_task_instance_type >=", value, "cfgTaskInstanceType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeLessThan(Byte value) {
            addCriterion("cfg_task_instance_type <", value, "cfgTaskInstanceType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeLessThanOrEqualTo(Byte value) {
            addCriterion("cfg_task_instance_type <=", value, "cfgTaskInstanceType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeIn(List<Byte> values) {
            addCriterion("cfg_task_instance_type in", values, "cfgTaskInstanceType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeNotIn(List<Byte> values) {
            addCriterion("cfg_task_instance_type not in", values, "cfgTaskInstanceType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeBetween(Byte value1, Byte value2) {
            addCriterion("cfg_task_instance_type between", value1, value2, "cfgTaskInstanceType");
            return (Criteria) this;
        }

        public Criteria andCfgTaskInstanceTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("cfg_task_instance_type not between", value1, value2, "cfgTaskInstanceType");
            return (Criteria) this;
        }

        public Criteria andParamName1IsNull() {
            addCriterion("param_name_1 is null");
            return (Criteria) this;
        }

        public Criteria andParamName1IsNotNull() {
            addCriterion("param_name_1 is not null");
            return (Criteria) this;
        }

        public Criteria andParamName1EqualTo(String value) {
            addCriterion("param_name_1 =", value, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamName1NotEqualTo(String value) {
            addCriterion("param_name_1 <>", value, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamName1GreaterThan(String value) {
            addCriterion("param_name_1 >", value, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamName1GreaterThanOrEqualTo(String value) {
            addCriterion("param_name_1 >=", value, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamName1LessThan(String value) {
            addCriterion("param_name_1 <", value, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamName1LessThanOrEqualTo(String value) {
            addCriterion("param_name_1 <=", value, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamName1Like(String value) {
            addCriterion("param_name_1 like", value, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamName1NotLike(String value) {
            addCriterion("param_name_1 not like", value, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamName1In(List<String> values) {
            addCriterion("param_name_1 in", values, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamName1NotIn(List<String> values) {
            addCriterion("param_name_1 not in", values, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamName1Between(String value1, String value2) {
            addCriterion("param_name_1 between", value1, value2, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamName1NotBetween(String value1, String value2) {
            addCriterion("param_name_1 not between", value1, value2, "paramName1");
            return (Criteria) this;
        }

        public Criteria andParamCode1IsNull() {
            addCriterion("param_code_1 is null");
            return (Criteria) this;
        }

        public Criteria andParamCode1IsNotNull() {
            addCriterion("param_code_1 is not null");
            return (Criteria) this;
        }

        public Criteria andParamCode1EqualTo(String value) {
            addCriterion("param_code_1 =", value, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamCode1NotEqualTo(String value) {
            addCriterion("param_code_1 <>", value, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamCode1GreaterThan(String value) {
            addCriterion("param_code_1 >", value, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamCode1GreaterThanOrEqualTo(String value) {
            addCriterion("param_code_1 >=", value, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamCode1LessThan(String value) {
            addCriterion("param_code_1 <", value, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamCode1LessThanOrEqualTo(String value) {
            addCriterion("param_code_1 <=", value, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamCode1Like(String value) {
            addCriterion("param_code_1 like", value, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamCode1NotLike(String value) {
            addCriterion("param_code_1 not like", value, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamCode1In(List<String> values) {
            addCriterion("param_code_1 in", values, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamCode1NotIn(List<String> values) {
            addCriterion("param_code_1 not in", values, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamCode1Between(String value1, String value2) {
            addCriterion("param_code_1 between", value1, value2, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamCode1NotBetween(String value1, String value2) {
            addCriterion("param_code_1 not between", value1, value2, "paramCode1");
            return (Criteria) this;
        }

        public Criteria andParamName2IsNull() {
            addCriterion("param_name_2 is null");
            return (Criteria) this;
        }

        public Criteria andParamName2IsNotNull() {
            addCriterion("param_name_2 is not null");
            return (Criteria) this;
        }

        public Criteria andParamName2EqualTo(String value) {
            addCriterion("param_name_2 =", value, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamName2NotEqualTo(String value) {
            addCriterion("param_name_2 <>", value, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamName2GreaterThan(String value) {
            addCriterion("param_name_2 >", value, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamName2GreaterThanOrEqualTo(String value) {
            addCriterion("param_name_2 >=", value, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamName2LessThan(String value) {
            addCriterion("param_name_2 <", value, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamName2LessThanOrEqualTo(String value) {
            addCriterion("param_name_2 <=", value, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamName2Like(String value) {
            addCriterion("param_name_2 like", value, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamName2NotLike(String value) {
            addCriterion("param_name_2 not like", value, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamName2In(List<String> values) {
            addCriterion("param_name_2 in", values, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamName2NotIn(List<String> values) {
            addCriterion("param_name_2 not in", values, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamName2Between(String value1, String value2) {
            addCriterion("param_name_2 between", value1, value2, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamName2NotBetween(String value1, String value2) {
            addCriterion("param_name_2 not between", value1, value2, "paramName2");
            return (Criteria) this;
        }

        public Criteria andParamCode2IsNull() {
            addCriterion("param_code_2 is null");
            return (Criteria) this;
        }

        public Criteria andParamCode2IsNotNull() {
            addCriterion("param_code_2 is not null");
            return (Criteria) this;
        }

        public Criteria andParamCode2EqualTo(String value) {
            addCriterion("param_code_2 =", value, "paramCode2");
            return (Criteria) this;
        }

        public Criteria andParamCode2NotEqualTo(String value) {
            addCriterion("param_code_2 <>", value, "paramCode2");
            return (Criteria) this;
        }

        public Criteria andParamCode2GreaterThan(String value) {
            addCriterion("param_code_2 >", value, "paramCode2");
            return (Criteria) this;
        }

        public Criteria andParamCode2GreaterThanOrEqualTo(String value) {
            addCriterion("param_code_2 >=", value, "paramCode2");
            return (Criteria) this;
        }

        public Criteria andParamCode2LessThan(String value) {
            addCriterion("param_code_2 <", value, "paramCode2");
            return (Criteria) this;
        }

        public Criteria andParamCode2LessThanOrEqualTo(String value) {
            addCriterion("param_code_2 <=", value, "paramCode2");
            return (Criteria) this;
        }

        public Criteria andParamCode2Like(String value) {
            addCriterion("param_code_2 like", value, "paramCode2");
            return (Criteria) this;
        }

        public Criteria andParamCode2NotLike(String value) {
            addCriterion("param_code_2 not like", value, "paramCode2");
            return (Criteria) this;
        }

        public Criteria andParamCode2In(List<String> values) {
            addCriterion("param_code_2 in", values, "paramCode2");
            return (Criteria) this;
        }

        public Criteria andParamCode2NotIn(List<String> values) {
            addCriterion("param_code_2 not in", values, "paramCode2");
            return (Criteria) this;
        }

        public Criteria andParamCode2Between(String value1, String value2) {
            addCriterion("param_code_2 between", value1, value2, "paramCode2");
            return (Criteria) this;
        }

        public Criteria andParamCode2NotBetween(String value1, String value2) {
            addCriterion("param_code_2 not between", value1, value2, "paramCode2");
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