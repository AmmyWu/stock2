/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.sysadmin.security
 * 类/接口名	: MyAuthorizationFilter
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午4:57:31
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午4:57:31
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.security;


import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.stock.dao.model.sys.SysUser;
import com.stock.dao.util.DataAuthorityRegister;
import com.stock.service.sys.utils.SysConst;


 /**
  * 授权过滤器，用户认证通过后，访问受保护资源时，通过该过滤器获得资源所需要的权限（角色）
  * @author <a href="mailto:chengxl@stocksoft.com">chengxl</a>
  * @version $Id$   
  * @since 2.0
  */
  
public class MyAuthorizationFilter extends AbstractSecurityInterceptor implements Filter {

	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	
	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	@Override
	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
        
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		 //set MDC for log
	    logInfo(httpRequest);
	    		
		InterceptorStatusToken token = super.beforeInvocation(fi);
		
		//创建数据权限钥匙
		//获取当前数据授权部门列表
        String url = fi.getRequestUrl();// httpRequest.getRequestURI();
        List<Integer> roleL=(List<Integer>)httpRequest.getSession().getAttribute("roleList");
        
        if(roleL==null){
        	//没有权限   
    		throw new AccessDeniedException("重登陆！");
        }     
        
        Set<Integer> orgSet=SysConst.ROLE_RESOURCE.getOrgSetUnderResourceRoles(SysConst.RESOURCESTREE.resourceMatching(url),roleL);
        orgSet=SysConst.ORGANIZATIONSTRUCTURE_TREE.getAllChildren(orgSet);
 //     Set<Integer> employeeSet= Const.EMPLOYEE_ORG.getEmployeeSet(orgSet);
        //获取当前线程
        Thread currentTh=Thread.currentThread();
        //
        DataAuthorityRegister dataAuthorityRegister=(DataAuthorityRegister)httpRequest.getSession().getAttribute("dataAuthorityRegister");
        
//        SysEmployee currentEmployee = (SysEmployee) httpRequest.getSession().getAttribute("employee"); || currentEmployee == null
        
        if(dataAuthorityRegister == null ){
        	//没有权限   
    		throw new AccessDeniedException("重登陆！");
        }
        
//       dataAuthorityRegister.addNewAuthority(currentTh.getId(), orgSet,orgSet==null?false:true,currentEmployee.getEmployeeId());
        
        
       dataAuthorityRegister.addNewAuthority(currentTh.getId(), orgSet,orgSet==null?false:true);
        
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
			//销毁数据权限钥匙
			dataAuthorityRegister.delAuthority(currentTh.getId());
			
			//System.out.println("end:"+currentTh.getId());
	        //dataAuthorityRegister.show();
		} finally {
			//销毁数据权限钥匙
			dataAuthorityRegister.delAuthority(currentTh.getId());
			
			super.afterInvocation(token, null);
		}
	}
	
	public void init(FilterConfig arg0) throws ServletException {
	}

	public void destroy() {
		
	}

	private void logInfo(HttpServletRequest httpRequest){
		SysUser user = (SysUser)httpRequest.getSession().getAttribute("loginingUser");
		if(user!=null){
			MDC.put("userId", user.getUserId());
		}else{
			MDC.put("userId", new Integer(0));
		}
		MDC.put("IP", getRemortIP(httpRequest));
	}
	//获得经过代理的IP
	public String getRemortIP(HttpServletRequest request) {  
		if (request.getHeader("x-forwarded-for") == null) {  
			return request.getRemoteAddr();  
		}  
		return request.getHeader("x-forwarded-for");  
	} 

}
