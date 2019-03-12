package com.oa.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oa.common.vo.CheckBox;
import com.oa.sys.entity.SysRole;
import com.oa.sys.vo.SysRoleMenuResult;

public interface SysRoleDao {
	/**
	 * 查询所有角色信息
	 * @return
	 */
	List<CheckBox> findObjects();
	/**
	 * 基于id查询角色以及对应的菜单信息
	 * @param id
	 * @return
	 */
	SysRoleMenuResult findObjectById(Integer id);
	/**
	 * 更新角色自身信息
	 * @param entity
	 * @return
	 */
	int updateObject(SysRole entity);
	
	/**
	 * 保存角色自身信息
	 * @param entity
	 * @return
	 */
	int insertObject(SysRole entity);
	/**
	 * 删除角色自身信息
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
    /**
     * 依据条件统计角色个数
     * @param name
     * @return
     */
	int getRowCount(@Param("name")String name);
	/**
	 * 依据条件查询角色当前页信息
	 * @param name
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysRole> findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	
}






