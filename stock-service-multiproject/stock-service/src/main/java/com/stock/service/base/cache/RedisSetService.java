/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.cache
 * 类/接口名: RedisSetService
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-15下午3:15:30
 * 作者		: taofeng
 * 修改历史	: 2017-5-15下午3:15:30
 */
package com.stock.service.base.cache;

import java.util.Set;

/**
 * ClassName	: RedisSetService
 * Function		: 查询Set缓存的接口服务类.
 * date 		: 2017-5-15下午3:15:30
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
@SuppressWarnings("unchecked")
public interface RedisSetService {
	
	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。 假如 key 不存在，则创建一个只包含
	 * member 元素作成员的集合。
	 * @param key
	 * @param seconds 0 永不过期
	 * @param values
	 * @return long
	 */
	public abstract <T> long sadd(String key, int seconds, T... values);

	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。 假如 key 不存在，则创建一个只包含
	 * member 元素作成员的集合。 永不过期
	 * @param key
	 * @param values
	 * @return long
	 */
	public abstract <T> long sadd(String key, T... values);

	/**
	 * 返回所有set
	 * @param key
	 * @return Set<T> key 不存在将返回空的set
	 */
	public abstract <T> Set<T> smembers(String key);
	
}
