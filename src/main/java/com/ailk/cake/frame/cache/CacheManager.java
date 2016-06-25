package com.ailk.cake.frame.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.cake.frame.cache.redis.RedisCacheProvider;

/**
 * 
 * 缓存助手
 * 
 * @author Winter Lau
 */

public class CacheManager {

	private final static Logger log = LoggerFactory.getLogger(CacheManager.class);

	private static CacheProvider provider;

	static {

		initCacheProvider("com.asiainfo.cloud.frame.cache.redis.RedisCacheProvider");

	}

	private static void initCacheProvider(String prv_name) {

		try {

			CacheManager.provider = (CacheProvider) Class.forName(prv_name).newInstance();

			CacheManager.provider.start();

			log.info("Using CacheProvider : " + provider.getClass().getName());

		} catch (Exception e) {

			log.error("Unabled to initialize cache provider:" + prv_name + ", using ehcache default.", e);

			CacheManager.provider = new RedisCacheProvider();

		}

	}

	private final static Cache _GetCache(String cache_name, boolean autoCreate) {

		if (provider == null) {

			provider = new RedisCacheProvider();

		}

		return provider.buildCache(cache_name, autoCreate);

	}

	/**
	 * 
	 * 获取缓存中的数据
	 * 
	 * @param name
	 * 
	 * @param key
	 * 
	 * @return
	 */

	public final static Object get(String name, String key) {

		// System.out.println("GET1 => " + name+":"+key);

		if (name != null && key != null)

			return _GetCache(name, true).get(key);

		return null;

	}

	/**
	 * 
	 * 获取缓存中的数据
	 * 
	 * @param name
	 * 
	 * @return
	 */

	public final static List<Object> getByName(String name) {

		List<Object> list = new ArrayList<Object>();
		if (name != null) {
			Cache cache = _GetCache(name, true);
			@SuppressWarnings("unchecked")
			List<String> keys = cache.keys();
			if (keys == null)
				return null;
			else {
				for (int i = 0; i < keys.size(); i++) {
					Object obj = cache.get(keys.get(i));
					if (obj != null)
						list.add(obj);
				}
			}
		}

		return null;

	}

	/**
	 * 
	 * 获取缓存中的数据
	 * 
	 * @param <T>
	 * 
	 * @param resultClass
	 * 
	 * @param name
	 * 
	 * @param key
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public final static <T> T get(Class<T> resultClass, String name, Serializable key) {

		// System.out.println("GET2 => " + name+":"+key);

		if (name != null && key != null)

			return (T) _GetCache(name, true).get(key);

		return null;

	}

	/**
	 * 
	 * 写入缓存
	 * 
	 * @param name
	 * 
	 * @param key
	 * 
	 * @param value
	 */

	public final static void set(String name, String key, Object value) {

		// System.out.println("SET => " + name+":"+key+"="+value);

		if (name != null && key != null && value != null)

			_GetCache(name, true).put(key, value);

	}

	/**
	 * 
	 * 清除缓冲中的某个数据
	 * 
	 * @param name
	 * 
	 * @param key
	 */

	public final static void evict(String name, String key) {

		if (name != null && key != null)

			_GetCache(name, true).remove(key);

	}

	/**
	 * 
	 * 清除缓冲中的某个数据
	 * 
	 * @param name
	 * 
	 * @param key
	 */

	public final static void justEvict(String name, Serializable key) {

		if (name != null && key != null) {

			Cache cache = _GetCache(name, false);

			if (cache != null)

				cache.remove(key);

		}

	}

	/**
	 * 为数据指定失效缓存时间
	 * 
	 * @param name
	 * @param key
	 */
	public final static void expire(String name, String key) {

		if (name != null && key != null) {

			_GetCache(name, true).expire(key, 3000);

		}
	};

	public static void main(String[] args) {
		String cacheName = "testCache";
		String key = "key";
		String value = "oschina";
		// set(cacheName, key, value);
		String gValue = (String) get(cacheName, key);// A
		if (gValue != null) {
			System.out.println("获取缓存成功：" + gValue);
		} else {
			System.out.println("获取缓存为空，设置缓存");
			set(cacheName, key, value);
			System.out.println("设置缓存之后获取缓存：" + (String) get(cacheName, key));
		}

	}

}
