
package com.stock.service.sys.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.common.util.BeanUtils;
import com.stock.common.util.ReflectionUtil;
import com.stock.dao.mapper.sys.SysCustomerMapper;
import com.stock.dao.model.sys.SysAuthentication;
import com.stock.dao.model.sys.SysCustomer;
import com.stock.dao.model.sys.SysCustomerExample;
import com.stock.dao.model.sys.SysUser;
import com.stock.dao.model.sys.SysCustomerExample.Criteria;
import com.stock.dao.page.Page;
import com.stock.pojo.vo.basedata.SysCustomerVO;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.common.util.CommonUtils;
import com.stock.service.common.util.CriteriaUtils;
import com.stock.service.sys.SysAuthenticationService;
import com.stock.service.sys.SysCustomerService;
import com.stock.service.sys.SysUserRoleService;
import com.stock.service.sys.SysUserService;
import com.stock.service.sys.utils.SysConst;

/**
 * 
 * ClassName : SysCustomersServiceImpl Function : TODO date : 2017年5月7日下午6:05:45
 * 
 * @author chengxl
 * @version :
 * @since JDK 1.7
 */
@Service("customerService")
public class SysCustomerServiceImpl implements SysCustomerService {

	@Autowired
	private SysCustomerMapper customerMapper;
	@Autowired
	private SysUserService userService;

	@Autowired
	private SysAuthenticationService authenticationService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SysAuthenticationService authenticationInfoService;
	
	@Autowired
	private  DataAuthorizeService dataAuthorizeService;
	@Autowired
	private SysUserRoleService userRoleService;

	@Override
	public List<SysCustomer> getCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(SysCustomer customer) {
		dataAuthorizeService.addDataAuthorizeInfo(customer, "insert");
		customerMapper.insertSelective(customer);
		
		this.init_CUSTOMERMAP();
		SysCustomerExample example = new SysCustomerExample();
		example.or().andCustomerNameEqualTo(customer.getCustomerName());
		List<SysCustomer> customerIds = customerMapper.selectByExample(example);
		int customerId = customerIds.get(0).getCustomerId();
		int uid = 0;
		
		// 为员工创建用户
		if (!StringUtils.isEmpty(customer.getCustomerName())) {
			SysUser user = new SysUser();
			user.setUsername(customer.getCustomerName());
			user.setCreateTime(new Date());
			user.setType("员工");
			user.setStatus("启用");
			user.setUserDetailId(customerId);
			userService.insert(user);

			uid = user.getUserId();
		}
		//为员工创建手机对应的系统账号
		if (!StringUtils.isEmpty(customer.getCellPhone())) {
			// if (employee.getEmployeeCellPhone() != null) {
			SysAuthentication info = new SysAuthentication();
			info.setAccount(customer.getCellPhone());
			info.setUserId(uid);
			info.setType("员工");
			info.setStatus("启用");
			int length = customer.getCellPhone().length();
			info.setPassword(customer.getCellPhone().substring(
					length - 6, length));
			
			authenticationService.insert(info);
		}
		return false;
	}

	@Override
	public boolean delete(String ids) {
		SysCustomerExample example = new SysCustomerExample();
		example.or().andCustomerIdIn(CommonUtils.idsArrayToList(ids));
		customerMapper.deleteByExample(example);
		List<String> types = new ArrayList<String>();
		types.add("员工");

		userService.deleteByUserDetailId(CommonUtils.idsArrayToList(ids), types);
		return true;
	}

	@Override
	public boolean update(SysCustomer customer) {
	
	 this.updateUsersAndAuthentication(customer);
	 int i = customerMapper.updateByPrimaryKeySelective(customer);
		this.init_CUSTOMERMAP();
		return true;
	}

	private void updateUsersAndAuthentication(SysCustomer customer) {
		SysCustomer oldCustomer = customerMapper.selectByPrimaryKey(customer.getCustomerId());

		//员工姓名修改时，修改员工对应的用户名称
		if (!StringUtils.isEmpty(oldCustomer.getCustomerName()) && !oldCustomer.getCustomerName().equals(customer.getCustomerName())) {

			userService.setUsernameByEmployeeId(customer.getCustomerName(),customer.getCustomerId());

		}

		//手机号发生变化时，修改用户的登陆账号
		if (!StringUtils.isEmpty(oldCustomer.getCellPhone()) && !oldCustomer.getCellPhone().equals(
				customer.getCellPhone())) {
	

			authenticationService.updateAccount(customer.getCellPhone(), oldCustomer.getCellPhone());
		}
	}

	@Override
	public void init_CUSTOMERMAP() {
		SysConst.CUSTOMER_MAP = new HashMap<Integer, String>();

		List<SysCustomer> customerList = customerMapper.selectByExample(null);

		for (SysCustomer u : customerList) {

			SysConst.CUSTOMER_MAP.put(u.getCustomerId(), u.getCustomerName());
		}
		
	}

	@Override
	public Map<String, Object> getCustomerVO(String keys, Integer pageSize,
			Integer pageIndex) {
		SysCustomerExample example = new SysCustomerExample();
//		Criteria criteria = example.createCriteria();
		
		this.setExample(example, keys);


		int totalrecords = customerMapper.countByExample(example);
		/**
		 * for my sql db
		 */
		Page page = new Page();

		page.setBegin(pageIndex);// (-1)*pageSize
		page.setLength(pageSize);

		example.setPage(page);

		List<SysCustomer> list = customerMapper.selectByExample(example);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("recordsTotal", totalrecords);// total
			map.put("recordsFiltered", totalrecords);// total

			map.put("aaData", list);
			return map;
	}

	private void setExample(SysCustomerExample example, String keys) {
		JSONObject jKeys = JSONObject.fromObject(keys);

		Criteria criteria = example.createCriteria();
		

		if (keys == null || "{}".equals(keys))
			return;

		SysCustomer customer = (SysCustomer) JSONObject.toBean(jKeys, SysCustomer.class);

		if (customer == null)
			return;
		
		
		// 获得news model非空（null）属性list
		List<Map<?, ?>> list = ReflectionUtil.getFiledValues(customer, false);
		
		if(list == null || list.size() ==0)
			return;

		CriteriaUtils.setCriteria(criteria, list);
		
	}

	@Override
	public SysCustomer getCustomerById(Integer customerId) {
		// TODO Auto-generated method stub
		return customerMapper.selectByPrimaryKey(customerId);
	}

	@Override
	public boolean insert(SysCustomerVO customerVO) {
		dataAuthorizeService.addDataAuthorizeInfo(customerVO, "insert");
		SysCustomer customer = new SysCustomer();
		try {
			BeanUtils.copyProperties(customer, customerVO);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customerMapper.insertSelective(customer);
		
		this.init_CUSTOMERMAP();
		SysCustomerExample example = new SysCustomerExample();
		example.or().andCustomerNameEqualTo(customer.getCustomerName());
		List<SysCustomer> customerIds = customerMapper.selectByExample(example);
		int customerId = customerIds.get(0).getCustomerId();
		int uid = 0;
		
		// 为员工创建用户
		if (!StringUtils.isEmpty(customer.getCustomerName())) {
			SysUser user = new SysUser();
			user.setUsername(customer.getCustomerName());
			user.setCreateTime(new Date());
			user.setType("员工");
			user.setStatus("启用");
			user.setUserDetailId(customerId);
			userService.insert(user);

			uid = user.getUserId();
		}
		//为员工创建手机对应的系统账号
		if (!StringUtils.isEmpty(customer.getCellPhone())) {
			// if (employee.getEmployeeCellPhone() != null) {
			SysAuthentication info = new SysAuthentication();
			info.setAccount(customer.getCellPhone());
			info.setUserId(uid);
			info.setType("员工");
			info.setStatus("启用");
			int length = customer.getCellPhone().length();
			info.setPassword(customerVO.getPassword());
			
			authenticationService.insert(info);
		}
		userRoleService.insert(Integer.toString(uid),"31");
		return false;
		
	}

	

}
