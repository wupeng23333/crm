package com.wupeng.crm.workbench.service.impl;

import com.wupeng.crm.utils.SqlSessionUtil;
import com.wupeng.crm.utils.UUIDUtil;
import com.wupeng.crm.workbench.dao.ClueActivityRelationDao;
import com.wupeng.crm.workbench.dao.ClueDao;
import com.wupeng.crm.workbench.domain.Clue;
import com.wupeng.crm.workbench.domain.ClueActivityRelation;
import com.wupeng.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao= SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao=SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    @Override
    public boolean save(Clue c) {
        boolean flag=true;
        int count=clueDao.save(c);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Clue detail(String id) {
        Clue c=clueDao.detail(id);
        return c;
    }

    @Override
    public boolean unbund(String id) {
        boolean flag=true;
        int count=clueActivityRelationDao.unbund(id);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean bund(String cid, String[] aids) {
        boolean flag=true;
        for(String aid:aids){
            //取得每一个aid和cid做关联
            ClueActivityRelation car=new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(cid);

            //修改关联关系表中的记录
            int count=clueActivityRelationDao.bund(car);
            if(count!=1){
                flag=false;
            }
        }
        return flag;
    }
}
