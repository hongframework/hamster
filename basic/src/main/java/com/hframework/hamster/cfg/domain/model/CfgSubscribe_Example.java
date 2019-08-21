package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgSubscribe_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgSubscribe_Example() {
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

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Short value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Short value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Short value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Short value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Short value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Short> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Short> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Short value1, Short value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Short value1, Short value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdIsNull() {
            addCriterion("cfg_datasource_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdIsNotNull() {
            addCriterion("cfg_datasource_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdEqualTo(Long value) {
            addCriterion("cfg_datasource_id =", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdNotEqualTo(Long value) {
            addCriterion("cfg_datasource_id <>", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdGreaterThan(Long value) {
            addCriterion("cfg_datasource_id >", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_datasource_id >=", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdLessThan(Long value) {
            addCriterion("cfg_datasource_id <", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_datasource_id <=", value, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdIn(List<Long> values) {
            addCriterion("cfg_datasource_id in", values, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdNotIn(List<Long> values) {
            addCriterion("cfg_datasource_id not in", values, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdBetween(Long value1, Long value2) {
            addCriterion("cfg_datasource_id between", value1, value2, "cfgDatasourceId");
            return (Criteria) this;
        }

        public Criteria andCfgDatasourceIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_datasource_id not between", value1, value2, "cfgDatasourceId");
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

        public Criteria andLogBeginPositionIsNull() {
            addCriterion("log_begin_position is null");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionIsNotNull() {
            addCriterion("log_begin_position is not null");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionEqualTo(Long value) {
            addCriterion("log_begin_position =", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionNotEqualTo(Long value) {
            addCriterion("log_begin_position <>", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionGreaterThan(Long value) {
            addCriterion("log_begin_position >", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionGreaterThanOrEqualTo(Long value) {
            addCriterion("log_begin_position >=", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionLessThan(Long value) {
            addCriterion("log_begin_position <", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionLessThanOrEqualTo(Long value) {
            addCriterion("log_begin_position <=", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionIn(List<Long> values) {
            addCriterion("log_begin_position in", values, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionNotIn(List<Long> values) {
            addCriterion("log_begin_position not in", values, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionBetween(Long value1, Long value2) {
            addCriterion("log_begin_position between", value1, value2, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionNotBetween(Long value1, Long value2) {
            addCriterion("log_begin_position not between", value1, value2, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampIsNull() {
            addCriterion("log_begin_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampIsNotNull() {
            addCriterion("log_begin_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampEqualTo(Date value) {
            addCriterion("log_begin_timestamp =", value, "logBeginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampNotEqualTo(Date value) {
            addCriterion("log_begin_timestamp <>", value, "logBeginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampGreaterThan(Date value) {
            addCriterion("log_begin_timestamp >", value, "logBeginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("log_begin_timestamp >=", value, "logBeginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampLessThan(Date value) {
            addCriterion("log_begin_timestamp <", value, "logBeginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampLessThanOrEqualTo(Date value) {
            addCriterion("log_begin_timestamp <=", value, "logBeginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampIn(List<Date> values) {
            addCriterion("log_begin_timestamp in", values, "logBeginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampNotIn(List<Date> values) {
            addCriterion("log_begin_timestamp not in", values, "logBeginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampBetween(Date value1, Date value2) {
            addCriterion("log_begin_timestamp between", value1, value2, "logBeginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogBeginTimestampNotBetween(Date value1, Date value2) {
            addCriterion("log_begin_timestamp not between", value1, value2, "logBeginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionIsNull() {
            addCriterion("log_end_position is null");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionIsNotNull() {
            addCriterion("log_end_position is not null");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionEqualTo(Long value) {
            addCriterion("log_end_position =", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionNotEqualTo(Long value) {
            addCriterion("log_end_position <>", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionGreaterThan(Long value) {
            addCriterion("log_end_position >", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionGreaterThanOrEqualTo(Long value) {
            addCriterion("log_end_position >=", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionLessThan(Long value) {
            addCriterion("log_end_position <", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionLessThanOrEqualTo(Long value) {
            addCriterion("log_end_position <=", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionIn(List<Long> values) {
            addCriterion("log_end_position in", values, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionNotIn(List<Long> values) {
            addCriterion("log_end_position not in", values, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionBetween(Long value1, Long value2) {
            addCriterion("log_end_position between", value1, value2, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionNotBetween(Long value1, Long value2) {
            addCriterion("log_end_position not between", value1, value2, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampIsNull() {
            addCriterion("log_end_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampIsNotNull() {
            addCriterion("log_end_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampEqualTo(Date value) {
            addCriterion("log_end_timestamp =", value, "logEndTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampNotEqualTo(Date value) {
            addCriterion("log_end_timestamp <>", value, "logEndTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampGreaterThan(Date value) {
            addCriterion("log_end_timestamp >", value, "logEndTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("log_end_timestamp >=", value, "logEndTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampLessThan(Date value) {
            addCriterion("log_end_timestamp <", value, "logEndTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampLessThanOrEqualTo(Date value) {
            addCriterion("log_end_timestamp <=", value, "logEndTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampIn(List<Date> values) {
            addCriterion("log_end_timestamp in", values, "logEndTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampNotIn(List<Date> values) {
            addCriterion("log_end_timestamp not in", values, "logEndTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampBetween(Date value1, Date value2) {
            addCriterion("log_end_timestamp between", value1, value2, "logEndTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogEndTimestampNotBetween(Date value1, Date value2) {
            addCriterion("log_end_timestamp not between", value1, value2, "logEndTimestamp");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionIsNull() {
            addCriterion("log_current_position is null");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionIsNotNull() {
            addCriterion("log_current_position is not null");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionEqualTo(Long value) {
            addCriterion("log_current_position =", value, "logCurrentPosition");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionNotEqualTo(Long value) {
            addCriterion("log_current_position <>", value, "logCurrentPosition");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionGreaterThan(Long value) {
            addCriterion("log_current_position >", value, "logCurrentPosition");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionGreaterThanOrEqualTo(Long value) {
            addCriterion("log_current_position >=", value, "logCurrentPosition");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionLessThan(Long value) {
            addCriterion("log_current_position <", value, "logCurrentPosition");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionLessThanOrEqualTo(Long value) {
            addCriterion("log_current_position <=", value, "logCurrentPosition");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionIn(List<Long> values) {
            addCriterion("log_current_position in", values, "logCurrentPosition");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionNotIn(List<Long> values) {
            addCriterion("log_current_position not in", values, "logCurrentPosition");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionBetween(Long value1, Long value2) {
            addCriterion("log_current_position between", value1, value2, "logCurrentPosition");
            return (Criteria) this;
        }

        public Criteria andLogCurrentPositionNotBetween(Long value1, Long value2) {
            addCriterion("log_current_position not between", value1, value2, "logCurrentPosition");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameIsNull() {
            addCriterion("cfg_subscribe_name is null");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameIsNotNull() {
            addCriterion("cfg_subscribe_name is not null");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameEqualTo(String value) {
            addCriterion("cfg_subscribe_name =", value, "cfgSubscribeName");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameNotEqualTo(String value) {
            addCriterion("cfg_subscribe_name <>", value, "cfgSubscribeName");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameGreaterThan(String value) {
            addCriterion("cfg_subscribe_name >", value, "cfgSubscribeName");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_subscribe_name >=", value, "cfgSubscribeName");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameLessThan(String value) {
            addCriterion("cfg_subscribe_name <", value, "cfgSubscribeName");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameLessThanOrEqualTo(String value) {
            addCriterion("cfg_subscribe_name <=", value, "cfgSubscribeName");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameLike(String value) {
            addCriterion("cfg_subscribe_name like", value, "cfgSubscribeName");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameNotLike(String value) {
            addCriterion("cfg_subscribe_name not like", value, "cfgSubscribeName");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameIn(List<String> values) {
            addCriterion("cfg_subscribe_name in", values, "cfgSubscribeName");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameNotIn(List<String> values) {
            addCriterion("cfg_subscribe_name not in", values, "cfgSubscribeName");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameBetween(String value1, String value2) {
            addCriterion("cfg_subscribe_name between", value1, value2, "cfgSubscribeName");
            return (Criteria) this;
        }

        public Criteria andCfgSubscribeNameNotBetween(String value1, String value2) {
            addCriterion("cfg_subscribe_name not between", value1, value2, "cfgSubscribeName");
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

        public Criteria andLogBeginFileIsNull() {
            addCriterion("log_begin_file is null");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileIsNotNull() {
            addCriterion("log_begin_file is not null");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileEqualTo(String value) {
            addCriterion("log_begin_file =", value, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileNotEqualTo(String value) {
            addCriterion("log_begin_file <>", value, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileGreaterThan(String value) {
            addCriterion("log_begin_file >", value, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileGreaterThanOrEqualTo(String value) {
            addCriterion("log_begin_file >=", value, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileLessThan(String value) {
            addCriterion("log_begin_file <", value, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileLessThanOrEqualTo(String value) {
            addCriterion("log_begin_file <=", value, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileLike(String value) {
            addCriterion("log_begin_file like", value, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileNotLike(String value) {
            addCriterion("log_begin_file not like", value, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileIn(List<String> values) {
            addCriterion("log_begin_file in", values, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileNotIn(List<String> values) {
            addCriterion("log_begin_file not in", values, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileBetween(String value1, String value2) {
            addCriterion("log_begin_file between", value1, value2, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogBeginFileNotBetween(String value1, String value2) {
            addCriterion("log_begin_file not between", value1, value2, "logBeginFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileIsNull() {
            addCriterion("log_end_file is null");
            return (Criteria) this;
        }

        public Criteria andLogEndFileIsNotNull() {
            addCriterion("log_end_file is not null");
            return (Criteria) this;
        }

        public Criteria andLogEndFileEqualTo(String value) {
            addCriterion("log_end_file =", value, "logEndFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileNotEqualTo(String value) {
            addCriterion("log_end_file <>", value, "logEndFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileGreaterThan(String value) {
            addCriterion("log_end_file >", value, "logEndFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileGreaterThanOrEqualTo(String value) {
            addCriterion("log_end_file >=", value, "logEndFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileLessThan(String value) {
            addCriterion("log_end_file <", value, "logEndFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileLessThanOrEqualTo(String value) {
            addCriterion("log_end_file <=", value, "logEndFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileLike(String value) {
            addCriterion("log_end_file like", value, "logEndFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileNotLike(String value) {
            addCriterion("log_end_file not like", value, "logEndFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileIn(List<String> values) {
            addCriterion("log_end_file in", values, "logEndFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileNotIn(List<String> values) {
            addCriterion("log_end_file not in", values, "logEndFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileBetween(String value1, String value2) {
            addCriterion("log_end_file between", value1, value2, "logEndFile");
            return (Criteria) this;
        }

        public Criteria andLogEndFileNotBetween(String value1, String value2) {
            addCriterion("log_end_file not between", value1, value2, "logEndFile");
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