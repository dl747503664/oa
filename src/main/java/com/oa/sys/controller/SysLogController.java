package com.oa.sys.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oa.common.annotation.CheckToken;
import com.oa.common.vo.JsonResult;
import com.oa.common.vo.PageObject;
import com.oa.sys.entity.SysLog;
import com.oa.sys.service.SysLogService;
/**
 * 日志管理的控制层对象
 * @Controller注解
 * 用于描述这个对象要交给spring管理，
 * 可以认为是spring中的一个bean对象。
 * @RequestMapping 用于定义此controller对象
 * 的一个根路径映射，当访问某个方法时，方法映射
 * 路径的前面都会加上这个根路径
 */
@Controller
@RequestMapping("/log/")
public class SysLogController {
	  
	  @Autowired
	  private SysLogService sysLogService;
	  /**
	   * 通过此方法返回日志列表页面
	   * 方法映射：建议和方法名相同
	   * 思考：如下方法如何被调用？反射技术
	   */
	  @RequestMapping("doLogListUI")
	  @CheckToken
	  public String doLogListUI(){
		  //此值会交给视图解析器进行解析
		  return "sys/log_list";
		  //WEB-INF/pages/sys/log_list.html
	  }
	  /**
	   * 执行日志删除操作
	   * @param ids 请求参数(日志记录id)
	   * @return
	   * log/doDeleteObjects.do?ids=1,2,3
	   * 说明：当客户端的请求方式与服务端可以处理的请求方式
	   * 不一致时就会出现405的异常
	   */
	  //@RequestMapping(value="doDeleteObjects",method=RequestMethod.POST)
	  @PostMapping("doDeleteObjects")//只能处理post请求
	  @ResponseBody
	  @CheckToken
	  public JsonResult doDeleteObjects(
			  Integer... ids){//spring mvc请求参数映射
		  System.out.println("log.delete.service="+sysLogService.getClass().getName());
		  sysLogService.deleteObjects(ids);
		  return new JsonResult("delete ok");
	  }
	  /**
	   * 其中@RequestParam注解修饰方法参数时，用于
	   * 告诉spring mvc这参数的值来自于请求
	   * 中的哪个参数，一旦使用此注解修饰某个参数,
	   * spring mvc 会要求在请求参数中必须包含指定参数的
	   * 参数值，否则可能会出现400异常，当然也可以将
	   * @RequestParam 注解中的require属性的值修改为false.
	   * @param username
	   * @param page
	   * @return
	   */
	  //../log/doFindPageObjects.do?pageCurrent=1    
	  @RequestMapping("doFindPageObjects")
	  @ResponseBody
	  @CheckToken
	  public JsonResult doFindPageObjects(   
			  String username,
			  @RequestParam(value="pageCurrent"
			  ,required=false)Integer page){
		  PageObject<SysLog> pageObject=
		  sysLogService.findPageObjects(username,
				  page);
		  return new JsonResult(pageObject);
	  }

}
