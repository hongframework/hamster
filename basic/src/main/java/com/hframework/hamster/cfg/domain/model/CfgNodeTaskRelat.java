package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgNodeTaskRelat {
    private Long cfgNodeTaskRelatId;

    private Long cfgNodeId;

    private Long cfgTaskInstId;

    private Byte isMainNode;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public CfgNodeTaskRelat(Long cfgNodeTaskRelatId, Long cfgNodeId, Long cfgTaskInstId, Byte isMainNode, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.cfgNodeTaskRelatId = cfgNodeTaskRelatId;
        this.cfgNodeId = cfgNodeId;
        this.cfgTaskInstId = cfgTaskInstId;
        this.isMainNode = isMainNode;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getCfgNodeTaskRelatId() {
        return cfgNodeTaskRelatId;
    }

    public Long getCfgNodeId() {
        return cfgNodeId;
    }

    public Long getCfgTaskInstId() {
        return cfgTaskInstId;
    }

    public Byte getIsMainNode() {
        return isMainNode;
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

    public void setCfgNodeTaskRelatId(Long cfgNodeTaskRelatId) {
        this.cfgNodeTaskRelatId=cfgNodeTaskRelatId;
    }

    public void setCfgNodeId(Long cfgNodeId) {
        this.cfgNodeId=cfgNodeId;
    }

    public void setCfgTaskInstId(Long cfgTaskInstId) {
        this.cfgTaskInstId=cfgTaskInstId;
    }

    public void setIsMainNode(Byte isMainNode) {
        this.isMainNode=isMainNode;
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

    public CfgNodeTaskRelat() {
        super();
    }
}