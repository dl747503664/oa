package com.oa.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 借助此DAO对象操作sys_user_roles表(用户角色关系表)
 */
public interface SysUserRoleDao {
	/**
	 * 基于用户id查询用户所属角色
	 * @param id
	 * @return
	 */
	List<Integer> findRoleIdsByUserId(Integer id);
	/**
	 * 保存用户和角色的关系数据
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	int insertObjects(
			@Param("userId")Integer userId,
			@Param("roleIds")Integer[]roleIds);
	
	/**
	 * 基于用户id删除用户和角色关系数据
	 * @param userId
	 * @return
	 */
	int deleteObjectsByUserId(Integer userId);
	
	/**
	 * 基于角色id删除用户和角色的关系
	 * @param roleId
	 * @return
	 */
	int deleteObjectsByRoleId(Integer roleId);
}
