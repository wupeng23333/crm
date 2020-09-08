package com.wupeng.crm.settings.service;

import com.wupeng.crm.settings.domain.User;
import com.wupeng.crm.settings.exception.LoginException;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}
