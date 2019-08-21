package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgStatistics {
    private Long cfgStatisticsId;

    private String cfgStatisticsName;

    private Long cfgDatasourceId;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private String logBeginFile;

    private Long logBeginPosition;

    private Date logBeginTimestamp;

    private String logEndFile;

    private Long logEndPosition;

    private Date logEndTimestamp;

    public CfgStatistics(Long cfgStatisticsId, String cfgStatisticsName, Long cfgDatasourceId, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, String logBeginFile, Long logBeginPosition, Date logBeginTimestamp, String logEndFile, Long logEndPosition, Date logEndTimestamp) {
        this.cfgStatisticsId = cfgStatisticsId;
        this.cfgStatisticsName = cfgStatisticsName;
        this.cfgDatasourceId = cfgDatasourceId;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.logBeginFile = logBeginFile;
        this.logBeginPosition = logBeginPosition;
        this.logBeginTimestamp = logBeginTimestamp;
        this.logEndFile = logEndFile;
        this.logEndPosition = logEndPosition;
        this.logEndTimestamp = logEndTimestamp;
    }

    public Long getCfgStatisticsId() {
        return cfgStatisticsId;
    }

    public String getCfgStatisticsName() {
        return cfgStatisticsName;
    }

    public Long getCfgDatasourceId() {
        return cfgDatasourceId;
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

    public String getLogBeginFile() {
        return logBeginFile;
    }

    public Long getLogBeginPosition() {
        return logBeginPosition;
    }

    public Date getLogBeginTimestamp() {
        return logBeginTimestamp;
    }

    public String getLogEndFile() {
        return logEndFile;
    }

    public Long getLogEndPosition() {
        return logEndPosition;
    }

    public Date getLogEndTimestamp() {
        return logEndTimestamp;
    }

    public void setCfgStatisticsId(Long cfgStatisticsId) {
        this.cfgStatisticsId=cfgStatisticsId;
    }

    public void setCfgStatisticsName(String cfgStatisticsName) {
        this.cfgStatisticsName=cfgStatisticsName;
    }

    public void setCfgDatasourceId(Long cfgDatasourceId) {
        this.cfgDatasourceId=cfgDatasourceId;
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

    public void setLogBeginFile(String logBeginFile) {
        this.logBeginFile=logBeginFile;
    }

    public void setLogBeginPosition(Long logBeginPosition) {
        this.logBeginPosition=logBeginPosition;
    }

    public void setLogBeginTimestamp(Date logBeginTimestamp) {
        this.logBeginTimestamp=logBeginTimestamp;
    }

    public void setLogEndFile(String logEndFile) {
        this.logEndFile=logEndFile;
    }

    public void setLogEndPosition(Long logEndPosition) {
        this.logEndPosition=logEndPosition;
    }

    public void setLogEndTimestamp(Date logEndTimestamp) {
        this.logEndTimestamp=logEndTimestamp;
    }

    public CfgStatistics() {
        super();
    }
}