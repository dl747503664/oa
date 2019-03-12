package com.oa.sys.vo;
import java.util.List;
/***
 * 借助此对象封装角色和菜单相关信息
 * @author ta
 */
public class SysRoleMenuResult {
	/**角色id*/
	private Integer id;
	/**角色名称*/
	private String name;
	/**角色描述*/
	private String note;
	/**角色对应的菜单id*/
	private List<Integer> menuIds;
	public List<Integer> getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(List<Integer> menuIds) {
		this.menuIds = menuIds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
