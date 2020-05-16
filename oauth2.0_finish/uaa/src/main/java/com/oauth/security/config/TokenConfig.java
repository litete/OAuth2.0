package com.oauth.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @ClassName TokenConfig
 * @Description Token令牌配置
 * @Author Zesysterm
 * @Date 2020/5/9 22:13
 * @Version 1.0
 **/
@Configuration
public class TokenConfig {

    /**
     * 对称加密的密钥
     */
    private static String KEY = "uaa123";

    /**
     * 配置令牌的类型：
     *      InMemoryTokenStore：使用内存方式保存令牌
     *      JdbcTokenStore：使用数据库查询的方式保存令牌
     *      JwtTokenStore：使用jwt的方式保存令牌
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * 令牌产生需要一定的hash算法。
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //对称秘钥，资源服务器使用该秘钥来验证
        converter.setSigningKey(KEY);
        return converter;
    }
}
