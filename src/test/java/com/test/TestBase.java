package com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.oa.common.cache.SynchronizedLruCache;
import com.oa.common.config.AppRootConfig;

public class TestBase {
	protected AnnotationConfigApplicationContext ctx;
	@Before //在@Test注解修饰的方法之前执行
	public void init(){
		//基于配置文件初始化spring容器
		//new ClassPathXmlApplicationContext("xxx.xml");
		//基于AppRootConfig.class中的配置初始化spring容器
		ctx=new AnnotationConfigApplicationContext(
				AppRootConfig.class);
		//问题？
		//1.为什么传入AppRootConfig.class
		//2.将AppRootConfig.class传给ApplicationContext底层要做什么？
		//3.系统底层如何对此spring框架进行初始化呢？
	}
	@Test
	public void testSpringFrameWork(){
		System.out.println(ctx);
	}
	@Test
	public void testLruCache(){
		SynchronizedLruCache lruCache=
		ctx.getBean("lruCache",SynchronizedLruCache.class);
	    System.out.println(lruCache);
	}
	@After //在@Test注解修饰的方法之后执行
	public void close(){
		ctx.close();
	}
}
