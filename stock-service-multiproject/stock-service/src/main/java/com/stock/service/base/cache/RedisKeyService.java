/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.cache
 * 类/接口名: RedisKeyService
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-15下午2:29:56
 * 作者		: taofeng
 * 修改历史	: 2017-5-15下午2:29:56
 */
package com.stock.service.base.cache;

/**
 * ClassName	: RedisKeyService
 * Function		: 查询Key缓存的接口实现类.
 * date 		: 2017-5-15下午2:29:56
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
public interface RedisKeyService {
	
	/**
	 * exists:判断key值的缓存是否存在.
	 * @author taofeng
	 * @param key
	 * @return Boolean 
	 * @since JDK 1.7
	 */
	public Boolean exists(final String key);

	/**
	 * del:删除若干Key值的缓存.
	 * @author taofeng
	 * @param keys
	 * @return long 
	 * @since JDK 1.7
	 */
	public long del(final String... keys);

	/**
	 * expire:设置Key值的缓存超时时间.
	 * @author taofeng
	 * @param key
	 * @param seconds 
	 * @return Boolean
	 * @since JDK 1.7
	 */
	public Boolean expire(final String key, final int seconds);

	/**
	 * persist:实例化Key值缓存.
	 * @author taofeng
	 * @param key
	 * @return Boolean
	 * @since JDK 1.7
	 */
	public Boolean persist(final String key);

	/**
	 * ttl:返回剩余超时时间.
	 * @author taofeng
	 * @param key
	 * @return Long
	 * @since JDK 1.7
	 */
	public Long ttl(final String key);
	
	/**
	 * 添加key value(String)
	 * @author taofeng
	 * @param key
	 * @param value 
	 * @return Long
	 */
	public void set(final String key, final String value);
	/**
	 * 
	 * setString:(当以字符串方式存储调用此方法). 
	 * TODO(这里描述这个方法适用条件 – 可选).
	 * 
	 * @author taofeng
	 * @param key
	 * @param value 
	 * @since JDK 1.7
	 */
	public void setString(final String key, final String value);
	/**
	 * 添加key value 并且设置存活时间(String)
	 * @author taofeng
	 * @param key
	 * @param value 
	 * @param liveTime
	 * @return Long
	 */
	public void set(final String key, final String value, long liveTime);
	
	/**
	 * 添加key value 并且设置存活时间(byte)
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(byte[] key, byte[] value, long liveTime);
	
	/**
	 * 添加key value (字节)(序列化)
	 * @param key
	 * @param value
	 */
	public void set(byte[] key, byte[] value);
	
	/**
	 * 取Key的 value值（字符串）
	 * @param key
	 * @return String
	 */
	public String get(String key);
	
	/**
	 * 取Key的 value值（序列化）
	 * @param key
	 * @return String
	 */
	public byte[] get(byte[] key);
	
	/**
	 * 
	 * 
	 * 获取key的value+1值
	 * @author taofeng
	 * @param key
	 * @return 
	 * @since JDK 1.7
	 */
	public Long inc(final String key);

}
