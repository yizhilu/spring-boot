package com.vanda.oauth2.server.service;
/**
 * oauth2用户服务层
 * @author Client
 *
 */

import com.vanda.oauth2.server.entity.OauthAccountEntity;

public interface OauthAccountService {
  
  /**
   * 添加oauth用户
   * @param OauthAccountEntity
   * @return
   */
  OauthAccountEntity addOauthAccount(OauthAccountEntity oauthAccountEntity);
}
