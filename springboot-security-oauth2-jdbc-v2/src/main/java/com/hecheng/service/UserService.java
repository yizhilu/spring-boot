package com.hecheng.service;

import com.hecheng.entity.UserEntity;

public interface UserService {


  UserEntity findByUserName(String username);

}
