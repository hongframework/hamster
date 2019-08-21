package com.hframework.hamster.sec.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HfsecOrganize_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public HfsecOrganize_Example() {
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

        public Criteria andHfsecOrganizeIdIsNull() {
            addCriterion("hfsec_organize_id is null");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeIdIsNotNull() {
            addCriterion("hfsec_organize_id is not null");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeIdEqualTo(Long value) {
            addCriterion("hfsec_organize_id =", value, "hfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeIdNotEqualTo(Long value) {
            addCriterion("hfsec_organize_id <>", value, "hfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeIdGreaterThan(Long value) {
            addCriterion("hfsec_organize_id >", value, "hfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("hfsec_organize_id >=", value, "hfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeIdLessThan(Long value) {
            addCriterion("hfsec_organize_id <", value, "hfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeIdLessThanOrEqualTo(Long value) {
            addCriterion("hfsec_organize_id <=", value, "hfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeIdIn(List<Long> values) {
            addCriterion("hfsec_organize_id in", values, "hfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeIdNotIn(List<Long> values) {
            addCriterion("hfsec_organize_id not in", values, "hfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeIdBetween(Long value1, Long value2) {
            addCriterion("hfsec_organize_id between", value1, value2, "hfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeIdNotBetween(Long value1, Long value2) {
            addCriterion("hfsec_organize_id not between", value1, value2, "hfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeIsNull() {
            addCriterion("hfsec_organize_code is null");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeIsNotNull() {
            addCriterion("hfsec_organize_code is not null");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeEqualTo(String value) {
            addCriterion("hfsec_organize_code =", value, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeNotEqualTo(String value) {
            addCriterion("hfsec_organize_code <>", value, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeGreaterThan(String value) {
            addCriterion("hfsec_organize_code >", value, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("hfsec_organize_code >=", value, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeLessThan(String value) {
            addCriterion("hfsec_organize_code <", value, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeLessThanOrEqualTo(String value) {
            addCriterion("hfsec_organize_code <=", value, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeLike(String value) {
            addCriterion("hfsec_organize_code like", value, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeNotLike(String value) {
            addCriterion("hfsec_organize_code not like", value, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeIn(List<String> values) {
            addCriterion("hfsec_organize_code in", values, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeNotIn(List<String> values) {
            addCriterion("hfsec_organize_code not in", values, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeBetween(String value1, String value2) {
            addCriterion("hfsec_organize_code between", value1, value2, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeCodeNotBetween(String value1, String value2) {
            addCriterion("hfsec_organize_code not between", value1, value2, "hfsecOrganizeCode");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameIsNull() {
            addCriterion("hfsec_organize_name is null");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameIsNotNull() {
            addCriterion("hfsec_organize_name is not null");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameEqualTo(String value) {
            addCriterion("hfsec_organize_name =", value, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameNotEqualTo(String value) {
            addCriterion("hfsec_organize_name <>", value, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameGreaterThan(String value) {
            addCriterion("hfsec_organize_name >", value, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameGreaterThanOrEqualTo(String value) {
            addCriterion("hfsec_organize_name >=", value, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameLessThan(String value) {
            addCriterion("hfsec_organize_name <", value, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameLessThanOrEqualTo(String value) {
            addCriterion("hfsec_organize_name <=", value, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameLike(String value) {
            addCriterion("hfsec_organize_name like", value, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameNotLike(String value) {
            addCriterion("hfsec_organize_name not like", value, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameIn(List<String> values) {
            addCriterion("hfsec_organize_name in", values, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameNotIn(List<String> values) {
            addCriterion("hfsec_organize_name not in", values, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameBetween(String value1, String value2) {
            addCriterion("hfsec_organize_name between", value1, value2, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeNameNotBetween(String value1, String value2) {
            addCriterion("hfsec_organize_name not between", value1, value2, "hfsecOrganizeName");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeIsNull() {
            addCriterion("hfsec_organize_type is null");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeIsNotNull() {
            addCriterion("hfsec_organize_type is not null");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeEqualTo(Byte value) {
            addCriterion("hfsec_organize_type =", value, "hfsecOrganizeType");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeNotEqualTo(Byte value) {
            addCriterion("hfsec_organize_type <>", value, "hfsecOrganizeType");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeGreaterThan(Byte value) {
            addCriterion("hfsec_organize_type >", value, "hfsecOrganizeType");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("hfsec_organize_type >=", value, "hfsecOrganizeType");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeLessThan(Byte value) {
            addCriterion("hfsec_organize_type <", value, "hfsecOrganizeType");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeLessThanOrEqualTo(Byte value) {
            addCriterion("hfsec_organize_type <=", value, "hfsecOrganizeType");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeIn(List<Byte> values) {
            addCriterion("hfsec_organize_type in", values, "hfsecOrganizeType");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeNotIn(List<Byte> values) {
            addCriterion("hfsec_organize_type not in", values, "hfsecOrganizeType");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeBetween(Byte value1, Byte value2) {
            addCriterion("hfsec_organize_type between", value1, value2, "hfsecOrganizeType");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("hfsec_organize_type not between", value1, value2, "hfsecOrganizeType");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelIsNull() {
            addCriterion("hfsec_organize_level is null");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelIsNotNull() {
            addCriterion("hfsec_organize_level is not null");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelEqualTo(Byte value) {
            addCriterion("hfsec_organize_level =", value, "hfsecOrganizeLevel");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelNotEqualTo(Byte value) {
            addCriterion("hfsec_organize_level <>", value, "hfsecOrganizeLevel");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelGreaterThan(Byte value) {
            addCriterion("hfsec_organize_level >", value, "hfsecOrganizeLevel");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelGreaterThanOrEqualTo(Byte value) {
            addCriterion("hfsec_organize_level >=", value, "hfsecOrganizeLevel");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelLessThan(Byte value) {
            addCriterion("hfsec_organize_level <", value, "hfsecOrganizeLevel");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelLessThanOrEqualTo(Byte value) {
            addCriterion("hfsec_organize_level <=", value, "hfsecOrganizeLevel");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelIn(List<Byte> values) {
            addCriterion("hfsec_organize_level in", values, "hfsecOrganizeLevel");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelNotIn(List<Byte> values) {
            addCriterion("hfsec_organize_level not in", values, "hfsecOrganizeLevel");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelBetween(Byte value1, Byte value2) {
            addCriterion("hfsec_organize_level between", value1, value2, "hfsecOrganizeLevel");
            return (Criteria) this;
        }

        public Criteria andHfsecOrganizeLevelNotBetween(Byte value1, Byte value2) {
            addCriterion("hfsec_organize_level not between", value1, value2, "hfsecOrganizeLevel");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdIsNull() {
            addCriterion("parent_hfsec_organize_id is null");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdIsNotNull() {
            addCriterion("parent_hfsec_organize_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdEqualTo(Long value) {
            addCriterion("parent_hfsec_organize_id =", value, "parentHfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdNotEqualTo(Long value) {
            addCriterion("parent_hfsec_organize_id <>", value, "parentHfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdGreaterThan(Long value) {
            addCriterion("parent_hfsec_organize_id >", value, "parentHfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_hfsec_organize_id >=", value, "parentHfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdLessThan(Long value) {
            addCriterion("parent_hfsec_organize_id <", value, "parentHfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_hfsec_organize_id <=", value, "parentHfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdIn(List<Long> values) {
            addCriterion("parent_hfsec_organize_id in", values, "parentHfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdNotIn(List<Long> values) {
            addCriterion("parent_hfsec_organize_id not in", values, "parentHfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdBetween(Long value1, Long value2) {
            addCriterion("parent_hfsec_organize_id between", value1, value2, "parentHfsecOrganizeId");
            return (Criteria) this;
        }

        public Criteria andParentHfsecOrganizeIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_hfsec_organize_id not between", value1, value2, "parentHfsecOrganizeId");
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