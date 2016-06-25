package com.ailk.cake.frame.common;

import org.springframework.context.ApplicationContext;

/**
 * 文件名：SysContext.java
 * 作者：张新发【zhangxf@asiainfo-linkage.com】
 * 创建日期：2013-01-23
 * 描述：用于前端action调用获取spring管理的bean,实现松耦合
 *
 */

public class SysContext {
	
	private static ApplicationContext ctx = null;
	
	private static String contextPath;
	
	public static Object getBean(String objName)
	{	
		return ctx.getBean(objName);
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static void setContextPath(String contextPath) {
		SysContext.contextPath = contextPath;
	}

	public static void setCtx(ApplicationContext ctx) {
		SysContext.ctx = ctx;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
