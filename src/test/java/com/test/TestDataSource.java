package com.test;
import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;

public class TestDataSource extends TestBase {
	 @Test
	 public void testDruidDataSource()
	 throws Exception{
		 DataSource ds=
		 ctx.getBean("dataSource",DataSource.class);
	     Connection conn=
	    		 ds.getConnection();
	     System.out.println(conn);
	 }
	 @Test
	 public void testC3p0DataSource()
			 throws Exception{
		 DataSource ds=
				 ctx.getBean("c3p0",DataSource.class);
		 Connection conn=
				 ds.getConnection();
		 System.out.println(conn);
	 }
}
