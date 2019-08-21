package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgSubscribeData {
    private Long cfgSubscribeDataId;

    private Byte cfgSubscribeDataType;

    private String cfgSubscribeDataCode;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Byte containChangeBeforeValue;

    private Long cfgSubscribeId;

    public CfgSubscribeData(Long cfgSubscribeDataId, Byte cfgSubscribeDataType, String cfgSubscribeDataCode, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, Byte containChangeBeforeValue, Long cfgSubscribeId) {
        this.cfgSubscribeDataId = cfgSubscribeDataId;
        this.cfgSubscribeDataType = cfgSubscribeDataType;
        this.cfgSubscribeDataCode = cfgSubscribeDataCode;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.containChangeBeforeValue = containChangeBeforeValue;
        this.cfgSubscribeId = cfgSubscribeId;
    }

    public Long getCfgSubscribeDataId() {
        return cfgSubscribeDataId;
    }

    public Byte getCfgSubscribeDataType() {
        return cfgSubscribeDataType;
    }

    public String getCfgSubscribeDataCode() {
        return cfgSubscribeDataCode;
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

    public Byte getContainChangeBeforeValue() {
        return containChangeBeforeValue;
    }

    public Long getCfgSubscribeId() {
        return cfgSubscribeId;
    }

    public void setCfgSubscribeDataId(Long cfgSubscribeDataId) {
        this.cfgSubscribeDataId=cfgSubscribeDataId;
    }

    public void setCfgSubscribeDataType(Byte cfgSubscribeDataType) {
        this.cfgSubscribeDataType=cfgSubscribeDataType;
    }

    public void setCfgSubscribeDataCode(String cfgSubscribeDataCode) {
        this.cfgSubscribeDataCode=cfgSubscribeDataCode;
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

    public void setContainChangeBeforeValue(Byte containChangeBeforeValue) {
        this.containChangeBeforeValue=containChangeBeforeValue;
    }

    public void setCfgSubscribeId(Long cfgSubscribeId) {
        this.cfgSubscribeId=cfgSubscribeId;
    }

    public CfgSubscribeData() {
        super();
    }
}