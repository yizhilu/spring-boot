package com.vanda.oauth2.resource.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

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
}

