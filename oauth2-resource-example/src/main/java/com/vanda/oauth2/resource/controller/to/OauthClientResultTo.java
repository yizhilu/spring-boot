package com.vanda.oauth2.resource.controller.to;

import java.io.Serializable;

public class OauthClientResultTo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1821443642488613984L;

  /**
   * 客户端ID
   */
  private String clientId;

  /**
   * 客户端secret
   */
  private String clientSecret;

  /**
   * 用户名
   */
  private String userName;

  /**
   * 密码
   */
  private String passWord;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }


}
