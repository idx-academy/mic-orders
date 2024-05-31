package com.academy.orders.boot.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("auth")
public record UsersConfig(List<AppUser> users) {

	public record AppUser(String username, String password, String roles) {

	}

}
