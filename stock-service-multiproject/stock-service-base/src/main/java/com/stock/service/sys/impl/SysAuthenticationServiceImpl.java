/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys.impl
 * 类/接口名	: SysAuthenticationInfoServiceImpl
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

import com.stock.common.util.MD5Utils;
import com.stock.dao.mapper.sys.SysAuthenticationMapper;
import com.stock.dao.model.sys.SysAuthentication;
import com.stock.dao.model.sys.SysAuthenticationExample;
import com.stock.dao.model.sys.SysAuthenticationExample.Criteria;
import com.stock.pojo.vo.sys.SysAuthenticationVO;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.common.SmsService;
import com.stock.service.common.util.CommonUtils;
import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.sys.SysAuthenticationService;




/**
 * 
 * ClassName	: SysAuthenticationInfoServiceImpl
 * Function		: TODO
 * date 		: 2017年5月7日下午6:01:19
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
@Service("authenticationInfoService")
public class SysAuthenticationServiceImpl implements
		SysAuthenticationService {

	@Autowired 
	private SysAuthenticationMapper authenticationMapper;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private DataAuthorizeService dataAuthorizeService;
	


	public SysAuthenticationServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean insert(SysAuthentication authInfo) {
		// TODO Auto-generated method stub
		authInfo.setPassword(new MD5Utils().getMD5(authInfo.getPassword()));
		dataAuthorizeService.addDataAuthorizeInfo(authInfo, "insert");
		
		return authenticationMapper.insertSelective(authInfo) == 1 ? true
				: false;
	}
	@Override
	public boolean delete(String ids) {
		// TODO Auto-generated method stub
		SysAuthenticationExample example = new SysAuthenticationExample();

		example.or().andAuthenticationIdIn(CommonUtils.idsArrayToList(ids));

		try {

			authenticationMapper.deleteByExample(example);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	@Override
	public boolean update(SysAuthentication authInfo) {
		// TODO Auto-generated method stub

			dataAuthorizeService.addDataAuthorizeInfo(authInfo, "update");
			
			return authenticationMapper.updateByPrimaryKeySelective(authInfo) == 1 ? true
					: false;
	}
	
	/**
	 * 修改用户的账号，当员工修改手机号
	 * @param newAccount
	 * @param oldAccount
	 * @return
	 * @see com.stock.service.sys.SysAuthenticationService#updateAccount(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateAccount(String newAccount,String oldAccount) {
		// TODO Auto-generated method stub

		SysAuthentication authenInfo = new SysAuthentication();

		authenInfo.setAccount(newAccount);
		

		SysAuthenticationExample example = new SysAuthenticationExample();

		example.or().andAccountEqualTo(oldAccount);
		

		dataAuthorizeService.addDataAuthorizeInfo(authenInfo, "update");
		

		return authenticationMapper.updateByExampleSelective(authenInfo,
				example) == 1 ? true: false;

	}

	@Override
	public List<SysAuthentication> getAuthenticationInfo(String uId) {

		if (uId == null || "".equals(uId))
			return null;

		SysAuthenticationExample example = new SysAuthenticationExample();

		example.or().andUserIdEqualTo(Integer.parseInt(uId));// andAccountEqualTo(account);//

		// TODO Auto-generated method stub
		return authenticationMapper.selectByExample(example);
	}
	
	
	@Override
	public Map<String,Object> getAuthenticationMap(Integer uId) {
		
		 Map<String,Object> authenticationMap = new HashMap<String,Object>();

		if (uId == null || "".equals(uId))
			return null;

		SysAuthenticationExample example = new SysAuthenticationExample();

		example.or().andUserIdEqualTo(uId);// andAccountEqualTo(account);//
		
		
		int totalSize  =   authenticationMapper.countByExample(example);
		
		List<SysAuthentication>  authenticationList = authenticationMapper.selectByExample(example);

		authenticationMap.put("recordsFiltered", totalSize);
		
		authenticationMap.put("recordsTotal", totalSize);
		
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		
		authenticationMap.put("aaData", JSONArray.fromObject(this.convert(authenticationList),config));
		
		// TODO Auto-generated method stub
		return authenticationMap;
	}
	
	
	private List<SysAuthenticationVO> convert(List<SysAuthentication> aList) {

		if (aList == null)
			return null;

		List<SysAuthenticationVO> aVOList = new ArrayList<SysAuthenticationVO>();

		for (SysAuthentication a : aList) {
			SysAuthenticationVO aVO = new SysAuthenticationVO();
			// ReflectionUtils.parentToChild(e, he);

			BeanUtils.copyProperties(a, aVO);
		
			//todo handle
			aVO.setAmenderName("admin");

//		
			aVOList.add(aVO);
		
			
		}

		return aVOList;

	}

	@Override
	public boolean deleteByUserId(Integer uId) {
		// TODO Auto-generated method stub
		SysAuthenticationExample example = new SysAuthenticationExample();

		example.or().andUserIdEqualTo(uId);

		try {

			authenticationMapper.deleteByExample(example);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public String resetPassword(String account) {

		SysAuthenticationExample example = new SysAuthenticationExample();

		example.or().andAccountEqualTo(account);
		
	
		String randomPassword = CommonUtils.getRandomPassword(6); 
		
		
		SysAuthentication authInfo = new SysAuthentication();
	
		
		authInfo.setPassword(new MD5Utils().getMD5(randomPassword));
		
		if( authenticationMapper.updateByExampleSelective(authInfo, example) >0){
			
			//todo
			JSONObject jo = smsService.sendSMS(account,randomPassword + "为您重置后的密码，请登陆后及时修改！");
			
			if(!"短信发送成功".equals(jo.getString("result")))
				return "密码重置失败，"+jo.getString("result")+"！";
			
			return "新密码已发您手机！";
		}
		else
		
			return "密码重置失败，数据库访问异常！";

	}
	
	@Override
	public List<SysAuthentication> getAuthenticationInfo(String account,
			String password,String type) {

		if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password))
			return null;
		
		SysAuthenticationExample example = new SysAuthenticationExample();
		
		Criteria criteria = example.createCriteria();
		

		criteria.andAccountEqualTo(account);
		criteria.andPasswordEqualTo(password);
		criteria.andTypeEqualTo(type);

		// TODO Auto-generated method stub
		return authenticationMapper.selectByExample(example);
		
	}

	@Override
	public List<SysAuthentication> getAuthenticationInfo(String account,
			String password) {

		if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password))
			return null;
		
		SysAuthenticationExample example = new SysAuthenticationExample();
		
		Criteria criteria = example.createCriteria();
		

		criteria.andAccountEqualTo(account);
		criteria.andPasswordEqualTo(password);

		// TODO Auto-generated method stub
		return authenticationMapper.selectByExample(example);
		
	}

	@Override
	public boolean isExistAccount(String account) {
		// TODO Auto-generated method stub
		
		SysAuthenticationExample example = new SysAuthenticationExample();
		example.or().andAccountEqualTo(account);
		
		return authenticationMapper.selectByExample(example).size() == 0?false:true;
	}

	@Override
	public String updatePassword(String account, String password,
			String newPassword) {
		// TODO Auto-generated method stub
		
		if(StringUtils.isEmpty(account))
			return "修改失败，帐号不能为空！";
		System.out.println(".....................................");
		
//		password = new MD5Utils().getMD5(password);
		newPassword  = new MD5Utils().getMD5(newPassword);
				
		List<SysAuthentication>  authInfoList = this.getAuthenticationInfo(account, password);
		
		if(authInfoList == null || authInfoList.size() == 0)
			return "修改失败，原密码输入不正确!";
		
		int i = 0;
		for(SysAuthentication authInfo:authInfoList){
			
			authInfo.setPassword(newPassword);
			i += authenticationMapper.updateByPrimaryKeySelective(authInfo);
			
		}
//		return i > 0 ? "success"
		return i > 0 ?"密码修改成功！":"密码修改失败，数据库访问异常！";
	}

	@Override
	public SysAuthentication getAuthenticationByAccount(String account) {
		
		if(StringUtils.isEmpty(account) )
			return null;
		
		SysAuthenticationExample example = new SysAuthenticationExample();
		
		Criteria criteria = example.createCriteria();
		

		criteria.andAccountEqualTo(account);
		
		List<SysAuthentication> authList = authenticationMapper.selectByExample(example);
		
		if(authList.size() > 0)
			return authList.get(0);

		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String lockAccount(String account) {
		// TODO Auto-generated method stub
		SysAuthentication userInfo = getAuthenticationByAccount(account);
//		System.out.println(userInfo.toString());
		userInfo.setStatus("禁用");
		update(userInfo);
		return "success";
	}
	
	@Override
	public String alterWrongTimes(String account) {
		SysAuthentication userInfo = getAuthenticationByAccount(account);
		if(userInfo.getRecordVersion() < 5 && userInfo.getStatus().equals("启用")){
			userInfo.setRecordVersion(userInfo.getRecordVersion()+1);
			update(userInfo);
			return userInfo.getRecordVersion().toString();
		}else{
			userInfo.setStatus("禁用");
			userInfo.setRecordVersion(0);
			update(userInfo);
			return "lock";
		}
	}

	@Override
	public String checkStatus(String account) {
		// TODO Auto-generated method stub
		SysAuthentication userInfo = getAuthenticationByAccount(account);
		if(userInfo == null)
			return "用户不存在";
		return userInfo.getStatus();
	}

}
