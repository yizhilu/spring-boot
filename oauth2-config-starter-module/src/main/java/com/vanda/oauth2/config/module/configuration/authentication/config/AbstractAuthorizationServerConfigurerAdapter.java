package com.vanda.oauth2.config.module.configuration.authentication.config;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.vanda.oauth2.config.module.configuration.properties.Oauth2TokenProperties;

/**
 * 授权服务器配置类
 * 
 * @author hc
 *
 */
@EnableAuthorizationServer
@EnableConfigurationProperties({Oauth2TokenProperties.class})
public abstract class AbstractAuthorizationServerConfigurerAdapter
    extends AuthorizationServerConfigurerAdapter {

  @Autowired
  protected TokenStore tokenStore;
  @Autowired
  protected AuthenticationManager authenticationManager;
  @Autowired
  protected UserDetailsService userDetailsService;
  @Autowired(required = false)
  protected ClientDetailsService clientDetailsService;
  @Autowired
  protected Oauth2TokenProperties oauth2TokenProperties;
  @Autowired(required = false)
  protected JwtAccessTokenConverter jwtAccessTokenConverter;

  /**
   * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化， 把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
   * client模式，没有用户的概念，直接与认证服务器交互，用配置中的客户端信息去申请accessToken，客户端有自己的client_id,client_secret
   * 对应于用户的username,password，而客户端也拥有自己的authorities，当采取client模式认证时，对应的权限也就是客户端自己的authorities。
   *
   * password模式，自己本身有一套用户体系，在认证时需要带上自己的用户名和密码，以及客户端的client_id,client_secret。此时，accessToken所包含的权限是用户本身的权限，而不是客户端的权限。
   * ClientCredentialsTokenEndpointFilter OAuth2AuthenticationProcessingFilter
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    if (Objects.nonNull(clientDetailsService)) {
      clients.withClientDetails(clientDetailsService);
    }
  }

  /**
   * 配置授权服务器端点，如令牌存储，令牌自定义，用户批准和授权类型，不包括端点安全配置
   * 
   * @param endpoints
   * @throws Exception
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    if (tokenStore instanceof JwtTokenStore) {
      TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
      endpoints.accessTokenConverter(jwtAccessTokenConverter);
      tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
      endpoints.tokenEnhancer(tokenEnhancerChain);
    }
    endpoints.tokenStore(tokenStore);
    endpoints.authenticationManager(authenticationManager);
    endpoints.userDetailsService(userDetailsService);
    endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setReuseRefreshToken(oauth2TokenProperties.isReuseRefreshToken());
    defaultTokenServices.setSupportRefreshToken(oauth2TokenProperties.isSupportRefreshToken());
    defaultTokenServices.setTokenStore(tokenStore);
    defaultTokenServices
        .setAccessTokenValiditySeconds(oauth2TokenProperties.getAccessTokenValiditySeconds());
    defaultTokenServices
        .setRefreshTokenValiditySeconds(oauth2TokenProperties.getRefreshTokenValiditySeconds());
    defaultTokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
    // 若通过 JDBC 存储令牌
    if (Objects.nonNull(clientDetailsService)) {
      defaultTokenServices.setClientDetailsService(clientDetailsService);
    }
    endpoints.tokenServices(defaultTokenServices);
  }


  /**
   * 配置授权服务器端点的安全
   * 
   * @param oauthServer
   * @throws Exception
   */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer.tokenKeyAccess("permitAll()")// <br>
        .checkTokenAccess("permitAll()")// <br>
        .allowFormAuthenticationForClients();
  }

}
