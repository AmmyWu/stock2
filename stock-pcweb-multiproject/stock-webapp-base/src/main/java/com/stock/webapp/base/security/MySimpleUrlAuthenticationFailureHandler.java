/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.sysadmin.security
 * 类/接口名	: MySimpleUrlAuthenticationFailureHandler
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午4:57:31
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午4:57:31
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.security;

/**
 * 
 * @author M
 *
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


public class MySimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	 
	public MySimpleUrlAuthenticationFailureHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param defaultFailureUrl
	 */
	public MySimpleUrlAuthenticationFailureHandler(String defaultFailureUrl) {
		super(defaultFailureUrl);
		
		// TODO Auto-generated constructor stub
	}

/*	@Autowired
	private OlogService ologService;
	@Autowired
	private LogAspect logAspect;*/

	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request, 
			HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
		/* MDC.put("uid", ologService.getUid());
	     MDC.put("rid", ologService.getRid());
	     MDC.put("type", "登录");
	     MDC.put("tablename","operate_log" );
	     logger.info(ologService.getUsername()+"   尝试登入�?); */
//		System.out.println(exception.toString());
//		//super.onAuthenticationFailure(request, response, exception);

		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
//		out.print();
		
		JSONObject jo = new JSONObject();
		
		jo.put("status",false);
		jo.put("msg", exception.getMessage());
		
		out.print(jo);
		out.flush();
		out.close();
//		request.setCharacterEncoding("gb2312");
//		response.setCharacterEncoding("gb2312")
		
//		String message = URLEncoder.encode(exception.getMessage(),"utf-8");;
//		response.sendRedirect("login.html?errorMsg=error");
      
    }
	
}
