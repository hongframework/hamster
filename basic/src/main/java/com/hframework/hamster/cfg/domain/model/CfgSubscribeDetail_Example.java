package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgSubscribeDetail_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgSubscribeDetail_Example() {
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

        public Criteria andCfgSubscribeDetailIdIsNull() {
            addCriterion("cfg_subscribe_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeDetailIdIsNotNull() {
            addCriterion("cfg_subscribe_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeDetailIdEqualTo(Long value) {
            addCriterion("cfg_subscribe_detail_id =", value, "cfgSubscribeDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeDetailIdNotEqualTo(Long value) {
            addCriterion("cfg_subscribe_detail_id <>", value, "cfgSubscribeDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeDetailIdGreaterThan(Long value) {
            addCriterion("cfg_subscribe_detail_id >", value, "cfgSubscribeDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeDetailIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_subscribe_detail_id >=", value, "cfgSubscribeDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeDetailIdLessThan(Long value) {
            addCriterion("cfg_subscribe_detail_id <", value, "cfgSubscribeDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeDetailIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_subscribe_detail_id <=", value, "cfgSubscribeDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeDetailIdIn(List<Long> values) {
            addCriterion("cfg_subscribe_detail_id in", values, "cfgSubscribeDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeDetailIdNotIn(List<Long> values) {
            addCriterion("cfg_subscribe_detail_id not in", values, "cfgSubscribeDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeDetailIdBetween(Long value1, Long value2) {
            addCriterion("cfg_subscribe_detail_id between", value1, value2, "cfgSubscribeDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeDetailIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_subscribe_detail_id not between", value1, value2, "cfgSubscribeDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdIsNull() {
            addCriterion("cfg_subscribe_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdIsNotNull() {
            addCriterion("cfg_subscribe_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdEqualTo(Long value) {
            addCriterion("cfg_subscribe_id =", value, "cfgSubscribeId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdNotEqualTo(Long value) {
            addCriterion("cfg_subscribe_id <>", value, "cfgSubscribeId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdGreaterThan(Long value) {
            addCriterion("cfg_subscribe_id >", value, "cfgSubscribeId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_subscribe_id >=", value, "cfgSubscribeId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdLessThan(Long value) {
            addCriterion("cfg_subscribe_id <", value, "cfgSubscribeId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_subscribe_id <=", value, "cfgSubscribeId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdIn(List<Long> values) {
            addCriterion("cfg_subscribe_id in", values, "cfgSubscribeId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdNotIn(List<Long> values) {
            addCriterion("cfg_subscribe_id not in", values, "cfgSubscribeId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdBetween(Long value1, Long value2) {
            addCriterion("cfg_subscribe_id between", value1, value2, "cfgSubscribeId");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_subscribe_id not between", value1, value2, "cfgSubscribeId");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameIsNull() {
            addCriterion("db_object_name is null");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameIsNotNull() {
            addCriterion("db_object_name is not null");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameEqualTo(String value) {
            addCriterion("db_object_name =", value, "dbObjectName");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameNotEqualTo(String value) {
            addCriterion("db_object_name <>", value, "dbObjectName");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameGreaterThan(String value) {
            addCriterion("db_object_name >", value, "dbObjectName");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("db_object_name >=", value, "dbObjectName");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameLessThan(String value) {
            addCriterion("db_object_name <", value, "dbObjectName");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameLessThanOrEqualTo(String value) {
            addCriterion("db_object_name <=", value, "dbObjectName");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameLike(String value) {
            addCriterion("db_object_name like", value, "dbObjectName");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameNotLike(String value) {
            addCriterion("db_object_name not like", value, "dbObjectName");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameIn(List<String> values) {
            addCriterion("db_object_name in", values, "dbObjectName");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameNotIn(List<String> values) {
            addCriterion("db_object_name not in", values, "dbObjectName");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameBetween(String value1, String value2) {
            addCriterion("db_object_name between", value1, value2, "dbObjectName");
            return (Criteria) this;
        }

        public Criteria andDbObjectNameNotBetween(String value1, String value2) {
            addCriterion("db_object_name not between", value1, value2, "dbObjectName");
            return (Criteria) this;
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

        public Criteria andDbObjectDatasIsNull() {
            addCriterion("db_object_datas is null");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasIsNotNull() {
            addCriterion("db_object_datas is not null");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasEqualTo(String value) {
            addCriterion("db_object_datas =", value, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasNotEqualTo(String value) {
            addCriterion("db_object_datas <>", value, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasGreaterThan(String value) {
            addCriterion("db_object_datas >", value, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasGreaterThanOrEqualTo(String value) {
            addCriterion("db_object_datas >=", value, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasLessThan(String value) {
            addCriterion("db_object_datas <", value, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasLessThanOrEqualTo(String value) {
            addCriterion("db_object_datas <=", value, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasLike(String value) {
            addCriterion("db_object_datas like", value, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasNotLike(String value) {
            addCriterion("db_object_datas not like", value, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasIn(List<String> values) {
            addCriterion("db_object_datas in", values, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasNotIn(List<String> values) {
            addCriterion("db_object_datas not in", values, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasBetween(String value1, String value2) {
            addCriterion("db_object_datas between", value1, value2, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andDbObjectDatasNotBetween(String value1, String value2) {
            addCriterion("db_object_datas not between", value1, value2, "dbObjectDatas");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyIsNull() {
            addCriterion("partition_strategy is null");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyIsNotNull() {
            addCriterion("partition_strategy is not null");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyEqualTo(Byte value) {
            addCriterion("partition_strategy =", value, "partitionStrategy");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyNotEqualTo(Byte value) {
            addCriterion("partition_strategy <>", value, "partitionStrategy");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyGreaterThan(Byte value) {
            addCriterion("partition_strategy >", value, "partitionStrategy");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyGreaterThanOrEqualTo(Byte value) {
            addCriterion("partition_strategy >=", value, "partitionStrategy");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyLessThan(Byte value) {
            addCriterion("partition_strategy <", value, "partitionStrategy");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyLessThanOrEqualTo(Byte value) {
            addCriterion("partition_strategy <=", value, "partitionStrategy");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyIn(List<Byte> values) {
            addCriterion("partition_strategy in", values, "partitionStrategy");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyNotIn(List<Byte> values) {
            addCriterion("partition_strategy not in", values, "partitionStrategy");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyBetween(Byte value1, Byte value2) {
            addCriterion("partition_strategy between", value1, value2, "partitionStrategy");
            return (Criteria) this;
        }

        public Criteria andPartitionStrategyNotBetween(Byte value1, Byte value2) {
            addCriterion("partition_strategy not between", value1, value2, "partitionStrategy");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyIsNull() {
            addCriterion("partition_key is null");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyIsNotNull() {
            addCriterion("partition_key is not null");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyEqualTo(String value) {
            addCriterion("partition_key =", value, "partitionKey");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyNotEqualTo(String value) {
            addCriterion("partition_key <>", value, "partitionKey");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyGreaterThan(String value) {
            addCriterion("partition_key >", value, "partitionKey");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyGreaterThanOrEqualTo(String value) {
            addCriterion("partition_key >=", value, "partitionKey");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyLessThan(String value) {
            addCriterion("partition_key <", value, "partitionKey");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyLessThanOrEqualTo(String value) {
            addCriterion("partition_key <=", value, "partitionKey");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyLike(String value) {
            addCriterion("partition_key like", value, "partitionKey");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyNotLike(String value) {
            addCriterion("partition_key not like", value, "partitionKey");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyIn(List<String> values) {
            addCriterion("partition_key in", values, "partitionKey");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyNotIn(List<String> values) {
            addCriterion("partition_key not in", values, "partitionKey");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyBetween(String value1, String value2) {
            addCriterion("partition_key between", value1, value2, "partitionKey");
            return (Criteria) this;
        }

        public Criteria andPartitionKeyNotBetween(String value1, String value2) {
            addCriterion("partition_key not between", value1, value2, "partitionKey");
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

        public Criteria andDataFilterExpressionIsNull() {
            addCriterion("data_filter_expression is null");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionIsNotNull() {
            addCriterion("data_filter_expression is not null");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionEqualTo(String value) {
            addCriterion("data_filter_expression =", value, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionNotEqualTo(String value) {
            addCriterion("data_filter_expression <>", value, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionGreaterThan(String value) {
            addCriterion("data_filter_expression >", value, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionGreaterThanOrEqualTo(String value) {
            addCriterion("data_filter_expression >=", value, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionLessThan(String value) {
            addCriterion("data_filter_expression <", value, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionLessThanOrEqualTo(String value) {
            addCriterion("data_filter_expression <=", value, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionLike(String value) {
            addCriterion("data_filter_expression like", value, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionNotLike(String value) {
            addCriterion("data_filter_expression not like", value, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionIn(List<String> values) {
            addCriterion("data_filter_expression in", values, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionNotIn(List<String> values) {
            addCriterion("data_filter_expression not in", values, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionBetween(String value1, String value2) {
            addCriterion("data_filter_expression between", value1, value2, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDataFilterExpressionNotBetween(String value1, String value2) {
            addCriterion("data_filter_expression not between", value1, value2, "dataFilterExpression");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeIsNull() {
            addCriterion("db_object_operate_type is null");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeIsNotNull() {
            addCriterion("db_object_operate_type is not null");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeEqualTo(Byte value) {
            addCriterion("db_object_operate_type =", value, "dbObjectOperateType");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeNotEqualTo(Byte value) {
            addCriterion("db_object_operate_type <>", value, "dbObjectOperateType");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeGreaterThan(Byte value) {
            addCriterion("db_object_operate_type >", value, "dbObjectOperateType");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("db_object_operate_type >=", value, "dbObjectOperateType");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeLessThan(Byte value) {
            addCriterion("db_object_operate_type <", value, "dbObjectOperateType");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeLessThanOrEqualTo(Byte value) {
            addCriterion("db_object_operate_type <=", value, "dbObjectOperateType");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeIn(List<Byte> values) {
            addCriterion("db_object_operate_type in", values, "dbObjectOperateType");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeNotIn(List<Byte> values) {
            addCriterion("db_object_operate_type not in", values, "dbObjectOperateType");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeBetween(Byte value1, Byte value2) {
            addCriterion("db_object_operate_type between", value1, value2, "dbObjectOperateType");
            return (Criteria) this;
        }

        public Criteria andDbObjectOperateTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("db_object_operate_type not between", value1, value2, "dbObjectOperateType");
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