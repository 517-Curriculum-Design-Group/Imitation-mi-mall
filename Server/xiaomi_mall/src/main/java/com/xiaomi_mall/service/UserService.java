package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.UpdatePasswordDto;
import com.xiaomi_mall.enity.User;

public interface UserService extends IService<User> {
    Result register(User user);

    Result addadmin(User user);

    Result getUserList(Integer pageNum, Integer pageSize, String nickName);

    Result updateUserStatus(Long userId);

    Result getAdminList(Integer pageNum, Integer pageSize, String nickName);

    Result updateUserInfo(User user);

    Result getHome();

    Result getPersonInfo();



    Result getHeaderProducts();

    Result getLeftCategories();

    Result getLowerProducts();


    User addAdvater();

    Result updatePersonInfo(User user);

    Result updatePassword(UpdatePasswordDto updatePasswordDto);
}
