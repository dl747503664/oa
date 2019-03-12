package com.test.dao;
import java.util.List;

import org.junit.Test;

import com.oa.sys.dao.SysLogDao;
import com.oa.sys.entity.SysLog;
import com.test.TestBase;
public class TestSysLogDao extends TestBase{
	  @Test
	  public void testGetRowCount(){
		 //1.获取SysLogDao对象
		 SysLogDao dao=
		 ctx.getBean("sysLogDao",SysLogDao.class);
		 //ctx.getBean("sysLogDaoImpl",SysLogDao.class);
		 //2.调用dao对象的getRowCount方法
		 int rowCount=dao.getRowCount("admin");
		 //3.输出查询结果
		 System.out.println(rowCount);
	  }
	  @Test
	  public void testFindPageObjects(){
		  //1.获取SysLogDao对象
		  SysLogDao dao=
		  ctx.getBean("sysLogDao",SysLogDao.class);
		  //2.调用dao对象的findPageObjects方法
		  List<SysLog> list=
		  dao.findPageObjects("",0, 3);
		  //3.输出查询结果
		  System.out.println(list.size());
	  }
	  @Test
	  public void testDeleteObjects(){
		  //1.获取SysLogDao对象
		  SysLogDao dao=
				  ctx.getBean("sysLogDao",SysLogDao.class);
		  //2.调用dao对象的findPageObjects方法
		  int rows=dao.deleteObjects(78,79);
		  //3.输出查询结果
		  System.out.println("delete rows "+rows);
	  }
}







