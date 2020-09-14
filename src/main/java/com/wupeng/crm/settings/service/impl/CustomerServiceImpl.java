package com.wupeng.crm.settings.service.impl;

import com.wupeng.crm.utils.SqlSessionUtil;
import com.wupeng.crm.workbench.dao.CustomerDao;
import com.wupeng.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao= SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {
        List<String> sList=customerDao.getCustomerName(name);

        return sList;
    }
}
