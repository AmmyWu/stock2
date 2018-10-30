package com.stock.service.sys.utils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.DateFormatDeserializer;

/**
 * ClassName : a Function : TODO date : 2018年4月10日上午10:47:34
 * 
 * @author : hkd
 * @version : 1.0
 * @since JDK 1.7
 */

public class MyDateFormatDeserializer extends DateFormatDeserializer {
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof Calendar) {
			return (T) ((Calendar) value).getTime();
		} else if (value instanceof Date) {
			return (T) value;
		} else {
			long longValue = -1L;
			if (value instanceof Number) {
				longValue = ((Number) value).longValue();
				return (T) new Date(longValue);
			} else {
				if (value instanceof String) {
					String strVal = (String) value;
					if (strVal.indexOf(45) != -1) {
						String format;
						if (strVal.length() == 10) {
							format = "yyyy-MM-dd";
						} else if (strVal.length() == "yyyy-MM-dd HH:mm".length()) {
							format = "yyyy-MM-dd HH:mm";
						} else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
							format = "yyyy-MM-dd HH:mm:ss";
						} else {
							format = "yyyy-MM-dd HH:mm:ss.SSS";
						}

						SimpleDateFormat dateFormat = new SimpleDateFormat(format);

						try {
							return (T) dateFormat.parse(strVal);
						} catch (ParseException var7) {
							throw new JSONException("can not cast to Date, value : " + strVal);
						}
					}

					if (strVal.length() == 0) {
						return null;
					}

					longValue = Long.parseLong(strVal);
				}

				if (longValue < 0L) {
					throw new JSONException("can not cast to Date, value : " + value);
				} else {
					return (T) new Date(longValue);
				}
			}
		}
	}
}
