package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgTopic {
    private Long cfgTopicId;

    private String cfgTopicName;

    private String cfgTopicDesc;

    private Byte cfgTopicType;

    private String cfgTopicCode;

    private Byte partitions;

    private Byte replicas;

    private Short serialNo;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Long cfgBrokerId;

    public CfgTopic(Long cfgTopicId, String cfgTopicName, String cfgTopicDesc, Byte cfgTopicType, String cfgTopicCode, Byte partitions, Byte replicas, Short serialNo, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, Long cfgBrokerId) {
        this.cfgTopicId = cfgTopicId;
        this.cfgTopicName = cfgTopicName;
        this.cfgTopicDesc = cfgTopicDesc;
        this.cfgTopicType = cfgTopicType;
        this.cfgTopicCode = cfgTopicCode;
        this.partitions = partitions;
        this.replicas = replicas;
        this.serialNo = serialNo;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.cfgBrokerId = cfgBrokerId;
    }

    public Long getCfgTopicId() {
        return cfgTopicId;
    }

    public String getCfgTopicName() {
        return cfgTopicName;
    }

    public String getCfgTopicDesc() {
        return cfgTopicDesc;
    }

    public Byte getCfgTopicType() {
        return cfgTopicType;
    }

    public String getCfgTopicCode() {
        return cfgTopicCode;
    }

    public Byte getPartitions() {
        return partitions;
    }

    public Byte getReplicas() {
        return replicas;
    }

    public Short getSerialNo() {
        return serialNo;
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

    public Long getCfgBrokerId() {
        return cfgBrokerId;
    }

    public void setCfgTopicId(Long cfgTopicId) {
        this.cfgTopicId=cfgTopicId;
    }

    public void setCfgTopicName(String cfgTopicName) {
        this.cfgTopicName=cfgTopicName;
    }

    public void setCfgTopicDesc(String cfgTopicDesc) {
        this.cfgTopicDesc=cfgTopicDesc;
    }

    public void setCfgTopicType(Byte cfgTopicType) {
        this.cfgTopicType=cfgTopicType;
    }

    public void setCfgTopicCode(String cfgTopicCode) {
        this.cfgTopicCode=cfgTopicCode;
    }

    public void setPartitions(Byte partitions) {
        this.partitions=partitions;
    }

    public void setReplicas(Byte replicas) {
        this.replicas=replicas;
    }

    public void setSerialNo(Short serialNo) {
        this.serialNo=serialNo;
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

    public void setCfgBrokerId(Long cfgBrokerId) {
        this.cfgBrokerId=cfgBrokerId;
    }

    public CfgTopic() {
        super();
    }
}