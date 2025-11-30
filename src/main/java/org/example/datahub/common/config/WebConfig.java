package org.example.datahub.common.config;


import org.example.datahub.auth.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    private final JwtFilter jwtFilter;

    public WebConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterBean() {
        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(jwtFilter);
        bean.addUrlPatterns("/api/*");
        return bean;
    }
}
