package com.example.demo.config;

import com.example.demo.Interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 在拦截器中注入service，发起请求会报出错误，debug跟进发现注入的service怎么是Null
 * 这是因为加载顺序导致的问题，拦截器加载的时间点在springcontext之前，而Bean又是由spring进行管理。
 * 解决方案也很简单，我们在注册拦截器之前，先将Interceptor手动进行注入。
 * 注意：在registry.addInterceptor()注册的是MyInterceptor的实例。
 */
@Configuration
public class DefaultWebConfiguration implements WebMvcConfigurer {

    @Bean
    MyInterceptor myInterceptor(){
        System.out.println("注入了MyInterceptor");
        return new MyInterceptor();
    }

    // 拦截器默认的执行顺序，就是它的注册顺序，也可以通过Order手动设置控制，值越小越先执行
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        // registry.addInterceptor(myInterceptor()).order(-1);
        registry.addInterceptor(myInterceptor())
                .addPathPatterns("/**") //拦截所有路径
                .excludePathPatterns("/login","/","/getValidCode")  //排除路径
                .excludePathPatterns("/static/**"); //排除静态资源路径
    }
}
