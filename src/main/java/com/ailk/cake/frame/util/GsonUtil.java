package com.ailk.cake.frame.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

/**
 * 文件名：GsonUtil.java 作者：张新发【zhangxf@asiainfo-linkage.com】 创建时间：2013-01-22
 * 功能描述：实现对json对象的转换 修改历史： 序号 作者 修改时间 修改描述 1 2
 * 
 */

public class GsonUtil {

	private static Gson gson = new Gson();

	/**
	 * 作者:张新发【zhangxf@asiainfo-linkage.com】 创建时间：2013-01-22
	 * 功能描述：实现把一个对象转换为json字符串格式 参数：
	 * 
	 * @param jsonStr
	 * @param _class
	 *            返回值：
	 * @return 修改历史： 序号 作者 修改时间 修改描述 1 2
	 */
	public static <T> T jsonToObject(String jsonStr, Class<T> _class) {
		T transerObject = gson.fromJson(jsonStr, _class);
		return transerObject;
	}

	/**
	 * 作者：张新发【zhangxf@asiainfo-linkage.com】 创建时间：2013-01-22
	 * 功能描述：实现把一个对象转换为json字符串格式 参数：
	 * 
	 * @param object
	 *            返回值：
	 * @return 修改历史 序号 作者 修改时间 修改描述 1 2
	 */
	public static <T> String objectToJson(T object) {
		return gson.toJson(object);
	}

	public static final String jsonSucc = "{\"code\":0}";

	public static final String jsonFail = "{\"code\":500}";

	public static Object buildFailJson(String message) {

		return buildFailJson(500, message);

	}

	public static Object buildSucessJson(String message) {

		return buildFailJson(0, message);

	}

	public static Object buildFailJson(int code, String message) {

		return Builder.asMap("code", code, "msg", message);

	}

	public static Object buildSucessJson(int code, String message) {

		return Builder.asMap("code", code, "msg", message);

	}

	/**
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {

		String ss = "sss";
		System.out.println(StringUtils.substring(ss, 0, 100));
		// String json =
		// "{hostIP:\"192.168.2.1\",monitorTime:\"20120312\",bipcode:\"BIP2G001\",task:{taskId:\"2323\",taskName:\"test\"}}";
		String json = "{'content': {'taskDesc': '报文格式', 'commandInfo': ["
				+ " {'unused_m': '982.23', 'used_ratio': 5, 'mount_dir': '/'}, "
				+ "{'unused_m': '5714.94', 'used_ratio': 31, 'mount_dir': '/usr'},"
				+ " {'unused_m': '7277.22', 'used_ratio': 12, 'mount_dir': '/var'},"
				+ "{'unused_m': '292.02', 'used_ratio': 97, 'mount_dir': '/tmp'},"
				+ "{'unused_m': '353.26', 'used_ratio': 83, 'mount_dir': '/home'}, "
				+ "{'unused_m': '2397.70', 'used_ratio': 42, 'mount_dir': '/opt'},"
				+ "{'unused_m': '4028.92', 'used_ratio': 74, 'mount_dir': '/oracle'}, "
				+ "{'unused_m': '73290.65', 'used_ratio': 29, 'mount_dir': '/data'}" + "]," + " 'taskStatus': '01', "
				+ "'taskId': '1', " + " 'taskValue': '97'" + " }, " + "'bipcode': 'BIP2G003', "
				+ " 'monitorTime': '20130129124524'," + "'hostIp': '132.35.77.29'" + " }";
		HashMap map = jsonToObject(json, HashMap.class);
		Set keySet = map.keySet();
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (map.get(key) instanceof String) {
				System.out.println("the key is :" + key + "  the value is :" + map.get(key));
			} else if (map.get(key) instanceof Map) {
				Iterator iterators = ((Set) ((Map) map.get(key)).keySet()).iterator();
				while (iterators.hasNext()) {
					String keys = (String) iterators.next();
					if (keys.equals("commandInfo")) {
						System.out.println(((Map) map.get(key)).get(keys) instanceof String);
						System.out.println(((Map) map.get(key)).get(keys) instanceof Map);
						System.out.println(((Map) map.get(key)).get(keys) instanceof List);
					}
					System.out.println("the key2 is :" + keys + "  the value2 is :" + ((Map) map.get(key)).get(keys));
				}
			}
		}
	}

}
