package com.wwt.gateway.client;

import com.alibaba.fastjson.JSONObject;

import org.springframework.web.client.RestTemplate;

/**
 * UserClient
 */
public class UserClient {
    public JSONObject getUserByEmail(String email) {
        String response = new RestTemplate().getForObject("http://localhost:8080/USER-SERVICE/user/email/"+email, String.class);
        System.out.println(response);
        return JSONObject.parseObject(response);
    }
}