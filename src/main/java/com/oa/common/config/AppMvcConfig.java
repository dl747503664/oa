package com.oa.common.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SetFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.oa.common.interceptor.UserInterceptor;

/**
 * 在此配置类中实现spring mvc 资源对象的整合
 * @author ta
 */
@ComponentScan(
    value="com.oa",
    useDefaultFilters=false,//取消默认过滤器
    includeFilters={//只加载有指定注解修饰的类
      @Filter(type=FilterType.ANNOTATION,
              classes={Controller.class,
                       ControllerAdvice.class})})
@EnableWebMvc //启用mvc默认配置(内置很多类型转换器bean对象)
public class AppMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureViewResolvers(
			ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/pages/",".html");
	}
	/**
	 * 注册拦截器
	 */
	@Bean
	public UserInterceptor SetBean(){
		System.out.println("注入拦截器");
		return new UserInterceptor();
	}
	@Override
	public void addInterceptors(
			InterceptorRegistry registry) {
		 String [] exculudes = new String[]{"/*.html","/html/**","/js/**","/css/**","/images/**"};
		//注册拦截器对象
		registry.addInterceptor(SetBean())
		//配置要拦截的url
		.addPathPatterns("/**")
		.excludePathPatterns(exculudes);
	}

}






