package com.hecheng.security.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hecheng.security.controller.model.ResponseModel;

/**
 * 和权限安全相关的接口，在这里暴露<br>
 */
@RestController
public class TestController extends BaseController {

  @RequestMapping(value = "/test2", method = RequestMethod.POST)
  public ResponseModel loginSuccess(Principal logUser) {
    System.out.println(logUser != null ? logUser.getName() : null);
    return this.buildHttpReslut();
  }

  @RequestMapping(value = "/api/test2", method = RequestMethod.POST)
  public ResponseModel loginSuccess2(Principal logUser) {
    System.out.println(logUser != null ? logUser.getName() : null);
    return this.buildHttpReslut();
  }

}
