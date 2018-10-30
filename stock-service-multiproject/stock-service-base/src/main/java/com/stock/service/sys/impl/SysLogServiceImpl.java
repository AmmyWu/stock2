/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys.impl
 * 类/接口名	: SysLogServiceImpl
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
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.common.util.convert.DateConvert;
import com.stock.dao.mapper.sys.SysLogMapper;
import com.stock.dao.model.sys.SysLog;
import com.stock.dao.model.sys.SysLogExample;
import com.stock.dao.model.sys.SysLogExample.Criteria;
import com.stock.dao.page.Page;
import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.sys.SysLogService;

/**
 * 
 * ClassName : SysLogServiceImpl Function : TODO date : 2017年5月7日下午6:02:39
 * 
 * @author chengxl
 * @version :
 * @since JDK 1.7
 */

@Service("logService")
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogMapper logMapper;

	@Override
	public List<SysLog> getLogs(String keys) {
		// TODO Auto-generated method stub

		SysLogExample example = new SysLogExample();

		JSONObject jKeys = JSONObject.fromObject(keys);

		Criteria criteria = example.createCriteria();

		// 设置截关日期
		if (jKeys.containsKey("startDate"))
			criteria.andLogTimeGreaterThanOrEqualTo(DateConvert.strToDate(
					jKeys.getString("startDate"), "yyyy-MM-dd"));

		if (jKeys.containsKey("endDate"))
			criteria.andLogTimeLessThanOrEqualTo(DateConvert.strToDate(
					jKeys.getString("endDate"), "yyyy-MM-dd"));

		return logMapper.selectByExample(example);
	}

	@Override
	public Map<String, Object> getLogsByPage(String keys, Integer pageSize,
			Integer pageIndex) {
		// TODO Auto-generated method stub

		JSONObject jKeys = JSONObject.fromObject(keys);

		SysLogExample example = new SysLogExample();

		Criteria criteria = example.createCriteria();

		if (jKeys.containsKey("logUserId")
				&& !"".equals(jKeys.getString("logUserId")))
			criteria.andLogUserIdEqualTo(jKeys.getInt("logUserId"));

		if (jKeys.containsKey("type") && !"".equals(jKeys.getString("type")))
			criteria.andLogTypeEqualTo(jKeys.getString("type"));
		// criteria.andLogUserIdEqualTo(123);

		// 设置截关日期
		if (jKeys.containsKey("startDate")
				&& !"".equals(jKeys.getString("startDate")))
			criteria.andLogTimeGreaterThanOrEqualTo(DateConvert.strToDate(
					jKeys.getString("startDate"), "yyyy-MM-dd"));

		if (jKeys.containsKey("endDate")
				&& !"".equals(jKeys.getString("endDate")))
			criteria.andLogTimeLessThanOrEqualTo(DateConvert.strToDate(
					jKeys.getString("endDate"), "yyyy-MM-dd"));

		int totalrecords = logMapper.countByExample(example);

		/**
		 * for my sql db
		 */
		Page page = new Page();

		// page.setBegin((pageIndex-1)*pageSize);
		// page.setLength(pageSize);

		page.setBegin(pageIndex);// (-1)*pageSize
		page.setLength(pageSize);

		example.setPage(page);

		example.setOrderByClause(" create_time desc");

		List<SysLog> listLog = logMapper.selectByExample(example);

		// 处理json数据中的日期格式
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:ss:mm"));

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("recordsTotal", totalrecords);// total
		map.put("recordsFiltered", totalrecords);// total

		map.put("aaData", JSONArray.fromObject(listLog, config));

		return map;
	};
}
