/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.controller.pcweb
 * 类/接口名	: SysOrganizationStructureController
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午2:44:34
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午2:44:34
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.controller.pcweb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.sys.SysOrganizationStructure;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.sys.SysOrganizationStructureService;
import com.stock.service.sys.utils.SysConst;
import com.stock.service.sys.utils.SysOrganizationStructureTreeNode;

@Controller
@RequestMapping(value = "/organization")
public class SysOrganizationStructureController {

	@Autowired
	private SysOrganizationStructureService organizationStructureService;
/**
 * 
 * listOSTreeNodes:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @return 
 * @since JDK 1.7
 */
	@RequestMapping(value = "/listOSTree.do")
	public @ResponseBody List<SysOrganizationStructureTreeNode> listOSTreeNodes() {

		// List<OrganizationStructure> list=
		// organizationStructureService.getTreegrid(id);
		System.out.println(10);
		return SysConst.ORGANIZATIONSTRUCTURE_TREE.getOSTreeNodes();
	}
/**
 * 
 * listAll:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @return 
 * @since JDK 1.7
 */
	@RequestMapping(value = "/listAll.do")
	public @ResponseBody List<SysOrganizationStructure> listAll() {

		// List<OrganizationStructure> list=
		// organizationStructureService.getTreegrid(id);
		return SysConst.ORGANIZATIONSTRUCTURE_TREE.getListOrganizationStructure();
	}
/**
 * 
 * getOrganizationStructure_deprecated:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @param req
 * @param os
 * @return 
 * @since JDK 1.7
 */
	@RequestMapping(value = "/list.do")
	public @ResponseBody Object getOrganizationStructure(HttpServletRequest req) {

		String keys = req.getParameter("keys");
		Integer pageSize = Integer.parseInt(req.getParameter("rows"));
		Integer pageNow = Integer.parseInt(req.getParameter("page"));
		
//		Map<String, Object> map = organizationStructureService.getOrganizationByPage(os, pageSize, pageNow);
		return null;// JSONObject.fromObject(map);
	}
//	@RequestMapping(value = "/classesListByOsId.do")
//	public @ResponseBody JSONArray classesListByOsId(HttpServletRequest req) {
//
//		Integer osId = Integer.parseInt(req.getParameter("osId"));
//		JSONArray jarray = organizationStructureService.getClassesListByOsId(osId);
//		return jarray;
//	}
//		@RequestMapping(value = "/getAllClasses.do")
//		public @ResponseBody JSONArray getAllClasses(HttpServletRequest req) {
//			JSONArray jarray = organizationStructureService.getAllClasses();
//			return jarray;
//		}
//		@RequestMapping(value = "/updateDeafult.do")
//		public @ResponseBody String updateDeafult(HttpServletRequest req) {
//			// String deleted = req.getParameter("deleted");
//			String updated = req.getParameter("updated");
//			if (updated != null) {
//				JSONArray jsonArray = JSONArray.fromObject(updated);
//				List<SysOrganizationStructure> listUpdated = (List<SysOrganizationStructure>) JSONArray
//						.toCollection(jsonArray, SysOrganizationStructure.class);
//				for (SysOrganizationStructure organizationStructure : listUpdated) {
//					organizationStructureService.updateDefault(organizationStructure);
//				}
//			}
//			return "设置成功";
//		}
//		
		
		
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert.do")
	public @ResponseBody RequestResultVO insert(SysOrganizationStructure os) {
//		String osInfo = request.getParameter("osInfo");
//
//		JSONArray jsonArray = JSONArray.fromObject("[" + osInfo + "]");
//		List<SysOrganizationStructure> res = (List<SysOrganizationStructure>) JSONArray.toCollection(jsonArray,
//				SysOrganizationStructure.class);

		RequestResultVO j = new RequestResultVO();
		if (organizationStructureService.insert(os)) {
			j.setCode(0);
			j.setMessage("组织保存成功!");
		} else {
			j.setCode(1000);
			j.setMessage("组织保存失败!");
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update.do")
	public @ResponseBody RequestResultVO update(SysOrganizationStructure os) {

//		String osInfo = request.getParameter("osInfo");
//
//		JSONArray jsonArray = JSONArray.fromObject("[" + osInfo + "]");
//		List<SysOrganizationStructure> res = (List<SysOrganizationStructure>) JSONArray.toCollection(jsonArray,
//				SysOrganizationStructure.class);

		RequestResultVO j = new RequestResultVO();
		

		
		if (organizationStructureService.update(os)) {
			j.setCode(0);
			j.setMessage("组织修改成功!");
		} else {
			j.setCode(1000);
			j.setMessage("组织修改失败!");
		}
		return j;
	}
/**
 * 
 * add_deprecated:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @param req
 * @return 
 * @since JDK 1.7
 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save.do")
	public @ResponseBody String add_deprecated(HttpServletRequest req) {
		// String deleted = req.getParameter("deleted");
		String inserted = req.getParameter("inserted");
		String updated = req.getParameter("updated");

		// JSONUtils.getMorpherRegistry().registerMorpher(
		// new DateMorpherEx(new String[] { "yyyy-MM-dd" },(Date)null));
		if (inserted != null) {

			JSONArray jsonArray = JSONArray.fromObject(inserted);
			List<SysOrganizationStructure> listInserted = (List<SysOrganizationStructure>) JSONArray
					.toCollection(jsonArray, SysOrganizationStructure.class);
			for (SysOrganizationStructure organizationStructure : listInserted) {
				organizationStructure.setCreateTime(new Date());
				organizationStructureService.insert(organizationStructure);
			}
		}

		if (updated != null) {

			JSONArray jsonArray = JSONArray.fromObject(updated);
			List<SysOrganizationStructure> listUpdated = (List<SysOrganizationStructure>) JSONArray
					.toCollection(jsonArray, SysOrganizationStructure.class);
			for (SysOrganizationStructure organizationStructure : listUpdated) {
				organizationStructure.setCreateTime(new Date());
				organizationStructureService.update(organizationStructure);
			}
		}
		return "success";
	}
/**
 * 
 * del:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @param id
 * @return 
 * @since JDK 1.7
 */
	@RequestMapping(value = "/delete_derp.do")
	public @ResponseBody String del(String id) {

		if (id == null || "".equals(id))
			return "no found";

		return organizationStructureService.delete(Integer.parseInt(id));
	}
	
	
	/**
	 * 
	 * del:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param id
	 * @return 
	 * @since JDK 1.7
	 */
		@RequestMapping(value = "/delete.do")
		public @ResponseBody Object delete(int organizationStructureId) {

			/*if (organizationStructureIds == null || "".equals(organizationStructureIds))
				return "no found";*/
			RequestResultVO j = new RequestResultVO();
	        String message = organizationStructureService.delete(organizationStructureId);
	        if (message.equals("success")) {
	            j.setCode(0);
	            j.setMessage("组织删除成功!");
	        } else {
	            j.setCode(1000);
	            j.setMessage(message);
	        }
			return j;
		}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

}
