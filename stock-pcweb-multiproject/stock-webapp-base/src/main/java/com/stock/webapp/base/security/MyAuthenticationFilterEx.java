/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.sysadmin.security
 * 类/接口名	: MyAuthenticationFilter
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午4:57:31
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午4:57:31
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证过滤器
 * 1、前端提交用户名密码后，会调用该过滤器attemptAuthentication方法
 * 2、attemptAuthentication负责创建一个Authenticaion的子类UsernamePasswordAuthenticationToken对象，该对象封装输入的用户名密码
 * 3、attemptAuthentication该方法调用super.getAuthenticationManager()方法，返回一个AuthenticationManager对象
 * 4、AuthenticationManager对象调用authenticate()进行身份认证
 * 5、authenticate方法，会根据spring security的配置文件，找到一个provider manager的MyUserDetailService对象，表调用该对象的loadUserByUsername方法
 * 6、loadUserByUsername方法会根据UsernamePasswordAuthenticationToken对象的username获取一个账号信息，并根据账号信息获取对应的用户及其角色，封装到user（Spring security提供）对象
 * 7、authenticate方法，根据UsernamePasswordAuthenticationToken对象和user对象进行身份认证，认证通过后，会返回一个经过认证的Authenticaion对象，该对象封装了用户的授权信息（角色信息）
 * 8。1、认证通过调用MySimpleUrlAuthenticationSuccessHandler的onAuthenticationSuccess方法
 * 8.2、认证失败调用MySimpleUrlAuthenticationFailureHandler的onAuthenticationFailure方法
 *
 * @author <a href="mailto:chengxl@stocksoft.com">chengxl</a>
 * @version $Id$
 * @since 2.0
 */

public class MyAuthenticationFilterEx extends UsernamePasswordAuthenticationFilter {

//	private static final String MERCHANTID = "merchantId";
   private static final String USERNAME = "account";
   private static final String PASSWORD = "password";
   private static final String RANDOMCODE = "randomCode";
   private static final String TYPE = "type";



   //*****************************************************************************************
   /*
    *
    * 认证入口方法
    * 1、获取登陆的用户名密码，创建一个UsernamePasswordAuthenticationToken 对象
    * 2、
    *
    * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   @Override
   public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  throws AuthenticationException {

       /*if (!request.getMethod().equals("POST")) {
           throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
       }*/

       response.setContentType("text/html;charset=utf-8");

       String username = obtainUsername(request);
       String password = obtainPassword(request);
  //     String randomCode = obtainRandomcode(request);

       UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,password);/*MD5Encoder.getMD5Str(password)*/

       setDetails(request, authRequest);

       return this.getAuthenticationManager().authenticate(authRequest);
   }


   @Override
   protected String obtainUsername(HttpServletRequest request) {
       Object obj = request.getAttribute(USERNAME);
       return null == obj ? "" : obj.toString();
   }

   @Override
   protected String obtainPassword(HttpServletRequest request) {
       Object obj = request.getAttribute(PASSWORD);
       return null == obj ? "" : obj.toString();
   }

   protected String obtainRandomcode(HttpServletRequest request) {
       Object obj = request.getParameter(RANDOMCODE);
       return null == obj ? "" : obj.toString();
   }


   protected String obtainType(HttpServletRequest request) {
       Object obj = request.getParameter(TYPE);
       return null == obj ? "" : obj.toString();
   }

   @Override
   protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
       super.setDetails(request, authRequest);
       return;
   }

   //获得经过代理的IP
   public String getRemortIP(HttpServletRequest request) {
       if (request.getHeader("x-forwarded-for") == null) {
           return request.getRemoteAddr();
       }
       return request.getHeader("x-forwarded-for");
   }


}
