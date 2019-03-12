package com.oa.sys.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.oa.common.exception.ServiceException;
import com.oa.common.vo.CheckBox;
import com.oa.common.vo.PageObject;
import com.oa.sys.dao.SysRoleDao;
import com.oa.sys.dao.SysRoleMenuDao;
import com.oa.sys.dao.SysUserRoleDao;
import com.oa.sys.entity.SysRole;
import com.oa.sys.entity.SysUser;
import com.oa.sys.service.SysRoleService;
import com.oa.sys.vo.SysRoleMenuResult;

@Service
public class SysRoleServiceImpl implements SysRoleService {
  
	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public List<CheckBox> findObjects() {
		return sysRoleDao.findObjects();
	}
	
	@Override
	public SysRoleMenuResult findObjectById(Integer id) {
		if(id==null||id<1)
		throw new IllegalArgumentException("id值无效");
		SysRoleMenuResult result=sysRoleDao.findObjectById(id);
		if(result==null)
		throw new ServiceException("记录可能已经不存在");
		return result;
	}
	
	@Override
	public int updateObject(SysRole entity, 
			Integer[] menuIds) {
		//1.参数验证
		if(entity==null)
		throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
		throw new IllegalArgumentException("名字不能为空");
		if(menuIds==null||menuIds.length==0)
		throw new ServiceException("必须为角色赋予权限");
		//2.保存角色自身信息
		int rows=sysRoleDao.updateObject(entity);
		//3.保存角色和菜单关系数据
		//3.1删除原先关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		//3.2添加新的关系数据
		sysRoleMenuDao.insertObject(entity.getId(),
				menuIds);
		return rows;
	}
	@Override
	public int saveObject(SysRole entity, 
			Integer[] menuIds) {
		//1.参数验证
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("名字不能为空");
		if(menuIds==null||menuIds.length==0)
			throw new ServiceException("必须为角色赋予权限");
		return 0;
	}
	
	@Override
	public int deleteObject(Integer id) {
		//1.参数合法性校验
		if(id==null||id<1)
		throw new IllegalArgumentException("id值不合法");
		//2.删除角色自身信息
		int rows=sysRoleDao.deleteObject(id);
		if(rows==0)
		throw new ServiceException("记录可能已经不存在");
		//3.删除角色菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		//4.删除角色用户关系数据
		sysUserRoleDao.deleteObjectsByRoleId(id);
		return rows;
	}
	
	@Override
	public PageObject<SysRole> findPageObjects(
			String username, 
			Integer pageCurrent){
		//1.对方法参数进行校验
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("页码值不正确");
		//2.依据条件查询总记录数
		int rowCount=sysRoleDao.getRowCount(username);
		//3.对总记录数进行校验(等于0表示没有记录)
		if(rowCount==0)
		//throw new RuntimeException("记录不存在");//不够具体
		//NoRecordsFoundException
	    throw new ServiceException("记录不存在");
		//4.依据条件查询当前页要显示的记录
		int pageSize=3;//页面大小
		int startIndex=(pageCurrent-1)*pageSize;//起始位置
		List<SysRole> records=
		sysRoleDao.findPageObjects(username,
				startIndex, pageSize);
		System.out.println("records="+records);
		//5.对查询结果进行封装并返回。
		PageObject<SysRole> pageObject=new PageObject<>();
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









