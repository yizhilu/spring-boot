package com.vanda.oauth2.server.service.internal;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vanda.oauth2.server.entity.OauthAccountEntity;
import com.vanda.oauth2.server.repository.OauthAccountRepository;
import com.vanda.oauth2.server.service.OauthAccountService;
import com.vanda.oauth2.server.service.RoleService;

@Service
public class OauthAccountServiceImpl implements OauthAccountService{
  
  @Autowired
  private OauthAccountRepository oauthAccountRepository;
  
  @Autowired
  private RoleService roleService;
  
  @Override
  @Transactional
  public OauthAccountEntity addOauthAccount(OauthAccountEntity oauthAccountEntity) {
    String username = oauthAccountEntity.getUserName();
    Validate.notBlank(username, "用户名不能为空！");
    String password = new Md5PasswordEncoder().encodePassword(username.substring(username.length()-6), null);
    oauthAccountEntity.setPassword(password);
    oauthAccountEntity = oauthAccountRepository.saveAndFlush(oauthAccountEntity);
    
    //绑定角色
    roleService.bindRoleForUser(oauthAccountEntity.getId());
    return oauthAccountEntity;
  }

}
