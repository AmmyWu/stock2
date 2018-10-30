/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.controller.pcweb
 * 类/接口名	: SysRolesResourcesController
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午2:44:34
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午2:44:34
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.controller.pcweb;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.sys.SysRoleResourceService;

@Controller
@RequestMapping(value = "/roleResource")
public class SysRoleResourceController {

	@Autowired
	private SysRoleResourceService roleResourceService;

	/*@RequestMapping(value = "/getRolesResourcesOS.do")
	public @ResponseBody
	Object getRolesResourcesOS(HttpServletRequest request) {

		return roleResourceService.getRolesResourcesOS(
				request.getParameter("roleId"), request.getParameter("resourceId"));

	}*/
	/**
	 * 
	 * getRolesResourcesOS:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getRoleResourceOS.do")
	public @ResponseBody Object getRolesResourcesOS(Integer roleId, Integer resourceId) {
		
		
		return roleResourceService.getRoleResourceOS(roleId,resourceId);

	}
	/**
	 * 
	 * getRolesResourcesInitOS:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getRolesResourcesInitOS.do")
	public @ResponseBody JSONArray getRolesResourcesInitOS(HttpServletRequest req) {
		
		return roleResourceService.getRoleResourceInitOS();

	}
	/**
	 * 
	 * getRolesResources:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param request
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getRoleResource.do")
	public @ResponseBody
	Object getRolesResources(HttpServletRequest request) {

		String roleId = request.getParameter("roleId");

		return roleResourceService.getRoleResource(roleId);

	}
/**
 * 
 * insertResources:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @param request
 * @return  
 * @since JDK 1.7
 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertResource.do")
	public @ResponseBody
	RequestResultVO insertResource(Integer roleId, Integer resourceId) {

//		String roleId = request.getParameter("roleId");
//		String resourcesId = request.getParameter("resourceId");

		// JSONArray jsonArray=JSONArray.fromObject("["+resourceInfo+"]");
		// List<Resources> res =
		// (List<Resources>)JSONArray.toCollection(jsonArray, Resources.class);
		//
		RequestResultVO j = new RequestResultVO();
		if (roleResourceService.insert(roleId, resourceId)) {
			j.setCode(0);
			j.setMessage("ok!");
		} else {
			j.setCode(1000);
			j.setMessage("erro!");
		}
		return j;
	}
	/**
	 * 
	 * deleteResources:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param request
	 * @return 
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteRoleResource.do")
	public @ResponseBody
	RequestResultVO deleteRoleResource(Integer roleId, Integer resourceId) {
	
//		String roleId = request.getParameter("roleId");
//		String resourcesId = request.getParameter("resourcesId");
	
		// JSONArray jsonArray=JSONArray.fromObject("["+resourceInfo+"]");
		// List<Resources> res =
		// (List<Resources>)JSONArray.toCollection(jsonArray, Resources.class);
	
		RequestResultVO j = new RequestResultVO();
		if (roleResourceService.delete(roleId, resourceId)) {
			j.setCode(0);
			j.setMessage("ok!");
		} else {
			j.setCode(1000);
			j.setMessage("erro!");
		}
		return j;
	}
/**
 * 
 * insertOS:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @param request
 * @return 
 * @since JDK 1.7
 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertRoleResourceOS.do")
	public @ResponseBody
	RequestResultVO insertRoleResourceOS(Integer roleId, Integer resourceId, Integer organizationStructureId) {

//		String roleId = request.getParameter("roleId");
//		String resourcesId = request.getParameter("resourcesId");
//		String orgStrId = request.getParameter("orgStrId");

		RequestResultVO j = new RequestResultVO();
		if (roleResourceService.insert(roleId, resourceId,organizationStructureId)) {
			j.setCode(0);
			j.setMessage("ok!");
		} else {
			j.setCode(1000);
			j.setMessage("erro!");
		}
		return j;
	}
/**
 * 
 * deleteOS:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @param request
 * @return 
 * @since JDK 1.7
 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteRoleResourceOS.do")
	public @ResponseBody
	RequestResultVO deleteRoleResourceOS(Integer roleId, Integer resourceId,Integer organizationStructureId) {

//		String roleId = request.getParameter("roleId");
//		String resourcesId = request.getParameter("resourcesId");
//		String orgStrId = request.getParameter("orgStrId");

		RequestResultVO j = new RequestResultVO();
		if (roleResourceService.delete(roleId, resourceId,organizationStructureId)) {
			j.setCode(0);
			j.setMessage("ok!");
		} else {
			j.setCode(1000);
			j.setMessage("erro!");
		}
		return j;
	}



}
