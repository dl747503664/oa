package com.oa.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oa.common.annotation.CheckToken;
import com.oa.common.vo.JsonResult;
import com.oa.sys.entity.SysMenu;
import com.oa.sys.service.SysMenuService;

@Controller
@RequestMapping("/menu/")
public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping("doMenuListUI")
	@CheckToken
	public String doMenuListUI(){
	 return "sys/menu_list";
	}
	
    @RequestMapping("doMenuEditUI")
    @CheckToken
	public String doMenuEditUI(){
	  return "sys/menu_edit";
	}

    @RequestMapping("doUpdateObject")
    @ResponseBody
    @CheckToken
    public JsonResult doUpdateObject(SysMenu entity){
    	sysMenuService.updateObject(entity);
    	return new JsonResult("update ok");
    }
    
    @RequestMapping("doSaveObject")
    @ResponseBody
    @CheckToken
    public JsonResult doSaveObject(SysMenu entity){
    	sysMenuService.saveObject(entity);
    	return new JsonResult("save ok");
    }
    
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	@CheckToken
	public JsonResult doFindZtreeMenuNodes(){
		 return new JsonResult(
		 sysMenuService.findZtreeMenuNodes());
	}

	@RequestMapping("doDeleteObject")
	@ResponseBody
	@CheckToken
	public JsonResult doDeleteObject(Integer id){
		sysMenuService.deleteObject(id);
		return new JsonResult("delete ok");
	}

	@RequestMapping("doFindObjects")
	@ResponseBody
	@CheckToken
	public JsonResult doFindObjects(){
		List<Map<String,Object>> list=
				sysMenuService.findObjects();
		System.out.println(list);
		return new JsonResult(list);
	}
}








