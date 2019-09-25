package com.hecheng.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hecheng.entity.UserEntity;


@Repository("userRepository")
public interface UserRepository
    extends
      JpaRepository<UserEntity, String>,
      JpaSpecificationExecutor<UserEntity> {

}
