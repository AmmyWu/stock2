/**************************************************************************
* 项目名称：数联软件 web开发框架                          
***************************************************************************/
package com.stock.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 反射基础工具类.
 * @author <a href="mailto:taofeng@zjport.gov.cn">taofeng</a>
 * @version $Id$
 * @since 2.0
 */
public class ReflectionUtil {
	
	/** 常量8 */
	final private static int EIGHT = 8;
	
	/** 原始类型 */
	final private static Set<Class<?>> PRIMITIVE_TYPES;
	
	/** 原始类型类 */
	final private static Set<Class<?>> PRIMITIVE_WRAPPER_TYPES;
	
	/** 原始类型数组 */
	final private static Set<Class<?>> PRIMITIVE_ARRAY_TYPES;
	
	/** 原始类型类数组 */
	final private static Set<Class<?>> PRIMITIVE_WRAPPER_ARRAY_TYPES;

	/**
	 * 静态初始化.
	 */
	static {
		/** 原始类型 */
		PRIMITIVE_TYPES = new HashSet<Class<?>>(EIGHT);
		PRIMITIVE_TYPES.add(Boolean.TYPE);
		PRIMITIVE_TYPES.add(Character.TYPE);
		PRIMITIVE_TYPES.add(Byte.TYPE);
		PRIMITIVE_TYPES.add(Short.TYPE);
		PRIMITIVE_TYPES.add(Integer.TYPE);
		PRIMITIVE_TYPES.add(Long.TYPE);
		PRIMITIVE_TYPES.add(Float.TYPE);
		PRIMITIVE_TYPES.add(Double.TYPE);
		PRIMITIVE_TYPES.add(Void.TYPE);

		/** 原始类型类 */
		PRIMITIVE_WRAPPER_TYPES = new HashSet<Class<?>>(EIGHT);
		PRIMITIVE_WRAPPER_TYPES.add(Boolean.class);
		PRIMITIVE_WRAPPER_TYPES.add(Character.class);
		PRIMITIVE_WRAPPER_TYPES.add(Byte.class);
		PRIMITIVE_WRAPPER_TYPES.add(Short.class);
		PRIMITIVE_WRAPPER_TYPES.add(Integer.class);
		PRIMITIVE_WRAPPER_TYPES.add(Long.class);
		PRIMITIVE_WRAPPER_TYPES.add(Float.class);
		PRIMITIVE_WRAPPER_TYPES.add(Double.class);
		PRIMITIVE_WRAPPER_TYPES.add(Void.class);

		/** 原始类型数组 */
		PRIMITIVE_ARRAY_TYPES = new HashSet<Class<?>>(EIGHT);
		PRIMITIVE_ARRAY_TYPES.add(boolean[].class);
		PRIMITIVE_ARRAY_TYPES.add(char[].class);
		PRIMITIVE_ARRAY_TYPES.add(byte[].class);
		PRIMITIVE_ARRAY_TYPES.add(short[].class);
		PRIMITIVE_ARRAY_TYPES.add(int[].class);
		PRIMITIVE_ARRAY_TYPES.add(long[].class);
		PRIMITIVE_ARRAY_TYPES.add(float[].class);
		PRIMITIVE_ARRAY_TYPES.add(double[].class);

		/** 原始类型类数组 */
		PRIMITIVE_WRAPPER_ARRAY_TYPES = new HashSet<Class<?>>(EIGHT);
		PRIMITIVE_WRAPPER_ARRAY_TYPES.add(Boolean[].class);
		PRIMITIVE_WRAPPER_ARRAY_TYPES.add(Character[].class);
		PRIMITIVE_WRAPPER_ARRAY_TYPES.add(Byte[].class);
		PRIMITIVE_WRAPPER_ARRAY_TYPES.add(Short[].class);
		PRIMITIVE_WRAPPER_ARRAY_TYPES.add(Integer[].class);
		PRIMITIVE_WRAPPER_ARRAY_TYPES.add(Long[].class);
		PRIMITIVE_WRAPPER_ARRAY_TYPES.add(Float[].class);
		PRIMITIVE_WRAPPER_ARRAY_TYPES.add(Double[].class);
	}

	/**
	 * 获取所有的字段.
	 * @param clazz
	 * @return List<Field>
	 */
	public static List<Field> getAllFields(Class<?> clazz) {
		if (clazz == null) {
			return Collections.emptyList();
		}
		Field[] fields = clazz.getDeclaredFields();
		List<Field> list = new LinkedList<Field>();
		list.addAll(Arrays.asList(fields));
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != Object.class) {
			list.addAll(getAllFields(superClazz));
		}
		return list;
	}

	/**
	 * 获取所有的字段.
	 * @param clazz
	 * @return List<Field>
	 */
	static public Map<String, Field> getAllFieldsAsMap(Class<?> clazz) {
		if (clazz == null) {
			return Collections.emptyMap();
		}
		Field[] fields = clazz.getDeclaredFields();
		Map<String, Field> map = new HashMap<String, Field>();
		for (Field f : fields) {
			map.put(f.getName(), f);
		}
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != Object.class) {
			map.putAll(getAllFieldsAsMap(superClazz));
		}
		return map;
	}

	/**
	 * 反射调用GET方法.
	 * @param o
	 * @param getMethodName
	 * @return Object
	 * @throws Exception
	 */
	static public Object invokeGet(Object o, String getMethodName)
			throws Exception {
		Method getter = getMethod(o.getClass(), getMethodName, null);
		if (getter == null) {
			throw new Exception(String.format(
					"Method %s() not found in class %s", getMethodName, o
							.getClass().getName()));
		}
		return invokeGet(o, getter);
	}

	/**
	 * 反射调用GET方法
	 * @param o
	 * @param getter
	 * @return Object
	 * @throws Exception
	 */
	static public Object invokeGet(Object o, Method getter) throws Exception {
		return invokeMethod(o, getter, null);
	}

	/**
	 * 反射调用SET方法
	 * @param o
	 * @param setter
	 * @param param
	 * @throws Exception
	 */
	static void invokeSet(Object o, Method setter, Object param)
			throws Exception {
		try {
			invokeMethod(o, setter, new Object[] {param});
		} catch (Exception ex) {
			if (param != null) {
				param = param.getClass().getName();
			} else {
				param = "";
			}
			throw new Exception(String.format(
					"Failed to invoke method %s.%s(%s)", o.getClass()
							.getSimpleName(), setter.getName(), param), ex);
		}
	}

	/**
	 * 反射调用方法
	 * @param o
	 * @param m
	 * @param params
	 * @return Object
	 * @throws Exception
	 */
	static public Object invokeMethod(Object o, Method m, Object[] params)
			throws Exception {
		try {
			return m.invoke(o, params);
		} catch (InvocationTargetException ex) {
			Throwable theEx = ex;
			if (ex.getTargetException() != null) {
				theEx = ex.getTargetException();
				if (theEx instanceof Exception) {
					throw (Exception) theEx;
				}
			}
			throw new Exception(String.format(
					"Failed to invoke method %s.%s()", o.getClass()
							.getSimpleName(), m.getName()), theEx);

		} catch (IllegalAccessException ex) {
			Throwable theEx = ex;
			if (ex.getCause() != null) {
				theEx = ex.getCause();
				if (theEx instanceof Exception) {
					throw (Exception) theEx;
				}
			}
			throw new Exception(String.format(
					"Failed to invoke method %s.%s()", o.getClass()
							.getSimpleName(), m.getName()), theEx);
		}
	}

	/**
	 * 反射调用方法.
	 * @param o
	 * @param methodName
	 * @return Object
	 * @throws Exception
	 */
	static public Object invokeMethod(Object o, String methodName)
			throws Exception {
		return invokeMethod(o, methodName, null);
	}

	/**
	 * 反射调用方法.
	 * @param o
	 * @param methodName
	 * @param params
	 * @return Object
	 * @throws Exception
	 */
	static public Object invokeMethod(Object o, String methodName,
			Object[] params) throws Exception {
		Class<?>[] paramDef = null;
		if (params != null) {
			paramDef = new Class[params.length];
			for (int i = 0; i < params.length; i++) {
				paramDef[i] = params[i].getClass();
			}
		}
		Method m = getMethodIncludeSuperclass(o.getClass(), methodName,
				paramDef, true);
		if (m == null) {
			throw new Exception(String.format("Method not found: %s.%s()", o
					.getClass().getSimpleName(), methodName));
		}
		boolean originalAccessibility = m.isAccessible();
		try {
			m.setAccessible(true);
			return invokeMethod(o, m, params);
		} finally {
			m.setAccessible(originalAccessibility);
		}
	}

	/**
	 * 反射调用方法
	 * @param o
	 * @param methodName
	 * @param paramDefs
	 * @param params
	 * @return Object
	 * @throws Exception
	 */
	static public Object invokeMethod(Object o, String methodName,
			Class<?>[] paramDefs, Object[] params) throws Exception {
		Method m = getMethodIncludeSuperclass(o.getClass(), methodName,
				paramDefs, true);
		if (m == null) {
			String errMsg = String.format("Method not found: %s.%s()", o
					.getClass().getSimpleName(), methodName);
			if (paramDefs != null) {
				for (int i = 0; i < paramDefs.length; i++) {
					errMsg += "\n param " + i + ": " + paramDefs[i].getName();
				}
			}
			throw new Exception(errMsg);
		}
		boolean originalAccessibility = m.isAccessible();
		try {
			m.setAccessible(true);
			return invokeMethod(o, m, params);
		} finally {
			m.setAccessible(originalAccessibility);
		}
	}

	/**
	 * 反射获取方法
	 * @param o
	 * @param methodName
	 * @param args
	 * @return Method
	 */
	static public Method getMethod(Object o, String methodName, Class<?>[] args) {
		return getMethod(o.getClass(), methodName, args);
	}

	/**
	 * 反射获取方法
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @return Method
	 */
	static public Method getMethod(Class<?> clazz, String methodName,
			Class<?>[] args) {
		return getMethod(clazz, methodName, args, false);
	}

	/**
	 * 反射获取方法
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @param includePrivateMethods
	 * @return Method
	 */
	static public Method getMethodIncludeSuperclass(Class<?> clazz,
			String methodName, Class<?>[] args, boolean includePrivateMethods) {
		Class<?> curClass = clazz;
		while (curClass != Object.class) {
			Method m = getMethod(curClass, methodName, args, true);
			if (m != null) {
				return m;
			} else {
				curClass = curClass.getSuperclass();
			}
		}
		return null;
	}

	/**
	 * 反射获取方法
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @param includePrivateMethods
	 * @return Method
	 */
	static public Method getMethod(Class<?> clazz, String methodName,
			Class<?>[] args, boolean includePrivateMethods) {
		Method[] methods = null;
		if (includePrivateMethods) {
			methods = clazz.getDeclaredMethods();
		} else {
			methods = clazz.getMethods();
		}
		for (int i = 0; i < methods.length; i++) {
			if (!methods[i].getName().equals(methodName)) {
				continue;
			}
			Class<?>[] declaredArgs = methods[i].getParameterTypes();
			if (declaredArgs == null || declaredArgs.length == 0) {
				if (args == null) {
					return methods[i];
				}
				continue;
			}
			if (args == null || declaredArgs.length != args.length) {
				continue;
			}
			boolean allArgsAssignable = true;
			for (int j = 0; j < declaredArgs.length; j++) {
				if (!declaredArgs[j].isAssignableFrom(args[j])) {
					allArgsAssignable = false;
					break;
				}
			}
			if (allArgsAssignable) {
				return methods[i];
			}
		}
		return null;
	}

	/**
	 * 反射获取相应类型的字段
	 * @param clazz
	 * @param type
	 * @return List<Field>
	 */
	static public List<Field> getFieldsOfType(Class<?> clazz, Class<?> type) {
		if (clazz == null) {
			return null;
		}
		List<Field> result = new LinkedList<Field>();
		for (Field f : getAllFields(clazz)) {
			if (type.isAssignableFrom(f.getType())) {
				result.add(f);
			}
		}
		return result;
	}

	/**
	 * 反射获取相应的字段
	 * @param clazz
	 * @param name
	 * @return Field
	 */
	static public Field getField(Class<?> clazz, String name) {
		if (clazz == null) {
			return null;
		}
		Field field;
		try {
			field = clazz.getDeclaredField(name);
			if (field != null) {
				return field;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != Object.class) {
			field = getField(superClazz, name);
			if (field != null) {
				return field;
			}
		}
		return null;
	}

	/**
	 * 反射获取相应的字段的值
	 * @param o
	 * @param f
	 * @return Object
	 */
	static public Object getFieldValue(Object o, String f) {
		Field field = getField(o.getClass(), f);
		try {
			field.setAccessible(true);
			return field.get(o);
		} catch (Exception e) {
			return null;
		} finally {
			if (field != null) {
				field.setAccessible(false);
			}
		}
	}

	/**
	 * 反射设置相应的字段的值
	 * @param o
	 * @param strField
	 * @param value
	 * @throws Exception
	 */
	static public void setFieldValue(Object o, String strField, Object value)
			throws Exception {
		Field field = getField(o.getClass(), strField);
		if (field == null) {
			throw new Exception(String.format("Field not found: %s.%s", o
					.getClass().getSimpleName(), strField));
		}
		setFieldValue(o, field, value);
	}

	/**
	 * 反射设置相应的字段的值
	 * @param o
	 * @param field
	 * @param value
	 * @throws Exception
	 */
	static public void setFieldValue(Object o, Field field, Object value)
			throws Exception {
		try {
			field.setAccessible(true);
			if (value == null) {
				field.set(o, null);
				return;
			}
			String convertedValue;
			if (value instanceof byte[]) {
				convertedValue = new String((byte[]) value, "UTF-8");
			} else if (value instanceof String) {
				convertedValue = (String) value;
			} else {
				convertedValue = value.toString();
			}
			if (field.getType() == int.class) {
				field.setInt(o, Integer.parseInt(convertedValue));
			} else if (field.getType() == long.class) {
				field.setLong(o, Long.parseLong(convertedValue));
			} else if (field.getType() == float.class) {
				field.setFloat(o, Float.parseFloat(convertedValue));
			} else if (field.getType() == double.class) {
				field.setDouble(o, Double.parseDouble(convertedValue));
			} else if (field.getType() == boolean.class) {
				field.set(o, Boolean.parseBoolean(convertedValue));
			} else {
				if (field.getType() == Integer.class) {
					value = Integer.valueOf(convertedValue);
					field.set(o, value);
				} else if (field.getType() == Long.class) {
					value = Long.valueOf(convertedValue);
					field.set(o, value);
				} else if (field.getType() == BigInteger.class) {
					value = BigInteger.valueOf(Long.valueOf(convertedValue));
					field.set(o, value);
				} else if (field.getType() == Float.class) {
					value = Float.valueOf(convertedValue);
					field.set(o, value);
				} else if (field.getType() == Double.class) {
					value = Double.valueOf(convertedValue);
					field.set(o, value);
				} else if (field.getType() == Boolean.class) {
					value = Boolean.valueOf(convertedValue);
					field.set(o, value);
				} else if (field.getType() == Date.class) {
					if (Date.class.isAssignableFrom(value.getClass())) {
						field.set(o, value);
					} else {
						throw new RuntimeException("Can't copy Date type");
					}
				} else if (field.getType() == String.class) {
					field.set(o, convertedValue);
				} else if (field.getType().isAssignableFrom(value.getClass())) {
					field.set(o, value);
				} else {
					System.err.println("Failed to set value for field "
							+ field.getName() + " on class "
							+ o.getClass().getSimpleName());
				}
			}
		} finally {
			field.setAccessible(false);
		}
	}

	/**
	 * 字段类型判断
	 * @param f
	 * @return boolean
	 */
	static public boolean isPrimitiveType(Field f) {
		return PRIMITIVE_TYPES.contains(f.getType());
	}

	/**
	 * 类类型判断
	 * @param clazz
	 * @return boolean
	 */
	static public boolean isPrimitiveType(Class<?> clazz) {
		return PRIMITIVE_TYPES.contains(clazz);
	}

	/**
	 * 类类型判断
	 * @param clazz
	 * @return boolean
	 */
	static public boolean isPrimitiveArrayType(Class<?> clazz) {
		return PRIMITIVE_ARRAY_TYPES.contains(clazz);
	}

	/**
	 * 类类型判断
	 * @param clazz
	 * @return boolean
	 */
	static public boolean isPrimitiveWrapperType(Class<?> clazz) {
		return PRIMITIVE_WRAPPER_TYPES.contains(clazz);
	}

	/**
	 * 类类型判断
	 * @param clazz
	 * @return boolean
	 */
	static public boolean isPrimitiveWrapperArrayType(Class<?> clazz) {
		return PRIMITIVE_WRAPPER_ARRAY_TYPES.contains(clazz);
	}

	/**
	 * 反射生成一个类实例
	 * @param clazz
	 * @return T
	 */
	static public <T> T newInstance(Class<T> clazz) {
		Constructor<T> ctor = null;
		try {
			ctor = clazz.getDeclaredConstructor(new Class<?>[0]);
			ctor.setAccessible(true);
			T o = ctor.newInstance(new Object[0]);
			return o;
		} catch (Throwable t) {
			return null;
		} finally {
			if (ctor != null) {
				ctor.setAccessible(false);
			}
		}
	}

	/**
	 * 反射获取类参数类型
	 * @param parentClass
	 * @return Class
	 */
	static public Class<?> getClassGenericType(Class<?> parentClass) {
		return getClassGenericType(parentClass, 0);
	}

	/**
	 * 反射获取类参数类型
	 * @param parentClass
	 * @param position position of the parameter in class definition
	 * @return Class
	 */
	static public Class<?> getClassGenericType(Class<?> parentClass,
			int position) {
		return (Class<?>) ((ParameterizedType) parentClass
				.getGenericSuperclass()).getActualTypeArguments()[position];
	}

	/**
	 * Retrieve the parameterized type of a field, e.g. List<String> -> String
	 * @param field
	 * @return Class
	 */
	static public Class<?> getFieldGenericType(Field field) {
		ParameterizedType elemType = (ParameterizedType) field.getGenericType();
		Type type = elemType.getActualTypeArguments()[0];
		Class<?> elemClass;
		if (type instanceof Class<?>) {
			elemClass = (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			elemClass = (Class<?>) ((ParameterizedType) type).getRawType();
		} else {
			throw new RuntimeException("To be implemented.");
		}
		return elemClass;
	}

	/**
	 * 类注解是否存在判断
	 * @param targetClass
	 * @param annoClass
	 * @return boolean
	 */
	static public boolean isAnnotationPresent(Class<?> targetClass,
			Class<? extends Annotation> annoClass) {
		do {
			try {
				if (targetClass.isAnnotationPresent(annoClass)) {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		} while ((targetClass = targetClass.getSuperclass()) != Object.class);
		return false;
	}

	/**
	 * 类注解是否存在判断
	 * @param targetClass
	 * @param name
	 * @param params
	 * @param annoClass
	 * @return boolean
	 */
	static public boolean isAnnotationPresent(Class<?> targetClass,
			String name, Class<?>[] params,
			Class<? extends Annotation> annoClass) {
		do {
			try {
				Method method = targetClass.getMethod(name, params);
				if (method.isAnnotationPresent(annoClass)) {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		} while ((targetClass = targetClass.getSuperclass()) != Object.class);
		return false;
	}
	
	/**
	 * 根据对象方法名称，设置对象的属性值
	 * @param o 
	 * @param method ,is method name
	 * @param type ,is parameter type name,ex:class java.lant.Integer 
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
	
	public static List<Map<?,?>> getFiledValueOperators(Object o, boolean isContainNullValue) {
		List<Map<?,?>> list = new ArrayList<Map<?,?>>();

		Class<?> clazz = o.getClass();
		Field[] fields = clazz.getDeclaredFields();

		setContainOperatorMapByFields(o, list, fields, isContainNullValue);

		// 获取父类属性信息
		clazz = clazz.getSuperclass();
		if (clazz == Object.class)
			return list;

		fields = clazz.getDeclaredFields();

		// 设父类的属性
		setContainOperatorMapByFields(o, list, fields, isContainNullValue);

		return list;

	}

	private static void setMapByFields(Object o,List<Map<?,?>> list,
			Field[] fields, boolean isContainNullValue) {

		Map<String, Object> infoMap = null;
		Object value = null;
		// for (int i = 0; i < fields.length; i++) {
		for (Field field : fields) {
			infoMap = new HashMap<String, Object>();
			infoMap.put("type", field.getType().toString());

			infoMap.put("name", field.getName());

			value = getFieldValueByName(field.getName(), o);
			
			if (!isContainNullValue && (value == null || "".equals(value.toString())))
				continue;

			infoMap.put("value", value);
			list.add(infoMap);
		}

	}
	
	private static void setContainOperatorMapByFields(Object o,List<Map<?,?>> list,
			Field[] fields, boolean isContainNullValue) {

		Map<String, Object> infoMap = null;
		Object value = null;
		String fieldName = "";
		// for (int i = 0; i < fields.length; i++) {
		for (Field field : fields) {
			
			fieldName = field.getName();
			
			if(fieldName.contains("_Operator"))
				continue;
			
			infoMap = new HashMap<String, Object>();
			infoMap.put("type", field.getType().toString());

			infoMap.put("name", fieldName);
			
			value = getFieldValueByName(fieldName+"_Operator", o);
			
			infoMap.put("operator", value);

			value = getFieldValueByName(fieldName, o);
			if (!isContainNullValue && (value == null || "".equals(value.toString())))
				continue;
			infoMap.put("value", value);
			
		
			list.add(infoMap);
		}

	}
	
	

	/**
	 * 根据属性名获取属性值
	 * */
	private static Object getFieldValueByName(String fieldName, Object o) {
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

}

