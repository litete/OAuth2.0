package com.oauth.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName WebSecurityConfig
 * @Description
 * @Author
 * @Date 2020/5/10 15:16
 * @Version 1.0
 **/
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //安全拦截机制，由于我们上面对controller做了一个注解，这里面暂时不用具体写拦截，但是这个要有。
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
        .antMatchers("/r/**").authenticated()//所有/r/**的请求必须认证通
        .anyRequest().permitAll()//除了/r/**，其它的请求可以访问
        ;
    }
}
