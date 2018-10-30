/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.controller.pcweb
 * 类/接口名	: SysUsersRolesController
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午2:44:34
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午2:44:34
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.controller.pcweb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.sys.SysUserRole;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.sys.SysUserRoleService;

@Controller
@RequestMapping(value = "/userRole")
public class SysUserRoleController {

	@Autowired
	private SysUserRoleService userRoleService;
	
	
	/**
	 * 
	 * getHttpRoles:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param userId
	 * @return 
	 * @since JDK 1.7
	 */
		@RequestMapping(value = "/getUserRoleVOMap.do")
		public @ResponseBody Object getUserRoleVOMap(Integer userId) {
			
			
			
			return userRoleService.getUserRoleVOMap(userId);
		}

/**
 * 
 * getHttpRoles:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @param userId
 * @return 
 * @since JDK 1.7
 */
	@RequestMapping(value = "/getHttpRoles.do")
	public @ResponseBody List<SysUserRole> getHttpRoles(Integer userId) {
		
		return userRoleService.getUserRoleList(userId);
	}
/**
 * 
 * add:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @param request
 * @return 
 * @since JDK 1.7
 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert.do")
	public @ResponseBody RequestResultVO add(HttpServletRequest request) {
		
		String roleId = request.getParameter("roleId");
		String userId = request.getParameter("userId");

		//
		RequestResultVO j = new RequestResultVO();
		if (userRoleService.insert(userId,roleId)) {
			j.setCode(0);
			j.setMessage("角色分配成功！");
		} else {
			j.setCode(1000);
			j.setMessage("角色分配失败!");
		}
		return j;
	}
/**
 * 
 * del:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @param request
 * @return 
 * @since JDK 1.7
 */
	@RequestMapping(value = "/delete.do")
	public @ResponseBody
	RequestResultVO del(HttpServletRequest request) {

		String roleId = request.getParameter("roleId");
		String userId = request.getParameter("userId");

		//
		RequestResultVO j = new RequestResultVO();
		if (userRoleService.delete( userId,roleId)) {
			j.setCode(0);
			j.setMessage("角色删除成功!");
		} else {
			j.setCode(1000);
			j.setMessage("角色删除失败!");
		}
		return j;
	}



}
