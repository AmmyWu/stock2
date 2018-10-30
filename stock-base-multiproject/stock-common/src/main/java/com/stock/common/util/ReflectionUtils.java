package com.stock.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtils {


	/**
	 * 根据对象方法名称，设置对象的属性值
	 * @param o 
	 * @param method ,is method name
	 * @param type ,is parameter type name,ex:class java.lang.Integer 
	 * @param value ,is parameter value
	 */

	public static void setObjectValues(Object o, String method, String type,Object value) {
			
			try {
				
				Class<?> ParameterType  = Class.forName(type.split(" ")[1]);
				Method m = o.getClass().getMethod(method,ParameterType);
				m.invoke(o,value);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
	 * 
	 */

	public static List<Map<?,?>> getFiledValues(Object o, boolean isContainNullValue) {
		List<Map<?,?>> list = new ArrayList<Map<?,?>>();

		Class<?> clazz = o.getClass();
		Field[] fields = clazz.getDeclaredFields();

		setMapByFields(o, list, fields, isContainNullValue);

		// 获取父类属性信息
		clazz = clazz.getSuperclass();
		if (clazz == Object.class)
			return list;

		fields = clazz.getDeclaredFields();

		// 设父类的属性
		setMapByFields(o, list, fields, isContainNullValue);

		return list;

	}

	private static void setMapByFields(Object o,List<Map<?,?>> list,
			Field[] fields, boolean isContainNullValue) {

		Map<String, Object> infoMap = null;
		Object value = null;
		// for (int i = 0; i < fields.length; i++) {
		for (Field field : fields) {
			infoMap = new HashMap<String, Object>();
			
			value = getFieldValueByName(field.getName(), o);
			
			
				
			if (!isContainNullValue && value == null)
				continue;

			//如果为字符串型，并且为空字符串
			if(value instanceof String && "".equals(value))
				continue;
				
				
			infoMap.put("type", field.getType().toString());

			infoMap.put("name", field.getName());

			infoMap.put("value", value);
			list.add(infoMap);
		}

	}

	/**
	 * 根据属性名获取属性值
	 * */
	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});

			return value;
		} catch (Exception e) {

			return null;
		}
	}

	/*
	 * 将父类所有的属性COPY到子类中。 类定义中child一定要extends father；
	 * 而且child和father一定为严格javabean写法，属性为deleteDate，方法为getDeleteDate
	 */
	public static void parentToChild(Object father, Object child) {
		if (!(child.getClass().getSuperclass() == father.getClass())) {
			System.err.println("child不是father的子类");
		}
		Class fatherClass = father.getClass();
		Field ff[] = fatherClass.getDeclaredFields();
		for (int i = 0; i < ff.length; i++) {
			Field f = ff[i];// 取出每一个属性，如deleteDate
			Class type = f.getType();
			try {
				Method m = null;
				if(type.equals(boolean.class))
					m = fatherClass.getMethod("is"+ CommonUtils.upperHeadChar(f.getName()));
				else
					m = fatherClass.getMethod("get"
						+ CommonUtils.upperHeadChar(f.getName()));// 方法getDeleteDate
				Object obj = m.invoke(father);// 取出属性值
				f.setAccessible(true);// 抑制Java对修饰符的检查
				f.set(child, obj);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



}
