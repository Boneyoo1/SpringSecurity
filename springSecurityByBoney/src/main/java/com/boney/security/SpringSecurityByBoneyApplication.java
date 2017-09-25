package com.boney.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = { "com.boney.security" })
public class SpringSecurityByBoneyApplication extends SpringBootServletInitializer {

	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder.sources(SpringSecurityByBoneyApplication.class).bannerMode(Banner.Mode.OFF);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringSecurityByBoneyApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityByBoneyApplication.class, args);
	}
}
