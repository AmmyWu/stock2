/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.cache.impl
 * 类/接口名: RedisHashServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-15下午2:10:18
 * 作者		: taofeng
 * 修改历史	: 2017-5-15下午2:10:18
 */
package com.stock.service.base.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import com.stock.service.base.cache.RedisHashService;
import com.stock.service.base.cache.RedisService;

/**
 * ClassName	: RedisHashServiceImpl
 * Function		: redis hash 结构操作
 * date 		: 2017-5-15下午2:10:18
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
@SuppressWarnings("unchecked")
public class RedisHashServiceImpl extends RedisService implements RedisHashService  {
	
	/**
	 * Create a new instance of RedisHashServiceImpl
	 * @param redisTemplate
	 */
	public RedisHashServiceImpl(RedisTemplate<Serializable, Serializable> redisTemplate,RedisTemplate customStringRedisTemplate) {
		super(redisTemplate,customStringRedisTemplate);
	}
	
	/**
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 * @param key
	 * @param fields
	 * @return long 被成功移除的域的数量，不包括被忽略的域。
	 */
	public <T> long hdel(final String key, final T... fields) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		Assert.notEmpty(fields, "fields is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				List<byte[]> list = new ArrayList<byte[]>();
				for (T key : fields) {
					list.add(toBytes(key));
				}
				logger.debug("hdel key {} ", key);
				return connection.hDel(byteKey, (byte[][]) list.toArray(new byte[list.size()][]));
			}
		});
	}
	/**
	 * 
	 * hdelString:(功能同hdel，当存储类型为string时调用此方法). 
	 * 
	 * @author taofeng
	 * @param key
	 * @param fields
	 * @return 
	 * @since JDK 1.7
	 */
	public <T> long hdelString(final String key, final T... fields) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		Assert.notEmpty(fields, "fields is not allow empty..");
		return customStringRedisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				List<byte[]> list = new ArrayList<byte[]>();
				for (T key : fields) {
					list.add(getBytesKey(key));
				}
				logger.debug("hdel key {} ", key);
				return connection.hDel(byteKey, (byte[][]) list.toArray(new byte[list.size()][]));
			}
		});
	}

	/**
	 * 查看哈希表 key 中，给定域 field 是否存在。
	 * @param key
	 * @param field
	 * @return Boolean 如果哈希表含有给定域 返回true 如果哈希表不含有给定域，或 key 不存在 返回false
	 */
	public <T> Boolean hexists(final String key, final T field) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		Assert.notNull(field, "field is not allow null");
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.hExists(getBytesKey(key), toBytes(field));
			}
		});
	}

	/**
	 * 返回哈希表 key 中给定域 field 的值。
	 * @param key
	 * @param field
	 * @return T 当给定域不存在或是给定 key 不存在时 返回null
	 */
	public <T,V> V hget(final String key, final T field) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		Assert.notNull(field, "field is not allow null");
		return redisTemplate.execute(new RedisCallback<V>() {
			public V doInRedis(RedisConnection connection)
					throws DataAccessException {
				V v = null;
				byte[] byteKey = getBytesKey(key);
				byte[] byteField = toBytes(field);
				if (connection.hExists(byteKey, byteField)) {
					v = (V) toObject(connection.hGet(getBytesKey(key),
							toBytes(field)));
					logger.debug("hget key {} = {}", key, v);
				}
				return v;
			}
		});
	}

	/**
	 * 返回哈希表 key 中，所有的域和值。
	 * @param key
	 * @return Map<K, V> 以列表形式返回哈希表的域和域的值。 若 key 不存在，返回空列表。
	 */
	public <K, V> Map<K, V> hgetAll(final String key) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Map<K, V>>() {
			public Map<K, V> doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				Map<K, V> returnValue = new HashMap<K, V>();
				if (connection.exists(byteKey)) {
					Map<byte[], byte[]> map = connection.hGetAll(byteKey);
					for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
						returnValue.put((K) toObject(entry.getKey()),
								(V) toObject(entry.getValue()));
					}
					logger.debug("get map {} = {}", key, returnValue);
				}
				return returnValue;
			}
		});
	}

	/**
	 * 返回hash表中所有的key
	 * @param key
	 * @return Set<T> 当 key 不存在时 返回空表
	 */
	public <T> Set<T> hkeys(final String key) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Set<T>>() {
			public Set<T> doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				Set<byte[]> set = connection.hKeys(byteKey);
				Set<T> returnValue = new HashSet<T>();
				for (byte[] bytes : set) {
					returnValue.add((T) toObject(bytes));
				}
				logger.debug("hkeys key {} = {}", key, returnValue);
				return returnValue;
			}
		});
	}

	/**
	 * 返回hash表中所有的值
	 * @param key
	 * @return List<T> 当 key 不存在时 返回空表
	 */
	public <T> List<T> hvals(final String key) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<List<T>>() {
			public List<T> doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				List<byte[]> list = connection.hVals(byteKey);
				List<T> returnValue = new ArrayList<T>();
				for (byte[] bytes : list) {
					returnValue.add((T) toObject(bytes));
				}
				logger.debug("hvals key {} = {}", key, returnValue);
				return returnValue;
			}
		});
	}

	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。 如果域
	 * field 已经存在于哈希表中，旧值将被覆盖。
	 * @param key
	 * @param field
	 * @param value
	 * @param seconds 0 标识不过期
	 * @return Boolean 如果 field 是哈希表中的一个新建域，并且值设置成功，返回true 。 如果哈希表中域 field
	 *         已经存在且旧值已被新值覆盖，返回 false 。
	 */
	public <K, V> Boolean hset(final String key, final K field, final V value,
			final int seconds) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				Boolean returnValue = connection.hSet(byteKey, toBytes(field),
						toBytes(value));
				if (seconds != 0) {
					connection.expire(byteKey, seconds);
				}
				return returnValue;		
			}
		});
	}
	/**
	 * 
	 * 功能同hset，当以字符串方式存储时调用此方法.
	 * 
	 * @author taofeng
	 * @param key
	 * @param field
	 * @param value
	 * @param seconds
	 * @return 
	 * @since JDK 1.7
	 */
	public <K, V> Boolean hsetString(final String key, final K field, final V value,
			final int seconds) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return customStringRedisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				Boolean returnValue = connection.hSet(byteKey, getBytesKey(field),
						getBytesKey(value));
				if (seconds != 0) {
					connection.expire(byteKey, seconds);
				}
				return returnValue;		
			}
		});
	}
	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。 如果域
	 * field 已经存在于哈希表中，旧值将被覆盖。
	 * @param key
	 * @param field
	 * @param value
	 * @return Boolean 如果 field 是哈希表中的一个新建域，并且值设置成功，返回true 。 如果哈希表中域 field
	 *         已经存在且旧值已被新值覆盖，返回 false 。
	 */
	public <K, V> Boolean hset(final String key, final K field, final V value) {
		return hset(key, field, value, 0);
	}

	/**
	 * 将一组map放入key中
	 * @param key
	 * @param map
	 * @param seconds 0 不过期
	 */
	public <K, V> void hmset(final String key, final Map<K, V> map,
			final int seconds) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		Assert.notNull(map, "map is not allow null..");
		redisTemplate.execute(new RedisCallback<K>() {
			public K doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				Map<byte[], byte[]> hashes = new HashMap<byte[], byte[]>();
				for (Map.Entry<K, V> entry : map.entrySet()) {
					hashes.put(toBytes(entry.getKey()),
							toBytes(entry.getValue()));
				}
				connection.hMSet(byteKey, hashes);
				logger.debug("hmset key {} = {}", key, map);
				if (seconds != 0) {
					connection.expire(byteKey, seconds);
				}
				return null;
			}
		});
	}

	/**
	 * 永久放入map到key 中
	 * @param key
	 * @param map
	 */
	public <K, V> void hmset(final String key, final Map<K, V> map) {
		hmset(key, map, 0);
	}

	/**
	 * 返回哈希表 key 中，一个或多个给定域的值。
	 * @param key
	 * @param fields
	 * @return List<V>
	 */
	public <K, V> List<V> hmget(final String key, final K... fields) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<List<V>>() {
			public List<V> doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				List<V> returnValue = new ArrayList<V>();
				List<byte[]> list = new ArrayList<byte[]>();
				for (K k : fields) {
					list.add(toBytes(k));
				}
				list = connection.hMGet(byteKey, (byte[][]) list.toArray(new byte[list.size()][]));
				for (byte[] bytes : list) {
					returnValue.add((V) toBytes(bytes));
				}
				return returnValue;
			}
		});
	}
	/**
	 * 返回哈希表 key 中给定域 field 的加1值。
	 * @param key
	 * @param field
	 * @return T 当给定域不存在或是给定 key 不存在时 返回null
	 */
	public <T> Long hinc(final String key, final T field) {
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		Assert.notNull(field, "field is not allow null");
		return customStringRedisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				 Long l = null;
				byte[] byteKey = getBytesKey(key);
				byte[] byteField = getBytesKey(field);
				if (connection.hExists(byteKey, byteField)) {
					l = connection.hIncrBy(byteKey, byteField,1);
					logger.debug("hinc key {} = {}", key, l);
				}			
				return l;
			}
		});
	}

	/** 
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.stock.service.base.cache.RedisHashService#hsetString(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, V> Boolean hsetString(String key, K field, V value) {
		return hsetString(key, field, value, 0);
	}
	
}
