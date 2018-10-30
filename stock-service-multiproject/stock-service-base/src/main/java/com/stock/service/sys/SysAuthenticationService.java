/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys
 * 类/接口名	: SysAuthenticationInfoService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:46:13
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:46:13
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys;
/**
 * 
 */


import java.util.List;
import java.util.Map;

import com.stock.dao.model.sys.SysAuthentication;

/**
 * @author Apple
 *
 */
public interface SysAuthenticationService {
	
	
	Map<String,Object> getAuthenticationMap(Integer uId); 
	
	public String updatePassword(String account,String password,String newPassword);
	
	public boolean isExistAccount(String account);
	
	public String resetPassword(String account);

	public String lockAccount(String account);
	
	public List<SysAuthentication> getAuthenticationInfo(String account,String password,String type); 

	public List<SysAuthentication> getAuthenticationInfo(String account,String password); 
	
	public SysAuthentication getAuthenticationByAccount(String account); 
	
	public List<SysAuthentication> getAuthenticationInfo(String uId); 
	
	public boolean insert(SysAuthentication authInfo);
	
	public boolean delete(String ids);
	
	public boolean deleteByUserId(Integer uId);
	
	public boolean update(SysAuthentication authInfo);

	boolean updateAccount(String newAccount, String oldAccount);

	public String alterWrongTimes(String account);

	public String checkStatus(String account);


}
