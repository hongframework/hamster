package com.hframework.hamster.sch.domain.model;

import java.util.Date;

public class JobExeMeta {
    private Long id;

    private String deploy;

    private String datastore;

    private String sourceTable;

    private String targetTable;

    private Date updateTime;

    private Long delayTime;

    private Long totalDelay;

    private Date executeTime;

    private String description;

    private String updateNode;

    public JobExeMeta(Long id, String deploy, String datastore, String sourceTable, String targetTable, Date updateTime, Long delayTime, Long totalDelay, Date executeTime, String description, String updateNode) {
        this.id = id;
        this.deploy = deploy;
        this.datastore = datastore;
        this.sourceTable = sourceTable;
        this.targetTable = targetTable;
        this.updateTime = updateTime;
        this.delayTime = delayTime;
        this.totalDelay = totalDelay;
        this.executeTime = executeTime;
        this.description = description;
        this.updateNode = updateNode;
    }

    public Long getId() {
        return id;
    }

    public String getDeploy() {
        return deploy;
    }

    public String getDatastore() {
        return datastore;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Long getDelayTime() {
        return delayTime;
    }

    public Long getTotalDelay() {
        return totalDelay;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public String getDescription() {
        return description;
    }

    public String getUpdateNode() {
        return updateNode;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setDeploy(String deploy) {
        this.deploy=deploy;
    }

    public void setDatastore(String datastore) {
        this.datastore=datastore;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable=sourceTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable=targetTable;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime=updateTime;
    }

    public void setDelayTime(Long delayTime) {
        this.delayTime=delayTime;
    }

    public void setTotalDelay(Long totalDelay) {
        this.totalDelay=totalDelay;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime=executeTime;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setUpdateNode(String updateNode) {
        this.updateNode=updateNode;
    }

    public JobExeMeta() {
        super();
    }
}