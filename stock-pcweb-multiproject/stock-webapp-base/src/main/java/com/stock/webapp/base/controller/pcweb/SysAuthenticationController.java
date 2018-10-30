/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.controller.pcweb
 * 类/接口名	: SysAuthenticationInfoController
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午2:44:34
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午2:44:34
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.controller.pcweb;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.sys.SysAuthentication;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.sys.SysAuthenticationService;

/**
 * 
 * ClassName : SysAuthenticationInfoController Function : TODO date :
 * 2017年5月7日下午3:29:13
 * 
 * @author chengxl
 * @version :
 * @since JDK 1.7
 */
@Controller
@RequestMapping(value = "/authentication")
public class SysAuthenticationController {

	@Autowired
	private SysAuthenticationService authenticationService;

	/**
	 * 
	 * loginOut:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/loginOut.do")
	public @ResponseBody
	String loginOut(HttpServletRequest request) {

		request.getSession().invalidate();
		return "退出成功！";

	}

	/**
	 * 
	 * isExistAccount:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param account
	 * @return
	 * @since JDK 1.7
	 */

	@RequestMapping(value = "/isExistAccount.do")
	public @ResponseBody
	boolean isExistAccount(String account) {

		return authenticationService.isExistAccount(account);
	}

	/**
	 * 
	 * getAuthenticationInfo:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getAuthentication.do")
	public @ResponseBody
	List<SysAuthentication> getAuthenticationInfo(String userId) {

		return authenticationService.getAuthenticationInfo(userId);
	}

	@RequestMapping(value = "/getAuthenticationMap.do")
	public @ResponseBody
	Object getAuthenticationMap(HttpServletRequest request) {

		String uId = request.getParameter("userId");

		if (StringUtils.isEmpty(uId))
			return null;
		Integer userId = Integer.parseInt(uId);

		return authenticationService.getAuthenticationMap(userId);
	}

	/**
	 * 
	 * updatePassword:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param account
	 * @param password
	 * @param newPassword
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/updatePassword.do")
	public @ResponseBody
	String updatePassword(String account, String password, String newPassword) {
		
		String s = authenticationService.updatePassword(account, password,
				newPassword);
		return s;
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

		String id = request.getParameter("authencationId");

		RequestResultVO j = new RequestResultVO();

		if (id == null || "".equals(id)) {
			j.setCode(1000);
			j.setMessage("请选择删除的帐号！");
			return j;

		}

		if (authenticationService.delete(id)) {
			j.setCode(0);
			j.setMessage("帐号删除成功!");
		} else {

			j.setCode(1000);
			j.setMessage("帐号删除失败!");
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
	 * @throws UnsupportedEncodingException
	 * @since JDK 1.7
	 */
	// 角色更新
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save.do")
	public @ResponseBody
	RequestResultVO save(HttpServletRequest request)
			throws UnsupportedEncodingException {

		request.setCharacterEncoding("UTF-8");

		JSONObject jAuthInfo = JSONObject.fromObject(request
				.getParameter("authencationInfo"));
		jAuthInfo.remove("confirmPassword");
		SysAuthentication authInfo = (SysAuthentication) JSONObject.toBean(
				jAuthInfo, SysAuthentication.class);

		authInfo.setStatus("启用");

		RequestResultVO j = new RequestResultVO();

		// do insert
		if (authInfo.getAuthenticationId() == null) {

			if (authenticationService.insert(authInfo)) {
				j.setCode(0);
				j.setMessage("新增保存成功!");
			} else {
				j.setCode(1000);
				j.setMessage("新增保存失败!");
			}

		} else { // do update

			if (authenticationService.update(authInfo)) {
				j.setCode(0);
				j.setMessage("修改成功!");
			} else {
				j.setCode(1000);
				j.setMessage("修改失败!");
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
	 *            HttpServletRequest request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @since JDK 1.7
	 */
	// 角色更新
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert.do")
	public @ResponseBody
	RequestResultVO insert(SysAuthentication authInfo){

	
		authInfo.setStatus("启用");

		RequestResultVO j = new RequestResultVO();

		if (authenticationService.insert(authInfo)) {
			j.setCode(0);
			j.setMessage("新增保存成功!");
		} else {
			j.setCode(1000);
			j.setMessage("新增保存失败!");
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
	 *            HttpServletRequest request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @since JDK 1.7
	 */
	// 角色更新
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update.do")
	public @ResponseBody
	RequestResultVO update(SysAuthentication authInfo) {

		RequestResultVO j = new RequestResultVO();

		if (authenticationService.update(authInfo)) {
			j.setCode(0);
			j.setMessage("修改成功!");
		} else {
			j.setCode(1000);
			j.setMessage("修改失败!");
		}
		return j;

	}
	
	/**
	 * 
	 * lockAccount:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param account
	 * @param password
	 * @param newPassword
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lockAccount.do")
	public @ResponseBody
	String lockAccount(String account) {
		
		String s = authenticationService.lockAccount(account);
		return s;
	}
	/**
	 * 
	 * AlterWrongTimes:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param account
	 * @param password
	 * @param newPassword
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alterWrongTimes.do")
	public @ResponseBody
	String alterWrongTimes(String account) {
		
		String s = authenticationService.alterWrongTimes(account);
		return s;
	}	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkStatus.do")
	public @ResponseBody
	String checkStatus(String account) {
		
		String s = authenticationService.checkStatus(account);
		return s;
	}	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkSession.do")
	public @ResponseBody
	String checkSession() {
		return "success";
	}
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	        dateFormat.setLenient(false);    
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	}  
	

}
