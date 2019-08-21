package com.hframework.hamster.sec.domain.model;

import java.util.Date;

public class HfsecUser {
    private Long hfsecUserId;

    private String hfsecUserName;

    private String account;

    private String password;

    private Integer gender;

    private String mobile;

    private Integer email;

    private Integer addr;

    private String avatar;

    private Date lastLoginTime;

    private Integer status;

    private Long hfucOrgId;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Integer delFlag;

    private Long hfsecOrganizeId;

    public HfsecUser(Long hfsecUserId, String hfsecUserName, String account, String password, Integer gender, String mobile, Integer email, Integer addr, String avatar, Date lastLoginTime, Integer status, Long hfucOrgId, Long creatorId, Date createTime, Long modifierId, Date modifyTime, Integer delFlag, Long hfsecOrganizeId) {
        this.hfsecUserId = hfsecUserId;
        this.hfsecUserName = hfsecUserName;
        this.account = account;
        this.password = password;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
        this.addr = addr;
        this.avatar = avatar;
        this.lastLoginTime = lastLoginTime;
        this.status = status;
        this.hfucOrgId = hfucOrgId;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.delFlag = delFlag;
        this.hfsecOrganizeId = hfsecOrganizeId;
    }

    public Long getHfsecUserId() {
        return hfsecUserId;
    }

    public String getHfsecUserName() {
        return hfsecUserName;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public Integer getGender() {
        return gender;
    }

    public String getMobile() {
        return mobile;
    }

    public Integer getEmail() {
        return email;
    }

    public Integer getAddr() {
        return addr;
    }

    public String getAvatar() {
        return avatar;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getHfucOrgId() {
        return hfucOrgId;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public Long getHfsecOrganizeId() {
        return hfsecOrganizeId;
    }

    public void setHfsecUserId(Long hfsecUserId) {
        this.hfsecUserId=hfsecUserId;
    }

    public void setHfsecUserName(String hfsecUserName) {
        this.hfsecUserName=hfsecUserName;
    }

    public void setAccount(String account) {
        this.account=account;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public void setGender(Integer gender) {
        this.gender=gender;
    }

    public void setMobile(String mobile) {
        this.mobile=mobile;
    }

    public void setEmail(Integer email) {
        this.email=email;
    }

    public void setAddr(Integer addr) {
        this.addr=addr;
    }

    public void setAvatar(String avatar) {
        this.avatar=avatar;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime=lastLoginTime;
    }

    public void setStatus(Integer status) {
        this.status=status;
    }

    public void setHfucOrgId(Long hfucOrgId) {
        this.hfucOrgId=hfucOrgId;
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

    public void setDelFlag(Integer delFlag) {
        this.delFlag=delFlag;
    }

    public void setHfsecOrganizeId(Long hfsecOrganizeId) {
        this.hfsecOrganizeId=hfsecOrganizeId;
    }

    public HfsecUser() {
        super();
    }
}