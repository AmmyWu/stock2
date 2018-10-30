/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.sysadmin.security
 * 类/接口名	: MyUserDetailService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午4:57:31
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午4:57:31
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.security;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.stock.dao.model.sys.SysAuthentication;
import com.stock.dao.model.sys.SysUserRole;
import com.stock.service.sys.SysAuthenticationService;
import com.stock.service.sys.SysUserRoleService;
import com.stock.service.sys.SysUserService;


 /**
  * 在这个类中，你就可以从数据库中读入用户的密码，角色信息，是否锁定，账号是否过期等
  * 待办当前登陆的用户对象，存储用户所用户的权限（角色）信息
  * @author <a href="mailto:chengxl@stocksoft.com">chengxl</a>
  * @version $Id$   
  * @since 2.0
  */
  
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private SysUserService usersService;

	@Autowired
	private SysUserRoleService usersRolesService;
	
	@Autowired
	private SysAuthenticationService authenticationInfoService;
	
	

//  获取用户角色信息
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

//		Users user = usersMapper.selectByPrimaryKey(Integer.parseInt(username));
		SysAuthentication authentication =  authenticationInfoService.getAuthenticationByAccount(username);
		if(authentication == null) {
			throw new UsernameNotFoundException(username);
		}
		
		
//		SysUser user = usersService.getUsers(Integer.parseInt(username));

		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(authentication.getUserId());

//		User userdetail = new User(users.get(0).getUsername(), users.get(0).getPassword(), true, true, true, true, grantedAuths);
		User userdetail = new User(username, authentication.getPassword(), true, true, true, true, grantedAuths);
		
		return userdetail;
	}


	private Set<GrantedAuthority> obtionGrantedAuthorities(Integer userId) {
		
		Set<GrantedAuthority> authSet=new HashSet<GrantedAuthority>();
		
		List <SysUserRole> listUsersRoles = usersRolesService.getUserRoleList(userId);//usersRolesMapper.selectByExample(example);
		for(SysUserRole userRole:listUsersRoles){
			authSet.add(new SimpleGrantedAuthority(userRole.getRoleId().toString()));
		}
		authSet.add(new SimpleGrantedAuthority("-1"));
		return authSet;	
        }
}
