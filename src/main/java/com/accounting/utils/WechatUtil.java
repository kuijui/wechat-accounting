package com.accounting.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WechatUtil {

    @Value("${wechat.mini-program.app-id}")
    private String appId;

    @Value("${wechat.mini-program.app-secret}")
    private String appSecret;

    private final OkHttpClient httpClient = new OkHttpClient();

    private static final String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    public JSONObject code2Session(String code) {
        String url = String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                CODE2SESSION_URL, appId, appSecret, code);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return JSON.parseObject(responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValidOpenid(String openid) {
        return openid != null && openid.length() == 28;
    }
}