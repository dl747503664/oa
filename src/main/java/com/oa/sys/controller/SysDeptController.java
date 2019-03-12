package com.oa.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oa.common.annotation.CheckToken;
import com.oa.common.vo.JsonResult;
import com.oa.sys.entity.SysDept;
import com.oa.sys.service.SysDeptService;

@Controller
@RequestMapping("/dept/")
public class SysDeptController {
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@RequestMapping("doDeptListUI")
	@CheckToken
	public String doDeptListUI(){
		return "sys/dept_list";
	}
	@RequestMapping("doDeptEditUI")
	@CheckToken
	public String doDeptEditUI(){
		return "sys/dept_edit";
	}
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	@CheckToken
	public JsonResult doUpdateObject(SysDept entity){
		sysDeptService.updateObject(entity);
	    return new JsonResult("update ok");
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	@CheckToken
	public JsonResult doSaveObject(SysDept entity){
		sysDeptService.saveObject(entity);
		return new JsonResult("save ok");
	}
	
	@RequestMapping("doFindZTreeNodes")
	@ResponseBody
	@CheckToken
	public JsonResult doFindZTreeNodes(){
		return new JsonResult(
		sysDeptService.findZTreeNodes());
	}
	
	@RequestMapping("doDeleteObject")
	@ResponseBody
	@CheckToken
	public JsonResult doDeleteObject(Integer id){
		sysDeptService.deleteObject(id);
		return new JsonResult("delete ok");
	}
	
	@RequestMapping("doFindObjects")
	@ResponseBody
	@CheckToken
	public JsonResult doFindObjects(){
		return new JsonResult(sysDeptService.findObjects());
	}	
	
	
}
