package com.wupeng.crm.workbench.dao;

import com.wupeng.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer cus);
}
