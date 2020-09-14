package com.wupeng.crm.workbench.service;

import com.wupeng.crm.workbench.domain.Clue;
import com.wupeng.crm.workbench.domain.Tran;

public interface ClueService {
    boolean save(Clue c);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String clueId, Tran t, String createBy);
}
