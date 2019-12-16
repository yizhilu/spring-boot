package com.vanda.oauth2.config.module.configuration.authentication.store;

import java.security.KeyPair;

import javax.annotation.Resource;

import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * oauth2 token 储存在jwt
 * 
 * @author hecheng
 *
 */
@Configuration
public class AuthorizationServerJwtTokenStoreConfiguration {

  @Bean("keyProp")
  public KeyProperties keyProperties() {
    return new KeyProperties();
  }

  @Resource(name = "keyProp")
  private KeyProperties keyProperties;

  @Bean
  public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
    return new JwtTokenStore(jwtAccessTokenConverter);
  }

  @Bean("jwtAccessTokenConverter")
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    KeyPair keyPair = new KeyStoreKeyFactory(keyProperties.getKeyStore().getLocation(),
        keyProperties.getKeyStore().getSecret().toCharArray())
            .getKeyPair(keyProperties.getKeyStore().getAlias());
    converter.setKeyPair(keyPair);
    return converter;
  }
}
