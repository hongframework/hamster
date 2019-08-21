package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgStatisticsDetail_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgStatisticsDetail_Example() {
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

        public Criteria andCfgStatisticsDetailIdIsNull() {
            addCriterion("cfg_statistics_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsDetailIdIsNotNull() {
            addCriterion("cfg_statistics_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsDetailIdEqualTo(Long value) {
            addCriterion("cfg_statistics_detail_id =", value, "cfgStatisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsDetailIdNotEqualTo(Long value) {
            addCriterion("cfg_statistics_detail_id <>", value, "cfgStatisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsDetailIdGreaterThan(Long value) {
            addCriterion("cfg_statistics_detail_id >", value, "cfgStatisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsDetailIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_statistics_detail_id >=", value, "cfgStatisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsDetailIdLessThan(Long value) {
            addCriterion("cfg_statistics_detail_id <", value, "cfgStatisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsDetailIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_statistics_detail_id <=", value, "cfgStatisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsDetailIdIn(List<Long> values) {
            addCriterion("cfg_statistics_detail_id in", values, "cfgStatisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsDetailIdNotIn(List<Long> values) {
            addCriterion("cfg_statistics_detail_id not in", values, "cfgStatisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsDetailIdBetween(Long value1, Long value2) {
            addCriterion("cfg_statistics_detail_id between", value1, value2, "cfgStatisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsDetailIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_statistics_detail_id not between", value1, value2, "cfgStatisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdIsNull() {
            addCriterion("cfg_statistics_id is null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdIsNotNull() {
            addCriterion("cfg_statistics_id is not null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdEqualTo(Long value) {
            addCriterion("cfg_statistics_id =", value, "cfgStatisticsId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdNotEqualTo(Long value) {
            addCriterion("cfg_statistics_id <>", value, "cfgStatisticsId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdGreaterThan(Long value) {
            addCriterion("cfg_statistics_id >", value, "cfgStatisticsId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cfg_statistics_id >=", value, "cfgStatisticsId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdLessThan(Long value) {
            addCriterion("cfg_statistics_id <", value, "cfgStatisticsId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdLessThanOrEqualTo(Long value) {
            addCriterion("cfg_statistics_id <=", value, "cfgStatisticsId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdIn(List<Long> values) {
            addCriterion("cfg_statistics_id in", values, "cfgStatisticsId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdNotIn(List<Long> values) {
            addCriterion("cfg_statistics_id not in", values, "cfgStatisticsId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdBetween(Long value1, Long value2) {
            addCriterion("cfg_statistics_id between", value1, value2, "cfgStatisticsId");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsIdNotBetween(Long value1, Long value2) {
            addCriterion("cfg_statistics_id not between", value1, value2, "cfgStatisticsId");
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

        public Criteria andStatisticsViewIsNull() {
            addCriterion("statistics_view is null");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewIsNotNull() {
            addCriterion("statistics_view is not null");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewEqualTo(String value) {
            addCriterion("statistics_view =", value, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewNotEqualTo(String value) {
            addCriterion("statistics_view <>", value, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewGreaterThan(String value) {
            addCriterion("statistics_view >", value, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewGreaterThanOrEqualTo(String value) {
            addCriterion("statistics_view >=", value, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewLessThan(String value) {
            addCriterion("statistics_view <", value, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewLessThanOrEqualTo(String value) {
            addCriterion("statistics_view <=", value, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewLike(String value) {
            addCriterion("statistics_view like", value, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewNotLike(String value) {
            addCriterion("statistics_view not like", value, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewIn(List<String> values) {
            addCriterion("statistics_view in", values, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewNotIn(List<String> values) {
            addCriterion("statistics_view not in", values, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewBetween(String value1, String value2) {
            addCriterion("statistics_view between", value1, value2, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsViewNotBetween(String value1, String value2) {
            addCriterion("statistics_view not between", value1, value2, "statisticsView");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueIsNull() {
            addCriterion("statistics_value is null");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueIsNotNull() {
            addCriterion("statistics_value is not null");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueEqualTo(String value) {
            addCriterion("statistics_value =", value, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueNotEqualTo(String value) {
            addCriterion("statistics_value <>", value, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueGreaterThan(String value) {
            addCriterion("statistics_value >", value, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueGreaterThanOrEqualTo(String value) {
            addCriterion("statistics_value >=", value, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueLessThan(String value) {
            addCriterion("statistics_value <", value, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueLessThanOrEqualTo(String value) {
            addCriterion("statistics_value <=", value, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueLike(String value) {
            addCriterion("statistics_value like", value, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueNotLike(String value) {
            addCriterion("statistics_value not like", value, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueIn(List<String> values) {
            addCriterion("statistics_value in", values, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueNotIn(List<String> values) {
            addCriterion("statistics_value not in", values, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueBetween(String value1, String value2) {
            addCriterion("statistics_value between", value1, value2, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andStatisticsValueNotBetween(String value1, String value2) {
            addCriterion("statistics_value not between", value1, value2, "statisticsValue");
            return (Criteria) this;
        }

        public Criteria andCountIsNull() {
            addCriterion("`count` is null");
            return (Criteria) this;
        }

        public Criteria andCountIsNotNull() {
            addCriterion("`count` is not null");
            return (Criteria) this;
        }

        public Criteria andCountEqualTo(Byte value) {
            addCriterion("`count` =", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotEqualTo(Byte value) {
            addCriterion("`count` <>", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThan(Byte value) {
            addCriterion("`count` >", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThanOrEqualTo(Byte value) {
            addCriterion("`count` >=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThan(Byte value) {
            addCriterion("`count` <", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThanOrEqualTo(Byte value) {
            addCriterion("`count` <=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountIn(List<Byte> values) {
            addCriterion("`count` in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotIn(List<Byte> values) {
            addCriterion("`count` not in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountBetween(Byte value1, Byte value2) {
            addCriterion("`count` between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotBetween(Byte value1, Byte value2) {
            addCriterion("`count` not between", value1, value2, "count");
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