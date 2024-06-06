package com.mouse.challenge.config;

import com.mouse.challenge.config.jwt.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 필터 등록
 */
@Configuration
public class FilterConfig {
//    @Bean
//    public FilterRegistrationBean<JwtAuthenticationFilter> filter() {
//        FilterRegistrationBean<JwtAuthenticationFilter> bean = new FilterRegistrationBean<>(new JwtAuthenticationFilter());
//        bean.addUrlPatterns("/*");
//        bean.setOrder(0); // 낮은 번호가 필터 중에서 가장 먼저 실행됨.
//        return bean;
//    }
}
