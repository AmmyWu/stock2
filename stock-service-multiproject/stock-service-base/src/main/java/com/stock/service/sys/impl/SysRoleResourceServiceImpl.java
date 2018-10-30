/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys.impl
 * 类/接口名	: SysRolesResourceServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:50:19
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:50:19
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stock.dao.mapper.sys.SysRoleResourceMapper;
import com.stock.dao.model.sys.SysOrganizationStructure;
import com.stock.dao.model.sys.SysResource;
import com.stock.dao.model.sys.SysRoleResource;
import com.stock.dao.model.sys.SysRoleResourceExample;
import com.stock.dao.model.sys.SysRoleResourceExample.Criteria;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.sys.SysRoleResourceService;
import com.stock.service.sys.SysUserRoleService;
import com.stock.service.sys.utils.RoleResourceOrganization;
import com.stock.service.sys.utils.SysConst;
import com.stock.service.sys.utils.SysOrganizationStructureTreeNode;
import com.stock.service.sys.utils.SysResourceTreeNode;
/**
 * 
 * ClassName	: SysRolesResourceServiceImpl
 * Function		: TODO
 * date 		: 2017年5月7日下午6:03:59
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
@Service("roleResourceService")
public class SysRoleResourceServiceImpl implements SysRoleResourceService {

	@Autowired
	private SysRoleResourceMapper roleResourceMapper;

	@Autowired
	private SysResourceServiceImpl resourceService;

	@Autowired
	private SysUserRoleService usersRoleService;

	@Autowired
	private SysOrganizationStructureServiceImpl organizationStructureService;
	
	@Autowired
	private  DataAuthorizeService dataAuthorizeService;
	
	/**
	 * 获得所有的资源，并根据指定角色已分配的资源，设置资源node checked= true
	 */
	@Override
	public List<SysResourceTreeNode> getRoleResource(String roleId) {

		if (roleId == null || "".equals(roleId))
			return null;

		SysRoleResourceExample example = new SysRoleResourceExample();

		example.or().andRoleIdEqualTo(Integer.parseInt(roleId));
		
		example.setOrderByClause(" resource_id asc");

		List<SysRoleResource> rsList = roleResourceMapper
				.selectByExample(example);

		List<String> types = new ArrayList<String>();
		types.add("用户");
		types.add("数据");

		List<SysResource> resList = resourceService.getResource(types);
		// Map<Integer,ResourceTreeNode> rtnMap =
		// Const.RESOURCESTREE_UNINCLUDE_BUSINESS.getTree();

		List<SysResourceTreeNode> rtnList = new ArrayList<SysResourceTreeNode>();
		for (SysResource res : resList) {

			SysResourceTreeNode rtNode = new SysResourceTreeNode();

//			ReflectionUtils.parentToChild(res, rtNode);
			
			BeanUtils.copyProperties(res, rtNode);
			rtNode.setId(res.getResourceId());
			rtNode.setChecked(this.isSelectedResource(rtNode, rsList));
			rtNode.setName(res.getText());
			rtNode.setText(res.getType());
			
			rtnList.add(rtNode);

		}

		// System.out.println(JSONArray.fromObject(rtnList));

		return rtnList;

	}

	/**
	 * 判断指定的节点及其孩子节点是否分配给角色，如分配给角色，则设置checked == true
	 * 
	 * @param rtNode
	 * @param rsList
	 * @return
	 */
	private boolean isSelectedResource(SysResourceTreeNode rtNode,
			List<SysRoleResource> rsList) {

		// 根据角色已分配的资源，设置资源树的节点是否选中
		for (SysRoleResource rs : rsList) {
			if (rs.getResourceId().equals(rtNode.getResourceId()))
				return true;

		}


		return false;
	}

	/**
	 * 1 获得指定resourcesId 的所有叶子节点 2 依次插入叶子节点 2.1 需判断 roleId，resourceId 的节点是否已分配
	 * 2.2 将未分配的资源的 roleId，resourceId 插入到 roles_resources表中
	 */
	@Override
	@Transactional
	public boolean insert(Integer roleId, Integer resourceId) {

		if (roleId == null ||  resourceId == null)
			return false; 
	

		Set<Integer> resourceIdSet = new HashSet<Integer>();

		resourceIdSet.add(resourceId);
		
		// 获取当前节点的所有孩子节点的ID,包括当前节点
		Set<Integer> childrenResourceIdSet = SysConst.RESOURCESTREE_UNINCLUDE_BUSINESS.getAllChildrenResourceId(resourceIdSet);
		
		boolean re = false;

		Iterator it = childrenResourceIdSet.iterator();

		// 循环插入叶子节点
		while (it.hasNext()) {

			Integer rId = (Integer) it.next();
			SysRoleResource record = new SysRoleResource();

			record.setRoleId(roleId);
			record.setResourceId(rId);
		
			String resourceType = SysConst.RESOURCESTREE_UNINCLUDE_BUSINESS
					.typeOfResource(rId);
			
			if (resourceType != null && resourceType.equals("数据"))
				record.setOrganizationId(-1);

			if (this.isExistRoleResource(roleId, rId))
				continue;
			else
				re = roleResourceMapper.insertSelective(record) == 1 ? true
						: false;
		}

		if (re) {
			// 同步系统常量
			init_ROLE_RESOURCE();
		}

		return true;
	}

	/**
	 * 判断指定roleId，resourcesId的记录是否存在
	 * 
	 * @param roleId
	 * @param resourceId
	 * @return
	 */
	private boolean isExistRoleResource(Integer roleId, Integer resourceId) {

		SysRoleResourceExample example = new SysRoleResourceExample();
		Criteria criteria = example.createCriteria();

		criteria.andRoleIdEqualTo(roleId);
		criteria.andResourceIdEqualTo(resourceId);

		if (roleResourceMapper.countByExample(example) > 0)
			return true;

		return false;

	}

	/**
	 * 设置角色对应的资源的组织架构
	 * @param roleId
	 * @param resourceId
	 * @param orgStrId
	 * @return
	 * @see com.stock.service.sys.SysRoleResourceService#insert(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean insert(Integer roleId, Integer resourceId, Integer orgId) {

		if (roleId == null || resourceId == null|| orgId == null)
			return false;

		SysRoleResource rs = new SysRoleResource();

		rs.setRoleId(roleId);
		rs.setResourceId(resourceId);
//		Integer orgId = Integer.parseInt(orgStrId);
		
		Set<Integer> orgIdSet = new HashSet<Integer>();

		orgIdSet.add(orgId);
		// 获取当前节点的所有孩子节点的ID,包括当前节点
		Set<Integer> childrenOrgIdSet = SysConst.ORGANIZATIONSTRUCTURE_TREE
				.getAllChildren(orgIdSet);

		boolean re = false;

		Iterator it = childrenOrgIdSet.iterator();
		// 循环插入叶子节点
		while (it.hasNext()) {

			rs.setOrganizationId((Integer) it.next());

			dataAuthorizeService.addDataAuthorizeInfo(rs, "insert");
			
			re = roleResourceMapper.insertSelective(rs) == 1 ? true : false;
		}

//		if (re) {
		// 同步系统常量
		init_ROLE_RESOURCE();
//		}
		return re;
	}

	
//	public boolean insert(String roleId, String resourceId, String orgStrId) {
//
//		if (roleId == null || "".equals(roleId) || resourceId == null
//				|| "".equals(resourceId) || orgStrId == null
//				|| "".equals(orgStrId))
//			return false;
//
//		SysRoleResource rs = new SysRoleResource();
//
//		rs.setRoleId(Integer.parseInt(roleId));
//		rs.setResourceId(Integer.parseInt(resourceId));
//		Integer orgId = Integer.parseInt(orgStrId);
//		if (orgId == null)
//			orgId = new Integer(-1);
//		rs.setOrganizationId(orgId);
//
//		boolean re = roleResourceMapper.insertSelective(rs) == 1 ? true
//				: false;
//		if (re) {
//			// 同步系统常量
//			init_ROLE_RESOURCE();
//		}
//		return re;
//	}

	/**
	 * 1 获得指定resourcesId 的所有叶子节点 2 依次删除叶子节点
	 */
	@Override
	@Transactional
	public boolean delete(Integer roleId, Integer resourceId) {

		if (roleId == null || resourceId == null)
			return false;

		Set<Integer> resourceIdSet = new HashSet<Integer>();

		resourceIdSet.add(resourceId);
		
		// 获取当前节点的所有孩子节点的ID,包括当前节点
		Set<Integer> childrenResourceIdSet = SysConst.RESOURCESTREE_UNINCLUDE_BUSINESS.getAllChildrenResourceId(resourceIdSet);
		
		boolean re = false;

		Iterator it = childrenResourceIdSet.iterator();

		// 循环插入叶子节点
		while (it.hasNext()) {
			
			Integer rId = (Integer) it.next();
			

			this.deleteLeafResource(roleId, rId);

			
		}

		return true;
	}

	/**
	 * 删除已分配的叶子节点资源
	 * 
	 * @param roleId
	 * @param resourceId
	 * @return
	 */
	private boolean deleteLeafResource(Integer roleId, Integer resourceId) {
		
		SysRoleResourceExample example = new SysRoleResourceExample();
		Criteria criteria = example.createCriteria();
		criteria.andRoleIdEqualTo(roleId);
		criteria.andResourceIdEqualTo(resourceId);
		boolean re = roleResourceMapper.deleteByExample(example) < 1 ? true
				: false;

		if (re) {
			// 同步系统常量
			init_ROLE_RESOURCE();
		}
		return re;

	}

	/**
	 * 获得指定角色，指定资源id 已分配的组织架构
	 */
	@Override
	public List<SysOrganizationStructureTreeNode> getRoleResourceOS(
			Integer roleId, Integer resourceId) {

		if (roleId == null || resourceId == null )
			return null;

		SysRoleResourceExample example = new SysRoleResourceExample();

		Criteria criteria = example.createCriteria();

		criteria.andRoleIdEqualTo(roleId);
		criteria.andResourceIdEqualTo(resourceId);

		List<SysRoleResource> rsList = roleResourceMapper
				.selectByExample(example);

		List<SysOrganizationStructure> osList = organizationStructureService
				.getOrganizationStructure();

		List<SysOrganizationStructureTreeNode> ostnList = new ArrayList<SysOrganizationStructureTreeNode>();
		for (SysOrganizationStructure os : osList) {

			SysOrganizationStructureTreeNode osNode = new SysOrganizationStructureTreeNode();

//			ReflectionUtils.parentToChild(os, osNode);

			BeanUtils.copyProperties(os, osNode);
			
			osNode.setChecked(this.isSelectedOrganizationStructure(osNode,
					rsList));
			osNode.setId(os.getOrganizationStructureId());
			osNode.setText(os.getName());
			ostnList.add(osNode);
		}
		return ostnList;
	}

	/**
	 * 判断指定的节点是否选中
	 * 
	 * @param rtNode
	 * @param rsList
	 * @return
	 */
	private boolean isSelectedOrganizationStructure(
			SysOrganizationStructureTreeNode osNode, List<SysRoleResource> rsList) {

		// 根据角色已分配的资源，设置资源树的节点是否选中
		for (SysRoleResource rs : rsList) {

			if (rs.getOrganizationId() == osNode.getOrganizationStructureId())
				return true;

		}

		return false;
	}

	@Override
	public boolean delete(Integer roleId, Integer resourceId,Integer orgId) {

		if (roleId == null || resourceId == null || orgId == null)
			return false;
		
		Set<Integer> orgIdSet = new HashSet<Integer>();

		orgIdSet.add(orgId);
		
		// 获取当前节点的所有孩子节点的ID,包括当前节点
		Set<Integer> childrenOrgIdSet = SysConst.ORGANIZATIONSTRUCTURE_TREE
						.getAllChildren(orgIdSet);

		List<Integer> childrenOrgIdList = new ArrayList<Integer>(childrenOrgIdSet);


		// TODO Auto-generated method stub
		SysRoleResourceExample example = new SysRoleResourceExample();
		Criteria criteria = example.createCriteria();
		criteria.andRoleIdEqualTo(roleId);
		criteria.andResourceIdEqualTo(resourceId);
		
		criteria.andOrganizationIdIn(childrenOrgIdList);
		

		roleResourceMapper.deleteByExample(example);
//		if (re) {
			// 同步系统常量
			init_ROLE_RESOURCE();
//		}
		return true;
	}

	@Override
	public void init_ROLE_RESOURCE() {
		SysRoleResourceExample example = new SysRoleResourceExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusIsNull();

		List<SysRoleResource> listRoleResource = roleResourceMapper
				.selectByExample(example);

		SysConst.ROLE_RESOURCE = new RoleResourceOrganization();
		SysConst.ROLE_RESOURCE.updateRRO(listRoleResource);
	}

	@Override
	public boolean delete(Integer roleId) {
		// TODO Auto-generated method stub

		SysRoleResourceExample example = new SysRoleResourceExample();
		example.or().andRoleIdEqualTo(roleId);

		roleResourceMapper.deleteByExample(example);

		init_ROLE_RESOURCE();

		return true;
	}

	@Override
	public String getButtonResourceIds(List<Integer> resIdList ) {

//		List<Integer> resIdList = this.getResourceIdList(userId);

		String resourceIds = "";
		SysResourceTreeNode node = null;
		// 将List 转为String
		for (Integer rId : resIdList) {
			
			node = SysConst.RESOURCESTREE.getNode(rId);
			
			if(node.getPriority() != null || resourceIds.contains("BTN_"+rId))//非根节点 或重复节点，button节点为根节点
				continue;
		

			resourceIds += "BTN_" + rId + "||";
		}

		return resourceIds;
	}

	@Override
	public List<Integer> getResourceIdList(List<Integer> roleIds) {

//		List<SysUserRole> urList = usersRoleService.getUserRoleList(userId);
//
//		if(urList== null || urList.size() == 0)
//			return null;
//
//		List<Integer> roleIdList = new ArrayList<Integer>();
//		
//
//		for (SysUserRole ur : urList) {
//			roleIdList.add(ur.getRoleId());
//
//		}

		SysRoleResourceExample example = new SysRoleResourceExample();
		
		example.setDistinct(true);
		example.or().andRoleIdIn(roleIds);

		List<Integer> resIdList = new ArrayList<Integer>();

		List<SysRoleResource> rsList = roleResourceMapper
				.selectByExample(example);

		for (SysRoleResource rs : rsList) {

			if (!resIdList.contains(rs.getResourceId()))
				resIdList.add(rs.getResourceId());
		}

		return resIdList;
	}

//	@Override
//	public JSONArray getRoleResourceOSTest(Integer roleId, Integer resourceId) {
//		JSONArray jarray = new JSONArray();
//		if (roleId == null ||  resourceId == null)
//			return null;
//
//		SysRoleResourceExample example = new SysRoleResourceExample();
//
//		Criteria criteria = example.createCriteria();
//
//		criteria.andRoleIdEqualTo(roleId);
//		criteria.andResourceIdEqualTo(resourceId);
//
//		List<SysRoleResource> rsList = roleResourceMapper
//				.selectByExample(example);
//
//		List<SysOrganizationStructure> osList = organizationStructureService
//				.getOrganizationStructure();
//		
////		StringBuilder osIds = new StringBuilder();
//		for (SysOrganizationStructure os : osList) {
//			
//			SysOrganizationStructureTreeNode osNode = new SysOrganizationStructureTreeNode();
//
////			ReflectionUtils.parentToChild(os, osNode);
//
//			BeanUtils.copyProperties(os, osNode);
//			
//			if(this.isSelectedOrganizationStructure(osNode,
//					rsList)){
//				
//				osNode.setChecked(true);//选中标志
//				
////				JSONObject json = new JSONObject();
////				json.put("id",os.getOrganizationStructureId());
//			
//			}
//			
//			
//			jarray.add(osNode);
//		}
	/*	return osIds.toString().substring(0, osIds.toString().length()-1);*/
//		return jarray;
//	}

	@Override
	public JSONArray getRoleResourceInitOS() {
		List<SysOrganizationStructure> osList = organizationStructureService
				.getOrganizationStructure();
		JSONArray jarray = new JSONArray();
		for (SysOrganizationStructure sos : osList) {
			JSONObject json = new JSONObject();
			json.put("id", sos.getOrganizationStructureId());
			json.put("text", sos.getName());
			jarray.add(json);
		}
		return jarray;
	}

}
