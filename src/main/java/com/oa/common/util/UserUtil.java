package com.oa.common.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mchange.v2.c3p0.util.TestUtils;
import com.oa.sys.service.SysUserService;
import com.oa.sys.service.impl.SysUserServiceImpl;

@Component     //申明为spring组件
public class UserUtil {  
    @Autowired    
    private SysUserService sysUserService;  //添加所需service的私有成员
    private static UserUtil  userUtils ;  // 静态初使化 一个工具类 

    public void setUserService(SysUserService  sysUserService) {  
        this.sysUserService = sysUserService;  
    }

    @PostConstruct     //   通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    public void init() {  
        userUtils = this;  
        userUtils.sysUserService = this.sysUserService;   // 初使化时将已静态化的testService实例化
    }  
    public SysUserService getService(){
    	return UserUtil.userUtils.sysUserService;
    }
} 
