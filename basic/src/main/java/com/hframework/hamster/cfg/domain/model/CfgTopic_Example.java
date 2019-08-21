package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgTopic_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgTopic_Example() {
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

        public Criteria andCfgTopicIdIsNull() {
            addCriterion("cfg_topic_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgTopicIdIsNotNull() {
            addCriterion("cfg_topic_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTopicIdEqualTo(Long value) {
            addCriterion("cfg_topic_id =", value, "cfgTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgTopicIdNotEqualTo(Long value) {
            addCriterion("cfg_topic_id <>", value, "cfgTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgTopicIdGreaterThan(Long value) {
            addCriterion("cfg_topic_id >", value, "cfgTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgTopicIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_topic_id >=", value, "cfgTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgTopicIdLessThan(Long value) {
            addCriterion("cfg_topic_id <", value, "cfgTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgTopicIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_topic_id <=", value, "cfgTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgTopicIdIn(List<Long> values) {
            addCriterion("cfg_topic_id in", values, "cfgTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgTopicIdNotIn(List<Long> values) {
            addCriterion("cfg_topic_id not in", values, "cfgTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgTopicIdBetween(Long value1, Long value2) {
            addCriterion("cfg_topic_id between", value1, value2, "cfgTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgTopicIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_topic_id not between", value1, value2, "cfgTopicId");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameIsNull() {
            addCriterion("cfg_topic_name is null");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameIsNotNull() {
            addCriterion("cfg_topic_name is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameEqualTo(String value) {
            addCriterion("cfg_topic_name =", value, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameNotEqualTo(String value) {
            addCriterion("cfg_topic_name <>", value, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameGreaterThan(String value) {
            addCriterion("cfg_topic_name >", value, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_topic_name >=", value, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameLessThan(String value) {
            addCriterion("cfg_topic_name <", value, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameLessThanOrEqualTo(String value) {
            addCriterion("cfg_topic_name <=", value, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameLike(String value) {
            addCriterion("cfg_topic_name like", value, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameNotLike(String value) {
            addCriterion("cfg_topic_name not like", value, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameIn(List<String> values) {
            addCriterion("cfg_topic_name in", values, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameNotIn(List<String> values) {
            addCriterion("cfg_topic_name not in", values, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameBetween(String value1, String value2) {
            addCriterion("cfg_topic_name between", value1, value2, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicNameNotBetween(String value1, String value2) {
            addCriterion("cfg_topic_name not between", value1, value2, "cfgTopicName");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescIsNull() {
            addCriterion("cfg_topic_desc is null");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescIsNotNull() {
            addCriterion("cfg_topic_desc is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescEqualTo(String value) {
            addCriterion("cfg_topic_desc =", value, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescNotEqualTo(String value) {
            addCriterion("cfg_topic_desc <>", value, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescGreaterThan(String value) {
            addCriterion("cfg_topic_desc >", value, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_topic_desc >=", value, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescLessThan(String value) {
            addCriterion("cfg_topic_desc <", value, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescLessThanOrEqualTo(String value) {
            addCriterion("cfg_topic_desc <=", value, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescLike(String value) {
            addCriterion("cfg_topic_desc like", value, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescNotLike(String value) {
            addCriterion("cfg_topic_desc not like", value, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescIn(List<String> values) {
            addCriterion("cfg_topic_desc in", values, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescNotIn(List<String> values) {
            addCriterion("cfg_topic_desc not in", values, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescBetween(String value1, String value2) {
            addCriterion("cfg_topic_desc between", value1, value2, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicDescNotBetween(String value1, String value2) {
            addCriterion("cfg_topic_desc not between", value1, value2, "cfgTopicDesc");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeIsNull() {
            addCriterion("cfg_topic_type is null");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeIsNotNull() {
            addCriterion("cfg_topic_type is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeEqualTo(Byte value) {
            addCriterion("cfg_topic_type =", value, "cfgTopicType");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeNotEqualTo(Byte value) {
            addCriterion("cfg_topic_type <>", value, "cfgTopicType");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeGreaterThan(Byte value) {
            addCriterion("cfg_topic_type >", value, "cfgTopicType");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("cfg_topic_type >=", value, "cfgTopicType");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeLessThan(Byte value) {
            addCriterion("cfg_topic_type <", value, "cfgTopicType");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeLessThanOrEqualTo(Byte value) {
            addCriterion("cfg_topic_type <=", value, "cfgTopicType");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeIn(List<Byte> values) {
            addCriterion("cfg_topic_type in", values, "cfgTopicType");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeNotIn(List<Byte> values) {
            addCriterion("cfg_topic_type not in", values, "cfgTopicType");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeBetween(Byte value1, Byte value2) {
            addCriterion("cfg_topic_type between", value1, value2, "cfgTopicType");
            return (Criteria) this;
        }

        public Criteria andCfgTopicTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("cfg_topic_type not between", value1, value2, "cfgTopicType");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeIsNull() {
            addCriterion("cfg_topic_code is null");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeIsNotNull() {
            addCriterion("cfg_topic_code is not null");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeEqualTo(String value) {
            addCriterion("cfg_topic_code =", value, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeNotEqualTo(String value) {
            addCriterion("cfg_topic_code <>", value, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeGreaterThan(String value) {
            addCriterion("cfg_topic_code >", value, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_topic_code >=", value, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeLessThan(String value) {
            addCriterion("cfg_topic_code <", value, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeLessThanOrEqualTo(String value) {
            addCriterion("cfg_topic_code <=", value, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeLike(String value) {
            addCriterion("cfg_topic_code like", value, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeNotLike(String value) {
            addCriterion("cfg_topic_code not like", value, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeIn(List<String> values) {
            addCriterion("cfg_topic_code in", values, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeNotIn(List<String> values) {
            addCriterion("cfg_topic_code not in", values, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeBetween(String value1, String value2) {
            addCriterion("cfg_topic_code between", value1, value2, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andCfgTopicCodeNotBetween(String value1, String value2) {
            addCriterion("cfg_topic_code not between", value1, value2, "cfgTopicCode");
            return (Criteria) this;
        }

        public Criteria andPartitionsIsNull() {
            addCriterion("PARTITIONS is null");
            return (Criteria) this;
        }

        public Criteria andPartitionsIsNotNull() {
            addCriterion("PARTITIONS is not null");
            return (Criteria) this;
        }

        public Criteria andPartitionsEqualTo(Byte value) {
            addCriterion("PARTITIONS =", value, "partitions");
            return (Criteria) this;
        }

        public Criteria andPartitionsNotEqualTo(Byte value) {
            addCriterion("PARTITIONS <>", value, "partitions");
            return (Criteria) this;
        }

        public Criteria andPartitionsGreaterThan(Byte value) {
            addCriterion("PARTITIONS >", value, "partitions");
            return (Criteria) this;
        }

        public Criteria andPartitionsGreaterThanOrEqualTo(Byte value) {
            addCriterion("PARTITIONS >=", value, "partitions");
            return (Criteria) this;
        }

        public Criteria andPartitionsLessThan(Byte value) {
            addCriterion("PARTITIONS <", value, "partitions");
            return (Criteria) this;
        }

        public Criteria andPartitionsLessThanOrEqualTo(Byte value) {
            addCriterion("PARTITIONS <=", value, "partitions");
            return (Criteria) this;
        }

        public Criteria andPartitionsIn(List<Byte> values) {
            addCriterion("PARTITIONS in", values, "partitions");
            return (Criteria) this;
        }

        public Criteria andPartitionsNotIn(List<Byte> values) {
            addCriterion("PARTITIONS not in", values, "partitions");
            return (Criteria) this;
        }

        public Criteria andPartitionsBetween(Byte value1, Byte value2) {
            addCriterion("PARTITIONS between", value1, value2, "partitions");
            return (Criteria) this;
        }

        public Criteria andPartitionsNotBetween(Byte value1, Byte value2) {
            addCriterion("PARTITIONS not between", value1, value2, "partitions");
            return (Criteria) this;
        }

        public Criteria andReplicasIsNull() {
            addCriterion("replicas is null");
            return (Criteria) this;
        }

        public Criteria andReplicasIsNotNull() {
            addCriterion("replicas is not null");
            return (Criteria) this;
        }

        public Criteria andReplicasEqualTo(Byte value) {
            addCriterion("replicas =", value, "replicas");
            return (Criteria) this;
        }

        public Criteria andReplicasNotEqualTo(Byte value) {
            addCriterion("replicas <>", value, "replicas");
            return (Criteria) this;
        }

        public Criteria andReplicasGreaterThan(Byte value) {
            addCriterion("replicas >", value, "replicas");
            return (Criteria) this;
        }

        public Criteria andReplicasGreaterThanOrEqualTo(Byte value) {
            addCriterion("replicas >=", value, "replicas");
            return (Criteria) this;
        }

        public Criteria andReplicasLessThan(Byte value) {
            addCriterion("replicas <", value, "replicas");
            return (Criteria) this;
        }

        public Criteria andReplicasLessThanOrEqualTo(Byte value) {
            addCriterion("replicas <=", value, "replicas");
            return (Criteria) this;
        }

        public Criteria andReplicasIn(List<Byte> values) {
            addCriterion("replicas in", values, "replicas");
            return (Criteria) this;
        }

        public Criteria andReplicasNotIn(List<Byte> values) {
            addCriterion("replicas not in", values, "replicas");
            return (Criteria) this;
        }

        public Criteria andReplicasBetween(Byte value1, Byte value2) {
            addCriterion("replicas between", value1, value2, "replicas");
            return (Criteria) this;
        }

        public Criteria andReplicasNotBetween(Byte value1, Byte value2) {
            addCriterion("replicas not between", value1, value2, "replicas");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNull() {
            addCriterion("serial_no is null");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNotNull() {
            addCriterion("serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNoEqualTo(Short value) {
            addCriterion("serial_no =", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotEqualTo(Short value) {
            addCriterion("serial_no <>", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThan(Short value) {
            addCriterion("serial_no >", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThanOrEqualTo(Short value) {
            addCriterion("serial_no >=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThan(Short value) {
            addCriterion("serial_no <", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThanOrEqualTo(Short value) {
            addCriterion("serial_no <=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIn(List<Short> values) {
            addCriterion("serial_no in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotIn(List<Short> values) {
            addCriterion("serial_no not in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoBetween(Short value1, Short value2) {
            addCriterion("serial_no between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotBetween(Short value1, Short value2) {
            addCriterion("serial_no not between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`STATUS` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`STATUS` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("`STATUS` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("`STATUS` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("`STATUS` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("`STATUS` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("`STATUS` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("`STATUS` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("`STATUS` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("`STATUS` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("`STATUS` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("`STATUS` not between", value1, value2, "status");
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