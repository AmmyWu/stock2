/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.sysadmin.security
 * 类/接口名	: MyAuthenticationListener
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午4:57:31
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午4:57:31
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.security;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


 /**
  * session监听器
  * @author <a href="mailto:chengxl@stocksoft.com">chengxl</a>
  * @version $Id$   
  * @since 2.0
  */
  
public class MyAuthenticationListener implements HttpSessionListener {
	
	private static Map<String,Integer> currentLoginUserIds= new HashMap<String,Integer>();

	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
		currentLoginUserIds.put(se.getSession().getId(), 0);

	}

	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		currentLoginUserIds.remove(se.getSession().getId());
	}
	
	public static void  addUserId(String sessionId,Integer userId){
		
		currentLoginUserIds.put(sessionId, userId);
	}
	
	public static Integer getUserId(String sessionId){
		
		
		return currentLoginUserIds.get(sessionId);
	}
	
	public static void removeUserId(String sessionId){
		
		currentLoginUserIds.remove(sessionId);
		
	}

}
