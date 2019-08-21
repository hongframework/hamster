package com.hframework.hamster.sec.domain.model;

import java.util.Date;

public class HfsecOrganize {
    private Long hfsecOrganizeId;

    private String hfsecOrganizeCode;

    private String hfsecOrganizeName;

    private Byte hfsecOrganizeType;

    private Byte hfsecOrganizeLevel;

    private Long parentHfsecOrganizeId;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public HfsecOrganize(Long hfsecOrganizeId, String hfsecOrganizeCode, String hfsecOrganizeName, Byte hfsecOrganizeType, Byte hfsecOrganizeLevel, Long parentHfsecOrganizeId, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.hfsecOrganizeId = hfsecOrganizeId;
        this.hfsecOrganizeCode = hfsecOrganizeCode;
        this.hfsecOrganizeName = hfsecOrganizeName;
        this.hfsecOrganizeType = hfsecOrganizeType;
        this.hfsecOrganizeLevel = hfsecOrganizeLevel;
        this.parentHfsecOrganizeId = parentHfsecOrganizeId;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getHfsecOrganizeId() {
        return hfsecOrganizeId;
    }

    public String getHfsecOrganizeCode() {
        return hfsecOrganizeCode;
    }

    public String getHfsecOrganizeName() {
        return hfsecOrganizeName;
    }

    public Byte getHfsecOrganizeType() {
        return hfsecOrganizeType;
    }

    public Byte getHfsecOrganizeLevel() {
        return hfsecOrganizeLevel;
    }

    public Long getParentHfsecOrganizeId() {
        return parentHfsecOrganizeId;
    }

    public Byte getStatus() {
        return status;
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

    public void setHfsecOrganizeId(Long hfsecOrganizeId) {
        this.hfsecOrganizeId=hfsecOrganizeId;
    }

    public void setHfsecOrganizeCode(String hfsecOrganizeCode) {
        this.hfsecOrganizeCode=hfsecOrganizeCode;
    }

    public void setHfsecOrganizeName(String hfsecOrganizeName) {
        this.hfsecOrganizeName=hfsecOrganizeName;
    }

    public void setHfsecOrganizeType(Byte hfsecOrganizeType) {
        this.hfsecOrganizeType=hfsecOrganizeType;
    }

    public void setHfsecOrganizeLevel(Byte hfsecOrganizeLevel) {
        this.hfsecOrganizeLevel=hfsecOrganizeLevel;
    }

    public void setParentHfsecOrganizeId(Long parentHfsecOrganizeId) {
        this.parentHfsecOrganizeId=parentHfsecOrganizeId;
    }

    public void setStatus(Byte status) {
        this.status=status;
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

    public HfsecOrganize() {
        super();
    }
}