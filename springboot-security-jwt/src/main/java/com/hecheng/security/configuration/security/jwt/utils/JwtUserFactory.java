package com.hecheng.security.configuration.security.jwt.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.hecheng.security.configuration.security.jwt.model.JwtUser;
import com.hecheng.security.entity.AccountEntity;
import com.hecheng.security.entity.RoleEntity;
import com.hecheng.security.entity.enums.StatusType;

public final class JwtUserFactory {

  private JwtUserFactory() {}

  public static JwtUser create(AccountEntity user, List<RoleEntity> roles) {
    return new JwtUser(user.getId(), user.getUserName(), user.getPassword(),
        mapToGrantedAuthorities(roles), user.getStatus() == StatusType.STATUS_NORMAL ? true : false,
        user.getLastPasswordResetDate());
  }

  private static List<GrantedAuthority> mapToGrantedAuthorities(List<RoleEntity> roles) {
    if (roles == null || roles.isEmpty()) {
      List<GrantedAuthority> authoritys = new ArrayList<>();
      authoritys.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
      return authoritys;
    } else {
      return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
          .collect(Collectors.toList());
    }
  }
}
