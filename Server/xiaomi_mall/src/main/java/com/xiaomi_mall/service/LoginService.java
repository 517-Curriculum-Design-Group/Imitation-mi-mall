package com.xiaomi_mall.service;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.User;

public interface LoginService {
    Result login(User user);

    Result logout();

    Result backLogin(User user);

    Result backLogout();
}
