package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgJobAttr {
    private Long id;

    private Long jobId;

    private Long attrId;

    private String attrVal;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private String attrCode;

    public CfgJobAttr(Long id, Long jobId, Long attrId, String attrVal, Long creatorId, Date createTime, Long modifierId, Date modifyTime, String attrCode) {
        this.id = id;
        this.jobId = jobId;
        this.attrId = attrId;
        this.attrVal = attrVal;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.attrCode = attrCode;
    }

    public Long getId() {
        return id;
    }

    public Long getJobId() {
        return jobId;
    }

    public Long getAttrId() {
        return attrId;
    }

    public String getAttrVal() {
        return attrVal;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setJobId(Long jobId) {
        this.jobId=jobId;
    }

    public void setAttrId(Long attrId) {
        this.attrId=attrId;
    }

    public void setAttrVal(String attrVal) {
        this.attrVal=attrVal;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId=creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime=createTime;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId=modifierId;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime=modifyTime;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode=attrCode;
    }

    public CfgJobAttr() {
        super();
    }
}