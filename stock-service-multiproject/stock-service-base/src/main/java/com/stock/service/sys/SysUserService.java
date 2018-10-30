/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys
 * 类/接口名	: SysUsersService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:46:13
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:46:13
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.sys.SysUser;

public interface SysUserService {

	
	public boolean deleteByUserDetailId(List<Integer> userDetailIds,List<String> types);
	
	public SysUser getUsers(Integer userId);
	
	public Map<String,Object> getUserVO(String keys,Integer pageSize,Integer pageIndex);
	
	public List<SysUser> getUsers();
	
	public List<SysUser> getUsers(String type);
	
	public boolean isExistUsers(Integer roleId);
	
	public boolean insert(SysUser user);
	
	public boolean delete(String ids);
	
	public boolean update(SysUser user);
	
	void init_USERMAP();

	boolean setUsernameByEmployeeId(String username, Integer employeeId);

	List<Integer> getUserDetailIdList(String type);

}
