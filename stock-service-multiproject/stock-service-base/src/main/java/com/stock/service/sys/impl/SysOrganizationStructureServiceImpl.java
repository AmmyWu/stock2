/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys.impl
 * 类/接口名	: SysOrganizationStructureServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:50:19
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:50:19
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.mapper.sys.SysOrganizationStructureMapper;
import com.stock.dao.model.sys.SysOrganizationStructure;
import com.stock.dao.model.sys.SysOrganizationStructureExample;
import com.stock.dao.model.sys.SysOrganizationStructureExample.Criteria;
import com.stock.dao.page.Page;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.sys.SysEmployeeService;
import com.stock.service.sys.SysOrganizationStructureService;
import com.stock.service.sys.utils.SysConst;
import com.stock.service.sys.utils.SysOrganizationStructureTree;
import com.stock.service.sys.utils.SysOrganizationStructureTreeNode;
/**
 * 
 * ClassName	: SysOrganizationStructureServiceImpl
 * Function		: TODO
 * date 		: 2017年5月7日下午6:03:05
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
@Service("organizationStructureService")
public class SysOrganizationStructureServiceImpl implements
		SysOrganizationStructureService {

	@Autowired
	private SysOrganizationStructureMapper sysOrganizationStructureMapper;
	
	
	@Autowired
	private SysEmployeeService sysEmployeeService;
	
	@Autowired
	private  DataAuthorizeService dataAuthorizeService;

	@Autowired
//	private ScheduleClassesExMapper scheduleClassesExMapper;
	@Override
	public void init_ORGANIZATIONSTRUCTURE_TREE() {
		// TODO Auto-generated method stub

		SysConst.ORGANIZATIONSTRUCTURE_TREE = new SysOrganizationStructureTree(
				this.getOrganizationStructure());// basicDataSetService.getALLBasicDataSet();
	}

	@Override
	public List<SysOrganizationStructure> getOrganizationStructure() {
		// TODO Auto-generated method stub

		 SysOrganizationStructureExample example = new
		 SysOrganizationStructureExample();
		 
		/* example.setOrderByClause("id asc");*/
		//
		// example.or().andTypeIn(types);

		return sysOrganizationStructureMapper.selectByExample(example);
	}

	@Override
	public boolean insert(SysOrganizationStructure os) {
		// TODO Auto-generated method stub
		
		if((os.getSuperiorId()==null))
				os.setSuperiorId(0);
		
		dataAuthorizeService.addDataAuthorizeInfo(os, "insert");
		
		if (sysOrganizationStructureMapper.insertSelective(os) != 1)
			return false;

		this.init_ORGANIZATIONSTRUCTURE_TREE();

		return true;

	}

	@Override
	public boolean delete(SysOrganizationStructure os) {
		// TODO Auto-generated method stub

		if (sysOrganizationStructureMapper.deleteByPrimaryKey(os.getOrganizationStructureId()) != 1)
			return false;

		this.init_ORGANIZATIONSTRUCTURE_TREE();

		return true;

	}

	@Override
	public boolean update(SysOrganizationStructure os) {
		// TODO Auto-generated method stub

		dataAuthorizeService.addDataAuthorizeInfo(os, "update");
		
		if (sysOrganizationStructureMapper.updateByPrimaryKeySelective(os) != 1)
			return false;

		this.init_ORGANIZATIONSTRUCTURE_TREE();

		return true;

	}

	@Override
	public boolean getChildOrganizationStructures(String pid) {
		// TODO Auto-generated method stub

		if (pid == null || "".equals(pid))
			return false;

		SysOrganizationStructureExample example = new SysOrganizationStructureExample();

		example.or().andSuperiorIdEqualTo(Integer.parseInt(pid));

		List<SysOrganizationStructure> resList = sysOrganizationStructureMapper
				.selectByExample(example);

		if (resList == null || resList.size() < 1)
			return false;
		else
			return true;

	}

	@Override
	public Map<String, Object> getOrganizationByPage(SysOrganizationStructure os,
			Integer pageSize, Integer pageNow) {
		// public Map<String, Object> getOrganizationByPage(Integer pageSize,
		// Integer pageNow) {
		SysOrganizationStructureExample example = new SysOrganizationStructureExample();
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(os.getName())) {
			criteria.andNameLike("%" + os.getName() + "%");
		}
		if (StringUtils.isNotEmpty(os.getTel())) {
			criteria.andTelLike("%" + os.getTel() + "%");
		}
		criteria.andOrganizationStructureIdIsNotNull();
		int total = sysOrganizationStructureMapper.countByExample(example);
		/**
		 * for my sql db
		 */
			Page page = new Page();
			
			page.setBegin((pageNow-1)*pageSize);
			page.setLength(pageSize);
			
			List<SysOrganizationStructure> oslist = sysOrganizationStructureMapper.selectByExample(example);

			
		/**
		 * for  sql server db
		 */
//		example.setPageIndex(pageNow);
//		example.setPageSize(pageSize);
//		List<SysOrganizationStructure> oslist = sysOrganizationStructureMapper
//				.selectPage(example);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", JSONArray.fromObject(oslist, config));
		map.put("total", total);

		return map;
	}

	@Override
	public String delete(Integer id) {
		
		SysOrganizationStructureTreeNode  ostn = SysConst.ORGANIZATIONSTRUCTURE_TREE.getNode(id);
		
		//如果存在子节点不允许删除
		if(ostn.getChildren() != null && ostn.getChildren().size() >0 )
			return "该节点有子节点不允许删除！";
		
		int employeeNum = sysEmployeeService.countEmployee(id);
		
		if(employeeNum >0 )
			return "该组织架构下有"+employeeNum+"个员工，节点不允许删除！";
		
		try{
			if (sysOrganizationStructureMapper.deleteByPrimaryKey(id) != 1)
				return "error";
		}catch(Exception e){
			String message = e.getMessage();
			if(message.contains("employee_department"))
				return "删除失败！<br>该部门有员工。";
			else
				return "删除失败！<br>数据库异常。";
		}
		this.init_ORGANIZATIONSTRUCTURE_TREE();
		return "success";
	}
	//
	// @Override
	// public String deleteOsById(String[] ids) {
	// for(String id :ids){
	// sysOrganizationStructureMapper.deleteByPrimaryKey(Integer.parseInt(id));
	// }
	// return "success" ;
	// }

	
	@Override
	public boolean updateDefault(SysOrganizationStructure os) {
		int i = sysOrganizationStructureMapper.updateByPrimaryKeySelective(os);
		if(i>0){
			return true;
		}else{
			return false;
		}

		
	}


}
