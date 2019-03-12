package com.test.service;
import org.junit.Assert;
import org.junit.Test;

import com.oa.common.vo.PageObject;
import com.oa.sys.entity.SysLog;
import com.oa.sys.service.SysLogService;
import com.test.TestBase;
public class TestSysLogService extends TestBase {
	
	@Test
	public void testDeleteObjects(){
		//1.获取SysLogService对象
			SysLogService sysLogService=
		    ctx.getBean("sysLogServiceImpl",
						SysLogService.class);
	    //2.执行删除操作
			int rows=sysLogService.deleteObjects(75,76);
			//断言测试(当方法返回值为false，程序不会继续向后执行)
			Assert.assertEquals(2,rows);
	}
	
	@Test
	public void testFindPageObjects(){
		//1.获取SysLogService对象
		SysLogService sysLogService=
		ctx.getBean("sysLogServiceImpl",
				SysLogService.class);
		//2.执行分页查询操作
		PageObject<SysLog> po=
		sysLogService.findPageObjects(
				"admin",1);
		System.out.println(po.getRowCount());
		System.out.println(po.getPageCount());
	}
}
