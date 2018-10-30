/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.controller.pcweb
 * 类/接口名	: SysSettingController
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午2:44:34
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午2:44:34
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.controller.pcweb;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.sys.SysSetting;
import com.stock.service.sys.SysSettingService;


@Controller
@RequestMapping(value = "/systemsetting") 
public class SysSettingController {
	
	@Autowired
	private SysSettingService systemSettingService;
	
	/**
	 * 
	 * getSystemSetting:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getSystemSetting.do")
	public @ResponseBody List<SysSetting> getSystemSetting(){ 

		return systemSettingService.getSystemSetting();
	}
	
	
	/**
	 * 
	 * insert:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/save.do")
	public @ResponseBody Object insert(HttpServletRequest req ) {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return systemSettingService.insert( req.getParameter("jsonSt"));
	}


}
