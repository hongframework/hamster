package com.hframework.hamster.sch.domain.model;

import java.util.Date;

public class LogDeploy {
    private Long id;

    private Long deployId;

    private Date startTime;

    private Date executeTime;

    private Date endTime;

    private String startInfo;

    private String endInfo;

    private String errorInfo;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private String selectKey;

    private String deployCode;

    public LogDeploy(Long id, Long deployId, Date startTime, Date executeTime, Date endTime, String startInfo, String endInfo, String errorInfo, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, String selectKey, String deployCode) {
        this.id = id;
        this.deployId = deployId;
        this.startTime = startTime;
        this.executeTime = executeTime;
        this.endTime = endTime;
        this.startInfo = startInfo;
        this.endInfo = endInfo;
        this.errorInfo = errorInfo;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.selectKey = selectKey;
        this.deployCode = deployCode;
    }

    public Long getId() {
        return id;
    }

    public Long getDeployId() {
        return deployId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getStartInfo() {
        return startInfo;
    }

    public String getEndInfo() {
        return endInfo;
    }

    public String getErrorInfo() {
        return errorInfo;
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

    public String getSelectKey() {
        return selectKey;
    }

    public String getDeployCode() {
        return deployCode;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setDeployId(Long deployId) {
        this.deployId=deployId;
    }

    public void setStartTime(Date startTime) {
        this.startTime=startTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime=executeTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime=endTime;
    }

    public void setStartInfo(String startInfo) {
        this.startInfo=startInfo;
    }

    public void setEndInfo(String endInfo) {
        this.endInfo=endInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo=errorInfo;
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

    public void setSelectKey(String selectKey) {
        this.selectKey=selectKey;
    }

    public void setDeployCode(String deployCode) {
        this.deployCode=deployCode;
    }

    public LogDeploy() {
        super();
    }
}