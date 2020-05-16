package com.oauth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @ClassName TokenConfig
 * @Description 网关服务配置类，通过配置多个内部类，设置多个资源
 * @Author Zesysterm
 * @Date 2020/5/9 22:13
 * @Version 1.0
 **/
@Configuration
public class ResouceServerConfig  {

    /**
     * 一般用于指定某个服务，比如这里就是统一的网关。
     */
    public static final String RESOURCE_ID = "res1";


    /**
     * 认证授权uaa服务配置。
    */
    @Configuration
    @EnableResourceServer
    public class UAAServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        private TokenStore tokenStore;

        /**
         * 和order资源服务可以类比
         */
        @Override
        public void configure(ResourceServerSecurityConfigurer resources){
            resources.tokenStore(tokenStore).resourceId(RESOURCE_ID)
                    .stateless(true);
        }

        /**
         * 和order资源服务可以类比
         */
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/uaa/**").permitAll();
        }
    }


    /**
     * 资源Order服务配置
     */
    @Configuration
    @EnableResourceServer
    public class OrderServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        private TokenStore tokenStore;

        /**
         * 和order资源服务可以类比
         */
        @Override
        public void configure(ResourceServerSecurityConfigurer resources){
            resources.tokenStore(tokenStore).resourceId(RESOURCE_ID)
                    .stateless(true);
        }

        /**
         * 和order资源服务可以类比
         */
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/order/**").access("#oauth2.hasAnyScope('ROLE_API','ROLE_ADMIN')");
        }
    }
}
