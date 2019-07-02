package com.hecheng.security.configuration.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.common.base.Objects;
import com.hecheng.security.configuration.security.jwt.utils.JwtUserFactory;
import com.hecheng.security.entity.AccountEntity;
import com.hecheng.security.entity.RoleEntity;
import com.hecheng.security.entity.UserEntity;
import com.hecheng.security.entity.enums.StatusType;
import com.hecheng.security.service.AccountService;

public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private AccountService accountService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AccountEntity currentAccount = accountService.findByUserName(username);
    if (currentAccount == null) {
      throw new UsernameNotFoundException("没有发现指定的账号！");
    }
    if (!Objects.equal(StatusType.STATUS_NORMAL, currentAccount.getStatus())) {
      throw new UsernameNotFoundException("账号已被禁用，请联系管理员！");
    }
    UserEntity user = accountService.findUserByAccountId(currentAccount.getId());
    if (user == null) {
      throw new UsernameNotFoundException(
          String.format("No user found with username '%s'.", username));
    } else {
      List<RoleEntity> roles = new ArrayList<>();
      return JwtUserFactory.create(currentAccount, roles);
    }
  }
}
