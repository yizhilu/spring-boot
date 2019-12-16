package com.vanda.oauth2.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vanda.oauth2.server.controller.model.ResponseModel;
import com.vanda.oauth2.server.controller.to.OauthClientDetailsTo;
import com.vanda.oauth2.server.controller.to.OauthClientResultTo;
import com.vanda.oauth2.server.service.OauthClientDetailsService;

@RestController
@RequestMapping("/v1/oauthClientDetails")
public class OauthClientDetailsController extends BaseController {

  @Autowired
  private OauthClientDetailsService oauthClientDetailsService;

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ResponseModel addOauthClientAndAccount(
      @RequestBody OauthClientDetailsTo oauthClientDetailsTo) {
    try {
      OauthClientResultTo oauthClientResultTo =
          oauthClientDetailsService.addOauthClientAndAccount(oauthClientDetailsTo);
      return this.buildHttpReslut(oauthClientResultTo);
    } catch (Exception e) {
      e.printStackTrace();
      return this.buildHttpReslutForException(e);
    }
  }

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public ResponseModel test() {
    try {
      return this.buildHttpReslut();
    } catch (Exception e) {
      e.printStackTrace();
      return this.buildHttpReslutForException(e);
    }
  }
}
