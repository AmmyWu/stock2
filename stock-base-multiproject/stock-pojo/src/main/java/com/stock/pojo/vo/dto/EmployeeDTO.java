package com.stock.pojo.vo.dto;

import com.stock.dao.model.sys.SysEmployee;


public class EmployeeDTO extends SysEmployee {
	//组织节点
	private String organizationStructureIdString;
	//员工代码
	private String employeeCodeString;
	
	private String employeeNameString;
	
	private String employeeCellPhoneString;
	
	private String employeeDepartmentString;
	
	private String creatorString;
	//错误信息
	private String errorMessage;

	

	/**  
	 *@return  the organizationStructureIdString
	 */
	
	public String getOrganizationStructureIdString() {
		return organizationStructureIdString;
	}

	/** 
	 * @param organizationStructureIdString the organizationStructureIdString to set
	 */
	public void setOrganizationStructureIdString(
			String organizationStructureIdString) {
		this.organizationStructureIdString = organizationStructureIdString;
	}

	/**  
	 *@return  the employeeCodeString
	 */
	
	public String getEmployeeCodeString() {
		return employeeCodeString;
	}

	/** 
	 * @param employeeCodeString the employeeCodeString to set
	 */
	public void setEmployeeCodeString(String employeeCodeString) {
		this.employeeCodeString = employeeCodeString;
	}

	/**  
	 *@return  the employeeNameString
	 */
	
	public String getEmployeeNameString() {
		return employeeNameString;
	}

	/** 
	 * @param employeeNameString the employeeNameString to set
	 */
	public void setEmployeeNameString(String employeeNameString) {
		this.employeeNameString = employeeNameString;
	}

	/**  
	 *@return  the employeeCellPhoneString
	 */
	
	public String getEmployeeCellPhoneString() {
		return employeeCellPhoneString;
	}

	/** 
	 * @param employeeCellPhoneString the employeeCellPhoneString to set
	 */
	public void setEmployeeCellPhoneString(String employeeCellPhoneString) {
		this.employeeCellPhoneString = employeeCellPhoneString;
	}

	/**  
	 *@return  the employeeDepartmentString
	 */
	
	public String getEmployeeDepartmentString() {
		return employeeDepartmentString;
	}

	/** 
	 * @param employeeDepartmentString the employeeDepartmentString to set
	 */
	public void setEmployeeDepartmentString(String employeeDepartmentString) {
		this.employeeDepartmentString = employeeDepartmentString;
	}

	/**  
	 *@return  the creatorString
	 */
	
	public String getCreatorString() {
		return creatorString;
	}

	/** 
	 * @param creatorString the creatorString to set
	 */
	public void setCreatorString(String creatorString) {
		this.creatorString = creatorString;
	}

	/**  
	 *@return  the errorMessage
	 */
	
	public String getErrorMessage() {
		return errorMessage;
	}

	/** 
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
