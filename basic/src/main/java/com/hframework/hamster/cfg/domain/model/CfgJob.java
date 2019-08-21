package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgJob {
    private Long id;

    private Long jobTemplateId;

    private String name;

    private String code;

    private String description;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public CfgJob(Long id, Long jobTemplateId, String name, String code, String description, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.jobTemplateId = jobTemplateId;
        this.name = name;
        this.code = code;
        this.description = description;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public Long getJobTemplateId() {
        return jobTemplateId;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
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

    public void setJobTemplateId(Long jobTemplateId) {
        this.jobTemplateId=jobTemplateId;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setCode(String code) {
        this.code=code;
    }

    public void setDescription(String description) {
        this.description=description;
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

    public CfgJob() {
        super();
    }
}