package com.hecheng.curator.service;

import com.hecheng.curator.entity.UserEntity;

public interface AccountUserService {
  /**
   * 根据账号id查询账号所属的用户
   * 
   * @param accountId
   * @return
   */
  UserEntity findByAccountId(String accountId);

  /**
   * 根据账号名查询账号所属的用户
   * 
   * @param username
   * @return
   */
  UserEntity findByUserName(String username);
}
