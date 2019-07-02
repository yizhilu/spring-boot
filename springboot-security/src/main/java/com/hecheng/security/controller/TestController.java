package com.hecheng.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hecheng.security.controller.model.ResponseModel;

/**
 * 和权限安全相关的接口，在这里暴露<br>
 */
@RestController
public class TestController extends BaseController {
  @Autowired(required = false)
  private PasswordEncoder passwordEncoder;

  @RequestMapping(value = "/test2", method = RequestMethod.POST)
  public ResponseModel loginSuccess2() {
    System.out.println(passwordEncoder);
    return this.buildHttpReslut();
  }


}
