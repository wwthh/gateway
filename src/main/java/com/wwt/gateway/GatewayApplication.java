package com.wwt.gateway;

import com.wwt.gateway.filter.AuthenticateFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator userRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes().route(r -> r.path("/v1/users/**").filters(f -> f.stripPrefix(1)).uri("lb://USER-SERVICE").filter(new AuthenticateFilter()).id("user")).build();
    }

    @Bean
    public RouteLocator paperRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes().route(r -> r.path("/v1/papers/**").filters(f -> f.stripPrefix(1)).uri("lb://PAPER-SERVICE").filter(new AuthenticateFilter()).id("paper")).build();
    }

    @Bean
    public RouteLocator expertRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes().route(r -> r.path("/v1/experts/**").filters(f -> f.stripPrefix(1)).uri("lb://EXPERT-SERVICE").filter(new AuthenticateFilter()).id("expert")).build();
    }

}
