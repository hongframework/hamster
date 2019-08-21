package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgBroker_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgBroker_Example() {
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

        public Criteria andCfgBrokerIdIsNull() {
            addCriterion("cfg_broker_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerIdIsNotNull() {
            addCriterion("cfg_broker_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerIdEqualTo(Long value) {
            addCriterion("cfg_broker_id =", value, "cfgBrokerId");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerIdNotEqualTo(Long value) {
            addCriterion("cfg_broker_id <>", value, "cfgBrokerId");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerIdGreaterThan(Long value) {
            addCriterion("cfg_broker_id >", value, "cfgBrokerId");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_broker_id >=", value, "cfgBrokerId");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerIdLessThan(Long value) {
            addCriterion("cfg_broker_id <", value, "cfgBrokerId");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_broker_id <=", value, "cfgBrokerId");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerIdIn(List<Long> values) {
            addCriterion("cfg_broker_id in", values, "cfgBrokerId");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerIdNotIn(List<Long> values) {
            addCriterion("cfg_broker_id not in", values, "cfgBrokerId");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerIdBetween(Long value1, Long value2) {
            addCriterion("cfg_broker_id between", value1, value2, "cfgBrokerId");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_broker_id not between", value1, value2, "cfgBrokerId");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameIsNull() {
            addCriterion("cfg_broker_name is null");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameIsNotNull() {
            addCriterion("cfg_broker_name is not null");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameEqualTo(String value) {
            addCriterion("cfg_broker_name =", value, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameNotEqualTo(String value) {
            addCriterion("cfg_broker_name <>", value, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameGreaterThan(String value) {
            addCriterion("cfg_broker_name >", value, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_broker_name >=", value, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameLessThan(String value) {
            addCriterion("cfg_broker_name <", value, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameLessThanOrEqualTo(String value) {
            addCriterion("cfg_broker_name <=", value, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameLike(String value) {
            addCriterion("cfg_broker_name like", value, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameNotLike(String value) {
            addCriterion("cfg_broker_name not like", value, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameIn(List<String> values) {
            addCriterion("cfg_broker_name in", values, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameNotIn(List<String> values) {
            addCriterion("cfg_broker_name not in", values, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameBetween(String value1, String value2) {
            addCriterion("cfg_broker_name between", value1, value2, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerNameNotBetween(String value1, String value2) {
            addCriterion("cfg_broker_name not between", value1, value2, "cfgBrokerName");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeIsNull() {
            addCriterion("cfg_broker_code is null");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeIsNotNull() {
            addCriterion("cfg_broker_code is not null");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeEqualTo(String value) {
            addCriterion("cfg_broker_code =", value, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeNotEqualTo(String value) {
            addCriterion("cfg_broker_code <>", value, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeGreaterThan(String value) {
            addCriterion("cfg_broker_code >", value, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_broker_code >=", value, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeLessThan(String value) {
            addCriterion("cfg_broker_code <", value, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeLessThanOrEqualTo(String value) {
            addCriterion("cfg_broker_code <=", value, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeLike(String value) {
            addCriterion("cfg_broker_code like", value, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeNotLike(String value) {
            addCriterion("cfg_broker_code not like", value, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeIn(List<String> values) {
            addCriterion("cfg_broker_code in", values, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeNotIn(List<String> values) {
            addCriterion("cfg_broker_code not in", values, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeBetween(String value1, String value2) {
            addCriterion("cfg_broker_code between", value1, value2, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerCodeNotBetween(String value1, String value2) {
            addCriterion("cfg_broker_code not between", value1, value2, "cfgBrokerCode");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeIsNull() {
            addCriterion("cfg_broker_type is null");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeIsNotNull() {
            addCriterion("cfg_broker_type is not null");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeEqualTo(Byte value) {
            addCriterion("cfg_broker_type =", value, "cfgBrokerType");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeNotEqualTo(Byte value) {
            addCriterion("cfg_broker_type <>", value, "cfgBrokerType");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeGreaterThan(Byte value) {
            addCriterion("cfg_broker_type >", value, "cfgBrokerType");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("cfg_broker_type >=", value, "cfgBrokerType");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeLessThan(Byte value) {
            addCriterion("cfg_broker_type <", value, "cfgBrokerType");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeLessThanOrEqualTo(Byte value) {
            addCriterion("cfg_broker_type <=", value, "cfgBrokerType");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeIn(List<Byte> values) {
            addCriterion("cfg_broker_type in", values, "cfgBrokerType");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeNotIn(List<Byte> values) {
            addCriterion("cfg_broker_type not in", values, "cfgBrokerType");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeBetween(Byte value1, Byte value2) {
            addCriterion("cfg_broker_type between", value1, value2, "cfgBrokerType");
            return (Criteria) this;
        }

        public Criteria andCfgBrokerTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("cfg_broker_type not between", value1, value2, "cfgBrokerType");
            return (Criteria) this;
        }

        public Criteria andAddrListIsNull() {
            addCriterion("addr_list is null");
            return (Criteria) this;
        }

        public Criteria andAddrListIsNotNull() {
            addCriterion("addr_list is not null");
            return (Criteria) this;
        }

        public Criteria andAddrListEqualTo(String value) {
            addCriterion("addr_list =", value, "addrList");
            return (Criteria) this;
        }

        public Criteria andAddrListNotEqualTo(String value) {
            addCriterion("addr_list <>", value, "addrList");
            return (Criteria) this;
        }

        public Criteria andAddrListGreaterThan(String value) {
            addCriterion("addr_list >", value, "addrList");
            return (Criteria) this;
        }

        public Criteria andAddrListGreaterThanOrEqualTo(String value) {
            addCriterion("addr_list >=", value, "addrList");
            return (Criteria) this;
        }

        public Criteria andAddrListLessThan(String value) {
            addCriterion("addr_list <", value, "addrList");
            return (Criteria) this;
        }

        public Criteria andAddrListLessThanOrEqualTo(String value) {
            addCriterion("addr_list <=", value, "addrList");
            return (Criteria) this;
        }

        public Criteria andAddrListLike(String value) {
            addCriterion("addr_list like", value, "addrList");
            return (Criteria) this;
        }

        public Criteria andAddrListNotLike(String value) {
            addCriterion("addr_list not like", value, "addrList");
            return (Criteria) this;
        }

        public Criteria andAddrListIn(List<String> values) {
            addCriterion("addr_list in", values, "addrList");
            return (Criteria) this;
        }

        public Criteria andAddrListNotIn(List<String> values) {
            addCriterion("addr_list not in", values, "addrList");
            return (Criteria) this;
        }

        public Criteria andAddrListBetween(String value1, String value2) {
            addCriterion("addr_list between", value1, value2, "addrList");
            return (Criteria) this;
        }

        public Criteria andAddrListNotBetween(String value1, String value2) {
            addCriterion("addr_list not between", value1, value2, "addrList");
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

        public Criteria andZkAddrListIsNull() {
            addCriterion("zk_addr_list is null");
            return (Criteria) this;
        }

        public Criteria andZkAddrListIsNotNull() {
            addCriterion("zk_addr_list is not null");
            return (Criteria) this;
        }

        public Criteria andZkAddrListEqualTo(String value) {
            addCriterion("zk_addr_list =", value, "zkAddrList");
            return (Criteria) this;
        }

        public Criteria andZkAddrListNotEqualTo(String value) {
            addCriterion("zk_addr_list <>", value, "zkAddrList");
            return (Criteria) this;
        }

        public Criteria andZkAddrListGreaterThan(String value) {
            addCriterion("zk_addr_list >", value, "zkAddrList");
            return (Criteria) this;
        }

        public Criteria andZkAddrListGreaterThanOrEqualTo(String value) {
            addCriterion("zk_addr_list >=", value, "zkAddrList");
            return (Criteria) this;
        }

        public Criteria andZkAddrListLessThan(String value) {
            addCriterion("zk_addr_list <", value, "zkAddrList");
            return (Criteria) this;
        }

        public Criteria andZkAddrListLessThanOrEqualTo(String value) {
            addCriterion("zk_addr_list <=", value, "zkAddrList");
            return (Criteria) this;
        }

        public Criteria andZkAddrListLike(String value) {
            addCriterion("zk_addr_list like", value, "zkAddrList");
            return (Criteria) this;
        }

        public Criteria andZkAddrListNotLike(String value) {
            addCriterion("zk_addr_list not like", value, "zkAddrList");
            return (Criteria) this;
        }

        public Criteria andZkAddrListIn(List<String> values) {
            addCriterion("zk_addr_list in", values, "zkAddrList");
            return (Criteria) this;
        }

        public Criteria andZkAddrListNotIn(List<String> values) {
            addCriterion("zk_addr_list not in", values, "zkAddrList");
            return (Criteria) this;
        }

        public Criteria andZkAddrListBetween(String value1, String value2) {
            addCriterion("zk_addr_list between", value1, value2, "zkAddrList");
            return (Criteria) this;
        }

        public Criteria andZkAddrListNotBetween(String value1, String value2) {
            addCriterion("zk_addr_list not between", value1, value2, "zkAddrList");
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