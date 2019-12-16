package com.vanda.oauth2.config.module.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.vanda.oauth2.config.module.configuration.authentication.clientdetails.DBClientDetailsConfiguration;

/**
 * 开启从数据库加载客户端详情
 * 
 * @author hc
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DBClientDetailsConfiguration.class)
public @interface EnableDBClientDetailsService {}
