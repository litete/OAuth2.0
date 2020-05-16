package com.oauth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @ClassName AuthorizationServer
 * @Description 授权服务器配置
 * @Author Zesysterm
 * @Date 2020/5/9 22:00
 * @Version 1.0
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 配置客户端信息从数据库中读取
     */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    /**
     * 设置授权码模式的授权码如何存取：
     *      InMemoryAuthorizationCodeServices：采用内存方式
     *      RandomValueAuthorizationCodeServices：
     *      JdbcAuthorizationCodeServices：采用数据库存储默认是存入oauth_code表
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 客户端管理服务：
     * 接口定义了一些操作使得你可以对令牌进行一些必要的管理，令牌可以被用来 加载身份信息，里面包含了这个令牌的相关权限
     * 可以创建 AuthorizationServerTokenServices 这个接口的实现，则需要继承 DefaultTokenServices 这个类
     * 默认的，当它尝试创建一个令牌的时候，是使用随机值来进行填充的，除了持久化令牌是委托一个 TokenStore 接口来实现以外，
     * 这个类几乎帮你做了所有的事情。
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices();
        //配置客户端详情服务
        service.setClientDetailsService(clientDetailsService);
        //支持刷新令牌
        service.setSupportRefreshToken(true);

        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);

        //令牌存储策略，配置对应的tokenStore
        service.setTokenStore(tokenStore);
        // 令牌默认有效期2小时
        service.setAccessTokenValiditySeconds(7200);
        // 刷新令牌默认有效期3天 return service;
        service.setRefreshTokenValiditySeconds(259200);
        return service;
    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束，暴露除了一些endpoint。
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
        // /oauth/token_key这个endpoint当使用JwtToken且使用非对称加密时，资源服务用于获取公钥而开放的，这里指这个 endpoint完全公开。
                .tokenKeyAccess("permitAll()")
        // /oauth/check_token用于资源服务令牌解析端点。
                .checkTokenAccess("permitAll()")
        // 允许表单认证，通过表单来申请令牌。
                .allowFormAuthenticationForClients() ;
    }

    /**
     * 用来配置令牌（token）的访问端点和令牌服务(token services)。
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //配置认证管理器
                .authenticationManager(authenticationManager)
                //配置用来设置授权码服务的，主要用于 "authorization_code" 授权码类型模式。
                .authorizationCodeServices(authorizationCodeServices)
                //配置tokenService
                .tokenServices(tokenService())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * 配置客户端详情，客户端信息在这里进行初始化。可以设置在内存中也可以读取数据库信息。
     * 比如对应的信息：
     *      client_id：（必须的）用来标识客户的Id。
     *      client_secret：（需要值得信任的客户端）客户端安全码，如果有的话。
     *      resourcesId：对应的资源的id
     *      authorizedGrantType：认证授权的类型
     *          authorization_code、password、client_credentials、implicit、refresh_token
     *      scopes：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     *      autoApprove：授权页面
     *      redirectUris：回调地址
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //默认读取数据库的oauth_client_details表，然后就读取出这信息了，这个不需要程序员去写。
        clients.withClientDetails(clientDetailsService);
    }
}
