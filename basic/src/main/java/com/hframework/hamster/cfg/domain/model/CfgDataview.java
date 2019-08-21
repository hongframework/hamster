package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgDataview {
    private Long id;

    private String viewName;

    private Long cfgDatasourceId;

    private String viewSql;

    private String mainTables;

    private String tables;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public CfgDataview(Long id, String viewName, Long cfgDatasourceId, String viewSql, String mainTables, String tables, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.viewName = viewName;
        this.cfgDatasourceId = cfgDatasourceId;
        this.viewSql = viewSql;
        this.mainTables = mainTables;
        this.tables = tables;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public String getViewName() {
        return viewName;
    }

    public Long getCfgDatasourceId() {
        return cfgDatasourceId;
    }

    public String getViewSql() {
        return viewSql;
    }

    public String getMainTables() {
        return mainTables;
    }

    public String getTables() {
        return tables;
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

    public void setId(Long id) {
        this.id=id;
    }

    public void setViewName(String viewName) {
        this.viewName=viewName;
    }

    public void setCfgDatasourceId(Long cfgDatasourceId) {
        this.cfgDatasourceId=cfgDatasourceId;
    }

    public void setViewSql(String viewSql) {
        this.viewSql=viewSql;
    }

    public void setMainTables(String mainTables) {
        this.mainTables=mainTables;
    }

    public void setTables(String tables) {
        this.tables=tables;
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

    public CfgDataview() {
        super();
    }
}