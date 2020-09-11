package com.wupeng.crm.workbench.web.controller;

import com.wupeng.crm.settings.domain.User;
import com.wupeng.crm.settings.service.UserService;
import com.wupeng.crm.settings.service.impl.UserServiceImpl;
import com.wupeng.crm.utils.PrintJson;
import com.wupeng.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到线索控制器");
        String path = request.getServletPath();
        if ("/workbench/clue/getUserList.do".equals(path)) {
            getUserList(request, response);
        } else if ("/workbench/activity/xxx.do".equals(path)) {
           // xxx(request, response);
        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");

        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList=us.getUserList();

        PrintJson.printJsonObj(response,uList);

    }


}
