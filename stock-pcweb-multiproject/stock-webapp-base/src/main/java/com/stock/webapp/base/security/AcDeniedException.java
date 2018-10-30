/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.sysadmin.security
 * 类/接口名	: AcDeniedException
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午4:57:31
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午4:57:31
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.security;


	@SuppressWarnings("serial")
	public class AcDeniedException extends RuntimeException
	{
		
	  public AcDeniedException(String msg)
	  {
	    super(msg);
	  }

	  public AcDeniedException(String msg, Throwable t)
	  {
	    super(msg, t);
	  }
	}


