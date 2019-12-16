package com.vanda.oauth2.resource.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class OauthClientDetailsController {



  @RequestMapping(value = "test", method = RequestMethod.GET)
  public Object test(Principal principal) {
    return principal;
  }
}
