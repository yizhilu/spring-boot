package com.hecheng.curator.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hecheng.curator.entity.OperatorEntity;
import com.hecheng.curator.entity.enums.StatusType;
import com.hecheng.curator.repository.AccountOperatorRepository;
import com.hecheng.curator.service.AccountOperatorService;

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
