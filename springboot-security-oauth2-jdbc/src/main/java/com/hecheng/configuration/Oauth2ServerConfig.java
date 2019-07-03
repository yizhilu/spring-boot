package com.hecheng.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.hecheng.configuration.security.UserSecurityDetailsService;

/***
 * 配置认证授权服务器
 * 
 * @author hc
 *
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {
  @Autowired
  private DataSource dataSource;
  @Autowired
  private TokenStore tokenStore;
  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;
  @Autowired
  @Qualifier("userSecurityDetailsService")
  private UserSecurityDetailsService userDetailsService;

  @Bean
  public TokenStore tokenStore() {
    // 使用数据库存储
    return new JdbcTokenStore(dataSource);
  }

  // 声明 ClientDetails实现
  @Bean
  public ClientDetailsService clientDetails() {
    // 使用数据库存储
    return new JdbcClientDetailsService(dataSource);
  }

  /***
   * 用来配置令牌端点(Token Endpoint)的安全约束.
   */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.realm("oauth2-resource")// <br>
        .tokenKeyAccess("permitAll()")// <br>
        .checkTokenAccess("isAuthenticated()")// <br>
        // 允许表单认证
        .allowFormAuthenticationForClients();
  }

  /**
   * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化， 把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetails());
  }

  /***
   * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    // 设置认证管理器
    endpoints.authenticationManager(authenticationManager);
    // 设置访问/oauth/token接口，获取token的方式
    endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    // 设置token的存储方式
    endpoints.tokenStore(tokenStore);
    // 要使用refresh_token的话，需要额外配置userDetailsService
    endpoints.userDetailsService(userDetailsService);
    // 自定义登录或者鉴权失败时的返回信息
    // endpoints.exceptionTranslator(webResponseExceptionTranslator);
    // // 配置TokenServices参数
    // DefaultTokenServices tokenServices = new DefaultTokenServices();
    // tokenServices.setTokenStore(endpoints.getTokenStore());
    // // 是否支持token刷新
    // tokenServices.setSupportRefreshToken(false);
    // tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
    // // 一种访问令牌增强器，在将其保存在令牌存储中之前将应用于该新令牌。
    // tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
    // // 设置令牌过期时间
    // tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)); // 30天
    // endpoints.tokenServices(tokenServices);
  }

}

