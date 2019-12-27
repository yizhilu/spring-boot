package com.hecheng.curator.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hecheng.curator.entity.UserEntity;
import com.hecheng.curator.repository.AccountUserRepository;
import com.hecheng.curator.service.AccountUserService;

@Service("accountUserService")
public class AccountUserServiceImpl implements AccountUserService {
  @Autowired
  private AccountUserRepository accountUserRepository;

  @Override
  public UserEntity findByAccountId(String accountId) {
    return accountUserRepository.findByAccountId(accountId);
  }

  @Override
  public UserEntity findByUserName(String username) {
    return accountUserRepository.findByUserName(username);
  }

}
