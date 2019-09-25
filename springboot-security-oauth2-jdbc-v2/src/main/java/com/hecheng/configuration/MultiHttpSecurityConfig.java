package com.hecheng.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.hecheng.configuration.security.UserSecurityDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MultiHttpSecurityConfig {
  @Value("${author.apiIgnoreUrls}")
  private String[] apiIgnoreUrls;
  @Value("${author.adminIgnoreUrls}")
  private String[] adminIgnoreUrls;
  @Value("${author.staticIgnoreUrls}")
  private String[] staticIgnoreUrls;

  @Bean
  public Md5PasswordEncoder passwordEncoder() {
    return new Md5PasswordEncoder();

  }


  /**
   * 管理后台登录http权限验证
   */
  @Configuration
  @Order(1)
  public class AdminLoginWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      // 多HttpSecurity配置时必须设置这个，除最后一个外，因为不设置的话默认匹配所有，就不会执行到下面的HttpSecurity了
      // /v1/**url 执行这里的验证
      http.antMatcher("/v1/**").authorizeRequests()
          // 系统中的“登录页面”在被访问时，不进行权限控制
          .antMatchers(adminIgnoreUrls).permitAll()
          // 静态资源
          .antMatchers(staticIgnoreUrls).permitAll()
          // 其它访问都要验证权限
          .anyRequest().authenticated().and()
          // ==================== 设定登录页的url地址，它不进行权限控制
          .formLogin()
          // 由于后端提供的都是restful接口，并没有直接跳转的页面
          // 所以只要访问的url没有通过权限认证，就跳到这个请求上，并直接排除权限异常
          .loginPage("/v1/security/loginFail")
          // 登录请求点
          .loginProcessingUrl("/v1/login")
          // 登录失败，返回到这里
          .failureForwardUrl("/v1/security/loginFail")
          // 登录成功后，默认到这个URL，返回登录成功后的信息
          .successForwardUrl("/v1/security/loginSuccess").permitAll().and().headers().frameOptions()
          .disable().and()
          // ===================== 设定登出后的url地址
          .logout()
          // 登出页面
          .logoutUrl("/v1/security/logout")
          // 登录成功后
          .logoutSuccessUrl("/v1/security/logoutSuccess").permitAll().and()
          // ===================== 关闭csrf
          .csrf().disable().rememberMe()
          // 持续化登录，登录时间为100天
          .tokenValiditySeconds(100 * 24 * 60 * 60).rememberMeCookieName("persistence")
          .alwaysRemember(true).userDetailsService(adminUserDetailsService());

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(this.adminDaoAuthenticationProvider());
    }

    @Bean(value = "adminDaoAuthenticationProvider")
    public DaoAuthenticationProvider adminDaoAuthenticationProvider() {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setHideUserNotFoundExceptions(false);
      provider.setUserDetailsService(this.adminUserDetailsService());
      provider.setPasswordEncoder(passwordEncoder());
      return provider;
    }

    @Bean(value = "adminUserDetailsService")
    public UserDetailsService adminUserDetailsService() {
      return new UserSecurityDetailsService();
    }
  }
}
