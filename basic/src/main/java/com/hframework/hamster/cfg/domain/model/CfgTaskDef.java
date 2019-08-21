package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgTaskDef {
    private Long cfgTaskDefId;

    private String cfgTaskDefCode;

    private String cfgTaskDefName;

    private Byte cfgTaskDefType;

    private Byte cfgTaskInstanceType;

    private String paramName1;

    private String paramCode1;

    private String paramName2;

    private String paramCode2;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public CfgTaskDef(Long cfgTaskDefId, String cfgTaskDefCode, String cfgTaskDefName, Byte cfgTaskDefType, Byte cfgTaskInstanceType, String paramName1, String paramCode1, String paramName2, String paramCode2, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.cfgTaskDefId = cfgTaskDefId;
        this.cfgTaskDefCode = cfgTaskDefCode;
        this.cfgTaskDefName = cfgTaskDefName;
        this.cfgTaskDefType = cfgTaskDefType;
        this.cfgTaskInstanceType = cfgTaskInstanceType;
        this.paramName1 = paramName1;
        this.paramCode1 = paramCode1;
        this.paramName2 = paramName2;
        this.paramCode2 = paramCode2;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getCfgTaskDefId() {
        return cfgTaskDefId;
    }

    public String getCfgTaskDefCode() {
        return cfgTaskDefCode;
    }

    public String getCfgTaskDefName() {
        return cfgTaskDefName;
    }

    public Byte getCfgTaskDefType() {
        return cfgTaskDefType;
    }

    public Byte getCfgTaskInstanceType() {
        return cfgTaskInstanceType;
    }

    public String getParamName1() {
        return paramName1;
    }

    public String getParamCode1() {
        return paramCode1;
    }

    public String getParamName2() {
        return paramName2;
    }

    public String getParamCode2() {
        return paramCode2;
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

    public void setCfgTaskDefId(Long cfgTaskDefId) {
        this.cfgTaskDefId=cfgTaskDefId;
    }

    public void setCfgTaskDefCode(String cfgTaskDefCode) {
        this.cfgTaskDefCode=cfgTaskDefCode;
    }

    public void setCfgTaskDefName(String cfgTaskDefName) {
        this.cfgTaskDefName=cfgTaskDefName;
    }

    public void setCfgTaskDefType(Byte cfgTaskDefType) {
        this.cfgTaskDefType=cfgTaskDefType;
    }

    public void setCfgTaskInstanceType(Byte cfgTaskInstanceType) {
        this.cfgTaskInstanceType=cfgTaskInstanceType;
    }

    public void setParamName1(String paramName1) {
        this.paramName1=paramName1;
    }

    public void setParamCode1(String paramCode1) {
        this.paramCode1=paramCode1;
    }

    public void setParamName2(String paramName2) {
        this.paramName2=paramName2;
    }

    public void setParamCode2(String paramCode2) {
        this.paramCode2=paramCode2;
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

    public CfgTaskDef() {
        super();
    }
}