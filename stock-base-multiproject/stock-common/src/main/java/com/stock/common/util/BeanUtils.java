/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.common.util
 * 类/接口名: BeanUtils
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-16下午6:31:52
 * 作者		: taofeng
 * 修改历史	: 2017-5-16下午6:31:52
 */
package com.stock.common.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;

import com.stock.common.util.convert.DateConvert;

import java.lang.reflect.*;
import java.math.BigDecimal;

/**
 * ClassName	: BeanUtils
 * Function		: BeanUtils类扩展.
 * date 		: 2017-5-16下午6:31:52
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	
	private BeanUtils() {
	}

	/**
	 * 初始化.
	 */
	static {
		// 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
		ConvertUtils.register(new SqlDateConverter(), java.util.Date.class);
		// 注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
		ConvertUtils.register(new DateConvert(), java.util.Date.class);
		//注册BigDecimal
		ConvertUtils.register(new BigDecimalConverter(), BigDecimal.class);
	}

	/**
	 * copyProperties:属性赋值.
	 * @author taofeng
	 * @param target
	 * @param source
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException 
	 * @since JDK 1.7
	 */
	public static void copyProperties(Object target, Object source)
			throws InvocationTargetException, IllegalAccessException {
		org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
	}
	
}
