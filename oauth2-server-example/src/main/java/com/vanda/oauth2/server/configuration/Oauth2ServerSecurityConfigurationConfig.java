package com.vanda.oauth2.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import com.vanda.oauth2.config.module.configuration.authentication.config.AbstractAuthorizationServerConfigurerAdapter;
import com.vanda.oauth2.server.service.security.OauthAccountSecurityDetailsService;

/*****
 * oauth2 数据库方式配置**
 * 
 * @author hc springboot 1.3.x~1.5.x
 * 
 *         security: oauth2: resource: filter-order: 3
 * 
 *         原理是将资源过滤器链提升到springsecurity的过滤器链之前，否则，/api/{id}的认证和鉴权将会被 springSecurityFilterChain
 *         拦截，不被oauth2相关的 过滤器链处理，你应当了解一个 springSecurity 的设计：一次请求，只会被 FilterProxyChain 中的最多一个过滤器链处理。
 */
@Configuration
public class Oauth2ServerSecurityConfigurationConfig {
  /**
   * 这个配置为客户端账号密码存储在数据库时password授权时使用
   * 
   * @author hc
   *
   */
  @Configuration
  @Order(1)
  protected class Oauth2SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthorConfig authorConfig;

    @Bean
    public Md5PasswordEncoder passwordEncoder() {
      return new Md5PasswordEncoder();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean(value = "oauthDaoAuthenticationProvider")
    public DaoAuthenticationProvider daoAuthenticationProvider() {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setHideUserNotFoundExceptions(false);
      provider.setUserDetailsService(this.userDetailsService());
      provider.setPasswordEncoder(passwordEncoder());
      return provider;
    }


    @Override
    @Bean(value = "oauthUserDetailsService")
    public OauthAccountSecurityDetailsService userDetailsService() {
      return new OauthAccountSecurityDetailsService();
    }

    // @Bean
    // public BCryptPasswordEncoder bCryptPasswordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()// <br>
          .antMatchers(authorConfig.getIgnoreApiUrls()).permitAll()// <br>
          .anyRequest().authenticated().and()// <br>
          .formLogin().and().csrf().disable();
    }

    /**
     * 需要配置这个支持password模式 数据库模式
     */
    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
      AuthenticationManager manager = super.authenticationManagerBean();
      return manager;
    }
  }

  /**
   * 令牌的请求由Spring MVC控制器端点处理，对受保护资源的访问由标准的Spring Security请求过滤器处理。为了实现OAuth 2.0授权服务器，Spring
   * Security过滤器链中需要以下端点：
   * 
   * AuthorizationEndpoint用于服务授权请求。默认网址：/oauth/authorize。
   * TokenEndpoint用于服务访问令牌的请求。默认网址：/oauth/token。 实施OAuth 2.0资源服务器需要以下过滤器：
   * 
   * 将OAuth2AuthenticationProcessingFilter用于加载给定的认证访问令牌请求的认证。
   *
   * 配置认证授权服务器
   */
  @Configuration
  @EnableAuthorizationServer
  protected static class AuthorizationServerConfiguration
      extends AbstractAuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("oauthUserDetailsService")
    private OauthAccountSecurityDetailsService userDetailsService;

    /***
     * 用来配置令牌端点(TokenEndpoint)的安全约束.
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
      security.realm("api-resource")// <br>
          .tokenKeyAccess("permitAll()")// <br>
          // isAuthenticated():排除anonymous
          // isFullyAuthenticated():排除anonymous以及remember-me
          .checkTokenAccess("isAuthenticated()")// <br>
          // .checkTokenAccess("permitAll()")// <br>
          // 允许表单认证
          .allowFormAuthenticationForClients();
    }

    /***
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services) 定义授权和令牌端点和令牌服务
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      super.configure(endpoints);

    }
  }
}

