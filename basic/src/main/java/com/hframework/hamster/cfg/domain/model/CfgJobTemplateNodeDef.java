package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgJobTemplateNodeDef {
    private Long id;

    private Long jobTemplateId;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Byte mutix;

    private Long nodeId;

    private Byte executeOrder;

    private Long pri;

    public CfgJobTemplateNodeDef(Long id, Long jobTemplateId, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, Byte mutix, Long nodeId, Byte executeOrder, Long pri) {
        this.id = id;
        this.jobTemplateId = jobTemplateId;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.mutix = mutix;
        this.nodeId = nodeId;
        this.executeOrder = executeOrder;
        this.pri = pri;
    }

    public Long getId() {
        return id;
    }

    public Long getJobTemplateId() {
        return jobTemplateId;
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

    public Byte getMutix() {
        return mutix;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public Byte getExecuteOrder() {
        return executeOrder;
    }

    public Long getPri() {
        return pri;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setJobTemplateId(Long jobTemplateId) {
        this.jobTemplateId=jobTemplateId;
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

    public void setMutix(Byte mutix) {
        this.mutix=mutix;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId=nodeId;
    }

    public void setExecuteOrder(Byte executeOrder) {
        this.executeOrder=executeOrder;
    }

    public void setPri(Long pri) {
        this.pri=pri;
    }

    public CfgJobTemplateNodeDef() {
        super();
    }
}