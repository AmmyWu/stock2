/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.cache
 * 类/接口名: RedisObjectService
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-15下午3:11:14
 * 作者		: taofeng
 * 修改历史	: 2017-5-15下午3:11:14
 */
package com.stock.service.base.cache;

/**
 * ClassName	: RedisObjectService
 * Function		: 查询Object缓存的接口服务类.
 * date 		: 2017-5-15下午3:11:14
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
public interface RedisObjectService {

	/**
	 * set:设置键值为Key的缓存对象.
	 * @author taofeng
	 * @param key
	 * @param obj
	 * @param clazz
	 * @param seconds 
	 * @since JDK 1.7
	 */
	public <T> void set(final String key, final T obj, Class<T> clazz,
			final int seconds);

	/**
	 * get:获取键值key的缓存对象.
	 * @author taofeng
	 * @param key
	 * @param clazz
	 * @return 
	 * @since JDK 1.7
	 */
	public <T> T get(final String key, Class<T> clazz);

	/**
	 * set:设置键值为Key的缓存对象.
	 * @author taofeng
	 * @param key
	 * @param obj
	 * @param clazz 
	 * @since JDK 1.7
	 */
	public <T> void set(final String key, final T obj, Class<T> clazz);

	/**
	 * setString:设置键值为Key的缓存字符串数据.
	 * @author taofeng
	 * @param key
	 * @param value
	 * @param seconds 
	 * @since JDK 1.7
	 */
	public void setString(final String key, final String value,
			final int seconds);

	/**
	 * setString:设置键值为Key的缓存字符串数据.
	 * @author taofeng
	 * @param key
	 * @param value 
	 * @since JDK 1.7
	 */
	public void setString(final String key, final String value);
	
}
