/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.cache.impl
 * 类/接口名: RedisSetServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-15下午2:10:46
 * 作者		: taofeng
 * 修改历史	: 2017-5-15下午2:10:46
 */
package com.stock.service.base.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import com.stock.service.base.cache.RedisService;
import com.stock.service.base.cache.RedisSetService;

/**
 * ClassName	: RedisSetServiceImpl
 * Function		: redis 对set的操作
 * date 		: 2017-5-15下午2:10:46
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
@SuppressWarnings("unchecked")
public class RedisSetServiceImpl extends RedisService implements RedisSetService {

	/**
	 * Create a new instance of RedisSetServiceImpl
	 * @param redisTemplate
	 */
	public RedisSetServiceImpl(
			RedisTemplate<Serializable, Serializable> redisTemplate,RedisTemplate customStringRedisTemplate) {
		super(redisTemplate,customStringRedisTemplate);
	}

	/**
	 * @see com.stock.service.base.cache.RedisSetService#sadd(java.lang.String, int, T[])
	 */
	public <T> long sadd(final String key, final int seconds, final T... values) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		Assert.notEmpty(values, "values is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				List<byte[]> list = new ArrayList<byte[]>();
				byte[] byteKey = getBytesKey(key);
				long returnValue = 0;
				for (T v : values) {
					list.add(toBytes(v));
				}
				returnValue = connection.sAdd(byteKey,
						(byte[][]) list.toArray(new byte[list.size()][]));
				if (seconds != 0) {
					connection.expire(byteKey, seconds);
				}
				logger.debug("lpush key {} = {} ", key, values);
				return returnValue;
			}

		});
	}

	/**
	 * @see com.stock.service.base.cache.RedisSetService#sadd(java.lang.String, T[])
	 */
	public <T> long sadd(final String key, final T... values) {
		return sadd(key, 0, values);
	}

	/**
	 * @see com.stock.service.base.cache.RedisSetService#smembers(java.lang.String)
	 */
	public <T> Set<T> smembers(final String key) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Set<T>>() {
			public Set<T> doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				Set<byte[]> set = connection.sMembers(byteKey);
				Set<T> returnValue = new HashSet<T>();
				for (byte[] bytes : set) {
					returnValue.add((T) toObject(bytes));
				}
				return returnValue;
			}
		});
	}
	
}
