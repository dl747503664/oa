package com.oa.common.thread;
//实现用户信息共享

import com.oa.sys.entity.SysUser;;

public class UserThreadLocal {
	
	private static ThreadLocal<SysUser> threadLocal = 
			new ThreadLocal<>();
	
	public static void set(SysUser user) {
		
		threadLocal.set(user);
	}
	
	public static SysUser get() {
		
		return threadLocal.get();
	}
	
	//防止内存泄漏
	public static void remove() {
		
		threadLocal.remove();
	}
	
	
	
	
	
	
	
	
}
