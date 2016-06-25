package com.ailk.cake.frame.cache;

import java.util.List;

/**
 * 文件名：Cache.java
 * 作者：张新发【zhangxf@asiainfo-linkage.com】
 * 创建时间：2013-03-12
 * 描述：
 *      统一缓存接口
 * 修改历史：
 *         序号                      作者                             修改时间                                描述
 *         1
 *         2
 *
 */

public interface Cache {
	
	 /**  

     * 从缓存中获取某个特定的对象

     * @param key  

     * @return the cached object or <tt>null</tt>  

     * @throws CacheException  

     */ 

    public Object get(Object key) throws CacheException;  

       

    /**  

     * 新增缓存对象到缓存中

     * @param key  

     * @param value  

     * @throws CacheException  

     */ 

    public void put(Object key, Object value) throws CacheException;  

       

    /**  

     * 更新缓存中的某个对象

     * @param key  

     * @param value  

     * @throws CacheException  

     */ 

    public void update(Object key, Object value) throws CacheException;  


    /**
     * 获取缓存中存储的所有的KEY
     * RedisCache暂时不支持
     * @return
     * @throws CacheException
     */
    @SuppressWarnings("rawtypes")  

    public List keys() throws CacheException ;  

       

    /**  

     * 从缓存中清除某个特定的对象

     */ 

    public void remove(Object key) throws CacheException;  
    
    
    
       
    /**  

     * 清空缓存

     */ 

    public void clear() throws CacheException;  

       

    /**  

     * Clean up  

     */ 

    public void destroy() throws CacheException;  
    
    /**
     * 判断是否存在某个KEY
     * @param key
     * @return
     * @throws CacheException
     */
    
    public boolean  exists(Object key) throws  CacheException;
    
    /**
     * 给key加一个失效时间
     * @param key
     * @param  seconds 秒
     * @return
     */
    public  boolean   expire(Object key,int seconds) throws CacheException;


}
