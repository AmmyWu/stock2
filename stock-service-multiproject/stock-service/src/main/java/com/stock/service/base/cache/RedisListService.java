/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.cache
 * 类/接口名: RedisListService
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-15下午2:42:47
 * 作者		: taofeng
 * 修改历史	: 2017-5-15下午2:42:47
 */
package com.stock.service.base.cache;

import java.util.List;

/**
 * ClassName	: RedisListService
 * Function		: 查询List缓存的接口服务类.
 * date 		: 2017-5-15下午2:42:47
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
@SuppressWarnings("unchecked")
public interface RedisListService {

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头
	 * @param key
	 * @param seconds 0 为永不过期
	 * @param values
	 * @return long 执行 LPUSH 命令后，列表的长度。
	 */
	public abstract <T> long lpush(String key, int seconds, T... values);

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头 为永不过期
	 * @param key
	 * @param values
	 * @return long 执行 LPUSH 命令后，列表的长度。
	 */
	public abstract <T> long lpush(String key, T... values);

	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾
	 * @param key
	 * @param seconds 0 为永不过期
	 * @param values
	 * @return long 执行 LPUSH 命令后，列表的长度。
	 */
	public abstract <T> long rpush(String key, int seconds, T... values);

	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾 为永不过期
	 * @param key
	 * @param values
	 * @return long 执行 LPUSH 命令后，列表的长度。
	 */
	public abstract <T> long rpush(String key, T... values);

	/**
	 * 取出list.
	 * @param key
	 * @param begin
	 * @param end -1 表示倒数第一以此类推
	 * @return List<T>
	 */
	public abstract <T> List<T> lrange(String key, int begin, int end);
	
	/**
	 * 顺序取出一个元素
	 * @param key
	 * @return T 执行 LPUSH 命令后，列表的长度。
	 */
	public abstract <T> T lpop(String key);
	
	/**
	 * 倒序取出一个元素
	 * @param key
	 * @return T 执行 LPUSH 命令后，列表的长度。
	 */
	public abstract <T> T rpop(String key);
	
	
}
