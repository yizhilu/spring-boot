package com.vanda.oauth2.server.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthClientDetailsController extends BaseController {
  @RequestMapping(value = "/check/user", method = RequestMethod.GET)
  public Object user(Principal principal) {
    return principal;
  }
}
