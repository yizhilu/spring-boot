package com.vanda.oauth2.config.module.configuration.authentication.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器配置类
 * 
 * @author hc
 *
 */
@EnableResourceServer
public abstract class AbstractResourceServerConfigurerAdapter
    extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    super.configure(resources);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    super.configure(http);
  }


}
