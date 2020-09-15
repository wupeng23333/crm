package com.wupeng.crm.workbench.service.impl;

import com.wupeng.crm.utils.DateTimeUtil;
import com.wupeng.crm.utils.SqlSessionUtil;
import com.wupeng.crm.utils.UUIDUtil;
import com.wupeng.crm.workbench.dao.CustomerDao;
import com.wupeng.crm.workbench.dao.TranDao;
import com.wupeng.crm.workbench.dao.TranHistoryDao;
import com.wupeng.crm.workbench.domain.Customer;
import com.wupeng.crm.workbench.domain.Tran;
import com.wupeng.crm.workbench.domain.TranHistory;
import com.wupeng.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {
    private TranDao tranDao= SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao= SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao=SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public boolean save(Tran t, String customerName) {
        /*
               添加交易之前，在客户表中按照名称查询客户是否存在，
               如果存在则取出id，不存在则创建，并将id存入到t
               t中信息完全，执行添加交易操作
         */

        boolean flag=true;
        Customer  cus=customerDao.getCustomerByName(customerName);

        //如何cus为空，则需要创建客户
        if(cus==null){

            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setName(customerName);
            cus.setCreateBy(t.getCreateBy());
            cus.setCreateTime(DateTimeUtil.getSysTime());
            cus.setContactSummary(t.getContactSummary());
            cus.setNextContactTime(t.getNextContactTime());
            cus.setOwner(t.getOwner());
            int count1=customerDao.save(cus);
            if (count1!=1){
                flag=false;
            }
        }

        //通过以上对于客户的处理，不论是查询出来已有的客户，还是以前没有我们新增的客户，总之客户已经有了，客户的id就有了
        //将客户id封装到t对象中
        t.setCustomerId(cus.getId());

        //添加交易
        int count2 = tranDao.save(t);
        if(count2!=1){
            flag = false;
        }

        //添加交易历史
        TranHistory th=new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        th.setCreateBy(t.getCreateBy());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setExpectedDate(t.getExpectedDate());
        th.setMoney(t.getMoney());

        int count3=tranHistoryDao.save(th);
        if (count3!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Tran detail(String id) {
        Tran t=tranDao.detail(id);
        return t;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {
        List<TranHistory> thList=tranHistoryDao.getHistoryListByTranId(tranId);
        return thList;
    }

    @Override
    public boolean changeStage(Tran t) {

        boolean flag=true;

        //改变交易阶段
        int count1=tranDao.changeStage(t);
        if (count1!=1){
            flag=false;
        }

        //添加交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateBy(t.getEditBy());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setExpectedDate(t.getExpectedDate());
        th.setMoney(t.getMoney());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        //添加交易历史
        int count2 = tranHistoryDao.save(th);
        if(count2!=1){

            flag = false;

        }

        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {

        //取得total
        int total=tranDao.getTotal();

        //取得dataList
        List<Map<String,Object>> dataList=tranDao.getCharts();

        //保存到map
        Map<String, Object> map=new HashMap<>();
        map.put("total",total);
        map.put("dataList",dataList);
        return map;
    }
}
