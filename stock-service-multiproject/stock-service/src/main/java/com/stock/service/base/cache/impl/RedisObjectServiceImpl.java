/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.cache.impl
 * 类/接口名: RedisObjectServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-15下午2:10:37
 * 作者		: taofeng
 * 修改历史	: 2017-5-15下午2:10:37
 */
package com.stock.service.base.cache.impl;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import com.stock.service.base.cache.RedisObjectService;
import com.stock.service.base.cache.RedisService;

/**
 * ClassName	: RedisObjectServiceImpl
 * Function		: redis 对象操作 包含string
 * date 		: 2017-5-15下午2:10:37
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
@SuppressWarnings("unchecked")
public class RedisObjectServiceImpl extends RedisService implements RedisObjectService {
	
	/**
	 * Create a new instance of RedisObjectServiceImpl
	 * @param redisTemplate
	 */
	public RedisObjectServiceImpl(
			RedisTemplate<Serializable, Serializable> redisTemplate,RedisTemplate customStringRedisTemplate) {
		super(redisTemplate,customStringRedisTemplate);
	}

	/**
	 * 设置一个对象 可以是string
	 * @param key
	 * @param obj
	 * @param clazz
	 * @param seconds 超时时间 0 代表永不过期
	 */
	public <T> void set(final String key, final T obj, Class<T> clazz,
			final int seconds) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		redisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				connection.set(byteKey, toBytes(obj));
				if (seconds != 0) {
					connection.expire(byteKey, seconds);
				}
				logger.debug("set object key {} = {} ", key, obj);
				return null;
			}
		});
	}

	/**
	 * 设置一个对象 可以是string
	 * @param key
	 * @param obj
	 * @param clazz
	 */
	public <T> void set(final String key, final T obj, Class<T> clazz) {
		this.set(key, obj, clazz, 0);
	}

	/**
	 * 取一个对象
	 * @see com.stock.service.base.cache.RedisObjectService#get(java.lang.String, java.lang.Class)
	 */
	public <T> T get(final String key, Class<T> clazz) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				T returnVaue = null;
				byte[] byteKey = getBytesKey(key);
				if (connection.exists(byteKey)) {
					returnVaue = (T) toObject(connection.get(byteKey));
					logger.debug("get object key {} = {} ", key, returnVaue);
				}
				return returnVaue;
			}
		});
	}

	/**
	 * 设置字符串
	 * @param key
	 * @param value
	 * @param seconds 超时时间 0 代表永不过期
	 */
	public void setString(final String key, final String value,
			final int seconds) {
		this.set(key, value, String.class, seconds);
	}

	/**
	 * 设置字符串
	 * @param key
	 * @param value
	 */
	public void setString(final String key, final String value) {
		this.set(key, value, String.class, 0);
	}
	
}
