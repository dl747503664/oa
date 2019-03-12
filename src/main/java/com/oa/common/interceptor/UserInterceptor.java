package com.oa.common.interceptor;

import java.lang.reflect.Method;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.oa.sys.entity.SysUser;
import com.oa.sys.service.SysUserService;
import com.oa.common.annotation.CheckToken;
import com.oa.common.annotation.UnAuthRequest;
import com.oa.common.thread.UserThreadLocal;
import com.oa.common.util.JwtUtil;
import com.oa.common.util.UserUtil;

@Configuration
public class UserInterceptor implements HandlerInterceptor {

	private UserUtil userUtil = new UserUtil();
	/**
	 * 如何判断用户是否登录???
	 * 	1.通过request请求获取Cookie
	 *  2.获取Cookie中的ticket 
	 *  	if(null){用户没有登陆,跳转登陆页面}
	 *  	else{ 
	 *  		解析ticket
	 *  		成功则直接跳转页面,放行
	 *  	}
	 *  	
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("请求被拦截");
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		UnAuthRequest annotation = method.getAnnotation(UnAuthRequest.class);
		if (Objects.nonNull(annotation)) {
			return true;
		}
		String token = null;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {	
				if("OA_TICKET".equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}

		//检查有没有需要用户认证的注解
		if (method.isAnnotationPresent(CheckToken.class)) {
			CheckToken checkToken = method.getAnnotation(CheckToken.class);
			if (checkToken.required()) {
				// 执行认证
				if (token != null) {
					// 获取 token 中的 user id
					String username;
					try {
						username = JWT.decode(token).getClaim("username").asString();
						System.out.println(username);
					} catch (JWTDecodeException j) {
						throw new RuntimeException("访问异常！");
					}
					SysUser user = userUtil.getService().findUserByUsername(username);
					if (user == null) {
						throw new RuntimeException("用户不存在，请重新登录");
					}
					Boolean verify = JwtUtil.isVerify(token, user);
					if (!verify) {
						throw new RuntimeException("非法访问！");
					}
					return true;
				}
			}
		}
		//重定向到用户登录页面
		response.sendRedirect("/JH-OA-V1.01/doLoginUI.do");
		return false; //请求拦截 一般在false之前都会重定向
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//防止服务器内存泄漏

		UserThreadLocal.remove();
	}

}
