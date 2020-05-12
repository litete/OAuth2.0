package com.oauth.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @ClassName TokenConfig
 * @Description
 * @Author
 * @Date 2020/5/9 22:13
 * @Version 1.0
 **/
@Configuration
public class TokenConfig {

    private static String KEY = "uaa123";

    /**
     * （2）
     * InMemoryTokenStore、JdbcTokenStore、JwtTokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        //使用内存存储令牌
//        return new InMemoryTokenStore();
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(KEY); //对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }
}
