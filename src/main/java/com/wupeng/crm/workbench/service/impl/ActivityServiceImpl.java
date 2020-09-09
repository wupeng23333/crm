package com.wupeng.crm.workbench.service.impl;

import com.wupeng.crm.utils.SqlSessionUtil;
import com.wupeng.crm.vo.PaginationVO;
import com.wupeng.crm.workbench.dao.ActivityDao;
import com.wupeng.crm.workbench.domain.Activity;
import com.wupeng.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao= SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    @Override
    public boolean save(Activity a) {
        boolean flag=true;

        int count=activityDao.save(a);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        //取得total
        int total=activityDao.getTotalByCondition(map);


        //取得dataList
        List<Activity> dataList=activityDao.getActivityListByCondition(map);

        //创建一个vo对象
        PaginationVO<Activity> vo=new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }
}
