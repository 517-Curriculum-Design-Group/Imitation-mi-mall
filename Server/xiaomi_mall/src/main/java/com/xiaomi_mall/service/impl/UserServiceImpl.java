package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.User;
import com.xiaomi_mall.mapper.UserMapper;
import com.xiaomi_mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 普通用户注册
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public boolean register(User user) {
        //防止注册的用户重名
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, user.getUserName());
        User user1 = getOne(wrapper);

        if (Objects.isNull(user1)) {
            //user1为空时，说明不重名，可以注册
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);

            boolean flag = save(user);
            return flag;
        }
        return false;
    }

    /**
     * 管理员注册
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public boolean addadmin(User user) {
        //防止注册的用户重名
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, user.getUserName());
        User user2 = getOne(queryWrapper);

        if (Objects.isNull(user2)) {
            //user2为空时，说明不重名，可以注册
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            user.setUserType("超级管理员");
            boolean res = save(user);
            return res;
        }
        return false;
    }
}
