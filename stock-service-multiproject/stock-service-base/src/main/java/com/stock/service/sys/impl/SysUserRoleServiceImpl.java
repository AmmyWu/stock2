/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys.impl
 * 类/接口名	: SysUsersRolesServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:50:19
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:50:19
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.mapper.sys.SysUserRoleMapper;
import com.stock.dao.model.sys.SysRole;
import com.stock.dao.model.sys.SysUserRole;
import com.stock.dao.model.sys.SysUserRoleExample;
import com.stock.dao.model.sys.SysUserRoleExample.Criteria;
import com.stock.pojo.vo.sys.SysRoleVO;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.common.util.CommonUtils;
import com.stock.service.sys.SysRoleService;
import com.stock.service.sys.SysUserRoleService;

/**
 * 
 * ClassName	: SysUsersRolesServiceImpl
 * Function		: TODO
 * date 		: 2017年5月7日下午6:05:27
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
@Service("usersRolesService")
public class SysUserRoleServiceImpl implements SysUserRoleService {

	@Autowired
	private SysUserRoleMapper usersRolesMapper;

	@Autowired
	private SysRoleService rolesService;
	
	@Autowired
	private  DataAuthorizeService dataAuthorizeService;


	public SysUserRoleServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isExistRoleUser(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub

		SysUserRoleExample example = new SysUserRoleExample();

		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andRoleIdEqualTo(roleId);

		List<SysUserRole> list = usersRolesMapper.selectByExample(example);

		if (list == null || list.size() < 1)
			return false;

		return true;
	}

	@Override
	public boolean isExistUsers(List<Integer> rolesIds) {
		// TODO Auto-generated method stub

		SysUserRoleExample example = new SysUserRoleExample();

		example.or().andRoleIdIn(rolesIds);

		List<SysUserRole> list = usersRolesMapper.selectByExample(example);

		if (list == null || list.size() < 1)
			return false;

		return true;
	}

	@Override
	public boolean insert(SysUserRole usersRoles) {

		// TODO Auto-generated method stub
		
		dataAuthorizeService.addDataAuthorizeInfo(usersRoles, "insert");
		return usersRolesMapper.insertSelective(usersRoles) == 1 ? true : false;
	}

	@Override
	public boolean delete(String ids) {

		// TODO Auto-generated method stub
		SysUserRoleExample example = new SysUserRoleExample();

		example.or().andUserRoleIdIn(CommonUtils.idsArrayToList(ids));
		
		try{
			
			usersRolesMapper.deleteByExample(example) ;
			
			
		}catch(Exception e){
			e.printStackTrace();
			
			return false;
			
		}
		

		return true;
	}

	@Override
	public boolean updata(SysUserRole usersRoles) {
		// TODO Auto-generated method stub
		dataAuthorizeService.addDataAuthorizeInfo(usersRoles, "update");
		
		return usersRolesMapper.updateByPrimaryKey(usersRoles) == 1 ? true
				: false;
	}

	@Override
	public String getRoleNames(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysRoleVO> getUserRoleVOList(Integer userId) {
		// TODO Auto-generated method stub
		if (userId == null || "".equals(userId))
			return null;

		Map<Integer, Boolean> checkedRoles = this.setCheckedRoles(this
				.getUserRoleList(userId));

		List<SysRole> roles = rolesService.getRoles(""); 

		List<SysRoleVO> httpRoles = new ArrayList<SysRoleVO>();
		for (SysRole role : roles) {
			SysRoleVO httpRole = new SysRoleVO();

//			ReflectionUtils.parentToChild(role, httpRole);


			BeanUtils.copyProperties(role, httpRole);
			if (checkedRoles.containsKey(role.getRoleId()))
				httpRole.setChecked(true);
			else
				httpRole.setChecked(false);
			httpRoles.add(httpRole);
		}

		return httpRoles;
	}

	private Map<Integer, Boolean> setCheckedRoles(List<SysUserRole> usersRoles) {

		Map<Integer, Boolean> checkedRoles = new HashMap<Integer, Boolean>();

		for (SysUserRole userRole : usersRoles) {

			checkedRoles.put(userRole.getRoleId(), true);

		}

		return checkedRoles;
	}

	@Override
	public List<SysUserRole> getUserRoleList(Integer userId) {

		// TODO Auto-generated method stub
		SysUserRoleExample example = new SysUserRoleExample();
		System.out.println("userId:"+userId);
		example.or().andUserIdEqualTo(userId);

		return usersRolesMapper.selectByExample(example);

	}

	@Override
	public boolean insert(String userId, String roleId) {
		// TODO Auto-generated method stub

		if (userId == null || "".equals(userId) || roleId == null
				|| "".equals(roleId))
			return false;

		// 如果存在已分配的角色不再分配
//		if (this.isExistRoleUser(Integer.parseInt(userId),
//				Integer.parseInt(roleId)))
//			return true;

		//先删除用户角色
		this.delete(userId, null);
		
		//一次给一个用户分配多个角色
		List<Integer> roleIdList = CommonUtils.idsArrayToList(roleId);
		
		
		for(Integer rId :roleIdList){
			
			SysUserRole ur = new SysUserRole();

			ur.setUserId(Integer.parseInt(userId));
			ur.setRoleId(rId);

		
			this.insert(ur);
		}
		
		return true;
	}

	@Override
	public boolean delete(String userId, String roleId) {
		// TODO Auto-generated method stub
		if (userId == null || "".equals(userId))
			return false;

		SysUserRoleExample example = new SysUserRoleExample();

		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(Integer.parseInt(userId));
		
		if( roleId == null || "".equals(roleId))
		;
		else
			criteria.andRoleIdEqualTo(Integer.parseInt(roleId));
		
		try{
			usersRolesMapper.deleteByExample(example);
		}catch(Exception e){
			e.printStackTrace();
			return false;
			
		}

		return true;
	}

	@Override
	public boolean isExistUsers(Integer roleId) {
		// TODO Auto-generated method stub
		SysUserRoleExample example = new SysUserRoleExample();

		example.or().andRoleIdEqualTo(roleId);

		List<SysUserRole> list = usersRolesMapper.selectByExample(example);

		if (list == null || list.size() < 1)
			return false;
		
		return true;

	}

	@Override
	public Map<String,Object> getUserRoleVOMap(Integer userId) {
		// TODO Auto-generated method stub
		
		Map<String,Object>  map = new HashMap();
		
		List list =  this.getUserRoleVOList(userId);
		
		map.put("aaData",list);
		map.put("recordsFiltered", list.size() );
		map.put("recordsTotal", list.size());
		
		return map;
	}
}
