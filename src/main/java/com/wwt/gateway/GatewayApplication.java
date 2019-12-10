package com.wwt.gateway;

import com.wwt.gateway.filter.LoginFilter;

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
	public RouteLocator loginRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes().route(r -> r.path("/v1/users/login").filters(f->f.stripPrefix(2)).uri("lb://USER-SERVICE").id("login").filter(new LoginFilter())).build();
	}

	@Bean
	public RouteLocator registRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes().route(r->r.path("/v1/users/register").filters(f->f.stripPrefix(1)).uri("lb://USER-SERVICE").id("register")).build();
	}

}
