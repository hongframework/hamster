package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgBroker {
    private Long cfgBrokerId;

    private String cfgBrokerName;

    private String cfgBrokerCode;

    private Byte cfgBrokerType;

    private String addrList;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private String zkAddrList;

    public CfgBroker(Long cfgBrokerId, String cfgBrokerName, String cfgBrokerCode, Byte cfgBrokerType, String addrList, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, String zkAddrList) {
        this.cfgBrokerId = cfgBrokerId;
        this.cfgBrokerName = cfgBrokerName;
        this.cfgBrokerCode = cfgBrokerCode;
        this.cfgBrokerType = cfgBrokerType;
        this.addrList = addrList;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.zkAddrList = zkAddrList;
    }

    public Long getCfgBrokerId() {
        return cfgBrokerId;
    }

    public String getCfgBrokerName() {
        return cfgBrokerName;
    }

    public String getCfgBrokerCode() {
        return cfgBrokerCode;
    }

    public Byte getCfgBrokerType() {
        return cfgBrokerType;
    }

    public String getAddrList() {
        return addrList;
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

    public String getZkAddrList() {
        return zkAddrList;
    }

    public void setCfgBrokerId(Long cfgBrokerId) {
        this.cfgBrokerId=cfgBrokerId;
    }

    public void setCfgBrokerName(String cfgBrokerName) {
        this.cfgBrokerName=cfgBrokerName;
    }

    public void setCfgBrokerCode(String cfgBrokerCode) {
        this.cfgBrokerCode=cfgBrokerCode;
    }

    public void setCfgBrokerType(Byte cfgBrokerType) {
        this.cfgBrokerType=cfgBrokerType;
    }

    public void setAddrList(String addrList) {
        this.addrList=addrList;
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

    public void setZkAddrList(String zkAddrList) {
        this.zkAddrList=zkAddrList;
    }

    public CfgBroker() {
        super();
    }
}