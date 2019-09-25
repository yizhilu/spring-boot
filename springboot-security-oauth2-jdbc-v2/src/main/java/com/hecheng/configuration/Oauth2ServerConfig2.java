package com.hecheng.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.hecheng.configuration.security.OauthAccountSecurityDetailsService;

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
public class Oauth2ServerConfig2 {
  /**
   * 这个配置为客户端账号密码存储在数据库时password授权时使用
   * 
   * @author hc
   *
   */
  @Configuration
  @Order(4)
  protected class Oauth2SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * 需要配置这个支持password模式 数据库模式
     */
    @Bean("oauthAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
      AuthenticationManager manager = super.authenticationManagerBean();
      return manager;
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
      provider.setPasswordEncoder(bCryptPasswordEncoder());
      return provider;
    }


    @Override
    @Bean(value = "oauthUserDetailsService")
    public OauthAccountSecurityDetailsService userDetailsService() {
      return new OauthAccountSecurityDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
      return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.anonymous().disable().requestMatchers().anyRequest().and().authorizeRequests()
          .antMatchers("/oauth/**").permitAll();
      // http.anonymous().disable().authorizeRequests().antMatchers("/oauth/*").permitAll();
    }
  }

  /**
   *
   *
   * 配置认证授权服务器
   */
  @Configuration
  @EnableAuthorizationServer
  protected static class AuthorizationServerConfiguration
      extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    @Qualifier("oauthAuthenticationManager")
    private AuthenticationManager authenticationManager;
    @Autowired
    @Qualifier("oauthUserDetailsService")
    private OauthAccountSecurityDetailsService userDetailsService;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * token 存储在redis中
     * 
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
      return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
      TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
      handler.setTokenStore(tokenStore);
      handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetails()));
      handler.setClientDetailsService(clientDetails());
      return handler;

    }

    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
      TokenApprovalStore store = new TokenApprovalStore();
      store.setTokenStore(tokenStore);
      return store;
    }

    /**
     * 客户端账号储存在数据库中
     * 
     * @return
     */
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
      security.realm("api")// <br>
          .tokenKeyAccess("permitAll()")// <br>
          // isAuthenticated():排除anonymous
          // isFullyAuthenticated():排除anonymous以及remember-me
          .checkTokenAccess("isAuthenticated()")// <br>
          // 允许表单认证
          .allowFormAuthenticationForClients();
    }

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
      clients.withClientDetails(clientDetails());
    }

    /***
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.tokenStore(tokenStore());
      endpoints.authenticationManager(authenticationManager);
      endpoints.userDetailsService(userDetailsService);
      // endpoints.accessTokenConverter(accessTokenConverter());
      // 设置认证管理器
      // 设置访问/oauth/token接口，获取token的方式
      endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
      // 设置token的存储方式
      // 要使用refresh_token的话，需要额外配置userDetailsService
      endpoints.reuseRefreshTokens(true);
      // 自定义登录或者鉴权失败时的返回信息
      // endpoints.exceptionTranslator(webResponseExceptionTranslator);
    }
  }
  /**
   *
   * 资源服务器
   *
   */
  @Configuration
  @EnableResourceServer
  protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
      resources.resourceId("api").stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
      http// .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
          .requestMatchers().anyRequest().and().anonymous().disable().authorizeRequests()
          .antMatchers("/api/**").authenticated();// 配置api访问控制，必须认证过后才可以访问
    }
  }

}

