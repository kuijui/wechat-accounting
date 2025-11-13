package com.accounting.controller;

import com.accounting.common.Result;
import com.accounting.dto.LoginRequest;
import com.accounting.dto.LoginResponse;
import com.accounting.entity.User;
import com.accounting.service.UserService;
import com.accounting.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.login(request);
            return Result.success("登录成功", response);
        } catch (Exception e) {
            log.error("登录失败", e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        try {
            // 从请求头中获取token
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                return Result.error("未授权");
            }
            
            token = token.substring(7); // 去掉"Bearer "
            
            // 验证token
            if (!jwtUtil.validateToken(token)) {
                return Result.error("Token无效");
            }
            
            // 获取用户ID
            Long userId = jwtUtil.getUserIdFromToken(token);
            User user = userService.findById(userId);
            
            if (user == null) {
                return Result.error("用户不存在");
            }
            
            return Result.success(user);
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/update")
    public Result<String> updateUser(@RequestBody User user, HttpServletRequest request) {
        try {
            // 从请求头中获取token
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                return Result.error("未授权");
            }
            
            token = token.substring(7);
            Long userId = jwtUtil.getUserIdFromToken(token);
            
            // 设置用户ID
            user.setId(userId);
            
            boolean success = userService.updateUser(user);
            if (success) {
                return Result.success("更新成功");
            } else {
                return Result.error("更新失败");
            }
        } catch (Exception e) {
            log.error("更新用户信息失败", e);
            return Result.error(e.getMessage());
        }
    }
}