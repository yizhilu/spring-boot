package com.hecheng.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class TestController {

  @RequestMapping(value = "/token/test", method = RequestMethod.GET)
  public Object test(Principal logUser) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return "test" + authentication.getName();
  }

  @RequestMapping(value = "/token/user", method = RequestMethod.GET)
  @PreAuthorize("hasAnyRole('ROLE_USER')") // 注解验证时默认加上前缀ROLE_，原因后面文章再讲
  public Object user(Principal logUser) {
    return "user" + logUser.getName();
  }

  @RequestMapping(value = "/user/a", method = RequestMethod.GET)
  public Object user2(Principal logUser) {
    return "user" + logUser.getName();
  }

  @RequestMapping(value = "/admin/b", method = RequestMethod.GET)
  public Object admin(Principal logUser) {
    return "user" + logUser.getName();
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(value = "/token/admin", method = RequestMethod.GET)
  public Object admin2(Principal logUser) {
    return "user" + logUser.getName();
  }
}
