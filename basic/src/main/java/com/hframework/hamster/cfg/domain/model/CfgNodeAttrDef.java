package com.hframework.hamster.cfg.domain.model;

import java.util.Date;

public class CfgNodeAttrDef {
    private Long id;

    private Long nodeId;

    private String code;

    private String name;

    private String description;

    private String type;

    private Byte necessary;

    private String relatAttr;

    private String relatDict;

    private String defVal;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public CfgNodeAttrDef(Long id, Long nodeId, String code, String name, String description, String type, Byte necessary, String relatAttr, String relatDict, String defVal, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.nodeId = nodeId;
        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.necessary = necessary;
        this.relatAttr = relatAttr;
        this.relatDict = relatDict;
        this.defVal = defVal;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Byte getNecessary() {
        return necessary;
    }

    public String getRelatAttr() {
        return relatAttr;
    }

    public String getRelatDict() {
        return relatDict;
    }

    public String getDefVal() {
        return defVal;
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

    public void setNodeId(Long nodeId) {
        this.nodeId=nodeId;
    }

    public void setCode(String code) {
        this.code=code;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setType(String type) {
        this.type=type;
    }

    public void setNecessary(Byte necessary) {
        this.necessary=necessary;
    }

    public void setRelatAttr(String relatAttr) {
        this.relatAttr=relatAttr;
    }

    public void setRelatDict(String relatDict) {
        this.relatDict=relatDict;
    }

    public void setDefVal(String defVal) {
        this.defVal=defVal;
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

    public CfgNodeAttrDef() {
        super();
    }
}