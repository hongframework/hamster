package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgSubscribe {
    private Long cfgSubscribeId;

    private Short type;

    private Long cfgDatasourceId;

    private String dbObjectName;

    private String dataFilterExpression;

    private Long cfgTopicId;

    private Byte partitionStrategy;

    private String partitionKey;

    private Long logBeginPosition;

    private Date logBeginTimestamp;

    private Long logEndPosition;

    private Date logEndTimestamp;

    private Long logCurrentPosition;

    private String cfgSubscribeName;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private String logBeginFile;

    private String logEndFile;

    private Long cfgBrokerId;

    private Byte dbObjectOperateType;

    public CfgSubscribe(Long cfgSubscribeId, Short type, Long cfgDatasourceId, String dbObjectName, String dataFilterExpression, Long cfgTopicId, Byte partitionStrategy, String partitionKey, Long logBeginPosition, Date logBeginTimestamp, Long logEndPosition, Date logEndTimestamp, Long logCurrentPosition, String cfgSubscribeName, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, String logBeginFile, String logEndFile, Long cfgBrokerId, Byte dbObjectOperateType) {
        this.cfgSubscribeId = cfgSubscribeId;
        this.type = type;
        this.cfgDatasourceId = cfgDatasourceId;
        this.dbObjectName = dbObjectName;
        this.dataFilterExpression = dataFilterExpression;
        this.cfgTopicId = cfgTopicId;
        this.partitionStrategy = partitionStrategy;
        this.partitionKey = partitionKey;
        this.logBeginPosition = logBeginPosition;
        this.logBeginTimestamp = logBeginTimestamp;
        this.logEndPosition = logEndPosition;
        this.logEndTimestamp = logEndTimestamp;
        this.logCurrentPosition = logCurrentPosition;
        this.cfgSubscribeName = cfgSubscribeName;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.logBeginFile = logBeginFile;
        this.logEndFile = logEndFile;
        this.cfgBrokerId = cfgBrokerId;
        this.dbObjectOperateType = dbObjectOperateType;
    }

    public Long getCfgSubscribeId() {
        return cfgSubscribeId;
    }

    public Short getType() {
        return type;
    }

    public Long getCfgDatasourceId() {
        return cfgDatasourceId;
    }

    public String getDbObjectName() {
        return dbObjectName;
    }

    public String getDataFilterExpression() {
        return dataFilterExpression;
    }

    public Long getCfgTopicId() {
        return cfgTopicId;
    }

    public Byte getPartitionStrategy() {
        return partitionStrategy;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public Long getLogBeginPosition() {
        return logBeginPosition;
    }

    public Date getLogBeginTimestamp() {
        return logBeginTimestamp;
    }

    public Long getLogEndPosition() {
        return logEndPosition;
    }

    public Date getLogEndTimestamp() {
        return logEndTimestamp;
    }

    public Long getLogCurrentPosition() {
        return logCurrentPosition;
    }

    public String getCfgSubscribeName() {
        return cfgSubscribeName;
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

    public String getLogEndFile() {
        return logEndFile;
    }

    public Long getCfgBrokerId() {
        return cfgBrokerId;
    }

    public Byte getDbObjectOperateType() {
        return dbObjectOperateType;
    }

    public void setCfgSubscribeId(Long cfgSubscribeId) {
        this.cfgSubscribeId=cfgSubscribeId;
    }

    public void setType(Short type) {
        this.type=type;
    }

    public void setCfgDatasourceId(Long cfgDatasourceId) {
        this.cfgDatasourceId=cfgDatasourceId;
    }

    public void setDbObjectName(String dbObjectName) {
        this.dbObjectName=dbObjectName;
    }

    public void setDataFilterExpression(String dataFilterExpression) {
        this.dataFilterExpression=dataFilterExpression;
    }

    public void setCfgTopicId(Long cfgTopicId) {
        this.cfgTopicId=cfgTopicId;
    }

    public void setPartitionStrategy(Byte partitionStrategy) {
        this.partitionStrategy=partitionStrategy;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey=partitionKey;
    }

    public void setLogBeginPosition(Long logBeginPosition) {
        this.logBeginPosition=logBeginPosition;
    }

    public void setLogBeginTimestamp(Date logBeginTimestamp) {
        this.logBeginTimestamp=logBeginTimestamp;
    }

    public void setLogEndPosition(Long logEndPosition) {
        this.logEndPosition=logEndPosition;
    }

    public void setLogEndTimestamp(Date logEndTimestamp) {
        this.logEndTimestamp=logEndTimestamp;
    }

    public void setLogCurrentPosition(Long logCurrentPosition) {
        this.logCurrentPosition=logCurrentPosition;
    }

    public void setCfgSubscribeName(String cfgSubscribeName) {
        this.cfgSubscribeName=cfgSubscribeName;
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

    public void setLogEndFile(String logEndFile) {
        this.logEndFile=logEndFile;
    }

    public void setCfgBrokerId(Long cfgBrokerId) {
        this.cfgBrokerId=cfgBrokerId;
    }

    public void setDbObjectOperateType(Byte dbObjectOperateType) {
        this.dbObjectOperateType=dbObjectOperateType;
    }

    public CfgSubscribe() {
        super();
    }
}