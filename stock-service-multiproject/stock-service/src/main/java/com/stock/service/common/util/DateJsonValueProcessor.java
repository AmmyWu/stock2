/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.utils
 * 类/接口名	: DateJsonValueProcessor
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午6:08:06
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午6:08:06
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * 
 * ClassName	: DateJsonValueProcessor
 * Function		: TODO
 * date 		: 2017年5月7日下午6:10:42
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
public class DateJsonValueProcessor implements JsonValueProcessor {
	private String format = "yyyy-MM-dd";

	public DateJsonValueProcessor() {
	}

	public DateJsonValueProcessor(String format) {
		this.format = format;
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if(value!=null)
		{
			if (value instanceof Date[]) {
				SimpleDateFormat sf = new SimpleDateFormat(format);
				Date[] dates = (Date[]) value;
				obj = new String[dates.length];
				for (int i = 0; i < dates.length; i++) {
					obj[i] = sf.format(dates[i]);
				}
			}
		}
		else
			return "";
		return obj;
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		if(value!=null)
		{
			if (value instanceof Date) {
				String str = new SimpleDateFormat(format).format((Date) value);
				return str;
			}
		}
		else
			return "";
		return value;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}

