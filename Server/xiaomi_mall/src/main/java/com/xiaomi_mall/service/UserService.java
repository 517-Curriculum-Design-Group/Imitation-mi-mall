package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.User;

import java.util.List;

public interface UserService extends IService<User> {
    Result register(User user);

    Result addadmin(User user);

    Result getUserList(Integer pageNum, Integer pageSize, String nickName);

    Result updateUserStatus(Long userId);

    Result getAdminList(Integer pageNum, Integer pageSize, String nickName);

    Result deleteUser(List<Long> userIds);

    Result updateUserInfo(Long userId);
}
