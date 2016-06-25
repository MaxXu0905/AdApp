package com.ailk.cake.frame.util;

import java.text.MessageFormat;

/**
 * 文件名：StringUtil.java
 * 作者：张新发【zhangxf@asiainfo-linkage.com】
 * 创建时间：2013-01-30
 * 所属模块：公共工具组件
 * 功能描述：实现字符串的处理
 *
 */
public class StringUtil {
	
	
	public  static String   replace(String sourceString,String ... replaceString) {
		String  resultString  = "";
		MessageFormat  messageFormat = new MessageFormat(sourceString);
		resultString = messageFormat.format(replaceString);
		 return resultString;
	}
	
	
	public static boolean isBlank(String str)
	{
		if(str == null || "".equals(str))
			return true;
		else 
			return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String  ss =  "主机{0},磁盘空间使用达到{1}%!!!!";
		String IP = "132.35.77.29";
		String VALUE = "90";
		String[] testArgs = {IP,VALUE};
		System.out.println(replace(ss,testArgs));

	}

}
