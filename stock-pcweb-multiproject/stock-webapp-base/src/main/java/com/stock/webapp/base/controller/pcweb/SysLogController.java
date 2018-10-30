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

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.sys.SysLogService;

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
@RequestMapping(value = "/log")
public class SysLogController {

	@Autowired
	private SysLogService logService;
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
		
		
		
		return  logService.getLogsByPage(keys, pageSize,pageIndex); //JSONObject.fromObject(
		
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

		return JSONArray.fromObject(logService.getLogs(keys),config);
	}

}
