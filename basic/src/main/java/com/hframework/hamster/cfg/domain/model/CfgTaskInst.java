package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgTaskInst {
    private Long cfgTaskInstId;

    private String cfgTaskInstDesc;

    private Long cfgTaskDefId;

    private Byte status;

    private String paramValue1;

    private String paramValueRemark1;

    private String paramValue2;

    private String paramValueRemark2;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public CfgTaskInst(Long cfgTaskInstId, String cfgTaskInstDesc, Long cfgTaskDefId, Byte status, String paramValue1, String paramValueRemark1, String paramValue2, String paramValueRemark2, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.cfgTaskInstId = cfgTaskInstId;
        this.cfgTaskInstDesc = cfgTaskInstDesc;
        this.cfgTaskDefId = cfgTaskDefId;
        this.status = status;
        this.paramValue1 = paramValue1;
        this.paramValueRemark1 = paramValueRemark1;
        this.paramValue2 = paramValue2;
        this.paramValueRemark2 = paramValueRemark2;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getCfgTaskInstId() {
        return cfgTaskInstId;
    }

    public String getCfgTaskInstDesc() {
        return cfgTaskInstDesc;
    }

    public Long getCfgTaskDefId() {
        return cfgTaskDefId;
    }

    public Byte getStatus() {
        return status;
    }

    public String getParamValue1() {
        return paramValue1;
    }

    public String getParamValueRemark1() {
        return paramValueRemark1;
    }

    public String getParamValue2() {
        return paramValue2;
    }

    public String getParamValueRemark2() {
        return paramValueRemark2;
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

    public void setCfgTaskInstId(Long cfgTaskInstId) {
        this.cfgTaskInstId=cfgTaskInstId;
    }

    public void setCfgTaskInstDesc(String cfgTaskInstDesc) {
        this.cfgTaskInstDesc=cfgTaskInstDesc;
    }

    public void setCfgTaskDefId(Long cfgTaskDefId) {
        this.cfgTaskDefId=cfgTaskDefId;
    }

    public void setStatus(Byte status) {
        this.status=status;
    }

    public void setParamValue1(String paramValue1) {
        this.paramValue1=paramValue1;
    }

    public void setParamValueRemark1(String paramValueRemark1) {
        this.paramValueRemark1=paramValueRemark1;
    }

    public void setParamValue2(String paramValue2) {
        this.paramValue2=paramValue2;
    }

    public void setParamValueRemark2(String paramValueRemark2) {
        this.paramValueRemark2=paramValueRemark2;
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

    public CfgTaskInst() {
        super();
    }
}