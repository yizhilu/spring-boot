package com.vanda.oauth2.server.service.security;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vanda.oauth2.server.entity.ClientRoleEntity;
import com.vanda.oauth2.server.entity.OauthAccountEntity;
import com.vanda.oauth2.server.repository.OauthAccountRepository;
import com.vanda.oauth2.server.service.RoleService;

/**
 * 
 * @author Administrator
 *
 */
public class OauthAccountSecurityDetailsService implements UserDetailsService {

  @Autowired
  private OauthAccountRepository oauthAccountRepository;
  @Autowired
  private RoleService roleService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    /*
     * 查找用户的处理过程描述如下： 1、首先查找代理商信息，如果没有找到就差早商户信息，如果还没有找到，就抛出异常 2、查找这个账号对应的角色信息（当然是目前还可以用的角色信息）
     * 3、构造UserDetails对象，并返回
     */
    // 查询用户基本信息
    OauthAccountEntity currentAccount = this.oauthAccountRepository.findByUserName(username);
    if (currentAccount == null) {
      throw new UsernameNotFoundException("没有发现指定的账号！");
    }
    // 查询用户角色信息
    List<ClientRoleEntity> roles = this.roleService.findByUserId(currentAccount.getId());
    List<SimpleGrantedAuthority> authorities = new LinkedList<>();
    if (roles != null) {
      for (ClientRoleEntity role : roles) {
        SimpleGrantedAuthority authoritie = new SimpleGrantedAuthority(role.getName());
        authorities.add(authoritie);
      }
    }
    // 角色信息形成authorities集合对象
    UserDetails securityDetails = new User(username, currentAccount.getPassword(), authorities);
    return securityDetails;
  }
}
