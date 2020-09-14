package com.wupeng.crm.workbench.service;

import com.wupeng.crm.workbench.domain.Tran;
import com.wupeng.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranService {
    boolean save(Tran t, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);
}
