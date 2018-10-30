/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base
 * 类/接口名	: SessionService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:39:29
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:39:29
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.common;

/**
 * 用于获取登陆后，存储于session里面的变量
 */

import java.util.List;

import com.stock.pojo.vo.sys.SysUserVO;

public interface SessionService {

	
	public void invalidateSession();
	
	//当前session的 userDetailId（可能是：EmployeeId，customerId）
	public Integer getUserDetailId();
	
	//当前session中 用户数据的访问组
	public List<String> getDataAccessGroup();
	
	public SysUserVO getSessionHttpUser();
	

}
