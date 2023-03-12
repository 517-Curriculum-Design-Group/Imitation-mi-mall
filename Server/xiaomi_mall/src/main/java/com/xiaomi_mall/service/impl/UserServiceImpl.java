package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.constants.SystemConstants;
import com.xiaomi_mall.enity.User;
import com.xiaomi_mall.enums.AppHttpCodeEnum;
import com.xiaomi_mall.exception.SystemException;
import com.xiaomi_mall.mapper.UserMapper;
import com.xiaomi_mall.service.UserService;
import com.xiaomi_mall.util.BeanCopyUtils;
import com.xiaomi_mall.vo.UserListPageVo;
import com.xiaomi_mall.vo.UserListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    /**
     * 普通用户注册
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public Result register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return Result.okResult();
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        if (count(queryWrapper) == SystemConstants.NICKNAME_NOT_EXIT) {
            return false;
        }
        return true;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        if (count(queryWrapper) == SystemConstants.USERNAME_NOT_EXIT) {
            return false;
        }
        return true;
    }

    /**
     * 管理员添加
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public Result addadmin(User user) {
        //防止注册的用户重名
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, user.getUserName());
        User user2 = getOne(queryWrapper);

        if (Objects.isNull(user2)) {
            //user2为空时，说明不重名，可以注册
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            user.setUserType("普通管理员");
            save(user);
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
    }

    @Override
    public Result getUserList(Integer pageNum, Integer pageSize, String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(nickName),User::getNickName, nickName);
        Page<User> pageInfo = new Page<>(pageNum, pageSize);
        page(pageInfo, queryWrapper);
        List<User> userList = pageInfo.getRecords();
        List<User> filterList = userList.stream()
                .filter(item -> Objects.equals(item.getUserType(), "普通用户"))
                .collect(Collectors.toList());
        int total = filterList.size();
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(filterList, UserListVo.class);
        UserListPageVo userListPageVo = new UserListPageVo(userListVos, total);
        return Result.okResult(userListPageVo);
    }

    @Override
    public Result updateUserStatus(Long userId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId, userId);
        User user = getOne(queryWrapper);
        if (user.getStatus() == SystemConstants.USER_STATUS_NORMAL) {
            user.setStatus(1);
        }
        else if (user.getStatus() == SystemConstants.USER_STATUS_ERROR){
            user.setStatus(0);
        }
        updateById(user);
        return Result.okResult();
    }

    @Override
    public Result deleteUser(List<Long> userIds) {
        List<User> users = listByIds(userIds);
        userMapper.deleteBatchIds(users);
        return Result.okResult();
    }

    @Override
    public Result getAdminList(Integer pageNum, Integer pageSize, String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(nickName),User::getNickName, nickName);
        Page<User> pageInfo = new Page<>(pageNum, pageSize);
        page(pageInfo, queryWrapper);
        List<User> userList = pageInfo.getRecords();
        List<User> filterList = userList.stream()
                .filter(item -> Objects.equals(item.getUserType(), "普通管理员"))
                .collect(Collectors.toList());
        int total = filterList.size();
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(filterList, UserListVo.class);
        UserListPageVo userListPageVo = new UserListPageVo(userListVos, total);
        return Result.okResult(userListPageVo);
    }
}
