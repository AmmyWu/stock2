package com.stock.dao.model.sys;

import java.io.Serializable;
import java.util.Date;

public class SysAuthentication implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.authentication_id
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private Integer authenticationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.user_id
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.account
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private String account;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.password
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.type
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.status
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.last_time
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private Date lastTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.last_ip
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private String lastIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.description
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.creator
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private Integer creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.create_time
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.access_group
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private Integer accessGroup;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.amender
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private Integer amender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.amend_time
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private Date amendTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.record_version
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private Integer recordVersion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_authentication.is_deleted
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private Integer isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_authentication
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.authentication_id
     *
     * @return the value of sys_authentication.authentication_id
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public Integer getAuthenticationId() {
        return authenticationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.authentication_id
     *
     * @param authenticationId the value for sys_authentication.authentication_id
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setAuthenticationId(Integer authenticationId) {
        this.authenticationId = authenticationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.user_id
     *
     * @return the value of sys_authentication.user_id
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.user_id
     *
     * @param userId the value for sys_authentication.user_id
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.account
     *
     * @return the value of sys_authentication.account
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.account
     *
     * @param account the value for sys_authentication.account
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.password
     *
     * @return the value of sys_authentication.password
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.password
     *
     * @param password the value for sys_authentication.password
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.type
     *
     * @return the value of sys_authentication.type
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.type
     *
     * @param type the value for sys_authentication.type
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.status
     *
     * @return the value of sys_authentication.status
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.status
     *
     * @param status the value for sys_authentication.status
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.last_time
     *
     * @return the value of sys_authentication.last_time
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public Date getLastTime() {
        return lastTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.last_time
     *
     * @param lastTime the value for sys_authentication.last_time
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.last_ip
     *
     * @return the value of sys_authentication.last_ip
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public String getLastIp() {
        return lastIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.last_ip
     *
     * @param lastIp the value for sys_authentication.last_ip
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp == null ? null : lastIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.description
     *
     * @return the value of sys_authentication.description
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.description
     *
     * @param description the value for sys_authentication.description
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.creator
     *
     * @return the value of sys_authentication.creator
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.creator
     *
     * @param creator the value for sys_authentication.creator
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.create_time
     *
     * @return the value of sys_authentication.create_time
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.create_time
     *
     * @param createTime the value for sys_authentication.create_time
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.access_group
     *
     * @return the value of sys_authentication.access_group
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public Integer getAccessGroup() {
        return accessGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.access_group
     *
     * @param accessGroup the value for sys_authentication.access_group
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setAccessGroup(Integer accessGroup) {
        this.accessGroup = accessGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.amender
     *
     * @return the value of sys_authentication.amender
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public Integer getAmender() {
        return amender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.amender
     *
     * @param amender the value for sys_authentication.amender
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setAmender(Integer amender) {
        this.amender = amender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.amend_time
     *
     * @return the value of sys_authentication.amend_time
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public Date getAmendTime() {
        return amendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.amend_time
     *
     * @param amendTime the value for sys_authentication.amend_time
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setAmendTime(Date amendTime) {
        this.amendTime = amendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.record_version
     *
     * @return the value of sys_authentication.record_version
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public Integer getRecordVersion() {
        return recordVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.record_version
     *
     * @param recordVersion the value for sys_authentication.record_version
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_authentication.is_deleted
     *
     * @return the value of sys_authentication.is_deleted
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_authentication.is_deleted
     *
     * @param isDeleted the value for sys_authentication.is_deleted
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_authentication
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysAuthentication other = (SysAuthentication) that;
        return (this.getAuthenticationId() == null ? other.getAuthenticationId() == null : this.getAuthenticationId().equals(other.getAuthenticationId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getLastTime() == null ? other.getLastTime() == null : this.getLastTime().equals(other.getLastTime()))
            && (this.getLastIp() == null ? other.getLastIp() == null : this.getLastIp().equals(other.getLastIp()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getAccessGroup() == null ? other.getAccessGroup() == null : this.getAccessGroup().equals(other.getAccessGroup()))
            && (this.getAmender() == null ? other.getAmender() == null : this.getAmender().equals(other.getAmender()))
            && (this.getAmendTime() == null ? other.getAmendTime() == null : this.getAmendTime().equals(other.getAmendTime()))
            && (this.getRecordVersion() == null ? other.getRecordVersion() == null : this.getRecordVersion().equals(other.getRecordVersion()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_authentication
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAuthenticationId() == null) ? 0 : getAuthenticationId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getLastTime() == null) ? 0 : getLastTime().hashCode());
        result = prime * result + ((getLastIp() == null) ? 0 : getLastIp().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getAccessGroup() == null) ? 0 : getAccessGroup().hashCode());
        result = prime * result + ((getAmender() == null) ? 0 : getAmender().hashCode());
        result = prime * result + ((getAmendTime() == null) ? 0 : getAmendTime().hashCode());
        result = prime * result + ((getRecordVersion() == null) ? 0 : getRecordVersion().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_authentication
     *
     * @mbggenerated Mon May 22 06:22:45 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", authenticationId=").append(authenticationId);
        sb.append(", userId=").append(userId);
        sb.append(", account=").append(account);
        sb.append(", password=").append(password);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", lastTime=").append(lastTime);
        sb.append(", lastIp=").append(lastIp);
        sb.append(", description=").append(description);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", accessGroup=").append(accessGroup);
        sb.append(", amender=").append(amender);
        sb.append(", amendTime=").append(amendTime);
        sb.append(", recordVersion=").append(recordVersion);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}