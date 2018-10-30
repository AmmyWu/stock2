/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys.impl
 * 类/接口名	: SysUsersServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:50:19
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:50:19
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stock.common.util.ReflectionUtil;
import com.stock.common.util.convert.DateConvert;
import com.stock.dao.mapper.sys.SysUserMapper;
import com.stock.dao.model.sys.SysUser;
import com.stock.dao.model.sys.SysUserExample;
import com.stock.dao.model.sys.SysUserExample.Criteria;
import com.stock.dao.page.Page;
import com.stock.pojo.vo.sys.SysUserVO;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.common.util.CommonUtils;
import com.stock.service.common.util.CriteriaUtils;
import com.stock.service.sys.SysAuthenticationService;
import com.stock.service.sys.SysUserRoleService;
import com.stock.service.sys.SysUserService;
import com.stock.service.sys.utils.SysConst;

/**
 * 
 * ClassName : SysUsersServiceImpl Function : TODO date : 2017年5月7日下午6:05:45
 * 
 * @author chengxl
 * @version :
 * @since JDK 1.7
 */
@Service("usersService")
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper usersMapper;

	@Autowired
	private SysUserRoleService usersRolesService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SysAuthenticationService authenticationInfoService;
	
	@Autowired
	private  DataAuthorizeService dataAuthorizeService;

	@Override
	public Map<String, Object> getUserVO(String keys,Integer pageSize, Integer pageIndex
			) {

		// 获取包含员工或客户信息的用户对象
		/*
		 * select
		 * id,username,createtime,status,last_login_time,last_login_ip,type
		 * ,user_detail_id, case when type ='员工' then (SELECT employee_name from
		 * employee where employee_id = user_detail_id) when type = '客户'then
		 * (SELECT customer_name from customer where customer_id =
		 * user_detail_id) end user_detail_name from users
		 * 
		 * select u.id
		 * id,username,createtime,status,last_login_time,last_login_ip
		 * ,type,e.employee_id detail_id, e.employee_name name from users u LEFT
		 * JOIN employee e on u.user_detail_id = e.employee_id where type = '员工'
		 * or type is NULL UNION select u.id
		 * id,username,createtime,status,last_login_time
		 * ,last_login_ip,type,c.customer_id detail_id, c.customer_name name
		 * from users u LEFT JOIN customer c on u.user_detail_id = c.customer_id
		 * where type = '客户'
		 */

		SysUserExample example = new SysUserExample();
//		Criteria criteria = example.createCriteria();
		
		this.setExample(example, keys);

//		List<String> typeList = new ArrayList<String>();
//		if (user.getType() == null) {
//			typeList.add("员工");
//			typeList.add("客户");
//
//		} else {
//			String[] types = user.getType().split(",");
//			for (String type : types)
//				typeList.add(type);
//
//		}
//		criteria.andTypeIn(typeList);// criteria.andTypeEqualTo(user.getType());
//
//		if (!StringUtils.isEmpty(user.getUsername())) {
//			criteria.andUsernameLike("%" + user.getUsername() + "%");
//		}

		int totalrecords = usersMapper.countByExample(example);
		/**
		 * for my sql db
		 */
		Page page = new Page();

		page.setBegin(pageIndex);// (-1)*pageSize
		page.setLength(pageSize);

		example.setPage(page);

		List<SysUser> list = usersMapper.selectByExample(example);

		if (list == null || list.size() < 1) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("recordsTotal", totalrecords);// total
			map.put("recordsFiltered", totalrecords);// total

			map.put("aaData", list);
			return map;
		}

		StringBuffer sb = new StringBuffer();
		sb.append('(');
		for (int i = 0; i < list.size() - 1; i++) {
			sb.append(list.get(i).getUserId());
			sb.append(',');
		}
		sb.append(list.get(list.size() - 1).getUserId());
		sb.append(')');

		Connection conn = null;
		Statement stmt = null;

		ResultSet rs = null;

		List<SysUserVO> listHttpUsers = new ArrayList<SysUserVO>();

		try {
			conn = dataSource.getConnection();

			stmt = conn.createStatement();
			System.out.println(sb);
			// 查找用户所对应的员工或客户信息   (type =  '员工' or type is NULL ) and ,u.creator,u.create_time
			String sql = "select  u.user_id ,username,u.amend_time,u.amender,type,user_detail_id,e.employee_name detail_name,status"
					+ " from sys_user u LEFT JOIN sys_employee e on user_detail_id = e.employee_id "
					+ " where  u.user_id in "
					+ sb
					+ " order by user_id desc";
			// if(name == null || "".equals(name))
			// ;
			// else
			// sql =
			// "select * from ("+sql+") t where username like '%"+name+"%' ";

			rs = stmt.executeQuery(sql);

			this.RestuSetToListHttpUsers(conn, rs, listHttpUsers);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				rs.close();
				stmt.close();
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("total", totalrecords);
		// map.put("rows",listHttpUsers);

		map.put("recordsTotal", totalrecords);// total
		map.put("recordsFiltered", totalrecords);// total

		map.put("aaData", listHttpUsers);

		return map;

	}
	
	// 根据 keys的值，设置Criteria
		private void setExample(SysUserExample example, String keys) {
			
			JSONObject jKeys = JSONObject.fromObject(keys);

			Criteria criteria = example.createCriteria();
			

			if (keys == null || "{}".equals(keys))
				return;

			SysUser user = (SysUser) JSONObject.toBean(jKeys, SysUser.class);

			if (user == null)
				return;
			
			
			// 获得news model非空（null）属性list
			List<Map<?, ?>> list = ReflectionUtil.getFiledValues(user, false);
			
			if(list == null || list.size() ==0)
				return;

			CriteriaUtils.setCriteria(criteria, list);
			
		}

	private String getRoleNames(Connection conn, Integer userId)
			throws SQLException {

		// select *
		// from users_roles u,roles r
		// where u.rid = r.id and uid=1

		String roleName = "";
		String sql = "select u.role_id,name from sys_user_role u,sys_role r where u.role_id = r.role_id and user_id="
				+ userId;

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {

			roleName += rs.getString("name") + "  ";
		}

		rs.close();
		stmt.close();

		return roleName;
	}

	private void RestuSetToListHttpUsers(Connection conn, ResultSet rs,
			List<SysUserVO> listHttpUsers) throws SQLException {

		Integer userId = 0;

		while (rs.next()) {

			SysUserVO hu = new SysUserVO();

			userId = rs.getInt("user_id");
			hu.setUserId(userId);
			hu.setUsername(rs.getString("username"));
//			hu.setsCreateTime(DateConvert.dateToStr(DateConvert.strToDate(rs.getString("create_time"), "yyyy-MM-dd HH:mm:ss"),  "yyyy-MM-dd HH:mm:ss"));

			hu.setsAmendTime(DateConvert.dateToStr(DateConvert.strToDate(rs.getString("amend_time"), "yyyy-MM-dd HH:mm:ss"),  "yyyy-MM-dd HH:mm:ss"));

			hu.setAmenderName(SysConst.USER_MAP.get(rs.getInt("amender")));//修改人
			hu.setType(rs.getString("type"));
			hu.setUserDetailId(rs.getInt("user_detail_id"));
			hu.setUserDetailName(rs.getString("detail_name"));

			hu.setStatus(rs.getString("status"));
			hu.setRoleNames(this.getRoleNames(conn, userId));
			
			
			listHttpUsers.add(hu);
		}

	}

	@Override
	public List<SysUser> getUsers() {

		return usersMapper.selectByExample(null);
	}

	public SysUserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isExistUsers(Integer roleId) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean insert(SysUser user) {

		// TODO Auto-generated method stub
		
		//添加授权的信息，包括创建人，访问组，创建时间等
		dataAuthorizeService.addDataAuthorizeInfo(user, "insert");
		usersMapper.insertSelective(user);
		
		this.init_USERMAP();
		
		return false;
	}

	@Override
	@Transactional
	public boolean delete(String ids) {

		SysUserExample example = new SysUserExample();

		example.or().andUserIdIn(CommonUtils.idsArrayToList(ids));

		List<SysUser> userList = usersMapper.selectByExample(example);

		try {

			for (SysUser user : userList) {

				// 将用户定义的角色删除 ,将分配该用户的认证信息删除
				if (!usersRolesService.delete(user.getUserId().toString(), "")
						|| !authenticationInfoService.deleteByUserId(user
								.getUserId()))
					return false;

			}
			usersMapper.deleteByExample(example);

		} catch (Exception e) {
			e.printStackTrace();

			return false;

		}

		return true;
	}

	@Override
	public boolean update(SysUser user) {
		// TODO Auto-generatedS stub
		int i = usersMapper.updateByPrimaryKeySelective(user);
		
		this.init_USERMAP();
		return true;
	}

	
	/**
	 * 根据员工信息修改员工信息
	 * @param user
	 * @param employee
	 * @return
	 * @see com.stock.service.sys.SysUserService#updateByEmployee(com.stock.dao.model.sys.SysUser, com.stock.dao.model.sys.SysEmployee)
	 */
	@Override
	public boolean setUsernameByEmployeeId(String username,Integer employeeId) {


		SysUser user = new SysUser();

		user.setUsername(username);

		SysUserExample example = new SysUserExample();

		example.or().andUserDetailIdEqualTo(employeeId);
		
dataAuthorizeService.addDataAuthorizeInfo(user, "update");
		

		return usersMapper.updateByExampleSelective(user,
				example) == 1 ? true: false;
	}
	
	@Override
	public SysUser getUsers(Integer userId) {
		// TODO Auto-generated method stub
		return usersMapper.selectByPrimaryKey(userId);
	}

	//用户删除数，删除其对应的账号信息
	@Override
	public boolean deleteByUserDetailId(List<Integer> userDetailIds,
			List<String> types) {
		// TODO Auto-generated method stub
		SysUserExample example = new SysUserExample();

		example.or().andUserDetailIdIn(userDetailIds).andTypeIn(types);
		
		
		List<SysUser> userList = usersMapper.selectByExample(example);
		
		//依次删除用户对应的账号信息
		for(SysUser user:userList){
			
			authenticationInfoService.deleteByUserId(user.getUserId());
			
		}
		
		
		return usersMapper.deleteByExample(example) > 0 ? true : false;
	}
	
	@Override
	public void init_USERMAP() {
		// TODO Auto-generated method stub

		SysConst.USER_MAP = new HashMap<Integer, String>();

		List<SysUser> userList = usersMapper.selectByExample(null);

		for (SysUser u : userList) {

			SysConst.USER_MAP.put(u.getUserId(), u.getUsername());
		}

	}

	/**
	 * 根据type返回用户列表，type为员工或客户
	 * @param type
	 * @return
	 * @see com.stock.service.sys.SysUserService#getUsers(java.lang.String)
	 */
	@Override
	public List<SysUser> getUsers(String type) {
		// TODO Auto-generated method stub
		
		SysUserExample example = new SysUserExample();
		
		

		example.or().andTypeEqualTo(type);
		
		return usersMapper.selectByExample(example);
	}
	

	/**
	 * 根据type返回用户对应的员工或客户的id list
	 * @param type
	 * @return
	 * @see com.stock.service.sys.SysUserService#getUsers(java.lang.String)
	 */
	@Override
	public List<Integer> getUserDetailIdList(String type) {


		List<SysUser> userList  = this.getUsers(type);
		
		List<Integer> userDetailIdList =  new ArrayList<Integer>();
		
		for(SysUser user:userList){
			
			if(user.getUserDetailId() == null)
				continue;
			
			userDetailIdList.add(user.getUserDetailId());
			
		}
		
		return userDetailIdList;
	}

}
