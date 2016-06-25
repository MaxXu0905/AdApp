package com.ailk.cake.frame.common;

import java.net.MalformedURLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 自己实现一个简单的Singleton，扩展ContextLoaderListener类，在Web系统启动时压入Singleton。
 * 新的ContextLoaderListener类重载如下，ContextUtil中包含一个静态的ApplicationContext变量：   
 * @author 张新发
 * @date   2013-01-20
 */
public class ContextLoaderListener extends
		org.springframework.web.context.ContextLoaderListener {
	
	private static Logger logger = LoggerFactory.getLogger(ContextLoaderListener.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */

	public void contextInitialized(ServletContextEvent event) {
		
		// TODO Auto-generated method stub
		super.contextInitialized(event);
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
				
		//配合自定义ContextLoaderListener使用的工具类
		//装载ctx
		SysContext.setCtx(ctx);		
		try {
			String path = context.getResource("/").getPath();
			String contextPath = path.substring(0, path.lastIndexOf("/"));
			contextPath = contextPath.substring(contextPath.lastIndexOf("/"));
			SysContext.setContextPath(contextPath);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		logger.info("spring容器装载完成!");
		
		//SysStaticParam.init();
		
		
	}

}
