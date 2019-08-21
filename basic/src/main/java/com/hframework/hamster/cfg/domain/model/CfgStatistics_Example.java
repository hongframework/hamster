package com.hframework.hamster.cfg.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CfgStatistics_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CfgStatistics_Example() {
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

        public Criteria andCfgStatisticsNameIsNull() {
            addCriterion("cfg_statistics_name is null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameIsNotNull() {
            addCriterion("cfg_statistics_name is not null");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameEqualTo(String value) {
            addCriterion("cfg_statistics_name =", value, "cfgStatisticsName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameNotEqualTo(String value) {
            addCriterion("cfg_statistics_name <>", value, "cfgStatisticsName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameGreaterThan(String value) {
            addCriterion("cfg_statistics_name >", value, "cfgStatisticsName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_statistics_name >=", value, "cfgStatisticsName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameLessThan(String value) {
            addCriterion("cfg_statistics_name <", value, "cfgStatisticsName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameLessThanOrEqualTo(String value) {
            addCriterion("cfg_statistics_name <=", value, "cfgStatisticsName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameLike(String value) {
            addCriterion("cfg_statistics_name like", value, "cfgStatisticsName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameNotLike(String value) {
            addCriterion("cfg_statistics_name not like", value, "cfgStatisticsName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameIn(List<String> values) {
            addCriterion("cfg_statistics_name in", values, "cfgStatisticsName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameNotIn(List<String> values) {
            addCriterion("cfg_statistics_name not in", values, "cfgStatisticsName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameBetween(String value1, String value2) {
            addCriterion("cfg_statistics_name between", value1, value2, "cfgStatisticsName");
            return (Criteria) this;
        }

        public Criteria andCfgStatisticsNameNotBetween(String value1, String value2) {
            addCriterion("cfg_statistics_name not between", value1, value2, "cfgStatisticsName");
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