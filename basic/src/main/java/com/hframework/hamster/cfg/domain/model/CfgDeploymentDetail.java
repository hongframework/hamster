package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgDeploymentDetail {
    private Long id;

    private Long jobTemplateId;

    private Long jobId;

    private Long nodeTemplateId;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Byte status;

    private Long deploymentId;

    public CfgDeploymentDetail(Long id, Long jobTemplateId, Long jobId, Long nodeTemplateId, Long creatorId, Date createTime, Long modifierId, Date modifyTime, Byte status, Long deploymentId) {
        this.id = id;
        this.jobTemplateId = jobTemplateId;
        this.jobId = jobId;
        this.nodeTemplateId = nodeTemplateId;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.status = status;
        this.deploymentId = deploymentId;
    }

    public Long getId() {
        return id;
    }

    public Long getJobTemplateId() {
        return jobTemplateId;
    }

    public Long getJobId() {
        return jobId;
    }

    public Long getNodeTemplateId() {
        return nodeTemplateId;
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

    public Byte getStatus() {
        return status;
    }

    public Long getDeploymentId() {
        return deploymentId;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setJobTemplateId(Long jobTemplateId) {
        this.jobTemplateId=jobTemplateId;
    }

    public void setJobId(Long jobId) {
        this.jobId=jobId;
    }

    public void setNodeTemplateId(Long nodeTemplateId) {
        this.nodeTemplateId=nodeTemplateId;
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

    public void setStatus(Byte status) {
        this.status=status;
    }

    public void setDeploymentId(Long deploymentId) {
        this.deploymentId=deploymentId;
    }

    public CfgDeploymentDetail() {
        super();
    }
}