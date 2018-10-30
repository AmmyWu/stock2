package com.stock.pojo.vo.sys;


import com.stock.dao.model.sys.SysUser;

public class SysUserVO extends SysUser {
	
//	public String departmentId;

	
	public Integer accessGroup;
	
	private String userDetailName;
	
//	private String sCreateTime ;
	
	private String sLastLoginTime;
	
	private String roleNames;
	
	private String loginStatus;

//	private String creatorName;
	
	private String sAmendTime ;
	
	private String amenderName ;
	
	public String getsLastLoginTime() {
		return sLastLoginTime;
	}

	public void setsLastLoginTime(String sLastLoginTime) {
		this.sLastLoginTime = sLastLoginTime;
	}


	public String getUserDetailName() {
		return userDetailName;
	}

	public void setUserDetailName(String userDetailName) {
		this.userDetailName = userDetailName;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	

	public Integer getAccessGroup() {
		return accessGroup;
	}

	public void setAccessGroup(Integer accessGroup) {
		this.accessGroup = accessGroup;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}


	public String getsAmendTime() {
		return sAmendTime;
	}

	public void setsAmendTime(String sAmendTime) {
		this.sAmendTime = sAmendTime;
	}

	public String getAmenderName() {
		return amenderName;
	}

	public void setAmenderName(String amenderName) {
		this.amenderName = amenderName;
	}



	
	

}
