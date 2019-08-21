package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgDatasource {
    private Long cfgDatasourceId;

    private Byte cfgDatasourceType;

    private String url;

    private String username;

    private String password;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private String db;

    public CfgDatasource(Long cfgDatasourceId, Byte cfgDatasourceType, String url, String username, String password, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, String db) {
        this.cfgDatasourceId = cfgDatasourceId;
        this.cfgDatasourceType = cfgDatasourceType;
        this.url = url;
        this.username = username;
        this.password = password;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.db = db;
    }

    public Long getCfgDatasourceId() {
        return cfgDatasourceId;
    }

    public Byte getCfgDatasourceType() {
        return cfgDatasourceType;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    public String getDb() {
        return db;
    }

    public void setCfgDatasourceId(Long cfgDatasourceId) {
        this.cfgDatasourceId=cfgDatasourceId;
    }

    public void setCfgDatasourceType(Byte cfgDatasourceType) {
        this.cfgDatasourceType=cfgDatasourceType;
    }

    public void setUrl(String url) {
        this.url=url;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public void setPassword(String password) {
        this.password=password;
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

    public void setDb(String db) {
        this.db=db;
    }

    public CfgDatasource() {
        super();
    }
}