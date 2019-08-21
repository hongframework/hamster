package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgDeployment {
    private Long id;

    private String code;

    private String name;

    private Byte type;

    private Long datasourceId;

    private String logBeginPosition;

    private String logEndPosition;

    private Date logBeginTimestamp;

    private Date logEndTimestamp;

    private String labels;

    private String secondLabels;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private String deployJson;

    private String alarmStrategy;

    public CfgDeployment(Long id, String code, String name, Byte type, Long datasourceId, String logBeginPosition, String logEndPosition, Date logBeginTimestamp, Date logEndTimestamp, String labels, String secondLabels, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, String deployJson, String alarmStrategy) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.datasourceId = datasourceId;
        this.logBeginPosition = logBeginPosition;
        this.logEndPosition = logEndPosition;
        this.logBeginTimestamp = logBeginTimestamp;
        this.logEndTimestamp = logEndTimestamp;
        this.labels = labels;
        this.secondLabels = secondLabels;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.deployJson = deployJson;
        this.alarmStrategy = alarmStrategy;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Byte getType() {
        return type;
    }

    public Long getDatasourceId() {
        return datasourceId;
    }

    public String getLogBeginPosition() {
        return logBeginPosition;
    }

    public String getLogEndPosition() {
        return logEndPosition;
    }

    public Date getLogBeginTimestamp() {
        return logBeginTimestamp;
    }

    public Date getLogEndTimestamp() {
        return logEndTimestamp;
    }

    public String getLabels() {
        return labels;
    }

    public String getSecondLabels() {
        return secondLabels;
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

    public String getDeployJson() {
        return deployJson;
    }

    public String getAlarmStrategy() {
        return alarmStrategy;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setCode(String code) {
        this.code=code;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setType(Byte type) {
        this.type=type;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId=datasourceId;
    }

    public void setLogBeginPosition(String logBeginPosition) {
        this.logBeginPosition=logBeginPosition;
    }

    public void setLogEndPosition(String logEndPosition) {
        this.logEndPosition=logEndPosition;
    }

    public void setLogBeginTimestamp(Date logBeginTimestamp) {
        this.logBeginTimestamp=logBeginTimestamp;
    }

    public void setLogEndTimestamp(Date logEndTimestamp) {
        this.logEndTimestamp=logEndTimestamp;
    }

    public void setLabels(String labels) {
        this.labels=labels;
    }

    public void setSecondLabels(String secondLabels) {
        this.secondLabels=secondLabels;
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

    public void setDeployJson(String deployJson) {
        this.deployJson=deployJson;
    }

    public void setAlarmStrategy(String alarmStrategy) {
        this.alarmStrategy=alarmStrategy;
    }

    public CfgDeployment() {
        super();
    }
}