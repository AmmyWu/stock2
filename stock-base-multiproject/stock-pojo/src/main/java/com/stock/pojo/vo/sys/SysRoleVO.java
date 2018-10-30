package com.stock.pojo.vo.sys;

import com.stock.dao.model.sys.SysRole;

public class SysRoleVO extends SysRole {
	
	private boolean checked;
	
	private String amenderName;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getAmenderName() {
		return amenderName;
	}

	public void setAmenderName(String amenderName) {
		this.amenderName = amenderName;
	}

}
