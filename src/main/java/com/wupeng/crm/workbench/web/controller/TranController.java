package com.wupeng.crm.workbench.web.controller;

import com.wupeng.crm.settings.domain.User;
import com.wupeng.crm.settings.service.UserService;
import com.wupeng.crm.settings.service.impl.CustomerServiceImpl;
import com.wupeng.crm.settings.service.impl.UserServiceImpl;
import com.wupeng.crm.utils.DateTimeUtil;
import com.wupeng.crm.utils.PrintJson;
import com.wupeng.crm.utils.ServiceFactory;
import com.wupeng.crm.utils.UUIDUtil;
import com.wupeng.crm.workbench.domain.Tran;
import com.wupeng.crm.workbench.domain.TranHistory;
import com.wupeng.crm.workbench.service.CustomerService;
import com.wupeng.crm.workbench.service.TranService;
import com.wupeng.crm.workbench.service.impl.ClueServiceImpl;
import com.wupeng.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到线索控制器");
        String path = request.getServletPath();
        if ("/workbench/transaction/add.do".equals(path)) {
            add(request, response);
        } else if ("/workbench/transaction/getCustomerName.do".equals(path)) {
            getCustomerName(request, response);
        }else if ("/workbench/transaction/save.do".equals(path)) {
            save(request, response);
        }else if ("/workbench/transaction/detail.do".equals(path)) {
            detail(request, response);
        }else if ("/workbench/transaction/getHistoryListByTranId.do".equals(path)) {
            getHistoryListByTranId(request, response);
        }
    }

    private void getHistoryListByTranId(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据交易id取得相应的历史列表");

        String tranId=request.getParameter("tranId");

        TranService ts= (TranService) ServiceFactory.getService(new TranServiceImpl());

        List<TranHistory> thList=ts.getHistoryListByTranId(tranId);

        //阶段和可能性之前的关系
        Map<String,String> pMap= (Map<String, String>) this.getServletContext().getAttribute("pMap");

        //将交易历史列表遍历
        for (TranHistory th:thList){

            //根据每一条交易历史取出每一阶段
            String stage=th.getStage();
            String possibility=pMap.get(stage);
            th.setPossibility(possibility);
        }

        PrintJson.printJsonObj(response,thList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("跳转到详细信息页");

        String id=request.getParameter("id");

        TranService ts= (TranService) ServiceFactory.getService(new TranServiceImpl());

        Tran t=ts.detail(id);

        /*
            处理可能性：阶段t和可能性之间的对应关系 pMap
         */
        String stage=t.getStage();
        Map<String,String> pMap= (Map<String, String>) this.getServletContext().getAttribute("pMap");
        String possibility=pMap.get(stage);

        t.setPossibility(possibility);

        request.setAttribute("t",t);
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("执行添加交易的操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName"); //此处我们暂时只有客户名称，还没有id
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");

        Tran t=new Tran();

        t.setId(id);
        t.setOwner(owner);
        t.setMoney(money);
        t.setName(name);
        t.setExpectedDate(expectedDate);
        t.setStage(stage);
        t.setType(type);
        t.setSource(source);
        t.setActivityId(activityId);
        t.setContactsId(contactsId);
        t.setCreateTime(createTime);
        t.setCreateBy(createBy);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);

        TranService ts= (TranService) ServiceFactory.getService(new TranServiceImpl());

        boolean flag=ts.save(t,customerName);
        if(flag){

            //如果添加交易成功，跳转到列表页
            response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");

        }

    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得客户名称列表，按照客户名称进行模糊查询");
        String name =request.getParameter("name");

        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        List<String> sList=cs.getCustomerName(name);
        PrintJson.printJsonObj(response,sList);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到跳转到交易添加页的操作");

        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList=us.getUserList();

        request.setAttribute("uList",uList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }
}
