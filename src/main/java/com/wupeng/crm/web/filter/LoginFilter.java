package com.wupeng.crm.web.filter;

import com.wupeng.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入到验证有没有登录过的过滤器");
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;

        String path=request.getServletPath();
        if("/login.jsp".equals(path)||"/settings/user/login.do".equals(path)){
            chain.doFilter(req,resp);
        }else {
            HttpSession session=request.getSession();
            User user= (User) session.getAttribute("user");

            //如果user不为空，则已登录
            if(user!=null){
                chain.doFilter(req,resp);
            }else {
                //重定向到登陆页
            /*
                转发：使用的是特殊绝对路径，/login.jsp
                重定向：绝对路径，/crm/login.jsp
                使用重定向，使浏览器的地址栏改变
             */
                response.sendRedirect(request.getContextPath()+"/login.jsp");


            }
        }
    }
}
