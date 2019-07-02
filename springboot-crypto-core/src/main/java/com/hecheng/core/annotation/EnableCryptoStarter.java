package com.hecheng.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.hecheng.core.configuration.CryptoAutoConfiguration;

/**
 * springboot启用加密Starter
 * 
 * @author hc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CryptoAutoConfiguration.class})
public @interface EnableCryptoStarter {

}
