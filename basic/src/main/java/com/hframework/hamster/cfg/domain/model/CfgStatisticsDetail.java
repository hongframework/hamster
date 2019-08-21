package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgStatisticsDetail {
    private Long cfgStatisticsDetailId;

    private Long cfgStatisticsId;

    private String dbObjectName;

    private Byte dbObjectOperateType;

    private String dataFilterExpression;

    private Long cfgStatisticsTopicId;

    private String statisticsView;

    private String statisticsValue;

    private Byte count;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private String statisticsScript;

    public CfgStatisticsDetail(Long cfgStatisticsDetailId, Long cfgStatisticsId, String dbObjectName, Byte dbObjectOperateType, String dataFilterExpression, Long cfgStatisticsTopicId, String statisticsView, String statisticsValue, Byte count, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, String statisticsScript) {
        this.cfgStatisticsDetailId = cfgStatisticsDetailId;
        this.cfgStatisticsId = cfgStatisticsId;
        this.dbObjectName = dbObjectName;
        this.dbObjectOperateType = dbObjectOperateType;
        this.dataFilterExpression = dataFilterExpression;
        this.cfgStatisticsTopicId = cfgStatisticsTopicId;
        this.statisticsView = statisticsView;
        this.statisticsValue = statisticsValue;
        this.count = count;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.statisticsScript = statisticsScript;
    }

    public Long getCfgStatisticsDetailId() {
        return cfgStatisticsDetailId;
    }

    public Long getCfgStatisticsId() {
        return cfgStatisticsId;
    }

    public String getDbObjectName() {
        return dbObjectName;
    }

    public Byte getDbObjectOperateType() {
        return dbObjectOperateType;
    }

    public String getDataFilterExpression() {
        return dataFilterExpression;
    }

    public Long getCfgStatisticsTopicId() {
        return cfgStatisticsTopicId;
    }

    public String getStatisticsView() {
        return statisticsView;
    }

    public String getStatisticsValue() {
        return statisticsValue;
    }

    public Byte getCount() {
        return count;
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

    public String getStatisticsScript() {
        return statisticsScript;
    }

    public void setCfgStatisticsDetailId(Long cfgStatisticsDetailId) {
        this.cfgStatisticsDetailId=cfgStatisticsDetailId;
    }

    public void setCfgStatisticsId(Long cfgStatisticsId) {
        this.cfgStatisticsId=cfgStatisticsId;
    }

    public void setDbObjectName(String dbObjectName) {
        this.dbObjectName=dbObjectName;
    }

    public void setDbObjectOperateType(Byte dbObjectOperateType) {
        this.dbObjectOperateType=dbObjectOperateType;
    }

    public void setDataFilterExpression(String dataFilterExpression) {
        this.dataFilterExpression=dataFilterExpression;
    }

    public void setCfgStatisticsTopicId(Long cfgStatisticsTopicId) {
        this.cfgStatisticsTopicId=cfgStatisticsTopicId;
    }

    public void setStatisticsView(String statisticsView) {
        this.statisticsView=statisticsView;
    }

    public void setStatisticsValue(String statisticsValue) {
        this.statisticsValue=statisticsValue;
    }

    public void setCount(Byte count) {
        this.count=count;
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

    public void setStatisticsScript(String statisticsScript) {
        this.statisticsScript=statisticsScript;
    }

    public CfgStatisticsDetail() {
        super();
    }
}