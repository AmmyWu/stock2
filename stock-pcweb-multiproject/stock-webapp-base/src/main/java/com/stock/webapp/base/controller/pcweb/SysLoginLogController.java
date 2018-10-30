/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.controller.pcweb
 * 类/接口名	: SysLogController
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午2:44:34
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午2:44:34
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.controller.pcweb;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.sys.SysLoginLog;
import com.stock.dao.model.sys.SysUser;
import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.sys.SysLoginLogService;

/**
 * 
 * ClassName	: SysLogController
 * Function		: TODO
 * date 		: 2017年5月7日下午4:02:14
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
@Controller
@RequestMapping(value = "/loginlog")
public class SysLoginLogController {

	@Autowired
	private SysLoginLogService loginLogService;
	
	
	
	@Autowired
	HttpSession session;
/**
 * 
 * list:(这里用一句话描述这个方法的作用). <br/> 
 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
 * 
 * @author chengxl 
 * @param req
 * @return 
 * @since JDK 1.7
 */
	@RequestMapping(value = "/list.do")
	public @ResponseBody
	Object list(HttpServletRequest req) {

		String keys = req.getParameter("keys");

//		Integer pageSize = Integer.parseInt(req.getParameter("rows"));
//		Integer pageIndex = Integer.parseInt(req.getParameter("page"));
		
		Integer pageSize = Integer.parseInt(req.getParameter("length"));
		Integer pageIndex = Integer.parseInt(req.getParameter("start"));
		
		
		
		return  loginLogService.getLogsByPage(keys, pageSize,pageIndex); //JSONObject.fromObject(
		
	}
	/**
	 * 
	 * getLogs:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param keys
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getLogs.do")
	public @ResponseBody
	Object getLogs(String keys) {
		// List<Resources> list= resourcesService.getTreegrid(id);

		// 注册时间转换器，处理传入的JSON数据中的日期格式

		// 处理json数据中的日期格式
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:ss:mm"));

		return JSONArray.fromObject(loginLogService.getLogs(keys),config);
	}
	
	/**
	 * 
	 * list:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @return 
	 * @since JDK 1.7
	 */
		@RequestMapping(value = "/logout.do")
		public String logout(HttpServletRequest req) {


			SysUser loginingUser = (SysUser)session.getAttribute("loginingUser");
			
			
			SysLoginLog loginLog =  new SysLoginLog();
			loginLog.setAuthenticationAccount(session.getAttribute("loginingAccount") == null?"":(String)session.getAttribute("loginingAccount"));
			loginLog.setType("退出");
			loginLog.setLoginIp(req.getRemoteHost());
			loginLog.setLoginTime(new Date());
			loginLog.setCreator(loginingUser.getUserId());
			loginLog.setCreateTime(new Date());
			loginLog.setUserId(loginingUser.getUserId());
			loginLog.setAmendTime(new Date());
			loginLog.setUsername(loginingUser.getUsername());
			loginLog.setAccessGroup(loginingUser.getAccessGroup());
			
			loginLogService.insert(loginLog);
			
			session.invalidate();
			
			return "";
			
		}

}
