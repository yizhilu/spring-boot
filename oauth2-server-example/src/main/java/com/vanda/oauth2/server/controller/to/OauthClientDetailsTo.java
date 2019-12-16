package com.vanda.oauth2.server.controller.to;

import java.io.Serializable;

public class OauthClientDetailsTo implements Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = -9108408063818805155L;
  
  /**
   * 
   */
  private String phone;
  /**
   * 客户端所能访问的资源id集合,多个资源时用逗号(,)分隔,如: "unity-resource,mobile-resource".
   * 该字段的值必须来源于与security.xml中标签‹oauth2:resource-server的属性resource-id值一致.
   * 在security.xml配置有几个‹oauth2:resource-server标签, 则该字段可以使用几个该值. 在实际应用中,
   * 我们一般将资源进行分类,并分别配置对应的‹oauth2:resource-server,如订单资源配置一个‹oauth2:resource-server,
   * 用户资源又配置一个‹oauth2:resource-server. 当注册客户端时,根据实际需要可选择资源id,也可根据不同的注册流程,赋予对应的资源id.
   */
  private String resourceIds;
  /**
   * 所属小区
   */
  private String communityId;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getResourceIds() {
    return resourceIds;
  }

  public void setResourceIds(String resourceIds) {
    this.resourceIds = resourceIds;
  }

  public String getCommunityId() {
    return communityId;
  }

  public void setCommunityId(String communityId) {
    this.communityId = communityId;
  }
}
