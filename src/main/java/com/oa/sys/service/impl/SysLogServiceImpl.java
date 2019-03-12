package com.oa.sys.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.oa.common.annotation.RequiresCache;
import com.oa.common.exception.ServiceException;
import com.oa.common.vo.PageObject;
import com.oa.sys.dao.SysLogDao;
import com.oa.sys.entity.SysLog;
import com.oa.sys.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {
    /**
     * @Qualifier 要与@Autowired注解结合使用。
     * 当为@Autowired修饰的属性按类型注入值，存
     * 在多个类型时，可以借助@Qualifier指定其要
     * 注入的bean的名字
     */
	@Autowired
    @Qualifier("sysLogDao")
	private SysLogDao sysLogDao;
	
	@Override
	public int deleteObjects(Integer... ids) {
		//1.对参数进行校验
		if(ids==null||ids.length==0)
		throw new IllegalArgumentException("请先选择");
		//2.执行删除操作
		int rows=0;
		try{//通过dao访问数据库服务器可能会有异常，此异常也可不再此位置处理
		 rows=sysLogDao.deleteObjects(ids);
		}catch(Throwable e){//网络中断，磁盘坏了，..
		 e.printStackTrace();
		 //给运维人员发短信
		 throw new ServiceException(e);
		}
		//3.验证删除结果
		if(rows==0)
		throw new ServiceException("记录可能已经不存在");
		//4.返回结果
		return rows;
	}
	
	@RequiresCache
	@Override
	public PageObject<SysLog> findPageObjects(
			String username, 
			Integer pageCurrent){
		System.out.println("log.findPageObjects");
		//1.对方法参数进行校验
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("页码值不正确");
		//2.依据条件查询总记录数
		int rowCount=sysLogDao.getRowCount(username);
		//3.对总记录数进行校验(等于0表示没有记录)
		if(rowCount==0)
		//throw new RuntimeException("记录不存在");//不够具体
		//NoRecordsFoundException
	    throw new ServiceException("记录不存在");
		//4.依据条件查询当前页要显示的记录
		int pageSize=3;//页面大小
		int startIndex=(pageCurrent-1)*pageSize;//起始位置
		List<SysLog> records=
		sysLogDao.findPageObjects(username,
				startIndex, pageSize);
		System.out.println("records="+records);
		//5.对查询结果进行封装并返回。
		PageObject<SysLog> pageObject=new PageObject<>();
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		/*
		方法1：
		int pageCount=rowCount/pageSize;
		if(rowCount%pageSize!=0){
			pageCount++;
		}*/ 
		//方法2
		int pageCount=(rowCount-1)/pageSize+1;
		pageObject.setPageCount(pageCount);
		return pageObject;
	}

}









