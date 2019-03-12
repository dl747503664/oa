package com.oa.sys.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.oa.common.annotation.RequiresCache;
import com.oa.common.annotation.RequiresData;
import com.oa.common.annotation.RequiresLog;
import com.oa.common.exception.ServiceException;
import com.oa.common.vo.Node;
import com.oa.sys.dao.SysMenuDao;
import com.oa.sys.dao.SysRoleMenuDao;
import com.oa.sys.entity.SysMenu;
import com.oa.sys.service.SysMenuService;
/**
 * 声明式事务控制(底层借助代理机制控制事务)
 * 1)基于xml(了解)
 * 2)基于注解(趋势,例如spring boot)
 * 注解方式的声明式事务控制需要借助@Transactional
 * 注解对类或方法进行描述。
 */
@Transactional(rollbackFor=Throwable.class,
               timeout=5,
               isolation=Isolation.READ_COMMITTED)
@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Transactional(timeout=3)
	@RequiresData
	@Override
	public int updateObject(SysMenu entity) {
		//1.验证参数合法性
		if(entity==null)
			throw new IllegalArgumentException("参数异常");
		//2.验证对象属性
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("菜单名不能为空");
		//.....
		//3.保存菜单信息到数据
		//entity.setModifiedUser(ShiroUtils.getUser().getUsername());
		int rows=sysMenuDao.updateObject(entity);
		//4.返回结果
		return rows;
	}
	@RequiresData
	@Override
	public int saveObject(SysMenu entity) {
		//1.验证参数合法性
		if(entity==null)
		throw new IllegalArgumentException("参数异常");
		//2.验证对象属性
		if(StringUtils.isEmpty(entity.getName()))
		throw new IllegalArgumentException("菜单名不能为空");
		//.....
		//3.保存菜单信息到数据
		//entity.setCreatedUser(ShiroUtils.getUser().getUsername());
		//entity.setModifiedUser(ShiroUtils.getUser().getUsername());
		int rows=sysMenuDao.insertObject(entity);
		//4.返回结果
		return rows;
	}
	/**
	 * 假如是读事务，建议readOnly属性的值为true，
	 * readOnly默认为false
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> list=sysMenuDao.findZtreeMenuNodes();
		if(list==null||list.size()==0)
		throw new ServiceException("记录不存在");
		return list;
	}
	@Override
	public int deleteObject(Integer id) {
		//1.验证参数有效性
		if(id==null||id<1)
		throw new IllegalArgumentException("参数id无效");
		//2.统计菜单是否有子菜单，并进行验证
		int rowCount=sysMenuDao.getChildCount(id);
		if(rowCount>0)
		throw new ServiceException("请先删除子菜单");
		//3.删除当前菜单信息
		int rows=sysMenuDao.deleteObject(id);
		if(rows==0)
		throw new ServiceException("记录可能已经不存在");
		//4.删除菜单角色的关系数据
		int count=sysRoleMenuDao.deleteObjectsByMenuId(id);
		//if(count>0)throw new ServiceException("关系数据删除失败");
		return rows;
	}

	@RequiresLog("菜单查询")
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)
	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list=sysMenuDao.findObjects();
		if(list==null||list.size()==0)
		throw new ServiceException("没有对应数据");
		return list;
	}

}
