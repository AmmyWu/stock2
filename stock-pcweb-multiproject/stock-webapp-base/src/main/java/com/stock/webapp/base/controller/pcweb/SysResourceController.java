/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.controller.pcweb
 * 类/接口名	: SysResourcesController
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午2:44:34
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午2:44:34
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.controller.pcweb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.sys.SysResource;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.sys.SysResourceService;
import com.stock.service.sys.utils.SysConst;

/**
 * 
 * ClassName	: SysResourcesController
 * Function		: TODO
 * date 		: 2017年5月7日下午4:33:48
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
@Controller
@RequestMapping(value = "/resource")
public class SysResourceController {
	
	@Autowired
	private SysResourceService resourceService;	
	
	
	
	
	
	/**
	 * 
	 * getResources:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/listResource.do")
	public @ResponseBody Object  listResource(){ 
		//List<SysResources> list= resourceService.getTreegrid(id);
		
		List<String> typeList = new ArrayList();
		
		typeList.add("用户");
		typeList.add("数据");
		typeList.add("业务");
		
		
		return JSONArray.fromObject(resourceService.listResource(typeList));
	}
	
	
	/**
	 * 
	 * getResources:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getResources.do")
	public @ResponseBody List<SysResource> getResources(){ 
		//List<SysResources> list= resourceService.getTreegrid(id);
		return SysConst.RESOURCESTREE_UNINCLUDE_BUSINESS.getListResources();
	}
/**
 * 
 * insert:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @param request
 * @return 
 * @since JDK 1.7
 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/insert.do")
//	public @ResponseBody RequestResultVO insert(
//			HttpServletRequest request){ 
//		String resourceInfo = request.getParameter("resourceInfo");
//		
//		JSONArray jsonArray=JSONArray.fromObject("["+resourceInfo+"]");
//		List<SysResource> res = (List<SysResource>)JSONArray.toCollection(jsonArray, SysResource.class);
//		
//		RequestResultVO j = new RequestResultVO();
//		if(resourceService.insert(res.get(0))){
//			j.setCode(0);
//			j.setMessage("资源保存成功!");
//		}
//		else{
//			j.setCode(1000);
//			j.setMessage("资源保存成功!");
//		}		
//		return j;
//	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert.do")
	public @ResponseBody RequestResultVO insert(SysResource resource){ 
//		String resourceInfo = request.getParameter("resourceInfo");
//		
//		JSONArray jsonArray=JSONArray.fromObject("["+resourceInfo+"]");
//		List<SysResource> res = (List<SysResource>)JSONArray.toCollection(jsonArray, SysResource.class);
		
		RequestResultVO j = new RequestResultVO();
		
		resource.setCreator(resource.getAmender());
		resource.setCreateTime(resource.getAmendTime());
		
		if(resourceService.insert(resource)){
			j.setCode(0);
			j.setMessage("资源保存成功!");
		}
		else{
			j.setCode(1000);
			j.setMessage("资源保存成功!");
		}		
		return j;
	}
	
	/**
	 * 
	 * del:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param res
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/delete.do")
	public @ResponseBody RequestResultVO delete(SysResource res){ 
		
		RequestResultVO j = new RequestResultVO();
		
		if(resourceService.delete(res))
		{
			
			j.setCode(0);
			j.setMessage("资源删除成功！");
		}
		else
		{
			j.setCode(1000);
			j.setMessage("删除失败，可能存在以下原因：1，该资源已分配给角色,2，该资源为父节点,3,数据访问失败！");
		}
		return j;
	}
	
	
	/**
	 * 
	 * edit:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param request
	 * @return 
	 * @since JDK 1.7
	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/update.do")
//	public @ResponseBody RequestResultVO update(
//			HttpServletRequest request){ 
//		String resourceInfo = request.getParameter("resourceInfo");
//		
//		JSONArray jsonArray=JSONArray.fromObject("["+resourceInfo+"]");
//		List<SysResource> res = (List<SysResource>)JSONArray.toCollection(jsonArray, SysResource.class);
//		
//		RequestResultVO j = new RequestResultVO();
//		resourceService.update(res.get(0));
//		j.setCode(0);
//		j.setMessage("ok!");
//		return j;
//	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update.do")
	public @ResponseBody RequestResultVO update(SysResource resource){ 
//		String resourceInfo = request.getParameter("resourceInfo");
//		
//		JSONArray jsonArray=JSONArray.fromObject("["+resourceInfo+"]");
//		List<SysResource> res = (List<SysResource>)JSONArray.toCollection(jsonArray, SysResource.class);
//		
		RequestResultVO j = new RequestResultVO();
		
		
		
		resourceService.update(resource);
		j.setCode(0);
		j.setMessage("ok!");
		return j;
	}
	
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	        dateFormat.setLenient(false);    
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	}  
	
	
	
	
}
