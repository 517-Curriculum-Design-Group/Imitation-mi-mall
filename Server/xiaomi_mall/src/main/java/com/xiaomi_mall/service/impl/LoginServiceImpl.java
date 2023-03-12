package com.xiaomi_mall.service.impl;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.exception.enity.User;
import com.xiaomi_mall.exception.enity.authentication.LoginUser;
import com.xiaomi_mall.service.LoginService;
import com.xiaomi_mall.util.BeanCopyUtils;
import com.xiaomi_mall.util.JwtUtil;
import com.xiaomi_mall.util.RedisCache;
import com.xiaomi_mall.vo.UserInfoVo;
import com.xiaomi_mall.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public Result login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo vo = new UserLoginVo(jwt,userInfoVo);
        return Result.okResult(vo);
    }

    @Override
    public Result logout() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getUserId();
        redisCache.deleteObject("login:" + userid);
        return Result.okResult();
    }

    @Override
    public Result backLogin(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return Result.okResult(map);
    }

    @Override
    public Result backLogout() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getUserId();
        redisCache.deleteObject("login:" + userid);
        return Result.okResult();
    }

}
