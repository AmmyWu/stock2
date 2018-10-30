/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys
 * 类/接口名	: SysRolesService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:46:13
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:46:13
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.sys.SysRole;

public interface SysRoleService {

	public List<SysRole> getRoles(String text) ;
	
	public List<SysRole> getRoles();
	public Map<String,Object> getRoles(String keys,Integer pageSize,Integer pageIndex);

	public boolean insert(SysRole res);

	public boolean delete(Integer rid);
	
	public boolean deleteRoles(String rolesIds);

//	public boolean ddelete(Roles res);
//
//	public boolean updateAuth(SysRoles roles);
//
	public boolean update(SysRole roles);
}
