package com.hecheng.security.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hecheng.security.entity.UserEntity;
import com.hecheng.security.service.AccountService;
import com.hecheng.security.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
  @Autowired
  private AccountService accountService;

  @Override
  public UserEntity findByUserName(String username) {
    return accountService.findUserByAccount(username);
  }

}
