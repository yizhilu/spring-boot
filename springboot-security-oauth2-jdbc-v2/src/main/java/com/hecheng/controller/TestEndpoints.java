package com.hecheng.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEndpoints {
  @GetMapping("/v1/test")
  public Object test(Principal user) {
    // for debug

    return user;
  }

  // 暴露一个商品查询接口，后续不做安全限制，一个订单查询接口，后续添加访问控制
  @GetMapping("/product/{id}")
  public Object getProduct(@PathVariable String id) {
    // for debug
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return authentication;
  }

  @GetMapping("/api/{id}")
  public Object getOrder(@PathVariable String id) {
    // for debug
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication;
  }

}
