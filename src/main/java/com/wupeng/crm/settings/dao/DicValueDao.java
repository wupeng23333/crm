package com.wupeng.crm.settings.dao;

import com.wupeng.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
