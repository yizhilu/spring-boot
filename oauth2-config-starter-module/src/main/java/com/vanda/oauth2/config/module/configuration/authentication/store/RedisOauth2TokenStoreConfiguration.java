package com.vanda.oauth2.config.module.configuration.authentication.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * oauth2 token 储存在redis
 * 
 * @author hecheng
 *
 */
@Configuration
public class RedisOauth2TokenStoreConfiguration {

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;
  @Autowired(required = false)
  protected ClientDetailsService clientDetailsService;

  @Bean
  public TokenStore tokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }

  @Bean
  @Autowired
  public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
    TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
    handler.setTokenStore(tokenStore);
    handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
    handler.setClientDetailsService(clientDetailsService);
    return handler;

  }

  @Bean
  @Autowired
  public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
    TokenApprovalStore store = new TokenApprovalStore();
    store.setTokenStore(tokenStore);
    return store;
  }
}
