package com.stock.dao.model.sys;

import java.util.Date;

public class SysSetting {
    
	private Integer stKey;

    private String stValue;

    private String stType;

    private String description;

    private Integer creator;

    private Date createTime;

    private Integer accessGroup;

    private Integer amender;

    private Date amendTime;

    private Integer recordVersion;

    private Integer isDeleted;

    public Integer getStKey() {
        return stKey;
    }

    public void setStKey(Integer stKey) {
        this.stKey = stKey;
    }

    public String getStValue() {
        return stValue;
    }

    public void setStValue(String stValue) {
        this.stValue = stValue == null ? null : stValue.trim();
    }

    public String getStType() {
        return stType;
    }

    public void setStType(String stType) {
        this.stType = stType == null ? null : stType.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAccessGroup() {
        return accessGroup;
    }

    public void setAccessGroup(Integer accessGroup) {
        this.accessGroup = accessGroup;
    }

    public Integer getAmender() {
        return amender;
    }

    public void setAmender(Integer amender) {
        this.amender = amender;
    }

    public Date getAmendTime() {
        return amendTime;
    }

    public void setAmendTime(Date amendTime) {
        this.amendTime = amendTime;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}