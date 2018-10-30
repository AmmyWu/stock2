/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys
 * 类/接口名	: SysEmployeeService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:46:13
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:46:13
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.stock.dao.model.sys.SysEmployee;
import com.stock.pojo.vo.sys.SysEmployeeVO;



 /**
  * TODO 请在此处添加注释
  * @author <a href="mailto:chengxl@stocksoft.com">chengxl</a>
  * @version $Id$   
  * @since 2.0
  */
  
public interface SysEmployeeService {
	
	public int countEmployee(int organizationStructrueId);
	
	public SysEmployee getByCustomerName(String employeeName);
	
	public List<SysEmployee> getByCellPhone(String cellPhone);
	
	public Integer getByFromUserName(String fromusername);
	
	public void exportExcel(SysEmployeeVO employee,OutputStream  os);
	
	public List<SysEmployeeVO> getEmployees();
	
	public void init_EMPLOYEEMAP();
	
	public SysEmployee getEmployeeById(Integer employeeId);
	
	public Map<String,Object> getEmployeeByPage(String keys, Integer pageSize,
			Integer pageNow);
	 
	public Object update(SysEmployee employee);
	 
	public Object insert(SysEmployee employee);
	 
	public String deleteEmpById(String  ids[]);
	
	public List<Integer> getEmployeeIds(String employeeName);

	List<Map<String, Object>> getEmployeesOfUnallotUsers();

	public SysEmployee getByOpenId(String openId);

	public List<SysEmployee> getEmployeeByOrganizationId(
			Integer organizationStructureId);


	public List<SysEmployee> getEmployeeByCreatorId(Integer creator);

	public Object importExcel(MultipartFile excel);

	public List<SysEmployee> getEmployeeByCreatorIdAndEmployeeName(
			String employeeName, Integer creator);

	

}
