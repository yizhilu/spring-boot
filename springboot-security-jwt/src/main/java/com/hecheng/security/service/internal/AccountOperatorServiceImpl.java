package com.hecheng.security.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hecheng.security.entity.OperatorEntity;
import com.hecheng.security.entity.enums.StatusType;
import com.hecheng.security.repository.AccountOperatorRepository;
import com.hecheng.security.service.AccountOperatorService;

@Service("accountOperatorService")
public class AccountOperatorServiceImpl implements AccountOperatorService {
  @Autowired
  private AccountOperatorRepository accountOperatorRepository;

  @Override
  public OperatorEntity findByAccountId(String accountId) {
    return accountOperatorRepository.findByAccountId(accountId);
  }

  @Override
  public OperatorEntity findByAccountAndStatus(String account, StatusType status) {
    return accountOperatorRepository.findByAccountAndStatus(account, status);
  }

}
