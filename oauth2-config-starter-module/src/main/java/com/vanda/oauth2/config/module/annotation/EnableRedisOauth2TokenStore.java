package com.vanda.oauth2.config.module.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.vanda.oauth2.config.module.configuration.authentication.store.RedisOauth2TokenStoreConfiguration;

/**
 * 使用此注解启用数据库模式存储Oauth2Token
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@Import(RedisOauth2TokenStoreConfiguration.class)
public @interface EnableRedisOauth2TokenStore {

}
