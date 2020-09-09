package com.wupeng.crm.settings.service;

import com.wupeng.crm.settings.domain.User;
import com.wupeng.crm.settings.exception.LoginException;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
