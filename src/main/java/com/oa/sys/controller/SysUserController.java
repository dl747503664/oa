package com.oa.sys.controller;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oa.common.annotation.CheckToken;
import com.oa.common.annotation.UnAuthRequest;
import com.oa.common.util.JwtUtil;
import com.oa.common.vo.JsonResult;
import com.oa.sys.entity.SysUser;
import com.oa.sys.service.SysUserService;

@Controller
@RequestMapping("/user/")
public class SysUserController {
	 @Autowired
	 private SysUserService sysUserService;
	 
     @RequestMapping("doUserListUI")
     @CheckToken
	 public String doUserListUI(){
		return "sys/user_list"; 
	 }
     @RequestMapping("doUserEditUI")
     @CheckToken
     public String doUserEditUI(){
    	 return "sys/user_edit"; 
     }
     //基于CAS算法，实现的并发安全，底层乐观锁实现。
     private AtomicInteger counter=new AtomicInteger(0);
     //private int count;
     @RequestMapping("doLogin")
     @UnAuthRequest
     @ResponseBody
     public JsonResult doLogin(String username,String password,HttpServletResponse response){
    	 JsonResult jsonResult = sysUserService.loginIn(username,password);
    	 if(jsonResult.getState()==1){
    		 String token = JwtUtil.createJWT(3600*24*3*1000, (SysUser)jsonResult.getData());
    		 Cookie c = new Cookie("OA_TICKET",token);
    		 c.setMaxAge(3600*24*3);
    		 c.setPath("/");
    		 response.addCookie(c);
    		 System.out.println("cookie添加成功");
    		 int count=counter.incrementAndGet();//count+1;
    		 System.out.println("在线人数:"+count);
    	 }
    	 return jsonResult;
     }
     @RequestMapping("doLogout")
     @CheckToken
     public String doLogout(){
    	 System.out.println("=doLogout=");
    	
    	 counter.decrementAndGet();
    	 return "redirect:../doLoginUI.do";
     }
     
     @RequestMapping("doFindObjectByColumn")
     @ResponseBody
     @CheckToken
     public JsonResult doFindObjectByColumn(
    		 String columnName,
    		 String columnValue){
    	 return new JsonResult(
    		sysUserService.findObjectByColumn(
    			columnName, columnValue));
     }
     
     @RequestMapping("doFindObjectById")
     @ResponseBody
     @CheckToken
     public JsonResult doFindObjectById(Integer id){
    	 return new JsonResult(sysUserService.findObjectById(id));
     }
     
     @RequestMapping("doUpdateObject")
     @ResponseBody
     @CheckToken
     public JsonResult doUpdateObject(
    		 SysUser entity,
    		 Integer[] roleIds){
    	 sysUserService.updateObject(entity, roleIds);
    	 return new JsonResult("update OK");
     }
     @RequestMapping("doSaveObject")
     @ResponseBody
     @CheckToken
     public JsonResult doSaveObject(
    		 SysUser entity,
    		 Integer[] roleIds){
    	 sysUserService.saveObject(entity, roleIds);
    	 return new JsonResult("save OK");
     }
     
     @RequestMapping("doValidById")
     @ResponseBody
     @CheckToken
     public JsonResult doValidById(Integer id,Integer valid){
    	 System.out.println(sysUserService.getClass().getName());//$Proxy38
    	 sysUserService.validById(id,valid);//后续来自登录用户
    	 return new JsonResult("update ok");
     }
     
     @RequestMapping("doFindPageObjects")
     @ResponseBody
     @CheckToken
     public JsonResult doFindPageObjects(
    		 String username,
    		 Integer pageCurrent){
    	 System.out.println("find.service="+sysUserService.getClass().getName());
    	 return new JsonResult(
    			    sysUserService.findPageObjects(
    				username,pageCurrent));
     }
}



