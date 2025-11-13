package com.accounting.service;

import com.accounting.dto.LoginRequest;
import com.accounting.dto.LoginResponse;
import com.accounting.entity.User;
import com.accounting.mapper.UserMapper;
import com.accounting.utils.JwtUtil;
import com.accounting.utils.WechatUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WechatUtil wechatUtil;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 微信小程序登录
     */
    public LoginResponse login(LoginRequest request) {
        String openid;
        
        // 测试模式：如果code以"test-"开头，则使用测试openid
        if (request.getCode().startsWith("test-")) {
            openid = "test_openid_" + System.currentTimeMillis();
            log.info("测试模式登录，生成openid: {}", openid);
        } else {
            // 1. 调用微信接口获取openid
            JSONObject wechatResponse = wechatUtil.code2Session(request.getCode());
            if (wechatResponse == null || wechatResponse.containsKey("errcode")) {
                throw new RuntimeException("微信登录失败");
            }

            openid = wechatResponse.getString("openid");
            if (!StringUtils.hasText(openid)) {
                throw new RuntimeException("获取openid失败");
            }
        }

        // 2. 查找或创建用户
        User user = userMapper.findByOpenid(openid);
        boolean isNewUser = false;

        if (user == null) {
            // 新用户，创建账号
            user = new User();
            user.setOpenid(openid);
            user.setNickname(request.getNickname());
            user.setAvatarUrl(request.getAvatarUrl());
            user.setGender(request.getGender() != null ? request.getGender() : 0);
            user.setStatus(1);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            user.setDeleted(0);
            
            userMapper.insert(user);
            isNewUser = true;
            log.info("新用户注册，openid: {}", openid);
        } else {
            // 老用户，更新信息
            if (StringUtils.hasText(request.getNickname())) {
                user.setNickname(request.getNickname());
            }
            if (StringUtils.hasText(request.getAvatarUrl())) {
                user.setAvatarUrl(request.getAvatarUrl());
            }
            if (request.getGender() != null) {
                user.setGender(request.getGender());
            }
            user.setUpdateTime(LocalDateTime.now());
            userMapper.updateById(user);
            log.info("用户登录，openid: {}", openid);
        }

        // 3. 生成JWT token
        String token = jwtUtil.generateToken(user.getId());

        // 4. 返回登录结果
        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setNickname(user.getNickname());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setToken(token);
        response.setIsNewUser(isNewUser);

        return response;
    }

    /**
     * 根据ID查找用户
     */
    public User findById(Long userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 更新用户信息
     */
    public boolean updateUser(User user) {
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateById(user) > 0;
    }
}