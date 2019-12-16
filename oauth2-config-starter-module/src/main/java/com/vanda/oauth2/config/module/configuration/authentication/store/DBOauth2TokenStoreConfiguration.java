package com.vanda.oauth2.config.module.configuration.authentication.store;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * oauth2 token 储存在数据库
 * 
 * @author hecheng
 *
 */
public class DBOauth2TokenStoreConfiguration {

  @Autowired
  private DataSource dataSource;

  @Bean
  public TokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }
}
