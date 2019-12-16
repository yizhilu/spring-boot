package com.vanda.oauth2.config.module.configuration.authentication.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * oauth2 token 储存在redis
 * 
 * @author hecheng
 *
 */
public class RedisOauth2TokenStoreConfiguration {

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;

  @Bean
  public TokenStore tokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }
}
