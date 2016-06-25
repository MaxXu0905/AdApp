package com.ailk.cake.frame.cache.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.ailk.cake.frame.cache.Cache;
import com.ailk.cake.frame.cache.CacheException;
import com.ailk.cake.frame.cache.CacheProvider;
import com.ailk.cake.frame.util.Constants;
import com.ailk.cake.frame.util.LogUtil;

/**
 * 文件名：RedisCacheProvider.java 作者：张新发【zhangxf@asiainfo-linkage.com】
 * 创建日期：2013-03-12 描述：RedisCache的提供实现类
 * 
 */
public class RedisCacheProvider implements CacheProvider {

	private static final Logger logger = LoggerFactory.getLogger(RedisCacheProvider.class);

	private static JedisPool jedisPool;

	private Properties prop;

	/**
	 * 创建redis连接池,并返回一个可用的连接
	 */
	public Cache buildCache(String regionName, boolean autoCreate) throws CacheException {

		if (regionName == null || "".equals(regionName)) {

			throw new CacheException("缓存名字(regionName)不能为空!");

		}

		synchronized (this) {

			if (prop == null) {
				loadProperties();
			}

			if (jedisPool == null) {

				logger.info("====================开始创建redis连接池====================");

				JedisPoolConfig config = new JedisPoolConfig();

				config.setMaxActive(prop.getProperty("redis.maxActive") != null ? Integer.parseInt(prop
						.getProperty("redis.maxActive")) : 20);

				config.setMaxIdle(prop.getProperty("redis.maxIdle") != null ? Integer.parseInt(prop
						.getProperty("redis.maxIdle")) : 5);

				config.setMaxWait(prop.getProperty("redis.maxWait") != null ? Integer.parseInt(prop
						.getProperty("redis.maxWait")) : 1000);

				config.setTestOnBorrow(false);

				try {

					RedisCacheProvider.jedisPool = new JedisPool(config, prop.getProperty("redis.ip"),
							Integer.parseInt(prop.getProperty("redis.port")));

				} catch (Exception e) {

					LogUtil.loggerError(e, this);

					throw new CacheException("创建Redis连接池失败!");
				}

				logger.info("====================redis连接池创建完毕====================");

			}
		}

		Jedis jedis = jedisPool.getResource();

		return new RedisCache(jedis, regionName);
	}

	public void start() throws CacheException {

		logger.info("读取Redis连接池的配置文件 Start!");

		loadProperties();

		logger.info("读取Redis连接池的配置文件 End!");

	}

	public void stop() {

		logger.info("断开与Redis的连接池开始!");

		if (jedisPool != null) {

			jedisPool.destroy();

		}

		logger.info("断开与Redis的连接池结束!");

	}

	public static void releaseConnection(Jedis jedis) {

		jedisPool.returnBrokenResource(jedis);

	}

	/**
	 * 读取redispool的配置文件
	 */
	private void loadProperties() {

		if (prop != null)
			return;

		prop = new Properties();

		InputStream is = null;

		try {

			is = RedisCacheProvider.class.getResourceAsStream(Constants.RedisConfigPath);

			prop.load(is);

		} catch (Exception e) {

			logger.error("loadProperties()", e);

		} finally {

			try {

				if (is != null) {

					is.close();

				}

			} catch (IOException e) {

				logger.error("[关闭加载配置文件输入流出错]", e);

			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
