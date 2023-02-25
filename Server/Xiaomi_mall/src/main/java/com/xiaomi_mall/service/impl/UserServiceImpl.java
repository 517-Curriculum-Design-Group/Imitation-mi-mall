package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.enity.User;
import com.xiaomi_mall.mapper.UserMapper;
import com.xiaomi_mall.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
