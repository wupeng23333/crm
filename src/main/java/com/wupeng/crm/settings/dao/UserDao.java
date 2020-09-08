package com.wupeng.crm.settings.dao;

import com.wupeng.crm.settings.domain.User;

import java.util.Map;

public interface UserDao {
    User login(Map<String, String> map);
}
