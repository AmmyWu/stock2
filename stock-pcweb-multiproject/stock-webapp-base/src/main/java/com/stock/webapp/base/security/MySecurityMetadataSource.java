/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.sysadmin.security
 * 类/接口名	: MySecurityMetadataSource
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午4:57:31
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午4:57:31
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.security;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.stock.service.sys.utils.SysConst;
import com.stock.service.sys.utils.SysResourceTreeNode;


 /**
  * 根据所访问的受控资源url，返回该资源所需要的角色
  * @author <a href="mailto:chengxl@stocksoft.com">chengxl</a>
  * @version $Id$   
  * @since 2.0
  */
  
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	

	public synchronized Collection<ConfigAttribute> getAttributes(Object object)	throws IllegalArgumentException {
		
		String requestUrl= ((FilterInvocation) object).getRequestUrl();
		
		if(requestUrl.contains("?"))
			requestUrl=requestUrl.substring(0, requestUrl.indexOf("?"));
		
		List<Integer> resourceId=SysConst.RESOURCESTREE.resourceMatching(requestUrl);
		
		Collection<ConfigAttribute> configAttributes = getConfigAttributes(resourceId);
		
		return configAttributes;
	}
	
	private Collection<ConfigAttribute>  getConfigAttributes(List<Integer> resourceIdList){
		
		Collection<ConfigAttribute> configAttributes =new ArrayList<ConfigAttribute>();
		
		if(resourceIdList.size()==0)
			configAttributes.add(new SecurityConfig("-1"));///开放权限
		
		else{
			for(Integer resourceId: resourceIdList){
				Map<Integer, SysResourceTreeNode> resTree=SysConst.RESOURCESTREE.getTree();
				
				Integer currentId=resourceId;
				
				do{
					Set<Integer> roles=SysConst.ROLE_RESOURCE.getRoles(currentId);
					if(roles!=null){
						for(Integer role:roles){
							configAttributes.add(new SecurityConfig(role.toString()));
						}
					}
					
					currentId=resTree.get(currentId).getParentId();
					
				}while(!currentId.equals(new Integer(0)));
			}
			configAttributes.add(new SecurityConfig("-999888"));//超级管理员
			
		}
		return configAttributes;
	}
	

	
	
	

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}
//	判断是否使用当前的授权判断
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
