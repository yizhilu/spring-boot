package com.vanda.oauth2.config.module.model;

/**
 * 统一返回数据对象（对本次请求执行的操作结果是否成功 进行描述 flag=1时执行成功）
 * 
 * @author yhy yinwenjie
 * 
 */
public class ResponseModel {
  /**
   * 正常情况下返回的数据在这里进行记录和描述
   */
  private Long timestemp;

  /** 时间 */
  private Object data;
  /**
   * 响应标记，正常情况下是200
   */
  private ResponseCode responseCode = ResponseCode._200;
  /** 异常信息描述 */
  private String errorMsg;
  /**
   * 接口路径
   */
  private String path;

  public ResponseModel(Long timestemp, Object data, ResponseCode responseCode, String errorMsg) {
    this.timestemp = timestemp;
    this.data = data;
    this.responseCode = responseCode;
    this.errorMsg = errorMsg;
  }

  public ResponseModel() {}

  public Long getTimestemp() {
    return timestemp;
  }

  public void setTimestemp(Long timestemp) {
    this.timestemp = timestemp;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public ResponseCode getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(ResponseCode responseCode) {
    this.responseCode = responseCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

}
