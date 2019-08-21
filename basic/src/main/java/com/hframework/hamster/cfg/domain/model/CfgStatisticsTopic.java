package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgStatisticsTopic {
    private Long cfgStatisticsTopicId;

    private String cfgStatisticsTopicCode;

    private String cfgStatisticsTopicName;

    private String cfgStatisticsTopicDesc;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public CfgStatisticsTopic(Long cfgStatisticsTopicId, String cfgStatisticsTopicCode, String cfgStatisticsTopicName, String cfgStatisticsTopicDesc, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.cfgStatisticsTopicId = cfgStatisticsTopicId;
        this.cfgStatisticsTopicCode = cfgStatisticsTopicCode;
        this.cfgStatisticsTopicName = cfgStatisticsTopicName;
        this.cfgStatisticsTopicDesc = cfgStatisticsTopicDesc;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getCfgStatisticsTopicId() {
        return cfgStatisticsTopicId;
    }

    public String getCfgStatisticsTopicCode() {
        return cfgStatisticsTopicCode;
    }

    public String getCfgStatisticsTopicName() {
        return cfgStatisticsTopicName;
    }

    public String getCfgStatisticsTopicDesc() {
        return cfgStatisticsTopicDesc;
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

    public void setCfgStatisticsTopicId(Long cfgStatisticsTopicId) {
        this.cfgStatisticsTopicId=cfgStatisticsTopicId;
    }

    public void setCfgStatisticsTopicCode(String cfgStatisticsTopicCode) {
        this.cfgStatisticsTopicCode=cfgStatisticsTopicCode;
    }

    public void setCfgStatisticsTopicName(String cfgStatisticsTopicName) {
        this.cfgStatisticsTopicName=cfgStatisticsTopicName;
    }

    public void setCfgStatisticsTopicDesc(String cfgStatisticsTopicDesc) {
        this.cfgStatisticsTopicDesc=cfgStatisticsTopicDesc;
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

    public CfgStatisticsTopic() {
        super();
    }
}