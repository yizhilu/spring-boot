package com.vanda.oauth2.config.module.configuration.authentication.clientdetails;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

/**
 * oauth2 开启从数据库加载客户端详情
 * 
 * @author hecheng
 *
 */
public class DBClientDetailsConfiguration {

  @Autowired
  private DataSource dataSource;

  @Bean("clientDetailsService")
  public ClientDetailsService clientDetailsService() {
    return new JdbcClientDetailsService(dataSource);
  }
}
