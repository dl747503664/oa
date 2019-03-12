package com.test.proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.oa.sys.dao.SysLogDao;
import com.test.TestBase;
class DaoProxy implements InvocationHandler{
	private SqlSessionFactory factory;
	public DaoProxy(SqlSessionFactory factory){
		this.factory=factory;
	}
	@Override
	public Object invoke(Object proxy,Method method,
			Object[] args) throws Throwable {
		System.out.println("invoke");
		SqlSession session=factory.openSession();
		Object dao=session.getMapper(proxy.getClass().getInterfaces()[0]);
		System.out.println("invoke.proxy="+dao.getClass().getName());
		Object result=method.invoke(dao, args);
		session.close();
		return result;
	}
	@SuppressWarnings("unchecked")
	public <T>T getProxy(Class<?> targetCls){
		return (T)Proxy.newProxyInstance(
				targetCls.getClassLoader(),
				new Class[]{targetCls},
				this);
	}
}
public class TestMyBatisProxy01 extends TestBase{
	@Test
	public void testDaoProxy(){
		SqlSessionFactory factory=ctx.getBean(
		"sqlSessionFactory",SqlSessionFactory.class);
		DaoProxy dProxy=new DaoProxy(factory);
		SysLogDao dao=dProxy.getProxy(SysLogDao.class);
		System.out.println("test.proxy="+dao.getClass().getName());
		//int rows=dao.getRowCount("admin");
		//System.out.println(rows);
		Object obj=dao.findPageObjects("admin",1, 3);
		System.out.println(obj);
	}
}







