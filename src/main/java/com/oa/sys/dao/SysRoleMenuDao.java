package com.oa.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuDao {
	/**
	 * 基于角色id获取菜单id
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(
			@Param("roleIds")Integer...roleIds);

	/**
	 * 保存角色和菜单关系数据
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int insertObject(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);
	
	/**
	  * 基于角色id删除角色自身信息
	  * @param roleId
	  * @return
	  */
	 int deleteObjectsByRoleId(Integer roleId);
	/**
	 * 基于菜单id删除角色菜单关系表中的数据
	 * @param menuId
	 * @return
	 */
	int deleteObjectsByMenuId(Integer menuId);
	
	
}
