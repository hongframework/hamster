package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgSubscribeDetail {
    private Long cfgSubscribeDetailId;

    private Long cfgSubscribeId;

    private String dbObjectName;

    private Long cfgTopicId;

    private String dbObjectDatas;

    private Byte partitionStrategy;

    private String partitionKey;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private String dataFilterExpression;

    private Byte dbObjectOperateType;

    public CfgSubscribeDetail(Long cfgSubscribeDetailId, Long cfgSubscribeId, String dbObjectName, Long cfgTopicId, String dbObjectDatas, Byte partitionStrategy, String partitionKey, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, String dataFilterExpression, Byte dbObjectOperateType) {
        this.cfgSubscribeDetailId = cfgSubscribeDetailId;
        this.cfgSubscribeId = cfgSubscribeId;
        this.dbObjectName = dbObjectName;
        this.cfgTopicId = cfgTopicId;
        this.dbObjectDatas = dbObjectDatas;
        this.partitionStrategy = partitionStrategy;
        this.partitionKey = partitionKey;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.dataFilterExpression = dataFilterExpression;
        this.dbObjectOperateType = dbObjectOperateType;
    }

    public Long getCfgSubscribeDetailId() {
        return cfgSubscribeDetailId;
    }

    public Long getCfgSubscribeId() {
        return cfgSubscribeId;
    }

    public String getDbObjectName() {
        return dbObjectName;
    }

    public Long getCfgTopicId() {
        return cfgTopicId;
    }

    public String getDbObjectDatas() {
        return dbObjectDatas;
    }

    public Byte getPartitionStrategy() {
        return partitionStrategy;
    }

    public String getPartitionKey() {
        return partitionKey;
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

    public String getDataFilterExpression() {
        return dataFilterExpression;
    }

    public Byte getDbObjectOperateType() {
        return dbObjectOperateType;
    }

    public void setCfgSubscribeDetailId(Long cfgSubscribeDetailId) {
        this.cfgSubscribeDetailId=cfgSubscribeDetailId;
    }

    public void setCfgSubscribeId(Long cfgSubscribeId) {
        this.cfgSubscribeId=cfgSubscribeId;
    }

    public void setDbObjectName(String dbObjectName) {
        this.dbObjectName=dbObjectName;
    }

    public void setCfgTopicId(Long cfgTopicId) {
        this.cfgTopicId=cfgTopicId;
    }

    public void setDbObjectDatas(String dbObjectDatas) {
        this.dbObjectDatas=dbObjectDatas;
    }

    public void setPartitionStrategy(Byte partitionStrategy) {
        this.partitionStrategy=partitionStrategy;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey=partitionKey;
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

    public void setDataFilterExpression(String dataFilterExpression) {
        this.dataFilterExpression=dataFilterExpression;
    }

    public void setDbObjectOperateType(Byte dbObjectOperateType) {
        this.dbObjectOperateType=dbObjectOperateType;
    }

    public CfgSubscribeDetail() {
        super();
    }
}