package com.ailk.cake.frame.cache.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	
	/**
	 * 文件名：SerializeUtil.java
	 * 作者：张新发【zhangxf@asiainfo-linkage.com】
	 * 创建时间：2013-03-12
	 * 功能描述：实现对象的序列化与反序列化并存储，方便存储到redis缓存中
	 */
	
	/**
	 * 对对象序列化
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		
		ObjectOutputStream oos = null;
		
		ByteArrayOutputStream baos = null;
		
		try {
		//序列化
		baos = new ByteArrayOutputStream();
		
		oos = new ObjectOutputStream(baos);
		
		oos.writeObject(object);
		
		byte[] bytes = baos.toByteArray();
		
		return bytes;
		
		} catch (Exception e) {
			
		}
		
		return null;
	}
	 
	/**
	 * 反序列化得到对象
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		
		ByteArrayInputStream bais = null;
		
		try {
			
		//反序列化
		bais = new ByteArrayInputStream(bytes);
		
		ObjectInputStream ois = new ObjectInputStream(bais);
		
		return ois.readObject();
		
		} catch (Exception e) {
			
		}
		
		return null;
	}

}
