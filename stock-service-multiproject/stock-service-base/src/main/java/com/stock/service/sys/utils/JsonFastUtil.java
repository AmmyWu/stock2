package com.stock.service.sys.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;




public class JsonFastUtil {
	
	
    private static final SerializerFeature[] features = {
    		SerializerFeature.DisableCircularReferenceDetect,
    		SerializerFeature.WriteMapNullValue, // 输出空置字段  
            SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null  
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null  
            
//            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null  
//            SerializerFeature.WriteNullBooleanAsFalse // Boolean字段如果为null，输出为false，而不是null  
    };  
	
	/**
	 * 把String类型null变成 ""
	 * @author hkd
	 * @param object
	 * @param dateformat
	 * @return 
	 * @since JDK 1.7
	 */
	public static String buildJsonObject(Object object,String dateformat){
		return JSONObject.toJSONStringWithDateFormat(object,dateformat, features);
	}
	
	/**
	 * 
	 * Number的子类 如果是null 则返回0
	 * @author hkd
	 * @param object
	 * @param dateformat
	 * @return 
	 * @since JDK 1.7
	 */
	public static String buildJsonObjectNumberZero(Object object, String dateformat) {
		return JSONObject.toJSONStringWithDateFormat(object, dateformat,
				SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, // 输出空置字段
				SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
				SerializerFeature.WriteNullListAsEmpty, //list字段如果为null，输出为[]，而不是null  
				SerializerFeature.WriteNullNumberAsZero // 数值字段如果为null，输出为0，而不是null
		);
	}
	
	/**
	 * 所有null的 变成 "",包括Number的子类
	 * @author hkd
	 * @param object
	 * @param dateformat
	 * @return 
	 * @since JDK 1.7
	 */
	/*public static String buildJsonObjectNotNull(Object object,String dateformat){
		JSONObject.DEFFAULT_DATE_FORMAT=dateformat;//设置日期格式
		return JSONObject.toJSONString(object,new ValueFilter() {
			
			@Override
			public Object process(Object object, String name, Object value) {
				if (value == null)
					return "";
				return value;
			}
		},SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue);
	}*/
	/**
	 * 所有null的 变成 "",包括Number的子类
	 * @author hkd
	 * @param object
	 * @param dateformat
	 * @return 
	 * @since JDK 1.7
	 */
	public static String buildJsonObjectNotNull(Object object,String dateformat){
		String s = JSONObject.toJSONStringWithDateFormat(object, dateformat, features);
		Object o = null;
		if (object instanceof Collection) {
			o = JSON.parseArray(s);
		} else {
			o = JSON.parseObject(s);
		}
		return  JSONObject.toJSONString(o,new ValueFilter() {
			
			@Override
			public Object process(Object object, String name, Object value) {
				if (value == null)
					return "";
				return value;
			}
		},features);
	}
	
	/**
	 * 
	 * @author hkd
	 * @param object
	 * @param dateformat
	 * @return 
	 * @since JDK 1.7
	 */
	public static String buildJson(Object object,String dateformat){
		return JSONObject.toJSONStringWithDateFormat(object,dateformat, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 解决部分日期 yyyy-MM-dd HH:mm 反序列化报错
	 * @author hkd
	 * @param json
	 * @param clazz
	 * @return 
	 * @since JDK 1.7
	 */
	public static <T> T parseObject(String json, Class<T> clazz) {
		ParserConfig config = new ParserConfig();
		config.putDeserializer(Date.class, new MyDateFormatDeserializer()); // 我们自己实现的Deserializer
        return JSON.parseObject(json,clazz, config, JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
	}
	
	/**
	 * 解决部分日期 yyyy-MM-dd HH:mm 反序列化报错
	 * @author hkd
	 * @param json
	 * @param clazz
	 * @return 
	 * @since JDK 1.7
	 */
	public static <T> List<T> parseArray(String json, Class<T> clazz) {
		List<T>  result = new ArrayList<>();
		JSONArray parseArray = JSON.parseArray(json);
		if (!parseArray.isEmpty()) {
			for (int i = 0; i < parseArray.size(); i++) {
				result.add(parseObject(parseArray.getString(i), clazz));
			}
		}
		return result;
	}
}
