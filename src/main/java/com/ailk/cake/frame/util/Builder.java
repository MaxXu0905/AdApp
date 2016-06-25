package com.ailk.cake.frame.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/** 与容器类有关的builder和util */

public class Builder {
	
	public static Map<String, String> asStringMap(Object... args) {
		if (args.length % 2 != 0)
			throw new IllegalArgumentException("args.length = " + args.length);

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < args.length - 1; i += 2)
			map.put(String.valueOf(args[i]), args[i + 1] == null ? "" : String.valueOf(args[i + 1]));
		return map;
	}

	public static Map<String, Object> asMap(Object... args) {
		if (args.length % 2 != 0)
			throw new IllegalArgumentException("args.length = " + args.length);

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (int i = 0; i < args.length - 1; i += 2)
			map.put(String.valueOf(args[i]), args[i + 1]);
		return map;
	}

	public static <T> Set<T> asSet(T... args) {
		return new HashSet<T>(Arrays.asList(args));
	}

	/**
	 * 根据from更新to的内容。用于更新cacheMap，通常to为使用中的ConcurrentHashMap
	 */
	public static <K, V> void update(Map<K, V> from, Map<K, V> to) {
		// mark all keys as spare
		Set<K> spareKeys = new HashSet<K>(to.keySet());

		for (Entry<K, V> entry : from.entrySet()) {
			to.put(entry.getKey(), entry.getValue());
			spareKeys.remove(entry.getKey()); // mark
		}

		// remove spare
		for (K key : spareKeys)
			to.remove(key);
	}

}
