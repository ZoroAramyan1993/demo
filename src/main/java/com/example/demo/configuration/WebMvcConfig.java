package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.*;

public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private static final long MAX_AGE_SICS = 3600;

//    @Bean
//    WebMvcConfigurer myWebMvcConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//
//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
//                ViewControllerRegistration r = registry.addViewController("/");
//                r.setViewName("forward:/register.html");
//            }
//        };
//    }
//
//
//
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*").
//                allowedMethods("HEAD","OPTIONS","GET","POST","PUT","PATCH","DELETE").maxAge(MAX_AGE_SICS);
//    }
}
