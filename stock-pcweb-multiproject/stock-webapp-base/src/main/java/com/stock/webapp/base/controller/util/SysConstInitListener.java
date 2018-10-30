/**
 * 
 */
package com.stock.webapp.base.controller.util;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import com.stock.service.sys.SysBasicDataSetService;
import com.stock.service.sys.SysEmployeeService;
import com.stock.service.sys.SysOrganizationStructureService;
import com.stock.service.sys.SysResourceService;
import com.stock.service.sys.SysRoleResourceService;
import com.stock.service.sys.SysSettingService;
import com.stock.service.sys.SysUserService;

public class SysConstInitListener implements InitializingBean,
		ServletContextAware {

	@Autowired
	private SysResourceService resourcesService;
	
	@Autowired
	private SysOrganizationStructureService organizationStructureService;
	
	
	@Autowired
	private SysRoleResourceService rolesResourcesService;

	
	@Autowired
	private SysSettingService systemSettingService;
	
	@Autowired
	private SysEmployeeService employeeService;
	
	@Autowired
	private SysUserService userService;
	
	@Autowired
	private SysBasicDataSetService basicDataSetService;
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.context.ServletContextAware#setServletContext
	 * (javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 * spring
	 */
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
				
		resourcesService.init_RESOURCESTREE_UNINCLUDE_BUSINESS();
		
		resourcesService.init_RESOURCESTREE();
		
		organizationStructureService.init_ORGANIZATIONSTRUCTURE_TREE();
	
		rolesResourcesService.init_ROLE_RESOURCE();
	
		systemSettingService.init_SYSTEMSETTING();
		
		employeeService.init_EMPLOYEEMAP();
		
		userService.init_USERMAP();
		basicDataSetService.init_BASICDATASET_TREE();
	} 
}
