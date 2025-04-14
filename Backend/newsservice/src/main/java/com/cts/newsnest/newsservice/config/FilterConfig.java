package com.cts.newsnest.newsservice.config;


import com.cts.newsnest.newsservice.filter.JWTFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Indicates this as a configuration class
 */
@Configuration
public class FilterConfig {
    /*
     *  Create a bean for FilterRegistrationBean.
     *  1. Register the JwtFilter For Both User And Admin Role
     *  2. add URL pattern for all requests so that any request for
     *     that URL pattern will be intercepted by the filter
     */


    @Bean
    public FilterRegistrationBean jwtUserFilter() {

        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new JWTFilter());

        filter.addUrlPatterns("/newsnest/v1/news");

        return filter;

    }
}
