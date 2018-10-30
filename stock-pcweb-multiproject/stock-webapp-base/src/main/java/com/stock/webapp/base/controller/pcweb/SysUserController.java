/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.controller.pcweb
 * 类/接口名	: SysUsersController
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
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.sys.SysUser;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.pojo.vo.sys.SysUserVO;
import com.stock.service.sys.SysUserService;
import com.stock.service.sys.utils.SysConst;

@Controller
@RequestMapping(value = "/user")
public class SysUserController {

	@Autowired
	private SysUserService userService;

	@Autowired
	private HttpSession session;

	/**
	 * 
	 * getUsers:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param req
	 * @param user
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getUsers.do")
	public @ResponseBody
	Object getUsers(HttpServletRequest req) {

		String keys = req.getParameter("keys");

//		user.setType(userType);

		Integer pageSize = Integer.parseInt(req.getParameter("length"));
		Integer pageIndex = Integer.parseInt(req.getParameter("start"));

		return userService.getUserVO(keys,pageSize, pageIndex);
	}
	
	
	/**
	 * 
	 * getUsers:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param req
	 * @param user
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getUserIdName.do")
	public @ResponseBody
	Object getUsers(String userId) {
		
		return SysConst.USER_MAP;
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

		String usersId = request.getParameter("usersId");

		RequestResultVO j = new RequestResultVO();

		if (usersId == null || "".equals(usersId)) {
			j.setCode(1000);
			j.setMessage("请选择删除的用户！");

		}

		if (userService.delete(usersId)) {
			j.setCode(0);
			j.setMessage("用户删除成功!");
		} else {

			j.setCode(1000);
			j.setMessage("数据访问失败，用户删除失败");
		}

		return j;
	}

	/**
	 * 
	 * save:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save.do")
	public @ResponseBody
	RequestResultVO save(HttpServletRequest request) {

		List<SysUserVO> userList = (List<SysUserVO>) JSONArray.toCollection(
				JSONArray.fromObject(request.getParameter("data")),
				SysUserVO.class);

		RequestResultVO j = new RequestResultVO();

		SysUser user = new SysUser();

		BeanUtils.copyProperties(userList.get(0), user);

		if (StringUtils.isEmpty(user.getUsername())) {

			j.setCode(1000);
			j.setMessage("用户名不能为空，保存失败!");
			return j;
		}

		// do insert
		if (user.getUserId() == null) {
			if (userService.insert(user)) {
				j.setCode(0);
				j.setMessage("新增用户保存成功!");
			} else {
				j.setCode(1000);
				j.setMessage("新增用户保存失败!");
			}

		} else { // do update
			if (userService.update(user)) {
				j.setCode(0);
				j.setMessage("修改用户保存成功!");
			} else {
				j.setCode(1000);
				j.setMessage("修改用户保存失败!");
			}
		}

		return j;
	}

	/**
	 * 
	 * save:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert.do")
	public @ResponseBody
	RequestResultVO insert(SysUser user) {

		// SysUser user = new SysUser();
		//
		// BeanUtils.copyProperties(userVO, user);

		userService.insert(user);

		RequestResultVO rrVO = new RequestResultVO();

		rrVO.setCode(0);
		rrVO.setMessage("保存成功!");
		return rrVO;

	}

	/**
	 * 
	 * save:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update.do")
	public @ResponseBody
	RequestResultVO update(SysUser user) {

		// SysUser user = new SysUser();
		//
		// BeanUtils.copyProperties(userVO, user);

		userService.update(user);

		RequestResultVO rrVO = new RequestResultVO();

		rrVO.setCode(0);
		rrVO.setMessage("保存成功!");
		return rrVO;

	}
	
	
	

	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	        dateFormat.setLenient(false);    
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	}  
	
	

}
