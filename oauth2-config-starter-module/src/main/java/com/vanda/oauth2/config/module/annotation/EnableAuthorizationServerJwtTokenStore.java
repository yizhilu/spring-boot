package com.vanda.oauth2.config.module.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.vanda.oauth2.config.module.configuration.authentication.store.AuthorizationServerJwtTokenStoreConfiguration;

/**
 * 使用此注解启用jwt令牌存储Oauth2Token
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@Import(AuthorizationServerJwtTokenStoreConfiguration.class)
public @interface EnableAuthorizationServerJwtTokenStore {

}
