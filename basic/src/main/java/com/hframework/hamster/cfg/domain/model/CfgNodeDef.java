package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgNodeDef {
    private Long id;

    private String type;

    private String datasourceType;

    private String name;

    private String code;

    private String description;

    private String executeMethod;

    private String executeUri;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Byte status;

    public CfgNodeDef(Long id, String type, String datasourceType, String name, String code, String description, String executeMethod, String executeUri, Long creatorId, Date createTime, Long modifierId, Date modifyTime, Byte status) {
        this.id = id;
        this.type = type;
        this.datasourceType = datasourceType;
        this.name = name;
        this.code = code;
        this.description = description;
        this.executeMethod = executeMethod;
        this.executeUri = executeUri;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDatasourceType() {
        return datasourceType;
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

    public String getExecuteMethod() {
        return executeMethod;
    }

    public String getExecuteUri() {
        return executeUri;
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

    public void setId(Long id) {
        this.id=id;
    }

    public void setType(String type) {
        this.type=type;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType=datasourceType;
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

    public void setExecuteMethod(String executeMethod) {
        this.executeMethod=executeMethod;
    }

    public void setExecuteUri(String executeUri) {
        this.executeUri=executeUri;
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

    public CfgNodeDef() {
        super();
    }
}