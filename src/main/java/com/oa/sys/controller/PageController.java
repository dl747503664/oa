package com.oa.sys.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oa.common.annotation.CheckToken;
import com.oa.common.annotation.UnAuthRequest;
/**系统底层发现@RequestMapping注解以后会
找到一个HandlerMapping对象(具备全局唯一性)，
此对象内部对应的是一个map,用于存储url到具体方法的映射
说明:在spring mvc中会将@Controller注解修饰
的类理解为Handler对象
*/
@RequestMapping("/") 
//<bean id="" class=""/>
@Controller
public class PageController{//类加载(将类读到内存)-->Class
	  public PageController() {
		 System.out.println("PageController");
	  }
	 /**
	   * 通过此方法返回系统的首页页面
	   * @return
	   */
	  @RequestMapping("doIndexUI")
	  @CheckToken
	  public String doIndexUI(){
		  return "starter";//WEB-INF/pages/starter.html
	  }
	  @UnAuthRequest
	  @RequestMapping("doLoginUI")
	  public String doLoginUI(){
		  return "login";
	  }
	  
	  /**
	   * 此方法用户返回分页页面(本质就是一个div)
	   * @return
	   */
	  @RequestMapping("doPageUI")
	  @CheckToken
	  public String doPageUI(){
		  //try{Thread.sleep(3000);}catch(Exception e){}
		  return "common/page";//page.html
	  }
	  
}
