package com.oa.sys.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.oa.common.vo.Node;
import com.oa.sys.entity.SysMenu;

public interface SysMenuDao {
	/**
	 * 基于菜单id获取权限标识
	 * @param menuIds
	 * @return
	 */
	List<String> findPermissions(
			@Param("menuIds")
			Integer... menuIds);
	/**
	 * 通过此方法将entity对象持久化到数据库
	 * @param entity
	 * @return
	 * 
	 */
	int updateObject(SysMenu entity);
	/**
	 * 通过此方法将entity对象持久化到数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysMenu entity);
	
	/**
	 * 查询zTree中要显示的menu节点信息
	 * @return
	 */
	List<Node> findZtreeMenuNodes();
	
	/**
	 * 统计菜单对应子菜单的数量
	 * @param id 菜单id
	 * @return
	 */
	int getChildCount(Integer id);
	
	/**
	 * 基于id删除菜单对象
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	
	/**
	 * 通过此方法查询所有菜单以及对应的上级菜单信息
	 * @return
	 * 1)一个Map对象用于封装表中的一行记录(key为字段名，值为字段value)
	 * 2)多条菜单记录会封装为多个map，多个map再存储到list集合
	 */
	List<Map<String,Object>> findObjects();
}







