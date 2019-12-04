package com.wwt.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.wwt.gateway.client.UserClient;
import com.wwt.gateway.utils.*;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * LoginFilter
 */
public class LoginFilter implements GatewayFilter, Ordered {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "cipher";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String email = request.getQueryParams().getFirst(EMAIL);
        String password = request.getQueryParams().getFirst(PASSWORD);
        JSONObject simpleUser = new UserClient().getUserByEmail(email);
        String token;
        if (simpleUser.getString("email")==null || simpleUser.getString("password")==null) {
            token = "1";
        } else if(password == null || !password.equals(simpleUser.getString("password"))){
            token = "2";
        } else {
            token = JWTUtils.generateJWT(simpleUser.getString("id"));
        }
        // 修改request中的header
        request = request.mutate().header("token", token).build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}