/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys.impl
 * 类/接口名	: SysSettingServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:50:19
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:50:19
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.mapper.sys.SysSettingMapper;
import com.stock.dao.model.sys.SysSetting;
import com.stock.dao.model.sys.SysSettingExample;
import com.stock.service.sys.SysSettingService;
import com.stock.service.sys.utils.SysConst;
/**
 * 
 * ClassName	: SysSettingServiceImpl
 * Function		: TODO
 * date 		: 2017年5月7日下午6:04:47
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
@Service("settingService")
public class SysSettingServiceImpl implements SysSettingService {

	@Autowired
	private SysSettingMapper systemSettingMapper;

	@Override
	public List<SysSetting> getSystemSetting() {

		SysSettingExample example = new SysSettingExample();

		return systemSettingMapper.selectByExample(example);

	}

	@Override
	public boolean insert(SysSetting st) {

		if (systemSettingMapper.insertSelective(st) != 1)
			return false;

		// this.init_RESOURCESTREE_UNINCLUDE_BUSINESS();

		return true;
	}

	@Override
	public boolean update(SysSetting st) {

		if (systemSettingMapper.updateByPrimaryKey(st) != 1)
			return false;

		this.init_SYSTEMSETTING();
		
//		Const.initConstFor_QUERY("init_SYSTEMSETTING");

		return true;
	}

	@Override
	public void init_SYSTEMSETTING() {
		// TODO Auto-generated method stub

		SysConst.SYSTEMSETTING = new HashMap<Integer, String>();

		List<SysSetting> ssList = this.getSystemSetting();

		for (SysSetting ss : ssList) {

			SysConst.SYSTEMSETTING.put(ss.getStKey(), ss.getStValue());
		}


	}

	@Override
	public boolean insert(String jsonSt) {
		// TODO Auto-generated method stub
		if (jsonSt == null || "".equals(jsonSt))
			return false;

		JSONObject jSt = JSONObject.fromObject(jsonSt);
		SysSetting st  ;
		
		Iterator it = jSt.keys();
		while(it.hasNext()){
			
			String stKey = (String)it.next();
			
			st= new SysSetting();
			
			st.setStKey(Integer.parseInt(stKey));
			st.setStValue(jSt.getString(stKey));
			
			if(!this.update(st))
				return false;
		}


		// 注销时间转换器
		// JSONUtils.getMorpherRegistry().deregisterMorpher(new
		// DateMorpherEx(new String[] { "yyyy-MM-dd" },(Date)null));
	

		return true;
	}

}
