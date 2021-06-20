package com.dsc.ajude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.dsc.ajude.filter.TokenFilter;

@SpringBootApplication
public class AjudeApplication {
	
	@Bean
	public FilterRegistrationBean<TokenFilter> filterJwt() {
		FilterRegistrationBean<TokenFilter> filterRB = new FilterRegistrationBean<TokenFilter>();
		filterRB.setFilter(new TokenFilter());
		filterRB.addUrlPatterns("/auth");
		return filterRB;
	}

	public static void main(String[] args) {
		SpringApplication.run(AjudeApplication.class, args);
	}
	

}
