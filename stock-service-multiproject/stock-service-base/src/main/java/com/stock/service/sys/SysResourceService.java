/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys
 * 类/接口名	: SysResourcesService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:46:13
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:46:13
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys;

import java.util.List;

import com.stock.dao.model.sys.SysResource;
import com.stock.pojo.vo.sys.SysResourceVO;

public interface SysResourceService {

	
	public  List<SysResourceVO> listResource(List<String> types);
	
	public boolean getChildResource(String pid);
	
	public void init_RESOURCESTREE_UNINCLUDE_BUSINESS();
	
	public List<SysResource> getResource(List<String> types);

	public boolean insert(SysResource res);

	public boolean delete(SysResource res);
	
	public boolean delete(String id);

	public boolean update(SysResource res);

	public void init_RESOURCESTREE();
}
