package com.stock.pojo.vo.sys;

import com.stock.dao.model.sys.SysEmployee;

public class SysEmployeeVO extends SysEmployee {
	
	private String amenderName;
	

	private String departmentName;
	
	private String organizationStructureName;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getAmenderName() {
		return amenderName;
	}

	public void setAmenderName(String amenderName) {
		this.amenderName = amenderName;
	}

	public String getOrganizationStructureName() {
		return organizationStructureName;
	}

	public void setOrganizationStructureName(String organizationStructureName) {
		this.organizationStructureName = organizationStructureName;
	}


}
