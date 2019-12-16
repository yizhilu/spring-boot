package com.vanda.oauth2.server.service.internal;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanda.oauth2.server.common.utils.StringRandomUtils;
import com.vanda.oauth2.server.controller.to.OauthClientDetailsTo;
import com.vanda.oauth2.server.controller.to.OauthClientResultTo;
import com.vanda.oauth2.server.entity.OauthAccountEntity;
import com.vanda.oauth2.server.entity.OauthClientDetailsEntity;
import com.vanda.oauth2.server.repository.OauthClientDetailsRepository;
import com.vanda.oauth2.server.service.OauthAccountService;
import com.vanda.oauth2.server.service.OauthClientDetailsService;

@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

  @Autowired
  private OauthClientDetailsRepository oauthClientDetailsRepository;

  @Autowired
  private OauthAccountService oauthAccountService;

  @Override
  @Transactional
  public OauthClientDetailsEntity addOauthClientDetails(
      OauthClientDetailsEntity oauthClientDetailsEntity) {
    Validate.notBlank(oauthClientDetailsEntity.getResourceIds(), "客户端所能访问的资源id为空！");
    Validate.notBlank(oauthClientDetailsEntity.getCommunityId(), "所属小区ID不能为空！");
    OauthClientDetailsEntity oauthClientDetailsOld = oauthClientDetailsRepository.findByCommunityId(oauthClientDetailsEntity.getCommunityId());
    if(oauthClientDetailsOld !=null) {
      return oauthClientDetailsOld;
    }
    // 随机生成12位clientSecret
    String clientSecret = StringRandomUtils.getStringRandom(12);
    oauthClientDetailsEntity.setClientSecret(clientSecret);
    oauthClientDetailsEntity.setScope("read,write");
    oauthClientDetailsEntity.setAuthorizedGrantTypes("password");
    oauthClientDetailsEntity.setRefreshTokenValidity(60 * 60 * 24 * 30);
    oauthClientDetailsEntity.setAccessTokenValidity(60 * 60 * 12);
    return oauthClientDetailsRepository.save(oauthClientDetailsEntity);
  }

  @Override
  @Transactional
  public OauthClientResultTo addOauthClientAndAccount(OauthClientDetailsTo oauthClientDetailsTo) {

    Validate.notBlank(oauthClientDetailsTo.getResourceIds(), "客户端所能访问的资源id为空！");
    Validate.notBlank(oauthClientDetailsTo.getPhone(), "电话号码不能够为空！");
    Validate.notBlank(oauthClientDetailsTo.getCommunityId(), "所属小区ID不能为空！");
    OauthClientDetailsEntity oauthClientDetailsEntity = new OauthClientDetailsEntity();
    oauthClientDetailsEntity.setResourceIds(oauthClientDetailsTo.getResourceIds());
    oauthClientDetailsEntity.setCommunityId(oauthClientDetailsTo.getCommunityId());

    oauthClientDetailsEntity = addOauthClientDetails(oauthClientDetailsEntity);

    OauthAccountEntity oauthAccountEntity = new OauthAccountEntity();
    oauthAccountEntity.setUserName(oauthClientDetailsTo.getPhone());
    oauthAccountEntity = oauthAccountService.addOauthAccount(oauthAccountEntity);

    OauthClientResultTo oauthClientResultTo = new OauthClientResultTo();
    oauthClientResultTo.setClientId(oauthClientDetailsEntity.getClientId());
    oauthClientResultTo.setClientSecret(oauthClientDetailsEntity.getClientSecret());
    oauthClientResultTo.setUserName(oauthAccountEntity.getUserName());
    String password = oauthAccountEntity.getUserName();
    oauthClientResultTo.setPassWord(password.substring(password.length() - 6));
    return oauthClientResultTo;
  }

}
