package com.dsc.ajude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.dsc.ajude.filtro.TokenFiltro;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties
@EnableSwagger2
@EntityScan(basePackages = {"com.dsc.ajude.modelos"})
public class AjudeApplication {
	
	@Bean
	public FilterRegistrationBean<TokenFiltro> filterJwt() {
		FilterRegistrationBean<TokenFiltro> filterRB = new FilterRegistrationBean<TokenFiltro>();
		filterRB.setFilter(new TokenFiltro());
		filterRB.addUrlPatterns("/v1/api/auth/*");
		return filterRB;
	}

	public static void main(String[] args) {
		SpringApplication.run(AjudeApplication.class, args);
	}
	

}
