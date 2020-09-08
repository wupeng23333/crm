package com.wupeng.crm.workbench.service.impl;

import com.wupeng.crm.utils.SqlSessionUtil;
import com.wupeng.crm.workbench.dao.ActivityDao;
import com.wupeng.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao= SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
}
