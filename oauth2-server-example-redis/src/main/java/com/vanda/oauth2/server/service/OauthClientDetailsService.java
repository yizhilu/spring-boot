package com.vanda.oauth2.server.service;

import com.vanda.oauth2.server.controller.to.OauthClientDetailsTo;
import com.vanda.oauth2.server.controller.to.OauthClientResultTo;
import com.vanda.oauth2.server.entity.OauthClientDetailsEntity;

/**
 * Oauth2客户端服务层
 * @author Client
 *
 */
public interface OauthClientDetailsService {
  
  /**
   * 添加oauth2客户端
   * @param oauthClientDetailsEntity
   * @return
   */
  OauthClientDetailsEntity addOauthClientDetails(OauthClientDetailsEntity oauthClientDetailsEntity);
  
  /**
   * 添加客户端和用户
   * @param clientDetailsTo
   * @return
   */
  OauthClientResultTo addOauthClientAndAccount(OauthClientDetailsTo oauthClientDetailsTo);
}
