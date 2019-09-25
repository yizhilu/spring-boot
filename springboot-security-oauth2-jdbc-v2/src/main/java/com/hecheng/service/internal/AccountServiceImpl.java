package com.hecheng.service.internal;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hecheng.entity.AccountEntity;
import com.hecheng.entity.AccountOperatorEntity;
import com.hecheng.entity.AccountUserEntity;
import com.hecheng.entity.OperatorEntity;
import com.hecheng.entity.UserEntity;
import com.hecheng.entity.enums.StatusType;
import com.hecheng.repository.AccountOperatorRepository;
import com.hecheng.repository.AccountRepository;
import com.hecheng.repository.AccountUserRepository;
import com.hecheng.service.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private AccountOperatorRepository accountOperatorRepository;
  @Autowired
  private AccountUserRepository accountUserRepository;
  @Autowired(required = false)
  private PasswordEncoder passwordEncoder;

  @Override
  public AccountEntity findByUserName(String userName) {
    return accountRepository.findByUserName(userName);
  }

  @Override
  public OperatorEntity findOperatorByAccountAndStatus(String account, StatusType status) {
    return accountOperatorRepository.findByAccountAndStatus(account, status);
  }

  @Override
  public UserEntity findUserByAccount(String username) {
    return accountUserRepository.findByUserName(username);
  }

  @Override
  public UserEntity findUserByAccountId(String accountId) {
    return accountUserRepository.findByAccountId(accountId);
  }

  @Override
  public OperatorEntity findOperatorByAccountId(String accountId) {
    return accountOperatorRepository.findByAccountId(accountId);
  }

  @Override
  @Transactional
  public void create(OperatorEntity operator, AccountEntity account) {
    Validate.notNull(operator, "操作者信息不能为空");
    account = this.create(account);

    AccountOperatorEntity accountOperator = new AccountOperatorEntity();
    accountOperator.setOperator(operator);
    accountOperator.setAccount(account);
    accountOperatorRepository.save(accountOperator);
  }

  @Override
  @Transactional
  public void create(UserEntity User, AccountEntity account) {
    Validate.notNull(User, "用户信息不能为空");
    account = this.create(account);
    AccountUserEntity accountUser = new AccountUserEntity();
    accountUser.setUser(User);
    accountUser.setAccount(account);
    accountUserRepository.save(accountUser);
  }

  private AccountEntity create(AccountEntity account) {
    Validate.notNull(account, "账号信息不能为空");
    Validate.notBlank(account.getUserName(), "账号名不能为空");
    Validate.notBlank(account.getPassword(), "账号密码不能为空");
    // 加密密码
    String encodePassword = passwordEncoder.encode(account.getPassword());
    account.setPassword(encodePassword);
    return accountRepository.save(account);
  }
}
