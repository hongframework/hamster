package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgTaskNodeDef {
    private Long cfgTaskNodeDefId;

    private String cfgTaskNodeDefName;

    private String cfgTaskNodeDefCode;

    private String javaClass;

    private Long cfgTaskDefId;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public CfgTaskNodeDef(Long cfgTaskNodeDefId, String cfgTaskNodeDefName, String cfgTaskNodeDefCode, String javaClass, Long cfgTaskDefId, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.cfgTaskNodeDefId = cfgTaskNodeDefId;
        this.cfgTaskNodeDefName = cfgTaskNodeDefName;
        this.cfgTaskNodeDefCode = cfgTaskNodeDefCode;
        this.javaClass = javaClass;
        this.cfgTaskDefId = cfgTaskDefId;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getCfgTaskNodeDefId() {
        return cfgTaskNodeDefId;
    }

    public String getCfgTaskNodeDefName() {
        return cfgTaskNodeDefName;
    }

    public String getCfgTaskNodeDefCode() {
        return cfgTaskNodeDefCode;
    }

    public String getJavaClass() {
        return javaClass;
    }

    public Long getCfgTaskDefId() {
        return cfgTaskDefId;
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

    public void setCfgTaskNodeDefId(Long cfgTaskNodeDefId) {
        this.cfgTaskNodeDefId=cfgTaskNodeDefId;
    }

    public void setCfgTaskNodeDefName(String cfgTaskNodeDefName) {
        this.cfgTaskNodeDefName=cfgTaskNodeDefName;
    }

    public void setCfgTaskNodeDefCode(String cfgTaskNodeDefCode) {
        this.cfgTaskNodeDefCode=cfgTaskNodeDefCode;
    }

    public void setJavaClass(String javaClass) {
        this.javaClass=javaClass;
    }

    public void setCfgTaskDefId(Long cfgTaskDefId) {
        this.cfgTaskDefId=cfgTaskDefId;
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

    public CfgTaskNodeDef() {
        super();
    }
}