/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.cache.impl
 * 类/接口名: RedisListServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-15下午2:10:28
 * 作者		: taofeng
 * 修改历史	: 2017-5-15下午2:10:28
 */
package com.stock.service.base.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import com.stock.service.base.cache.RedisListService;
import com.stock.service.base.cache.RedisService;

/**
 * ClassName	: RedisListServiceImpl
 * Function		: redis的list操作
 * date 		: 2017-5-15下午2:10:28
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
@SuppressWarnings("unchecked")
public class RedisListServiceImpl extends RedisService implements RedisListService{
	
	/**
	 * Create a new instance of RedisListServiceImpl
	 * @param redisTemplate
	 */
	public RedisListServiceImpl(
			RedisTemplate<Serializable, Serializable> redisTemplate,RedisTemplate customStringRedisTemplate) {
		super(redisTemplate,customStringRedisTemplate);
	}
	
	

	/**
	 * 存储REDIS队列 顺序存储
	 * @see com.stock.service.base.cache.RedisListService#lpush(java.lang.String, int, T[])
	 */
	public <T> long lpush(final String key, final int seconds,
			final T... values) {
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
				logger.debug("lpush key {} = {} ", key, values);
				returnValue = connection.lPush(byteKey,
						(byte[][]) list.toArray(new byte[list.size()][]));
				if (seconds != 0) {
					connection.expire(byteKey, seconds);
				}
				return returnValue;
			}
		});
	}

	/**
	 * 存储REDIS队列 顺序存储
	 * @see com.stock.service.base.cache.RedisListService#lpush(java.lang.String, T[])
	 */
	public <T> long lpush(final String key, final T... values) {
		return lpush(key, 0, values);
	}

	/**
	 * 存储REDIS队列 反向存储.
	 * @see com.stock.service.base.cache.RedisListService#rpush(java.lang.String, int, T[])
	 */
	public <T> long rpush(final String key, final int seconds,
			final T... values) {
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
				logger.debug("rpush key {} = {} ", key, values);
				returnValue = connection.rPush(byteKey,
						(byte[][]) list.toArray(new byte[list.size()][]));
				if (seconds != 0) {
					connection.expire(byteKey, seconds);
				}
				return returnValue;
			}
		});
	}

	/**
	 * 存储REDIS队列 反向存储.
	 * @see com.stock.service.base.cache.RedisListService#rpush(java.lang.String, T[])
	 */
	public <T> long rpush(final String key, final T... values) {
		return rpush(key, 0, values);
	}

	/**
	 * 顺序取出队列元素.
	 * @see com.stock.service.base.cache.RedisListService#lrange(java.lang.String, int, int)
	 */
	public <T> List<T> lrange(final String key, final int begin, final int end) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<List<T>>() {
			public List<T> doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				List<T> returnValue = new ArrayList<T>();
				if (connection.exists(byteKey)) {
					List<byte[]> list = connection.lRange(byteKey, begin, end);
					for (byte[] bytes : list) {
						returnValue.add((T) toObject(bytes));
					}
					logger.debug("lrange key {} = {} ", key, returnValue);
				}
				return returnValue;
			}
		});
	}

	/** 
	 * @see com.stock.service.base.cache.RedisListService#lpop(java.lang.String)
	 */
	public <T> T lpop(final String key) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				T returnValue = null;
				if (connection.exists(byteKey)) {
					byte[] bytes = connection.lPop(byteKey);
					if(null != bytes && bytes.length !=0){
						returnValue = (T) toObject(bytes);
					}
					logger.debug("lpop key {} = {} ", key, returnValue);
				}
				return returnValue;
			}
		});
	}

	/** 
	 * @see com.stock.service.base.cache.RedisListService#rpop(java.lang.String)
	 */
	public <T> T rpop(final String key) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				T returnValue = null;
				if (connection.exists(byteKey)) {
					byte[] bytes = connection.rPop(byteKey);
					if(null != bytes && bytes.length !=0){
						returnValue = (T) toObject(bytes);
					}
					logger.debug("lpop key {} = {} ", key, returnValue);
				}
				return returnValue;
			}
		});
	}

}
