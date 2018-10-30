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

import com.stock.dao.model.sys.SysCustomer;
import com.stock.pojo.vo.basedata.SysCustomerVO;

public interface SysCustomerService {

	
	
	public Map<String,Object> getCustomerVO(String keys,Integer pageSize,Integer pageIndex);
	
	
	public List<SysCustomer> getCustomers();
	
	
	
	public boolean insert(SysCustomer customer);
	
	public boolean delete(String ids);
	
	public boolean update(SysCustomer customer);
	
	void init_CUSTOMERMAP();


	public SysCustomer getCustomerById(Integer userDetailId);


	public boolean insert(SysCustomerVO user);



}
