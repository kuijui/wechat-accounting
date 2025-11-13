package com.accounting.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    ERROR(500, "系统异常"),
    
    // 用户相关
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_LOGIN_ERROR(1002, "登录失败"),
    TOKEN_INVALID(1003, "Token无效"),
    TOKEN_EXPIRED(1004, "Token已过期"),
    
    // 参数相关
    PARAM_ERROR(2001, "参数错误"),
    PARAM_MISSING(2002, "参数缺失"),
    
    // 业务相关
    BILL_NOT_FOUND(3001, "账单不存在"),
    CATEGORY_NOT_FOUND(3002, "分类不存在"),
    BUDGET_NOT_FOUND(3003, "预算不存在"),
    
    // 权限相关
    UNAUTHORIZED(4001, "未授权"),
    FORBIDDEN(4003, "禁止访问");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}