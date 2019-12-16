package com.vanda.oauth2.resource.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.vanda.oauth2.config.module.configuration.authentication.CustomAccessDeniedHandler;
import com.vanda.oauth2.config.module.configuration.authentication.CustomAuthExceptionEntryPoint;
import com.vanda.oauth2.config.module.configuration.authentication.config.AbstractResourceServerConfigurerAdapter;

/**
 *
 * 资源服务器OAuth2AuthenticationProcessingFilter
 * 
 *
 */
@Configuration
public class ResourceServerConfiguration extends AbstractResourceServerConfigurerAdapter {


  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId("api-resource").stateless(true)
        .accessDeniedHandler(new CustomAccessDeniedHandler())
        .authenticationEntryPoint(new CustomAuthExceptionEntryPoint())
    // 其他扩展点（例如tokenExtractor从传入请求中提取令牌）
    // .tokenExtractor(tokenExtractor)
    // .expressionHandler(expressionHandler)
    ;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/api/**").sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()// <br>
        .antMatchers("/api/**").authenticated().and()// <br>
        .anonymous().disable()// 禁用匿名用户
        // 关闭跨站请求防护
        .csrf().disable();// 配置api访问控制，必须认证过后才可以访问
  }

//  @Autowired
//  private RedisConnectionFactory redisConnectionFactory;
//
//  /**
//   * token 存储在redis中
//   * 
//   * @return
//   */
//  @Bean
//  public TokenStore tokenStore() {
//    return new RedisTokenStore(redisConnectionFactory);
//  }
//
//  @Primary
//  @Bean
//  public ResourceServerTokenServices resourceServerTokenServices() {
//    DefaultTokenServices tokenServices = new DefaultTokenServices();
//    tokenServices.setTokenStore(tokenStore());
//    tokenServices.setSupportRefreshToken(true);
//    return tokenServices;
//  }
}

