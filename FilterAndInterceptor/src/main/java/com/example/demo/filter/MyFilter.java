package com.example.demo.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// 过滤器用@Order注解控制执行顺序，通过@Order控制过滤器的级别，值越小级别越高越先执行
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(urlPatterns = "/*", filterName = "myTestFilter")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(this.getClass().getSimpleName() + "创建啦...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(this.getClass().getSimpleName() + "执行啦...");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println(this.getClass().getSimpleName() + "被摧毁啦...");
    }

}