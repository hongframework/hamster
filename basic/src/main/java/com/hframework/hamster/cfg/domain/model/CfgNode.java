package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgNode {
    private Long cfgNodeId;

    private String cfgNodeCode;

    private String cfgNodeName;

    private String ip;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private String labels;

    public CfgNode(Long cfgNodeId, String cfgNodeCode, String cfgNodeName, String ip, Long creatorId, Date createTime, Long modifierId, Date modifyTime, String labels) {
        this.cfgNodeId = cfgNodeId;
        this.cfgNodeCode = cfgNodeCode;
        this.cfgNodeName = cfgNodeName;
        this.ip = ip;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.labels = labels;
    }

    public Long getCfgNodeId() {
        return cfgNodeId;
    }

    public String getCfgNodeCode() {
        return cfgNodeCode;
    }

    public String getCfgNodeName() {
        return cfgNodeName;
    }

    public String getIp() {
        return ip;
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

    public String getLabels() {
        return labels;
    }

    public void setCfgNodeId(Long cfgNodeId) {
        this.cfgNodeId=cfgNodeId;
    }

    public void setCfgNodeCode(String cfgNodeCode) {
        this.cfgNodeCode=cfgNodeCode;
    }

    public void setCfgNodeName(String cfgNodeName) {
        this.cfgNodeName=cfgNodeName;
    }

    public void setIp(String ip) {
        this.ip=ip;
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

    public void setLabels(String labels) {
        this.labels=labels;
    }

    public CfgNode() {
        super();
    }
}