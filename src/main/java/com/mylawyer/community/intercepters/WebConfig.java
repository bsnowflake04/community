package com.mylawyer.community.intercepters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/*@EnableWebMvc*/
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionIntercepter sessionIntercepter;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*registry.addInterceptor(new LocaleInterceptor());
        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");*/
        registry.addInterceptor(sessionIntercepter).addPathPatterns("/**")/*.excludePathPatterns("/admin/**")*/;
    }
}
