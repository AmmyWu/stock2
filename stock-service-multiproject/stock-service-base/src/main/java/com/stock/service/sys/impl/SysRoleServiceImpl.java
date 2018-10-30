/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys.impl
 * 类/接口名	: SysRolesServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:50:19
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:50:19
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stock.common.util.ReflectionUtil;
import com.stock.dao.mapper.sys.SysRoleMapper;
import com.stock.dao.model.sys.SysRole;
import com.stock.dao.model.sys.SysRoleExample;
import com.stock.dao.model.sys.SysRoleExample.Criteria;
import com.stock.dao.page.Page;
import com.stock.pojo.vo.sys.SysRoleVO;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.common.util.CommonUtils;
import com.stock.service.common.util.CriteriaUtils;
import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.sys.SysRoleResourceService;
import com.stock.service.sys.SysRoleService;
import com.stock.service.sys.SysUserRoleService;

/**
 * 
 * ClassName : SysRolesServiceImpl Function : TODO date : 2017年5月7日下午6:04:19
 * 
 * @author chengxl
 * @version :
 * @since JDK 1.7
 */
@Service("rolesService")
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleMapper rolesMapper;

	@Autowired
	private SysUserRoleService usersRolesService;

	@Autowired
	private SysRoleResourceService rolesResourcesService;
	
	@Autowired
	private  DataAuthorizeService dataAuthorizeService;



	@Override
	public List<SysRole> getRoles(String text) {

		SysRoleExample example = new SysRoleExample();

		if (!StringUtils.isEmpty(text)) {
			example.or().andNameLike("%" + text + "%");
		}

		return rolesMapper.selectByExample(example);
	}

	@Override
	public Map<String, Object> getRoles(String keys, Integer pageSize,
			Integer pageIndex) {

		SysRoleExample example = new SysRoleExample();
		
		this.setExample(example, keys);

		int total = rolesMapper.countByExample(example);
		/**
		 * for my sql db
		 */
		Page page = new Page();

		// page.setBegin((pageIndex-1)*pageSize);

		page.setBegin(pageIndex);

		page.setLength(pageSize);

		example.setPage(page);

		example.setOrderByClause(" role_id desc");

		List<SysRole> roles = rolesMapper.selectByExample(example);

		Map<String, Object> map = new HashMap<String, Object>();

		// map.put("total", total);
		// map.put("rows", roles);

		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		
		map.put("recordsTotal", total);// total
		map.put("recordsFiltered", total);// total

		map.put("aaData", JSONArray.fromObject(this.convert(roles),config));

		return map;
	}
	
	
	
	private List<SysRoleVO> convert(List<SysRole> roleList) {

		if (roleList == null)
			return null;

		List<SysRoleVO> roleVOList = new ArrayList<SysRoleVO>();

		for (SysRole role : roleList) {
			SysRoleVO roleVO = new SysRoleVO();
			// ReflectionUtils.parentToChild(e, he);

			BeanUtils.copyProperties(role, roleVO);

			roleVO.setAmenderName("test");
			

			roleVOList.add(roleVO);

		}

		return roleVOList;

	}


	// 根据 keys的值，设置Criteria
	private void setExample(SysRoleExample example, String keys) {

		JSONObject jKeys = JSONObject.fromObject(keys);

		Criteria criteria = example.createCriteria();

		if (keys == null || "{}".equals(keys))
			return;

		SysRole role = (SysRole) JSONObject.toBean(jKeys, SysRole.class);

		if (role == null)
			return;

		// 获得news model非空（null）属性list
		List<Map<?, ?>> list = ReflectionUtil.getFiledValues(role, false);

		if (list == null || list.size() == 0)
			return;

		CriteriaUtils.setCriteria(criteria, list);

	}

	@Override
	public List<SysRole> getRoles() {

		return rolesMapper.selectByExample(null);
	}

	// 需要修改 资源-角色表 、角色表
	// @Transactional
	@Override
	public boolean insert(SysRole role) {


		dataAuthorizeService.addDataAuthorizeInfo(role, "insert");
		
		return rolesMapper.insertSelective(role) == 1 ? true : false;
	}

	@Override
	@Transactional
	public boolean deleteRoles(String rolesIds) {

		// 判断删除的role是否已被赋予用户
		if (usersRolesService
				.isExistUsers(CommonUtils.idsArrayToList(rolesIds)))
			return false;

		SysRoleExample example = new SysRoleExample();

		// example.or().andIdIn(CommonUtils.idsArrayToList(rolesIds));

		for (Integer id : CommonUtils.idsArrayToList(rolesIds)) {

			if (!this.delete(id))
				return false;

		}

		return true;

	}

	@Override
	public boolean delete(Integer roleId) {
		// todo 判断该角色是否已分配给用户

		if (usersRolesService.isExistUsers(roleId))
			return false;

		// 删除角色已分配的资源信息 roles_resources表中的记录
		rolesResourcesService.delete(roleId);

		return rolesMapper.deleteByPrimaryKey(roleId) == 1 ? true : false;

	}

	// 更新角色信息
	@Override
	public boolean update(SysRole role) {
		
		dataAuthorizeService.addDataAuthorizeInfo(role, "update");

		int i = rolesMapper.updateByPrimaryKeySelective(role);
		return true;// rolesMapper.updateByPrimaryKey(rol) ==1?true:false;

	}

}
