/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.cache.impl
 * 类/接口名: RedisServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-15上午11:34:01
 * 作者		: taofeng
 * 修改历史	: 2017-5-15上午11:34:01
 */
package com.stock.service.base.cache.impl;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisTemplate;

import com.stock.service.base.cache.RedisService;

/**
 * ClassName	: RedisServiceImpl
 * Function		: Redis缓存服务接口实现类.
 * date 		: 2017-5-15上午11:34:01
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
public class RedisServiceImpl extends RedisService {

	/** 查询Key缓存的服务方法 */
	private RedisKeyServiceImpl redisKeyServiceImpl;
	
	/** 查询Hash缓存的服务方法 */
	private RedisHashServiceImpl redisHashServiceImpl;
	
	/** 查询List缓存的服务方法 */
	private RedisListServiceImpl redisListServiceImpl;
	
	/** 查询Object缓存的服务方法 */
	private RedisObjectServiceImpl redisObjectServiceImpl;
	
	/** 查询Set缓存的服务方法 */
	private RedisSetServiceImpl redisSetServiceImpl;

	/**
	 * Create a new instance of RedisServiceImpl
	 * @param redisTemplate
	 */
	public RedisServiceImpl(
			RedisTemplate<Serializable, Serializable> redisTemplate,RedisTemplate<String,String> customStringRedisTemplate) {
		super(redisTemplate,customStringRedisTemplate);
		redisKeyServiceImpl = new RedisKeyServiceImpl(redisTemplate,customStringRedisTemplate);
		redisHashServiceImpl = new RedisHashServiceImpl(redisTemplate,customStringRedisTemplate);
		redisListServiceImpl = new RedisListServiceImpl(redisTemplate,customStringRedisTemplate);
		redisObjectServiceImpl = new RedisObjectServiceImpl(redisTemplate,customStringRedisTemplate);
		redisSetServiceImpl = new RedisSetServiceImpl(redisTemplate,customStringRedisTemplate);
	}
	
	/**
	 * opsForKey:获得查询Key缓存的服务方法类.
	 * @author taofeng
	 * @return RedisKeyServiceImpl
	 * @since JDK 1.7
	 */
	public RedisKeyServiceImpl opsForKey(){
		return redisKeyServiceImpl;
	}
	
	/**
	 * opsForHash:获得查询Hash缓存的服务方法类.
	 * @author taofeng
	 * @return RedisHashServiceImpl
	 * @since JDK 1.7
	 */
	public RedisHashServiceImpl opsForHash(){
		return  redisHashServiceImpl;
	}
	
	/**
	 * opsForList:获得查询List缓存的服务方法类.
	 * @author taofeng
	 * @return RedisListServiceImpl
	 * @since JDK 1.7
	 */
	public RedisListServiceImpl opsForList(){
		return redisListServiceImpl;
	}
	
	/**
	 * opsForObject:获得查询Object缓存的服务方法类.
	 * @author taofeng
	 * @return RedisObjectServiceImpl 
	 * @since JDK 1.7
	 */
	public RedisObjectServiceImpl opsForObject(){
		return redisObjectServiceImpl;
	}
	
	/**
	 * opsForSet:获得查询Set缓存的服务方法类.
	 * @author taofeng
	 * @return RedisSetServiceImpl
	 * @since JDK 1.7
	 */
	public RedisSetServiceImpl opsForSet(){
		return redisSetServiceImpl;
	}
	
}
