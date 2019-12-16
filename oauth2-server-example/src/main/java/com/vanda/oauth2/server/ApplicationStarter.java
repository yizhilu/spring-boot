package com.vanda.oauth2.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.vanda.oauth2.config.module.annotation.EnableAuthorizationServerJwtTokenStore;
import com.vanda.oauth2.config.module.annotation.EnableDBClientDetailsService;

// 启动spring boot
@SpringBootApplication
@EnableAuthorizationServerJwtTokenStore
@EnableDBClientDetailsService
public class ApplicationStarter {
  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(ApplicationStarter.class).web(true).run(args);
  }
}
