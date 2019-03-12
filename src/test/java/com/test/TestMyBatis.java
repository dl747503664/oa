package com.test;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
public class TestMyBatis extends TestBase{
	@Test
	public void testSqlSessionFactory(){
		SqlSessionFactory ssf=
		ctx.getBean("sqlSessionFactory",
		SqlSessionFactory.class);
		System.out.println(ssf);
	}
	@Test
	public void testSqlSessionFactoryBean(){
		SqlSessionFactoryBean ssf=
		ctx.getBean("&sqlSessionFactory",
						SqlSessionFactoryBean.class);
		System.out.println(ssf);
	}
}
