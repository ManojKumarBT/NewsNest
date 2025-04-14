package com.cts.newsnest.newsservice;

import com.cts.newsnest.newsservice.filter.JWTFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class NewsserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsserviceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean getBean(){
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new JWTFilter());
		bean.addUrlPatterns("/newsnest/v1/*");
		return bean;
	}


}
