package com.hecheng.curator.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hecheng.curator.entity.UserEntity;
import com.hecheng.curator.service.AccountService;
import com.hecheng.curator.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
  @Autowired
  private AccountService accountService;

  @Override
  public UserEntity findByUserName(String username) {
    return accountService.findUserByAccount(username);
  }

}
