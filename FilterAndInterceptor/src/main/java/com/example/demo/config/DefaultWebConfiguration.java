package com.example.demo.config;

import com.example.demo.Interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DefaultWebConfiguration implements WebMvcConfigurer {
    @Autowired
    MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**") //拦截所有路径
                .excludePathPatterns("/login","/","/getValidCode")  //排除路径
                .excludePathPatterns("/static/**"); //排除静态资源路径
    }
}
