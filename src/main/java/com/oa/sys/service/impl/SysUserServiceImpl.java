package com.oa.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.oa.common.annotation.RequiresLog;
import com.oa.common.exception.ServiceException;
import com.oa.common.vo.JsonResult;
import com.oa.common.vo.PageObject;
import com.oa.sys.dao.SysUserDao;
import com.oa.sys.dao.SysUserRoleDao;
import com.oa.sys.entity.SysUser;
import com.oa.sys.service.SysUserService;
import com.oa.sys.vo.SysUserDeptResult;
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public int findObjectByColumn(String columnName,
			String columnValue) {
		if(StringUtils.isEmpty(columnName))
	    throw new IllegalArgumentException("参数名不能为空");
		if(StringUtils.isEmpty(columnValue))
		throw new IllegalArgumentException("参数值不能为空");
		int count=sysUserDao.findObjectByColumn(columnName, columnValue);
		if(count>0)
		throw new ServiceException(columnValue + "已存在");
		return count;
	}
	
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		//1.对参数进行校验
		if(id==null)
		throw new IllegalArgumentException("参数值无效");
		//2.查询用户以及对应的部门信息
		SysUserDeptResult result=
		sysUserDao.findObjectById(id);
		//3.查询用户对应的角色信息
		List<Integer> roleIds=
		sysUserRoleDao.findRoleIdsByUserId(id);
		//4.查询结果进行封装
		Map<String,Object> map=new HashMap<>();
		map.put("user", result);//key要与页面取值方式保持一致
		map.put("roleIds", roleIds);
		return map;
	}
	
	@Override
	public int updateObject(SysUser entity, 
			Integer[] roleIds) {
		//1.对参数进行校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		//....
		//2.保存用户自身信息
		int rows=sysUserDao.updateObject(entity);
		//3.保存用户与角色关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObjects(
				entity.getId(),
				roleIds);
		return rows;
	}
	@Override
	public int saveObject(SysUser entity, 
			Integer[] roleIds) {
		
		//1.对参数进行校验
		if(entity==null)
		throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
	    throw new IllegalArgumentException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
		throw new IllegalArgumentException("密码不能为空");
		if(roleIds==null||roleIds.length==0)
	    throw new IllegalArgumentException("必须指定其角色");
		//....
		//2.保存用户自身信息
		//2.1对密码进行加密
		//使用随机字符串作为salt(盐值)
		String salt=UUID.randomUUID().toString();
		entity.setSalt(salt);
		//密码，盐值加密
		int rows=sysUserDao.insertObject(entity);
		//3.保存用户与角色关系数据
		sysUserRoleDao.insertObjects(
				entity.getId(),
				roleIds);
		return rows;
	}
	
	@RequiresLog("用户禁用启用")
	@Override
	public int validById(Integer id, Integer valid) {
	    if(id==null||id<1)
	    throw new IllegalArgumentException("id值无效");
	    if(valid!=0&&valid!=1)
	    throw new IllegalArgumentException("状态值不正确");
		return 0;
	}
	
	
	@Override
	public PageObject<SysUserDeptResult> 
	             findPageObjects(
	              String username,Integer pageCurrent) {
		//1.验证参数有效性
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("页码值不正确");
		//2.基于条件查询总记录数并进行验证
		int rowCount=sysUserDao.getRowCount(username);
		if(rowCount==0)
		throw new ServiceException("没有找到对应记录");
		//3.基于条件查询当前页记录
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysUserDeptResult> records=
		sysUserDao.findPageObjects(username,
				startIndex, pageSize);
		//4.对查询结果进行封装并返回
		PageObject<SysUserDeptResult> pageObject=
		new PageObject<>();
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageCount((rowCount-1)/pageSize+1);
		return pageObject;
	}

	@Override
	public SysUser findUserByUsername(String username) {
		if(username==null){
			throw new IllegalArgumentException("用户名不能为空");
		}
		return sysUserDao.findUserByUserName(username);
	}

	@Override
	public JsonResult loginIn(String username, String password) {
		System.out.println(1);
		if(username==null){
			throw new IllegalArgumentException("用户名不能为空");
		}
		if(password==null){
			throw new IllegalArgumentException("密码不能为空");
		}
		SysUser user = sysUserDao.findUserByUserName(username);
		if(password.equals(user.getPassword())){
			JsonResult jsonResult=new JsonResult("login ok");
			System.out.println(2);
			jsonResult.setData(user);
			return jsonResult;
		}
		System.out.println(3);
		return null;
	}
}



