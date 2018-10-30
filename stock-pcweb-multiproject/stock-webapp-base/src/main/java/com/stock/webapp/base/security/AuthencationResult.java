/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.sysadmin.security
 * 类/接口名	: AuthencationResult
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午4:57:31
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午4:57:31
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */

package com.stock.webapp.base.security;

import com.stock.dao.model.sys.SysCustomer;
import com.stock.dao.model.sys.SysEmployee;
import com.stock.dao.model.sys.SysUser;

import net.sf.json.JSONObject;

public class AuthencationResult {
	
	private String loginingAccount;
	
	private JSONObject menuJSON;
	
	private boolean status;
	
	private String msg;
	
	private SysUser user;
	
	private SysEmployee employee;

	private String btnResourceIds;

	private SysCustomer customer;
	

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public SysEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(SysEmployee employee) {
		this.employee = employee;
	}

//	public SysCustomer getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(SysCustomer customer) {
//		this.customer = customer;
//	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public String getLoginingAccount() {
		return loginingAccount;
	}

	public void setLoginingAccount(String loginingAccount) {
		this.loginingAccount = loginingAccount;
	}

	public JSONObject getMenuJSON() {
		return menuJSON;
	}

	public void setMenuJSON(JSONObject menuJSON) {
		this.menuJSON = menuJSON;
	}

	public String getBtnResourceIds() {
		return btnResourceIds;
	}

	public void setBtnResourceIds(String btnResourceIds) {
		this.btnResourceIds = btnResourceIds;
	}

	/**
	 * @return the customer
	 */
	public SysCustomer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(SysCustomer customer) {
		this.customer = customer;
	}

	
	
}
