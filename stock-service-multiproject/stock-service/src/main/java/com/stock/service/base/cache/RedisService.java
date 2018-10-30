/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.cache
 * 类/接口名: RedisService
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-15上午11:26:24
 * 作者		: taofeng
 * 修改历史	: 2017-5-15上午11:26:24
 */
package com.stock.service.base.cache;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import com.stock.common.util.ObjectUtils;

/**
 * ClassName	: RedisService
 * Function		: Redis缓存服务抽象类.其中选择db可以通过connection.select(x)来指定某一个db
 * date 		: 2017-5-15上午11:26:24
 * @author     : taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
public abstract class RedisService {
	
	/** 日志对象 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 构造方法注入 */
	protected RedisTemplate<Serializable, Serializable> redisTemplate;
	
	protected RedisTemplate<String,String> customStringRedisTemplate;
	

	/**
	 * customStringRedisTemplate. 
	 * @return  the customStringRedisTemplate 
	 * @since   JDK 1.7 
	 */
	public RedisTemplate<String,String> getCustomStringRedisTemplate() {
		return customStringRedisTemplate;
	}

	/**
	 * customStringRedisTemplate. 
	 * @param   customStringRedisTemplate    the customStringRedisTemplate to set 
	 * @since   JDK 1.7 
	 */
	public void setCustomStringRedisTemplate(RedisTemplate<String,String> customStringRedisTemplate) {
		this.customStringRedisTemplate = customStringRedisTemplate;
	}

	/**
	 * redisTemplate. 
	 * @return  the redisTemplate 
	 * @since   JDK 1.7 
	 */
	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}

	/**
	 * redisTemplate. 
	 * @param   redisTemplate    the redisTemplate to set 
	 * @since   JDK 1.7 
	 */
	public void setRedisTemplate(
			RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * RedisService:Redis抽象类构造方法.
	 * @author taofeng 
	 * @param redisTemplate 
	 * @since JDK 1.7
	 */
	public RedisService(RedisTemplate<Serializable, Serializable> redisTemplate,RedisTemplate<String,String> customStringRedisTemplate) {
		super();
		Assert.notNull(redisTemplate,"redisTemplate is null");
		Assert.notNull(customStringRedisTemplate,"stringRedisTemplate is null");
		this.redisTemplate = redisTemplate;
		this.customStringRedisTemplate = customStringRedisTemplate;
	}
	
	/**
	 * 获取byte[]类型Key
	 * @param key
	 * @return byte[]
	 */
	protected static byte[] getBytesKey(Object object){
		if(object instanceof String){
    		try {
				return ((String) object).getBytes("CHARSET");
			} catch (UnsupportedEncodingException e) {
				return ((String) object).getBytes();
			}
    	}else{
    		return ObjectUtils.serialize(object);
    	}
	}
	/**
	 * Object转换byte[]类型
	 * @param key
	 * @return byte[]
	 */
	protected static byte[] toBytes(Object object){
    	return ObjectUtils.serialize(object);
	}

	/**
	 * byte[]型转换Object
	 * @param key
	 * @return Object
	 */
	protected static Object toObject(byte[] bytes){
		return ObjectUtils.unserialize(bytes);
	}
	
}
