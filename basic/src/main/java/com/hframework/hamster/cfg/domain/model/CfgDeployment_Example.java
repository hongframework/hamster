package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgDeployment_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgDeployment_Example() {
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

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
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

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdIsNull() {
            addCriterion("datasource_id is null");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdIsNotNull() {
            addCriterion("datasource_id is not null");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdEqualTo(Long value) {
            addCriterion("datasource_id =", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdNotEqualTo(Long value) {
            addCriterion("datasource_id <>", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdGreaterThan(Long value) {
            addCriterion("datasource_id >", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("datasource_id >=", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdLessThan(Long value) {
            addCriterion("datasource_id <", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdLessThanOrEqualTo(Long value) {
            addCriterion("datasource_id <=", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdIn(List<Long> values) {
            addCriterion("datasource_id in", values, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdNotIn(List<Long> values) {
            addCriterion("datasource_id not in", values, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdBetween(Long value1, Long value2) {
            addCriterion("datasource_id between", value1, value2, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdNotBetween(Long value1, Long value2) {
            addCriterion("datasource_id not between", value1, value2, "datasourceId");
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

        public Criteria andLogBeginPositionEqualTo(String value) {
            addCriterion("log_begin_position =", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionNotEqualTo(String value) {
            addCriterion("log_begin_position <>", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionGreaterThan(String value) {
            addCriterion("log_begin_position >", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionGreaterThanOrEqualTo(String value) {
            addCriterion("log_begin_position >=", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionLessThan(String value) {
            addCriterion("log_begin_position <", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionLessThanOrEqualTo(String value) {
            addCriterion("log_begin_position <=", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionLike(String value) {
            addCriterion("log_begin_position like", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionNotLike(String value) {
            addCriterion("log_begin_position not like", value, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionIn(List<String> values) {
            addCriterion("log_begin_position in", values, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionNotIn(List<String> values) {
            addCriterion("log_begin_position not in", values, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionBetween(String value1, String value2) {
            addCriterion("log_begin_position between", value1, value2, "logBeginPosition");
            return (Criteria) this;
        }

        public Criteria andLogBeginPositionNotBetween(String value1, String value2) {
            addCriterion("log_begin_position not between", value1, value2, "logBeginPosition");
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

        public Criteria andLogEndPositionEqualTo(String value) {
            addCriterion("log_end_position =", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionNotEqualTo(String value) {
            addCriterion("log_end_position <>", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionGreaterThan(String value) {
            addCriterion("log_end_position >", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionGreaterThanOrEqualTo(String value) {
            addCriterion("log_end_position >=", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionLessThan(String value) {
            addCriterion("log_end_position <", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionLessThanOrEqualTo(String value) {
            addCriterion("log_end_position <=", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionLike(String value) {
            addCriterion("log_end_position like", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionNotLike(String value) {
            addCriterion("log_end_position not like", value, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionIn(List<String> values) {
            addCriterion("log_end_position in", values, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionNotIn(List<String> values) {
            addCriterion("log_end_position not in", values, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionBetween(String value1, String value2) {
            addCriterion("log_end_position between", value1, value2, "logEndPosition");
            return (Criteria) this;
        }

        public Criteria andLogEndPositionNotBetween(String value1, String value2) {
            addCriterion("log_end_position not between", value1, value2, "logEndPosition");
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

        public Criteria andSecondLabelsIsNull() {
            addCriterion("second_labels is null");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsIsNotNull() {
            addCriterion("second_labels is not null");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsEqualTo(String value) {
            addCriterion("second_labels =", value, "secondLabels");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsNotEqualTo(String value) {
            addCriterion("second_labels <>", value, "secondLabels");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsGreaterThan(String value) {
            addCriterion("second_labels >", value, "secondLabels");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsGreaterThanOrEqualTo(String value) {
            addCriterion("second_labels >=", value, "secondLabels");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsLessThan(String value) {
            addCriterion("second_labels <", value, "secondLabels");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsLessThanOrEqualTo(String value) {
            addCriterion("second_labels <=", value, "secondLabels");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsLike(String value) {
            addCriterion("second_labels like", value, "secondLabels");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsNotLike(String value) {
            addCriterion("second_labels not like", value, "secondLabels");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsIn(List<String> values) {
            addCriterion("second_labels in", values, "secondLabels");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsNotIn(List<String> values) {
            addCriterion("second_labels not in", values, "secondLabels");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsBetween(String value1, String value2) {
            addCriterion("second_labels between", value1, value2, "secondLabels");
            return (Criteria) this;
        }

        public Criteria andSecondLabelsNotBetween(String value1, String value2) {
            addCriterion("second_labels not between", value1, value2, "secondLabels");
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

        public Criteria andDeployJsonIsNull() {
            addCriterion("deploy_json is null");
            return (Criteria) this;
        }

        public Criteria andDeployJsonIsNotNull() {
            addCriterion("deploy_json is not null");
            return (Criteria) this;
        }

        public Criteria andDeployJsonEqualTo(String value) {
            addCriterion("deploy_json =", value, "deployJson");
            return (Criteria) this;
        }

        public Criteria andDeployJsonNotEqualTo(String value) {
            addCriterion("deploy_json <>", value, "deployJson");
            return (Criteria) this;
        }

        public Criteria andDeployJsonGreaterThan(String value) {
            addCriterion("deploy_json >", value, "deployJson");
            return (Criteria) this;
        }

        public Criteria andDeployJsonGreaterThanOrEqualTo(String value) {
            addCriterion("deploy_json >=", value, "deployJson");
            return (Criteria) this;
        }

        public Criteria andDeployJsonLessThan(String value) {
            addCriterion("deploy_json <", value, "deployJson");
            return (Criteria) this;
        }

        public Criteria andDeployJsonLessThanOrEqualTo(String value) {
            addCriterion("deploy_json <=", value, "deployJson");
            return (Criteria) this;
        }

        public Criteria andDeployJsonLike(String value) {
            addCriterion("deploy_json like", value, "deployJson");
            return (Criteria) this;
        }

        public Criteria andDeployJsonNotLike(String value) {
            addCriterion("deploy_json not like", value, "deployJson");
            return (Criteria) this;
        }

        public Criteria andDeployJsonIn(List<String> values) {
            addCriterion("deploy_json in", values, "deployJson");
            return (Criteria) this;
        }

        public Criteria andDeployJsonNotIn(List<String> values) {
            addCriterion("deploy_json not in", values, "deployJson");
            return (Criteria) this;
        }

        public Criteria andDeployJsonBetween(String value1, String value2) {
            addCriterion("deploy_json between", value1, value2, "deployJson");
            return (Criteria) this;
        }

        public Criteria andDeployJsonNotBetween(String value1, String value2) {
            addCriterion("deploy_json not between", value1, value2, "deployJson");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyIsNull() {
            addCriterion("alarm_strategy is null");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyIsNotNull() {
            addCriterion("alarm_strategy is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyEqualTo(String value) {
            addCriterion("alarm_strategy =", value, "alarmStrategy");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyNotEqualTo(String value) {
            addCriterion("alarm_strategy <>", value, "alarmStrategy");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyGreaterThan(String value) {
            addCriterion("alarm_strategy >", value, "alarmStrategy");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyGreaterThanOrEqualTo(String value) {
            addCriterion("alarm_strategy >=", value, "alarmStrategy");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyLessThan(String value) {
            addCriterion("alarm_strategy <", value, "alarmStrategy");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyLessThanOrEqualTo(String value) {
            addCriterion("alarm_strategy <=", value, "alarmStrategy");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyLike(String value) {
            addCriterion("alarm_strategy like", value, "alarmStrategy");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyNotLike(String value) {
            addCriterion("alarm_strategy not like", value, "alarmStrategy");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyIn(List<String> values) {
            addCriterion("alarm_strategy in", values, "alarmStrategy");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyNotIn(List<String> values) {
            addCriterion("alarm_strategy not in", values, "alarmStrategy");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyBetween(String value1, String value2) {
            addCriterion("alarm_strategy between", value1, value2, "alarmStrategy");
            return (Criteria) this;
        }

        public Criteria andAlarmStrategyNotBetween(String value1, String value2) {
            addCriterion("alarm_strategy not between", value1, value2, "alarmStrategy");
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