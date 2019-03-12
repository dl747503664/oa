package com.oa.common.config;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
/***
 * tomcat 启动时会加载此类，然后执行相关方法
 * 完成初始化动作(此类中要完成原web.xml中要
 * 执行的一些操作)
 * @author ta
 */
public class AppWebInitializer extends 
    AbstractAnnotationConfigDispatcherServletInitializer {
	//此类对象在执行时首先会执行onStartup方法完成一些初始化操作
	//并且会注册spring mvc前端控制器
    @Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("onStartup()");
		//super.onStartup(servletContext);
		registerContextLoaderListener(servletContext);
		registerDispatcherServlet(servletContext);
	}
  

	//官方建议在此方法中加载model(service,respository)
	@Override
	protected Class<?>[] getRootConfigClasses() {
		System.out.println("getRootConfigClasses()");
		//return new Class[]{AppDataSourceConfig.class,AppMyBatisConfig.class};
		return new Class[]{AppRootConfig.class};
	}
	//官方建议在此方法中加载View,Controller,...
	@Override
	protected Class<?>[] getServletConfigClasses() {
		System.out.println("getServletConfigClasses()");
		return new Class[]{AppMvcConfig.class};
	}
	//官方建议在此方法中定义请求映射
	@Override
	protected String[] getServletMappings() {
		System.out.println("getServletMappings()");
		//由前端控制器处理所有以.do结尾的请求
		return new String[]{"*.do"};
	}
}
