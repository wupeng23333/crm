package com.wupeng.crm.settings.service.impl;

import com.wupeng.crm.settings.dao.UserDao;
import com.wupeng.crm.settings.domain.User;
import com.wupeng.crm.settings.exception.LoginException;
import com.wupeng.crm.settings.service.UserService;
import com.wupeng.crm.utils.DateTimeUtil;
import com.wupeng.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String,String> map =new HashMap<String, String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user=userDao.login(map);
        if (user==null){
            throw new LoginException("账号密码错误");
        }

        //验证失效时间
        String expireTime=user.getExpireTime();
        String currentTime= DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime)<0){
            throw new LoginException("账号已失效");
        }

        //判断锁定状态
        String lockState=user.getLockState();
        if("0".equals(lockState)){
            throw new LoginException("账号已锁定");
        }

        //判断ip地址
        String allowIps=user.getAllowIps();
        if(!allowIps.contains(ip)){
            throw new  LoginException("ip地址受限");
        }

        return user;
    }
}
