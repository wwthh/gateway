package com.wwt.gateway.filter;


import com.wwt.gateway.utils.JWTUtils;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * AuthenticateFilter
 */
public class AuthenticateFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String jwt = request.getHeaders().getFirst("token");
        String userId = JWTUtils.getUserIdFromJWT(jwt);
        if(userId==null) userId = "";
        // 修改request中的header
        request = request.mutate().header("userId", userId).build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return 2;
    }
}