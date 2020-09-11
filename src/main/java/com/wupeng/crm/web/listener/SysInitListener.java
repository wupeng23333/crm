package com.wupeng.crm.web.listener;

import com.wupeng.crm.settings.domain.DicValue;
import com.wupeng.crm.settings.service.DicService;
import com.wupeng.crm.settings.service.impl.DicServiceImpl;
import com.wupeng.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {

    /*
        该方法是用来监听上下文域对象，当服务器启动，上下文域对象创建，
        上下文域对象创建后，马上执行该方法

        event：该参数可以取得监听的对象
    */

    @Override
    public void contextInitialized(ServletContextEvent event) {

        System.out.println("服务器处理数据字典开始");
        //System.out.println("上下文域对象创建了");
        ServletContext application=event.getServletContext();

        DicService ds= (DicService) ServiceFactory.getService(new DicServiceImpl());
        /*
            业务层返回7个list，打包成一个map
            map.put("application",dvlist1)
         */
        Map<String, List<DicValue>> map=ds.getAll();

        //将map解析为上下文域对象中保存的键值对
        Set<String> set=map.keySet();
        for(String key:set){

            application.setAttribute(key,map.get(key));

        }

        System.out.println("服务器处理数据字典结束");

    }
}
