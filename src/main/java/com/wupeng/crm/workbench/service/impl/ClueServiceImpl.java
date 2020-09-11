package com.wupeng.crm.workbench.service.impl;

import com.wupeng.crm.utils.SqlSessionUtil;
import com.wupeng.crm.workbench.dao.ClueDao;
import com.wupeng.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao= SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
}
