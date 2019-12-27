package com.hecheng.curator.service;

import com.hecheng.curator.entity.UserEntity;

public interface UserService {


  UserEntity findByUserName(String username);

}
