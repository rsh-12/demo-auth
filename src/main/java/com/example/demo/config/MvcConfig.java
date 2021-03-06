package com.example.demo.config;
/*
 * Date: 12/2/20
 * Time: 5:30 PM
 * */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // заставляем нашу статику работать :)
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
