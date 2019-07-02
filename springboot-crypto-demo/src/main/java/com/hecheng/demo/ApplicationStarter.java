package com.hecheng.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.hecheng.core.annotation.EnableCryptoStarter;

// 启动spring boot
@SpringBootApplication
@EnableCryptoStarter
public class ApplicationStarter {
  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(ApplicationStarter.class).web(true).run(args);
  }
}
