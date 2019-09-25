package com.hecheng.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hecheng.entity.OperatorEntity;
import com.hecheng.entity.enums.StatusType;
import com.hecheng.repository.AccountOperatorRepository;
import com.hecheng.service.AccountOperatorService;

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
