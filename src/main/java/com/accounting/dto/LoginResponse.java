package com.accounting.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private Long userId;

    private String nickname;

    private String avatarUrl;

    private String token;

    private Boolean isNewUser;
}