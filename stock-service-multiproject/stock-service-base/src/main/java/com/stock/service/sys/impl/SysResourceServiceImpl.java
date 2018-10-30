/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys.impl
 * 类/接口名	: SysResourcesServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:50:19
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:50:19
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.mapper.sys.SysResourceMapper;
import com.stock.dao.model.sys.SysResource;
import com.stock.dao.model.sys.SysResourceExample;
import com.stock.dao.model.sys.SysResourceExample.Criteria;
import com.stock.pojo.vo.sys.SysResourceVO;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.sys.SysResourceService;
import com.stock.service.sys.utils.SysConst;
import com.stock.service.sys.utils.SysResourceTree;
/**
 * 
 * ClassName	: SysResourcesServiceImpl
 * Function		: TODO
 * date 		: 2017年5月7日下午6:03:34
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
@Service("resourcesService")
public class SysResourceServiceImpl implements SysResourceService {

	@Autowired
	private SysResourceMapper resourcesMapper;
	
	@Autowired
	private  DataAuthorizeService dataAuthorizeService;


	@Override
	public boolean getChildResource(String pid) {

		if (pid == null || "".equals(pid))
			return false;

		SysResourceExample example = new SysResourceExample();
		Criteria criteria = example.createCriteria();

		criteria.andParentIdEqualTo(Integer.valueOf(pid));

		List<SysResource> resList = resourcesMapper.selectByExample(example);

		if (resList == null || resList.size() < 1)
			return false;
		else
			return true;
	}

	@Override
	public List<SysResource> getResource(List<String> types) {

		SysResourceExample example = new SysResourceExample();

		example.or().andTypeIn(types);
		
		example.setOrderByClause(" resource_id asc");

		return resourcesMapper.selectByExample(example);

	}

	@Override
	public boolean insert(SysResource res) {
		
		dataAuthorizeService.addDataAuthorizeInfo(res, "insert");

		if (resourcesMapper.insertSelective(res) != 1)
			return false;

		this.init_RESOURCESTREE_UNINCLUDE_BUSINESS();
		this.init_RESOURCESTREE();

		return true;
	}

	/**
	 * 需判断资源是否已分配给角色
	 */
	@Override
	public boolean delete(SysResource res) {
		
		if(res == null)
			return false;
		
		if (this.getChildResource(res.getResourceId().toString()))
			 return false;

		if (resourcesMapper.deleteByPrimaryKey(res.getResourceId()) != 1)
			return false;

		this.init_RESOURCESTREE_UNINCLUDE_BUSINESS();
		this.init_RESOURCESTREE();

		return true;
	}
	

	@Override
	public boolean update(SysResource res) {
		
		dataAuthorizeService.addDataAuthorizeInfo(res, "update");


		if (resourcesMapper.updateByPrimaryKey(res) != 1)
			return false;

		this.init_RESOURCESTREE_UNINCLUDE_BUSINESS();
		this.init_RESOURCESTREE();

		return true;
	}

	@Override
	public void init_RESOURCESTREE_UNINCLUDE_BUSINESS() {
		// TODO Auto-generated method stub

		List<String> types = new ArrayList<String>();
		types.add("用户");
		types.add("数据");

		SysConst.RESOURCESTREE_UNINCLUDE_BUSINESS = new SysResourceTree(
				this.getResource(types));// basicDataSetService.getALLBasicDataSet();

	}
	
	@Override
	public void init_RESOURCESTREE() {
		// TODO Auto-generated method stub

		List<String> types = new ArrayList<String>();
		types.add("用户");
		types.add("数据");
		types.add("业务");

		SysConst.RESOURCESTREE = new SysResourceTree(
				this.getResource(types));// basicDataSetService.getALLBasicDataSet();

	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SysResourceVO> listResource(List<String> types) {
		// TODO Auto-generated method stub
		
		
		List<SysResource> resourceList = this.getResource(types); 
		
		 List<SysResourceVO> resourceVOList = new ArrayList<SysResourceVO>();
		 
		 for(SysResource resource: resourceList){
			 
			 SysResourceVO resourceVO = new SysResourceVO();
			 
			 BeanUtils.copyProperties(resource, resourceVO);
			 
			 resourceVO.setId(resource.getResourceId());
			 resourceVO.setName(resource.getText());

			 resourceVO.setpId(resource.getParentId());
			 
			 resourceVOList.add(resourceVO);
			 
		 }
		
		
		return resourceVOList;
	}

}
