package com.zero.springsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    // 在内存中配置用户
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 在内存中定义一个用户
        auth.inMemoryAuthentication().withUser("The-zero").password("123456").roles("admin");
    }

    // 在数据库中配置用户
    /*@Autowired
    DataSource dataSource;
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        if (!manager.userExists("javaboy")) {
            manager.createUser(User.withUsername("javaboy").password("123").roles("admin").build());
        }
        if (!manager.userExists("江南一点雨")) {
            manager.createUser(User.withUsername("江南一点雨").password("123").roles("user").build());
        }
        return manager;
    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 指定忽略的资源
        web.ignoring().antMatchers("/js/**","/css/**","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 前后端不分离的configure
         */
       /* http.authorizeRequests()
                .anyRequest().authenticated() // 拦截任何请求 要求认证
                .and()// 方法表示结束当前标签，上下文回到HttpSecurity，开启新一轮的配置
                .formLogin()
                .loginPage("/login.html") // 把登录页面和登录接口都设置为 /login.html
                .loginProcessingUrl("/doLogin") // 重新指定登录接口
                .defaultSuccessUrl("/hello",false) // 如果之前有访问的地址就跳转到之前输入的地址，否则就跳转到/hello 如果第二个参数为true就无论如何都会跳转到/hello
                .failureUrl("https://www.baidu.com") // 登录失败重定向的url failureForwardUrl() 会发生服务端跳转
                .permitAll() // 表示登录相关的页面/接口不要被拦截。
                .and()
                .logout() // 配置注销
                .logoutUrl("/logout")
                // logoutRequestMatcher 方法不仅可以修改注销 URL，还可以修改请求方式，实际项目中，这个方法和 logoutUrl 任意设置一个即可。
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))
                .logoutSuccessUrl("/index") // 注销要跳转的页面
                .deleteCookies() // 清除cookie
                .clearAuthentication(true) // 分别表示清除认证信息和使 HttpSession 失效，默认可以不用配置，默认就会清除。
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf().disable(); // 关闭 csrf*/

        /**
         * 前后端分离的configure
         */
        http.authorizeRequests()
                // 设置不同角色的权限
                // Security 在匹配的时候也是按照从上往下的顺序来匹配，一旦匹配到了就不继续匹配了，所以拦截规则的顺序很重要
                // 这里的匹配规则我们采用了 Ant 风格的路径匹配符，Ant 风格的路径匹配符在 Spring 家族中使用非常广泛，它的匹配规则也非常简单
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                // 表示任意其他的请求 只要认证登录即可访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/doLogin")
                // 设置前端表单的名称 默认为username和password
                /*.usernameParameter("name")
                .passwordParameter("passwd")*/
                // successHandler 方法的参数是一个 AuthenticationSuccessHandler 对象，这个对象中我们要实现的方法是 onAuthenticationSuccess。
                .successHandler((req, resp, authentication) -> {
                    // 利用 HttpServletRequest 我们可以做服务端跳转，利用 HttpServletResponse 我们可以做客户端跳转，当然，也可以返回 JSON 数据。
                    // Authentication 参数则保存了我们刚刚登录成功的用户信息。
                    Object principal = authentication.getPrincipal();
                    // 设置数据的格式
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, e) -> {
                    // e 为异常类型
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    if (e instanceof LockedException) {
                        out.write("账户被锁定，请联系管理员!");
                    } else if (e instanceof CredentialsExpiredException) {
                        out.write("密码过期，请联系管理员!");
                    } else if (e instanceof AccountExpiredException) {
                        out.write("账户过期，请联系管理员!");
                    } else if (e instanceof DisabledException) {
                        out.write("账户被禁用，请联系管理员!");
                    } else if (e instanceof BadCredentialsException) {
                        out.write("用户名或者密码输入错误，请重新输入!");
                    }
                    out.write(e.getMessage());
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .logout() // 注销
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("注销成功");
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .csrf().disable().exceptionHandling()
                .authenticationEntryPoint((req, resp, authException) -> {  // 重写用户未认证的处理方法
                            resp.setContentType("application/json;charset=utf-8");
                            PrintWriter out = resp.getWriter();
                            out.write("尚未登录，请先登录");
                            out.flush();
                            out.close();
                        }
                );
    }

    /**
     * 由于 Spring Security 支持多种数据源，例如内存、数据库、LDAP 等，
     * 不同来源的数据被共同封装成了一个 UserDetailService 接口，任何实现了该接口的对象都可以作为认证数据源。
     * 因此我们还可以通过重写 WebSecurityConfigurerAdapter 中的 userDetailsService 方法来提供一个 UserDetailService 实例进而配置多个用户：
     */
    /*@Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("javaboy").password("123").roles("admin").build());
        manager.createUser(User.withUsername("江南一点雨").password("123").roles("user").build());
        return manager;
    }*/


    /**
     * 角色继承
     * @return
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        // 使用角色继承,上级能具备下级的所有权限
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        // 配置时，需要给角色手动加上 ROLE_ 前缀
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }
}
