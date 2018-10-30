/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys
 * 类/接口名	: SysLogService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:46:13
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:46:13
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.sys.SysLoginLog;

public interface SysLoginLogService {
	
	public List<SysLoginLog> getLogs(String keys);
	
	public Map<String, Object>getLogsByPage(String keys,Integer pageSize,Integer pageIndex);
	
	void insert(SysLoginLog loginLog);
	

}

	

