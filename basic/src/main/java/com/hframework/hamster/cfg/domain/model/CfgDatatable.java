package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgDatatable {
    private Long id;

    private Long cfgDatasourceId;

    private String templateTableName;

    private String subTableName;

    private String createSql;

    private Byte status;

    private Date createTime;

    private Long creatorId;

    private Long modifierId;

    private Date modifyTime;

    public CfgDatatable(Long id, Long cfgDatasourceId, String templateTableName, String subTableName, String createSql, Byte status, Date createTime, Long creatorId, Long modifierId, Date modifyTime) {
        this.id = id;
        this.cfgDatasourceId = cfgDatasourceId;
        this.templateTableName = templateTableName;
        this.subTableName = subTableName;
        this.createSql = createSql;
        this.status = status;
        this.createTime = createTime;
        this.creatorId = creatorId;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public Long getCfgDatasourceId() {
        return cfgDatasourceId;
    }

    public String getTemplateTableName() {
        return templateTableName;
    }

    public String getSubTableName() {
        return subTableName;
    }

    public String getCreateSql() {
        return createSql;
    }

    public Byte getStatus() {
        return status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Long getCreatorId() {
        return creatorId;
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

    public void setCfgDatasourceId(Long cfgDatasourceId) {
        this.cfgDatasourceId=cfgDatasourceId;
    }

    public void setTemplateTableName(String templateTableName) {
        this.templateTableName=templateTableName;
    }

    public void setSubTableName(String subTableName) {
        this.subTableName=subTableName;
    }

    public void setCreateSql(String createSql) {
        this.createSql=createSql;
    }

    public void setStatus(Byte status) {
        this.status=status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime=createTime;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId=creatorId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId=modifierId;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime=modifyTime;
    }

    public CfgDatatable() {
        super();
    }
}