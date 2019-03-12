package com.oa.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oa.sys.entity.SysUser;
import com.oa.sys.vo.SysUserDeptResult;

public interface SysUserDao {
	/**
	 * 基于用户名查询用户对象
	 * @param username
	 * @return
	 */
	SysUser findUserByUserName(String username);
	
	int findObjectByColumn(
		@Param("columnName")String columnName,
		@Param("columnValue")String columnValue);
	
    /**
     * 更新用户自身信息
     * @param entity
     * @return
     */
    int updateObject(SysUser entity);
	
	/**
	 * 基于用户id查询用户自身信息以及对应的部门信息
	 * @param id
	 * @return
	 */
	SysUserDeptResult findObjectById(Integer id);
	/**
	 * 保存用户自身信息
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	
    int validById(@Param("id")Integer id,
			      @Param("valid")Integer valid,
			      @Param("modifiedUser")String modifiedUser);

	 /**
	  * 基于用户名查询记录总数
	  * @param username
	  * @return
	  */
	 int getRowCount(@Param("username")String username);
	 /**
	  * 基于用户名查询当前页记录
	  * @param username 查询条件
	  * @param startIndex 起始页位置
	  * @param pageSize 页面大小
	  * @return 当前页记录
	  */
	 List<SysUserDeptResult> findPageObjects(
			 @Param("username")String username,
			 @Param("startIndex")Integer startIndex,
			 @Param("pageSize")Integer pageSize);
}








