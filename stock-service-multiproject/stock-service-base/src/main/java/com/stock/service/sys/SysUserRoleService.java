/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys
 * 类/接口名	: SysUsersRolesService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:46:13
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:46:13
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.sys.SysUserRole;
import com.stock.pojo.vo.sys.SysRoleVO;

public interface SysUserRoleService {
	
	
	Map<String,Object> getUserRoleVOMap(Integer userId);
	
	 List<SysUserRole> getUserRoleList(Integer userId);
	
	 List<SysRoleVO> getUserRoleVOList(Integer userId);
	
	 boolean insert(String userId,String roleId);
	
	 boolean delete(String userId,String roleId);
	
	/**
	 * 返回指定userId已分配的角色名称
	 * @param userId
	 * @return
	 */
	public String getRoleNames(Integer userId);

	public boolean isExistRoleUser(Integer userId,Integer roleId);
	
	public boolean isExistUsers(Integer roleId);

	public boolean isExistUsers(List<Integer> rolesIds);

	public boolean insert(SysUserRole usersRoles);

	public boolean delete(String ids);

	public boolean updata(SysUserRole usersRoles);
}
