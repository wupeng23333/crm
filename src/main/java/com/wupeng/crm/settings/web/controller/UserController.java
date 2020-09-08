package com.wupeng.crm.settings.web.controller;

import com.wupeng.crm.settings.domain.User;
import com.wupeng.crm.settings.service.UserService;
import com.wupeng.crm.settings.service.impl.UserServiceImpl;
import com.wupeng.crm.utils.MD5Util;
import com.wupeng.crm.utils.PrintJson;
import com.wupeng.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到用户控制器");
        String path = request.getServletPath();
        if ("/settings/user/login.do".equals(path)) {
            login(request, response);
        } else if ("/settings/user/xxx.do".equals(path)) {

        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到验证登陆操作");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");

        //将密码转为md5密文
        loginPwd = MD5Util.getMD5(loginPwd);
        //接收浏览器ip
        String ip = request.getRemoteAddr();
        System.out.println("----------ip:" + ip);

        //业务层开发，统一使用代理形态的接口对象
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            User user = us.login(loginAct, loginPwd, ip);

            request.getSession().setAttribute("user", user);
            //程序执行到此，业务层无异常，登录成功
            //给前端返回success
            PrintJson.printJsonFlag(response, true);
        } catch (Exception e) {
            e.printStackTrace();
            //程序执行catch，业务层验证登陆失败

            //给前端返回success，msg
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", false);
            map.put("msg", msg);
            PrintJson.printJsonObj(response, map);

        }

    }
}
