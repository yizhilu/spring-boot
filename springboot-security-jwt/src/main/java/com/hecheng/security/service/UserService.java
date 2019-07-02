package com.hecheng.security.service;

import com.hecheng.security.entity.UserEntity;

public interface UserService {


  UserEntity findByUserName(String username);

}
