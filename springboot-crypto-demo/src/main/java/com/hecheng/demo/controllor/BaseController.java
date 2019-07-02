package com.hecheng.demo.controllor;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hecheng.demo.controllor.model.ResponseCode;
import com.hecheng.demo.controllor.model.ResponseModel;


public class BaseController {

  /**
   * 日志
   */
  private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

  /**
   * 获取登陆者账号
   * 
   * @param operator
   * @return
   */
  protected String getPrincipalAccount(Principal operator) {
    // 通过operator确定当前的登录者信息
    String account = operator.getName();
    Validate.notBlank(account, "not found op user!");
    return account;
  }

  /**
   * 当异常状况时，使用该方法构造返回值
   * 
   * @param e 错误的异常对象描述
   * @return 组装好的异常结果
   */
  protected ResponseModel buildHttpReslutForException(Exception e) {
    String errorMsg = "";
    if (e != null) {
      errorMsg = e.getMessage();
    }

    ResponseModel result =
        new ResponseModel(new Date().getTime(), null, ResponseCode._501, errorMsg);
    return result;
  }

  protected ResponseModel buildHttpCryptoReslut(Object obj) {
    Map<String, Object> map = new HashMap<>();
    ResponseModel result = null;
    if (obj != null) {
      map.put("result", obj);
      return new ResponseModel(new Date().getTime(), map, ResponseCode._200, null);
    }
    result = new ResponseModel(new Date().getTime(), obj, ResponseCode._200, null);
    return result;
  }

  /**
   * 该方法不返回任何信息，只是告诉调用者，调用业务成功了。
   * 
   * @return
   */
  protected ResponseModel buildHttpReslut() {
    return new ResponseModel(new Date().getTime(), null, ResponseCode._200, null);
  }
}
