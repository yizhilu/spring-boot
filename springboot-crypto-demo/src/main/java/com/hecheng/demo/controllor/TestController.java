package com.hecheng.demo.controllor;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hecheng.core.annotation.ApiDecryptAnno;
import com.hecheng.core.annotation.ApiEncryptAnno;
import com.hecheng.demo.controllor.model.ResponseModel;

/**
 * 和权限安全相关的接口，在这里暴露<br>
 */
@RestController
public class TestController extends BaseController {

  @ApiEncryptAnno
  @RequestMapping(value = "/test", method = RequestMethod.POST)
  public ResponseModel loginSuccess(@RequestBody JSONObject json) {
    return this.buildHttpCryptoReslut(json);
  }

  @ApiDecryptAnno
  @RequestMapping(value = "/decrypt", method = {RequestMethod.POST, RequestMethod.GET})
  public ResponseModel loginSuccess2(@RequestBody JSONObject json) {
    return this.buildHttpCryptoReslut(json);
  }

}
