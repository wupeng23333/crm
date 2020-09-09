package com.wupeng.crm.workbench.service;

import com.wupeng.crm.vo.PaginationVO;
import com.wupeng.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    boolean save(Activity a);

    PaginationVO<Activity> pageList(Map<String, Object> map);
}
