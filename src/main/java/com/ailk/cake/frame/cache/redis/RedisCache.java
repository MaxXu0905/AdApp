package com.ailk.cake.frame.cache.redis;

import java.util.List;

import redis.clients.jedis.Jedis;

import com.ailk.cake.frame.cache.Cache;
import com.ailk.cake.frame.cache.CacheException;




/**
 * 文件名：RedisCache.java
 * 作者：张新发【zhangxf@asiainfo-linkage.com】
 * 创建时间：2013-03-12
 * 功能描述：Redis缓存的封装类
 *
 */

public class RedisCache implements Cache {
	
	private  Jedis jedis;
	
	private  String prefixName;
	
	public RedisCache() {
		
	}
	
	public  RedisCache(Jedis jedis) {
		
		this.jedis = jedis;
	}
	
	public  RedisCache(Jedis jedis,String  prefixName){
		
		this.jedis = jedis;
		
		this.prefixName = prefixName;
	}
	

	public Object get(Object key) throws CacheException {
		
		try{
			if(key == null) {
				
				return null;
				
			}else {
				
				return  SerializeUtil.unserialize(jedis.get((prefixName + "." + key).getBytes()));
				
			}
		}catch (Exception e) {
			throw  new  CacheException(e);
		}finally{
			
			RedisCacheProvider.releaseConnection(jedis);
			
		}
	}

	public void put(Object key, Object value) throws CacheException {
		
		try { 
			
			jedis.set((prefixName + "." +  key).getBytes(),SerializeUtil.serialize(value));
			
		}catch (Exception e) {
			
		}finally{
			
			RedisCacheProvider.releaseConnection(jedis);
			
		}

	}

	public void update(Object key, Object value) throws CacheException {
		
		 put(key,value);
		 
	}

	@SuppressWarnings("rawtypes")
	public List keys() throws CacheException {
		
		boolean result = true;
		
		if(result) {
			
		   	throw new CacheException("暂不支持获取所有的key!");
		   	
		}
		
		return null;
	}

	public void remove(Object key) throws CacheException {
		
		try{
			
			jedis.del((prefixName + "." +  key).getBytes());
			
		}catch (Exception e) {
			
			throw new CacheException(e);
			
		}finally{
			
			RedisCacheProvider.releaseConnection(jedis);
			
		}
		

	}

	/**
	 * 
	 * 清空所有的缓存
	 * 谨慎使用
	 * 
	 */
	public void clear() throws CacheException {
		try{
			
			jedis.flushAll();
			
		}catch(Exception e){
			throw  new CacheException(e);
		}finally{
			
			RedisCacheProvider.releaseConnection(jedis);
			
		}
		
	}
	
	

	
	public void destroy() throws CacheException {
		 

	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

	/**
	 * 判断是否存在某个key的对象
	 */
	
	public boolean exists(Object key) throws CacheException {
		Boolean  result  = false;
		try{
			result = jedis.exists((prefixName + "." +  key).getBytes());
		}catch (Exception e) {
			throw new CacheException(e);
		}finally{
			
			RedisCacheProvider.releaseConnection(jedis);
			
		}
		return result;
	}

	/**
	 * 给某个key加一个失效时间默认的为秒
	 * 
	 */
	public boolean expire(Object key, int seconds) throws CacheException {
		boolean result = false;
		long  ischange = 0;
		try{
			ischange =  jedis.expire((prefixName + "." +  key).getBytes(), seconds);
			
			if(ischange == 1 ) {
				result = true;
			}
		}catch(Exception e) {
			throw  new CacheException(e);
		}finally{
			
			RedisCacheProvider.releaseConnection(jedis);
			
		}
		return result;
	}

}
