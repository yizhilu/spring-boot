package com.hecheng.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/***
 * 这里设置访问路径权限，相当于客户端url权限拦截
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId("test").stateless(true);
  }
  /**
   * 这里设置需要token验证的url
   * 这些url需要在WebSecurityConfigurerAdapter中排掉
   * 否则进入WebSecurityConfigurerAdapter,进行的是basic auth或表单认证,而不是token认证
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()// <br>
        .requestMatchers().antMatchers("/token/**").and()// <br>
        .authorizeRequests()// <br>
        // 配置test访问控制，必须认证过后才可以访问
        .antMatchers("/token/**").authenticated();
  }

}
