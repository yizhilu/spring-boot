package com.vanda.oauth2.resource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.vanda.oauth2.config.module.annotation.EnableResourceServerJwtTokenStore;

// 启动spring boot
@SpringBootApplication
@EnableResourceServerJwtTokenStore
public class ApplicationStarter {
  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(ApplicationStarter.class).web(true).run(args);
  }
}
