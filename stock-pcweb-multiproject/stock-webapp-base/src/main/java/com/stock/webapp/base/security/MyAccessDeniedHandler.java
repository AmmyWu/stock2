/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.sysadmin.security
 * 类/接口名	: MyAccessDeniedHandler
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午4:57:31
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午4:57:31
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

public class MyAccessDeniedHandler implements AccessDeniedHandler {

	private String errorPage;
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)  
			throws IOException, ServletException {  
		boolean isAjax = request.getHeader("X-Requested-With")==null?false:true;  
		if(isAjax){  

		}else if (!response.isCommitted()) {  
			if (errorPage != null) {  
				// Put exception into request scope (perhaps of use to a view)  
				request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);  

				// Set the 403 status code.  
				response.setStatus(HttpServletResponse.SC_FORBIDDEN); 

				String url =request.getRequestURI();
				// forward to error page.  
				RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);  
				dispatcher.forward(request,response);
			} else {  
				response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());  
			}  
		} 
	}  

	/** 
	 * The error page to use. Must begin with a "/" and is interpreted relative to the current context root. 
	 * 
	 * @param errorPage the dispatcher path to display 
	 * 
	 * @throws IllegalArgumentException if the argument doesn't comply with the above limitations 
	 */  
	public void setErrorPage(String errorPage) {  
		if ((errorPage != null) && !errorPage.startsWith("/")) {  
			throw new IllegalArgumentException("errorPage must begin with '/'");  
		}  

		this.errorPage = errorPage;  
	}  


}
