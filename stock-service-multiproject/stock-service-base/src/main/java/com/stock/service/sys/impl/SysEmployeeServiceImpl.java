/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys.impl
 * 类/接口名	: SysEmployeeServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:50:19
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:50:19
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.stock.common.tool.string.StringUtil;
import com.stock.common.util.ReflectionUtil;
import com.stock.common.util.excel.ExcelImporter;
import com.stock.common.util.excel.ExcelImporterTemplate;
import com.stock.dao.mapper.sys.SysEmployeeMapper;
import com.stock.dao.model.sys.SysAuthentication;
import com.stock.dao.model.sys.SysEmployee;
import com.stock.dao.model.sys.SysEmployeeExample;
import com.stock.dao.model.sys.SysUser;
import com.stock.dao.model.sys.SysEmployeeExample.Criteria;
import com.stock.dao.page.Page;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.pojo.vo.dto.EmployeeDTO;
import com.stock.pojo.vo.sys.SysEmployeeVO;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.common.util.CriteriaUtils;
import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.common.util.excel.ExportExcel;
import com.stock.service.sys.SysAuthenticationService;
import com.stock.service.sys.SysEmployeeService;
import com.stock.service.sys.SysUserService;
import com.stock.service.sys.utils.HttpResponseConstants;
import com.stock.service.sys.utils.SysConst;
import com.stock.service.sys.utils.SysOrganizationStructureTreeNode;

/**
 * 
 * ClassName : SysEmployeeServiceImpl Function : TODO date : 2017年5月7日下午6:02:05
 * 
 * @author chengxl
 * @version :
 * @since JDK 1.7
 */

@Service("employeeService")
public class SysEmployeeServiceImpl implements SysEmployeeService {

	@Autowired
	private SysEmployeeMapper employeeMapper;// SysEmployeeMapperEx
	
	@Autowired
	private SysUserService userService;

	@Autowired
	private SysAuthenticationService authenticationService;


	@Autowired
	private SysUserService usersService;
	
	@Autowired
	private DataAuthorizeService dataAuthorizeService;
	@Autowired
	private HttpSession session;
	

	@Override
	public Map<String, Object> getEmployeeByPage(String keys, Integer pageSize,
			Integer pageNow) {
		SysEmployeeExample example = new SysEmployeeExample();
		Criteria criteria = example.createCriteria();

		this.setCriteria(keys, criteria);
		/*Integer employeeId = (Integer) session.getAttribute("employeeId");
		//根据数据权限设置
		criteria.andCreatorEqualTo(employeeId);*/
		//dataAuthorizeService.setExampleByAuthorization(example);
		// criteria.andEmployeeIdIsNotNull();
		int totalrecords = employeeMapper.countByExample(example);

		/**
		 * for my sql db
		 */
		Page page = new Page();

		page.setBegin(pageNow);
		page.setLength(pageSize);

		example.setOrderByClause("employee_id desc");

		example.setPage(page);

		List<SysEmployee> employees = employeeMapper.selectByExample(example);

		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("total", totalrecords);
		// if (totalrecords % pageSize == 0)
		// map.put("totalPages", totalrecords / pageSize);
		// else
		// map.put("totalPages", totalrecords / pageSize + 1);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		// map.put("rows", JSONArray.fromObject(this.convert(employees),
		// config));
		map.put("aaData", JSONArray.fromObject(this.convert(employees), config));

		map.put("recordsTotal", totalrecords);// total
		map.put("recordsFiltered", totalrecords);// total

		return map;
	}

	private void setCriteria(String keys, Criteria criteria) {

		JSONObject jKeys = JSONObject.fromObject(keys);

		// Criteria criteria = example.createCriteria();

		if (keys == null || "{}".equals(keys))
			return;

		SysEmployee employee = (SysEmployee) JSONObject.toBean(jKeys,SysEmployee.class);

		if (employee == null)
			return;

		if (!StringUtils.isEmpty(employee.getEmployeeName())) {
			criteria.andEmployeeNameLike("%" + employee.getEmployeeName() + "%");
		}

		// 获得news model非空（null）属性list
		List<Map<?, ?>> list = ReflectionUtil.getFiledValues(employee, false);

		if (list == null || list.size() == 0)
			return;

		CriteriaUtils.setCriteria(criteria, list);

	}

	private List<SysEmployeeVO> convert(List<SysEmployee> listE) {

		if (listE == null)
			return null;

		List<SysEmployeeVO> listHE = new ArrayList<SysEmployeeVO>();

		for (SysEmployee e : listE) {
			SysEmployeeVO he = new SysEmployeeVO();
			// ReflectionUtils.parentToChild(e, he);

			BeanUtils.copyProperties(e, he);

			// if (e.getEmployeeDepartment() == null
			// || "".equals(e.getEmployeeDepartment()))
			// ;
			if (e.getOrganizationStructureId() == null)
				;
			else if (e.getOrganizationStructureId() == 0) { // 根节点
				he.setOrganizationStructureName(SysConst.ORGANIZATIONSTRUCTURE_TREE
						.getNode(0).getName());
			} else {
				SysOrganizationStructureTreeNode node = SysConst.ORGANIZATIONSTRUCTURE_TREE
						.getNode(e.getOrganizationStructureId());

				// he.setDepartmentName(SysConst.ORGANIZATIONSTRUCTURE_TREE
				// .getParentsNodeName(node) + node.getName());

				he.setOrganizationStructureName(SysConst.ORGANIZATIONSTRUCTURE_TREE
						.getParentsNodeName(node) + node.getName());

			}

			listHE.add(he);

		}

		return listHE;

	}

	/**
	 * 添加员工是需要
	 *    1、判断员工的代码，手机号，姓名是否存在
	 *    2、创建员工对应的系统用户
	 *    3、根据手机号创建对应的登陆账号
	 * 
	 * @param employee
	 * @return
	 * @see com.stock.service.sys.SysEmployeeService#insert(com.stock.dao.model.sys.SysEmployee)
	 */

	@Override
	public RequestResultVO insert(SysEmployee employee) {
		
		RequestResultVO rr = new RequestResultVO();
		
		/*if(this.isExistEmployeeName(employee.getEmployeeName())){
			
			rr.setCode(10001);
			rr.setMessage("员工姓名已存在!");
			
			return rr;
		}*/
		
		/*if(this.isExistEmployeeCode(employee)){
			
			rr.setCode(10001);
			rr.setMessage("员工代码已存在!");
			
			return rr;
		}*/
		
		
		//该手机号对应的员工已存在
		/*if(this.isExistphone(employee) ){
			
			rr.setCode(10002);
			rr.setMessage("该手机号对应的员工已存在!");
			
			return rr;
		}*/
		
		//该手机号对应的员工已存在
	/*	if(authenticationService.isExistAccount(employee.getEmployeeCellPhone())){
			
			rr.setCode(10002);
			rr.setMessage("该手机号对应的员工已存在!");
			
			return rr;
		}*/


		dataAuthorizeService.addDataAuthorizeInfo(employee,"insert");
		this.doInsert(employee);

		this.init_EMPLOYEEMAP();
		
		rr.setCode(0);
		rr.setMessage("新增成功！");

		// SysConst.initConstFor_QUERY("init_EMPLOYEEMAP");

		return  rr;
	}

	
	@Transactional
	private boolean doInsert(SysEmployee employee) {
	

		employeeMapper.insertSelective(employee);
		SysEmployeeExample example = new SysEmployeeExample();
		example.or().andEmployeeNameEqualTo(employee.getEmployeeName());

		List<SysEmployee> employeeIds = employeeMapper.selectByExample(example);
		int employeeId = employeeIds.get(0).getEmployeeId();
		int uid = 0;
		
		// 为员工创建用户
		if (!StringUtils.isEmpty(employee.getEmployeeName())) {
			SysUser user = new SysUser();
			user.setUsername(employee.getEmployeeName());
			user.setCreateTime(new Date());
			user.setType("员工");
			user.setStatus("启用");
			user.setUserDetailId(employeeId);
			userService.insert(user);

			uid = user.getUserId();
		}
		//为员工创建手机对应的系统账号
		if (!StringUtils.isEmpty(employee.getEmployeeCellPhone())) {
			// if (employee.getEmployeeCellPhone() != null) {
			SysAuthentication info = new SysAuthentication();
			info.setAccount(employee.getEmployeeCellPhone());
			info.setUserId(uid);
			info.setType("员工");
			info.setStatus("启用");
			int length = employee.getEmployeeCellPhone().length();
			info.setPassword(employee.getEmployeeCellPhone().substring(
					length - 6, length));
			
			authenticationService.insert(info);
		}

		return true;
	}

	
	//
	private boolean isExistEmployeeName(String employeeName) {

		SysEmployeeExample example = new SysEmployeeExample();

		example.or().andEmployeeNameEqualTo(employeeName);

		return employeeMapper.countByExample(example) > 0 ? true : false;
	}

	
	// 判断电话号码是否存在
	private boolean isExistEmployeeCode(SysEmployee employee) {
		
		if(StringUtils.isEmpty(employee.getEmployeeCode()))
			return false;

		SysEmployeeExample example = new SysEmployeeExample();

		example.or().andEmployeeCodeEqualTo(
				employee.getEmployeeCode());

		List<SysEmployee> employees = employeeMapper.selectByExample(example);

		if (employees.size() < 1)
			return false;

		for (SysEmployee e : employees) {

			if (e.getEmployeeId().equals(employee.getEmployeeId()))
				return false;

		}

		return true;

	}

	// 判断电话号码是否存在
	private boolean isExistphone(SysEmployee employee) {
		
		if(StringUtils.isEmpty(employee.getEmployeeCellPhone()))
			return false;

		SysEmployeeExample example = new SysEmployeeExample();

		example.or().andEmployeeCellPhoneEqualTo(
				employee.getEmployeeCellPhone());

		List<SysEmployee> employees = employeeMapper.selectByExample(example);

		if (employees.size() < 1)
			return false;

		//修改时，手机号放回的是同一个员工
		for (SysEmployee e : employees) {

			if (e.getEmployeeId().equals(employee.getEmployeeId()))
				return false;

			// System.out.println(e.getEmployeeId() ==
			// employee.getEmployeeId());
		}

		return true;

	}

	// 判断邮箱是否存在
	private boolean isExistEmail(SysEmployee employee) {

		if (StringUtils.isEmpty(employee.getEmail())) {
			return false;
		}
		SysEmployeeExample example = new SysEmployeeExample();

		example.or().andEmailEqualTo(employee.getEmail());

		List<SysEmployee> employees = employeeMapper.selectByExample(example);

		if (employees.size() < 1)
			return false;

		for (SysEmployee e : employees) {

			if (e.getEmployeeId() == employee.getEmployeeId())
				return false;
		}

		return true;

	}


	@Override
	public RequestResultVO update(SysEmployee employee) {
		
		RequestResultVO rr = new RequestResultVO();
		
		// 判断是否修改了手机号码及邮箱，如果修改了则需要修改相应的user表及authentciaionInfo表
		if (this.isExistphone(employee)){
			
			rr.setCode(10002);
			rr.setMessage("员工手机号已存在！");
			
			return rr;
			
		}

		// 判断是否修改了手机号码及邮箱，如果修改了则需要修改相应的user表及authentciaionInfo表
	/*	if ( this.isExistEmail(employee)){
			
			rr.setCode(10003);
			rr.setMessage("员工邮箱号已存在！");
			
			return rr;
			
		}*/

		 this.updateUsersAndAuthentication(employee);

		// if (employee.getEmployeeDepartment().equals(new Integer(0)))
		// employee.setEmployeeDepartment(null);

		if (employeeMapper.updateByPrimaryKeySelective(employee) < 1){
			
			rr.setCode(1);
			rr.setMessage("修改失败");
			
			return rr;
			
		}
		this.init_EMPLOYEEMAP();
		
		return rr;
	}

	
	/**
	 * 修改员工信息时，修其对应的用户信息和授权账号信息，如果手机号码修改了，修改器对应的登陆账号
	   * TODO 请在此处添加注释
	   * @param employee
	 */
	@Transactional
	private void updateUsersAndAuthentication(SysEmployee employee) {

		SysEmployee oldEmployee = employeeMapper.selectByPrimaryKey(employee
				.getEmployeeId());

		//员工姓名修改时，修改员工对应的用户名称
		if (!StringUtils.isEmpty(oldEmployee.getEmployeeName()) && !oldEmployee.getEmployeeName().equals(employee.getEmployeeName())) {

			userService.setUsernameByEmployeeId(employee.getEmployeeName(),employee.getEmployeeId());

		}

		//手机号发生变化时，修改用户的登陆账号
		if (!StringUtils.isEmpty(oldEmployee.getEmployeeCellPhone()) && !oldEmployee.getEmployeeCellPhone().equals(
				employee.getEmployeeCellPhone())) {
	

			authenticationService.updateAccount(employee.getEmployeeCellPhone(), oldEmployee.getEmployeeCellPhone());
		}


	}

	@Override
	@Transactional
	public String deleteEmpById(String[] ids) {

		List<Integer> intIds = new ArrayList<Integer>();
		for (String id : ids) {
			try {
				employeeMapper.deleteByPrimaryKey(Integer.parseInt(id));
				intIds.add(Integer.parseInt(id));

			} catch (Exception e) {
				if (e.getLocalizedMessage().contains("customer_counterman"))
					return "数据访问异常，删除失败！";
				else
					return "数据访问异常，删除失败！";
			}
		}

		List<String> types = new ArrayList<String>();
		types.add("员工");

		usersService.deleteByUserDetailId(intIds, types);

		this.init_EMPLOYEEMAP();

		// SysConst.initConstFor_QUERY("init_EMPLOYEEMAP");

		return "success";
	}

	@Override
	public SysEmployee getEmployeeById(Integer employeeId) {
		// TODO Auto-generated method stub

		return employeeMapper.selectByPrimaryKey(employeeId);
	}

	@Override
	public void init_EMPLOYEEMAP() {
		// TODO Auto-generated method stub

		SysConst.EMPLOYEE_MAP = new HashMap<Integer, String>();

		List<SysEmployee> employeeList = employeeMapper.selectByExample(null);

		for (SysEmployee e : employeeList) {

			SysConst.EMPLOYEE_MAP.put(e.getEmployeeId(), e.getEmployeeName());
		}

	}

	/**
	 * 返回未分配用户的员工List<Map>,用于创建用户下拉框选择
	 * @return
	 * @see com.stock.service.sys.SysEmployeeService#listEmployeesOfUnallotUsers()
	 */
	@Override
	public List<Map<String,Object>> getEmployeesOfUnallotUsers() {
		// TODO Auto-generated method stub
	
		
		SysEmployeeExample example = new SysEmployeeExample();
		
		example.or().andEmployeeIdNotIn(userService.getUserDetailIdList("员工"));
		
		List<SysEmployee> employeeList = employeeMapper.selectByExample(example);
		
		List<Map<String,Object>> employeeMapList =  new ArrayList<Map<String,Object>>();
		
		for(SysEmployee employee :employeeList){
			
			Map<String,Object> employeeMap = new HashMap<String,Object>();
			
			employeeMap.put("id", employee.getEmployeeId());
			employeeMap.put("text",employee.getEmployeeName());
			
			employeeMapList.add(employeeMap);
		}
		
		

		return employeeMapList;
	}

	
	@Override
	public List<SysEmployeeVO> getEmployees() {
		// TODO Auto-generated method stub

		return this.convert(employeeMapper.selectByExample(null));
	}

	/**
	 *
	 */
	@Override
	public List<Integer> getEmployeeIds(String employeeName) {

		List<Integer> eIdList = new ArrayList<Integer>();

		SysEmployeeExample example = new SysEmployeeExample();

		example.or().andEmployeeNameLike("%" + employeeName.trim() + "%");
		for (SysEmployee e : employeeMapper.selectByExample(example)) {
			eIdList.add(e.getEmployeeId());
		}

		return eIdList;
	}

	@Override
	public void exportExcel(SysEmployeeVO employee, OutputStream os) {

		SysEmployeeExample example = new SysEmployeeExample();

		Criteria criteria = example.createCriteria();

		// this.setCriteria(employee, criteria);

		example.setOrderByClause(" work_group asc");
		List<SysEmployeeVO> listEmployeeEx = this.convert(employeeMapper
				.selectByExample(example));

		ExportExcel<SysEmployeeVO> exportExcel = new ExportExcel<SysEmployeeVO>();

		exportExcel.exportExcel("员工", SysConst.ExcelConst.HEAD_SYSEMPLOYEE,
				SysConst.ExcelConst.FIELDNAMES_SYSEMPLOYEE, listEmployeeEx, os,
				"yyyy-MM-dd");

	}

	@Override
	public SysEmployee getByCustomerName(String employeeName) {

		SysEmployeeExample example = new SysEmployeeExample();

		example.or().andEmployeeNameEqualTo(employeeName);

		List<SysEmployee> customers = employeeMapper.selectByExample(example);

		if (customers == null || customers.size() == 0)
			return null;

		return customers.get(0);
	}

	@Override
	public Integer getByFromUserName(String fromusername) {

		SysEmployeeExample example = new SysEmployeeExample();

		example.or().andFromUserNameEqualTo(fromusername);

		List<SysEmployee> customers = employeeMapper.selectByExample(example);

		if (customers == null || customers.size() == 0)
			return null;

		return customers.get(0).getEmployeeId();
	}

	@Override
	public List<SysEmployee> getByCellPhone(String cellPhone) {

		SysEmployeeExample example = new SysEmployeeExample();

		example.or().andEmployeeCellPhoneEqualTo(cellPhone);

		List<SysEmployee> customers = employeeMapper.selectByExample(example);

		if (customers == null || customers.size() == 0)
			return null;

		return customers;
	}

	@Override
	public int countEmployee(int organizationStructrueId) {
		// TODO Auto-generated method stub

		SysEmployeeExample example = new SysEmployeeExample();

		example.or().andOrganizationStructureIdEqualTo(organizationStructrueId);

		// criteria.andEmployeeIdIsNotNull();
		return employeeMapper.countByExample(example);

	}

	@Override
	public SysEmployee getByOpenId(String openId) {
		SysEmployeeExample example = new SysEmployeeExample();
		example.or().andFromUserNameEqualTo(openId);
		
		return employeeMapper.selectByExample(example).get(0);
	}

	@Override
	public List<SysEmployee> getEmployeeByOrganizationId(
			Integer organizationStructureId) {
		SysEmployeeExample example = new SysEmployeeExample();
		example.or().andOrganizationStructureIdEqualTo(organizationStructureId);
		
		return employeeMapper.selectByExample(example);
	}

	@Override
	public List<SysEmployee> getEmployeeByCreatorIdAndEmployeeName(String employeeName,Integer creator) {
		SysEmployeeExample example = new SysEmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmployeeNameLike("%"+employeeName+"%");
		criteria.andCreatorEqualTo(creator);
		return employeeMapper.selectByExample(example);
	}

	@Override
	public List<SysEmployee> getEmployeeByCreatorId(Integer creator) {
		SysEmployeeExample example = new SysEmployeeExample();
		example.or().andCreatorEqualTo(creator);
		
		return employeeMapper.selectByExample(example);
	}

	@Override
	public Object importExcel(MultipartFile excel) {
		RequestResultVO requestResultVO = new RequestResultVO();
		String fileName = excel.getOriginalFilename();
		if(fileName == null || ( !"xls".equals(fileName.substring(fileName.lastIndexOf(".")+1))&&!"xlsx".equals(fileName.substring(fileName.lastIndexOf(".")+1)))){
			requestResultVO.setContent(HttpResponseConstants.Public.ERROR_CODE, HttpResponseConstants.Public.ERROR_500, null);
			return requestResultVO;
		}
		//excel类型
		String excelType = fileName.substring(fileName.lastIndexOf(".")+1);
		//用于存储导入的excel数据
		List<EmployeeDTO> EmployeeDTOs = new ArrayList<EmployeeDTO>();
		try {
			//Excel导入beans
			ExcelImporter.importExcel(excel.getInputStream(), excelType, EmployeeDTO.class, EmployeeDTOs, ExcelImporterTemplate.employeeMethodMap, true);
		} catch (IOException e) {
			e.printStackTrace();
			requestResultVO.setContent(HttpResponseConstants.Public.ERROR_CODE, HttpResponseConstants.Public.ERROR_500, null);
			return requestResultVO;
		}
		//转化结果
		boolean isSuccess = true;
		//转化失败的数据
		List<EmployeeDTO> errorEmployeeDTOs = new ArrayList<EmployeeDTO>();
		//转化成功的数据
		List<EmployeeDTO> successEmployeeDTOs = new ArrayList<EmployeeDTO>();
		for(EmployeeDTO employeeDTO : EmployeeDTOs){
			if(this.convertDTO(employeeDTO)){
				employeeDTO.setOrganizationStructureId(Integer.parseInt(employeeDTO.getOrganizationStructureIdString()));
				employeeDTO.setEmployeeDepartment(Integer.parseInt(employeeDTO.getOrganizationStructureIdString()));
				//转化成功
				successEmployeeDTOs.add(employeeDTO);
			}else{
				//转化失败
				isSuccess = false;
				errorEmployeeDTOs.add(employeeDTO);
			}
		}
		//把成功数据插入数据库
		for(EmployeeDTO employeeDTO : successEmployeeDTOs){
			if(!this.insertDTO(employeeDTO)){
				//导入失败
				isSuccess = false;
				errorEmployeeDTOs.add(employeeDTO);
			}
		}
		if(isSuccess){
			//全部导入成功
			requestResultVO.setContent(HttpResponseConstants.Public.SUCCESS_CODE, "总共"+EmployeeDTOs.size()+"条数据，成功导入"+successEmployeeDTOs.size()+"条，失败0条", null);
		}else{
			//存在数据导入失败
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
			//值为null的运价，转化为json后默认值为空
			config.registerDefaultValueProcessor(BigDecimal.class, new DefaultValueProcessor() {
				
				@Override
				public Object getDefaultValue(Class arg0) {
					
					return "";
				}
			});

			JSONArray array = JSONArray.fromObject(errorEmployeeDTOs, config);
			requestResultVO.setContent(HttpResponseConstants.Public.ERROR_CODE, "总共"+EmployeeDTOs.size()+"条运价，成功导入"+(EmployeeDTOs.size()-errorEmployeeDTOs.size())+"条，失败"+errorEmployeeDTOs.size()+"条", array);
		}
		return requestResultVO;
	}
	private boolean insertDTO(EmployeeDTO employeeDTO) {
		//设置创建人、创建时间、访问组
		dataAuthorizeService.addDataAuthorizeInfo(employeeDTO, "insert");
		employeeMapper.insertSelective(employeeDTO);
		return true;
}

private boolean convertDTO(EmployeeDTO employeeDTO) {
/**
 * 
   * 把name转换为id,如果转换错误返回false
   * @param CompanyStructureDTO
   * @return
 */
	//如果必填字段修改，下面的判断也要修改
	if(StringUtil.isBlank(employeeDTO.getEmployeeCode()) || StringUtil.isBlank(employeeDTO.getOrganizationStructureIdString())|| StringUtil.isBlank(employeeDTO.getEmployeeName())|| StringUtil.isBlank(employeeDTO.getEmployeeCellPhone())){
		employeeDTO.setErrorMessage("必填字段为空");
		return false;
	}


	return true;
}
}