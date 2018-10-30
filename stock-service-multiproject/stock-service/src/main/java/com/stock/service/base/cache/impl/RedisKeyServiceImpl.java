/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.cache.impl
 * 类/接口名: RedisKeyServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-15下午2:10:08
 * 作者		: taofeng
 * 修改历史	: 2017-5-15下午2:10:08
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

import com.stock.service.base.cache.RedisKeyService;
import com.stock.service.base.cache.RedisService;

/**
 * ClassName	: RedisKeyServiceImpl
 * Function		: 完成redis key 相关操作
 * date 		: 2017-5-15下午2:10:08
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
public class RedisKeyServiceImpl extends RedisService implements RedisKeyService {
	
	/**
	 * Create a new instance of RedisKeyServiceImpl
	 * @param redisTemplate
	 */
	public RedisKeyServiceImpl(
			RedisTemplate<Serializable, Serializable> redisTemplate,RedisTemplate<String,String> customStringRedisTemplate) {
		super(redisTemplate,customStringRedisTemplate);
	}
	
	/**
	 * 检查给定 key 是否存在。
	 * @param key
	 * @return Boolean 若 key 存在，返回 true ，否则返回 false 。
	 */
	public Boolean exists(final String key) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.exists(getBytesKey(key));
			}
		});
	}

	/**
	 * 删除给定的一个或多个 key 。 不存在的 key 会被忽略。
	 * @param keys
	 * @return long 被删除 key 的数量。
	 */
	public long del(final String... keys) {
		Assert.notEmpty(keys, "keys is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				List<byte[]> list = new ArrayList<byte[]>();
				for (String key : keys) {
					list.add(getBytesKey(key));
				}
				long returnValue = connection.del((byte[][]) list.toArray(new byte[list.size()][]));
				logger.debug("del key  {} amount {} ", keys, returnValue);
				return returnValue;
			}
		});
	}

	/**
	 * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
	 * @param key
	 * @param seconds 单位秒
	 * @return Boolean 设置成功返回true ，反之false
	 */
	public Boolean expire(final String key, final int seconds) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				logger.debug("set key {} expire {} ", key, seconds);
				return connection.expire(getBytesKey(key), seconds);
			}
		});
	}

	/**
	 * 移除给定 key 的生存时间
	 * @param key
	 * @return Boolean 当生存时间移除成功时true 如果 key 不存在或 key 没有设置生存时间，返回 false
	 */
	public Boolean persist(final String key) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				logger.debug("persist key {} ", key);
				return connection.persist(getBytesKey(key));
			}
		});
	}

	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间
	 * @param key
	 * @return Long 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以秒为单位，返回 key
	 *         的剩余生存时间。
	 */
	public Long ttl(final String key) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				long returnValue = 0;
				byte[] byteKey = getBytesKey(key);
				returnValue = connection.ttl(byteKey);
				logger.debug("ttl  key {} = {} ", key, returnValue);
				return returnValue;
			}
		});
	}

	/** 
	 * @see com.stock.service.base.cache.RedisKeyService#set(java.lang.String, java.lang.String)
	 */
	public void set(final String key,final String value) {
		this.set(key, value, 0L);
	}

	public void setString(final String key,final String value) {
		this.setString(key, value, 0L);
	}
	/** 
	 * @see com.stock.service.base.cache.RedisKeyService#set(java.lang.String, java.lang.String, long)
	 */
	public void set(String key, String value, long liveTime) {
		this.set(getBytesKey(key), toBytes(value), liveTime);
	}
	public void setString(String key, String value, long liveTime) {
		this.setString(getBytesKey(key), getBytesKey(value), liveTime);
	}
	/** 
	 * @see com.stock.service.base.cache.RedisKeyService#set(byte[], byte[], long)
	 */
	public void set(final byte[] key, final byte[] value, final long liveTime) {
		Assert.isTrue(key.length > 0, "key is not allow empty..");
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(key, value);
				if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
				return 1L;
			}
		});
	}
	public void setString(final byte[] key, final byte[] value, final long liveTime) {
		Assert.isTrue(key.length > 0, "key is not allow empty..");
		customStringRedisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(key, value);
				if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
				return 1L;
			}
		});
	}

	/** 
	 * @see com.stock.service.base.cache.RedisKeyService#set(byte[], byte[])
	 */
	public void set(byte[] key, byte[] value) {
		this.set(key, value, 0L);
	}
	
	/**
	 * @see com.stock.service.base.cache.RedisKeyService#get(java.lang.String)
	 */
	public String get(final String key) {
		byte[] returnValue = this.get(getBytesKey(key));
		return (String) toObject(returnValue);
	}

	/**
	 * @see com.stock.service.base.cache.RedisKeyService#get(byte[])
	 */
	public byte[] get(final byte[] key) {
		Assert.isTrue(key.length > 0, "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<byte[]>() {
			public byte[] doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] returnValue = connection.get(key);
				return returnValue;
			}
		});
	}
	
	public Long inc(final String key){
		return this.inc(getBytesKey(key));
	}
	private Long inc(final byte[] key){
		Assert.isTrue(key.length > 0, "key is not allow empty..");
		return customStringRedisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException{
				Long returnValue = connection.incr(key);
				return returnValue;
			}
		});
	}
	
}
