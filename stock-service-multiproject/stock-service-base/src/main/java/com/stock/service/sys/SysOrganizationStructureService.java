/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys
 * 类/接口名	: SysOrganizationStructureService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:46:13
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:46:13
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.sys.SysOrganizationStructure;

public interface SysOrganizationStructureService {
	
	public boolean getChildOrganizationStructures(String pid);

	public void init_ORGANIZATIONSTRUCTURE_TREE();
	
	public List<SysOrganizationStructure> getOrganizationStructure();

	public boolean insert(SysOrganizationStructure os);

	public boolean delete(SysOrganizationStructure os);


	public boolean update(SysOrganizationStructure os);
	
	
	public Map<String,Object> getOrganizationByPage(SysOrganizationStructure os, Integer pageSize,Integer pageNow);
	
//	public String deleteOsById(String  ids[]);
	
	public String delete(Integer  id);

	public boolean updateDefault(SysOrganizationStructure organizationStructure);
}
