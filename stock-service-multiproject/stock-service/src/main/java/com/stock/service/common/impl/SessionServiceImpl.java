/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.impl
 * 类/接口名	: SessionServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:32:17
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:32:17
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.common.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.util.DataAuthorityRegister;
import com.stock.pojo.vo.sys.SysUserVO;
import com.stock.service.common.SessionService;


/**
 * 
 * ClassName	: SessionServiceImpl
 * Function		: TODO
 * date 		: 2017年5月7日下午5:38:26
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService {

	@Autowired
	HttpSession session;

	@Override
	public Integer getUserDetailId() {
		// TODO Auto-generated method stub

		SysUserVO user = this.getSessionHttpUser();

		if (user == null || user.getUserDetailId() == null)
			return -1;

		return user.getUserDetailId();
	}

	@Override
	public List<String> getDataAccessGroup() {
		// TODO Auto-generated method stub

		DataAuthorityRegister dataAuthorityRegister = session
				.getAttribute("dataAuthorityRegister") == null ? null
				: (DataAuthorityRegister) session
						.getAttribute("dataAuthorityRegister");

		if (dataAuthorityRegister == null)
			return null;

		Thread currentTh = Thread.currentThread();
		if (dataAuthorityRegister.isDataUnderControl(currentTh.getId())) {

		}
		Set<Integer> orgSet = dataAuthorityRegister.getOrgList(currentTh
				.getId());

		List<String> orgList = new ArrayList<String>();

		for (Integer org : orgSet) {
			orgList.add(org.toString());
		}

		return orgList;
	}

	@Override
	public synchronized SysUserVO getSessionHttpUser() {
		// TODO Auto-generated method stub

		return session.getAttribute("loginingUser") == null ? null
				: (SysUserVO) session.getAttribute("loginingUser");

	}



	@Override
	public void invalidateSession() {
		// TODO Auto-generated method stub
		session.invalidate();
	}

}
