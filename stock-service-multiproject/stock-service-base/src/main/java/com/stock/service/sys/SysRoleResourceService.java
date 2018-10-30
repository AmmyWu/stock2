/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys
 * 类/接口名	: SysRoleResourceService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:46:13
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:46:13
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys;

import java.util.List;

import com.stock.service.sys.utils.SysOrganizationStructureTreeNode;
import com.stock.service.sys.utils.SysResourceTreeNode;

import net.sf.json.JSONArray;

public interface SysRoleResourceService {

	/**
	 * 根据resourceId 返回其所有的叶子节点,并将其保存到role_resources表中
	 * 
	 * @param roleId
	 * @param resourceId
	 * @return
	 */
	public boolean insert(Integer roleId, Integer resourceId);
	
	public boolean insert(Integer roleId, Integer resourceId,Integer orgId);

	public List<SysResourceTreeNode> getRoleResource(String roleId);
	
	public List<SysOrganizationStructureTreeNode> getRoleResourceOS(Integer roleId, Integer resourceId);
	
	public boolean delete(Integer roleId);

	public boolean delete(Integer roleId, Integer resourceId);
	
	public boolean delete(Integer roleId, Integer resourceId,Integer orgId);
	
	/**
	 * 根据一组角色，返回该组角色所赋予的资源id，以 逗号分割的“,”资源Id字符串,用于前端设置菜单是否可以点击用
	 * @param 
	 * @return
	 */
	public List<Integer> getResourceIdList(List<Integer> roleIds);
	
	
	/**
	 * 根据指定角色返回以 逗号分割的“,”资源Id字符串,用于前端设置菜单是否可以点击用
	 * @param roleIds 返回形式BTN_1,BTN_2,BTN_3
	 * @return
	 */
	public String getButtonResourceIds(List<Integer> resIdList );
	
	public void init_ROLE_RESOURCE();

//	public JSONArray getRoleResourceOSTest(Integer roleId, Integer resourceId);

	public JSONArray getRoleResourceInitOS();


}
