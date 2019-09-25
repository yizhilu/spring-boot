package com.hecheng.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hecheng.entity.OauthAccountEntity;


@Repository("oauthAccountRepository")
public interface OauthAccountRepository
    extends
      JpaRepository<OauthAccountEntity, String>,
      JpaSpecificationExecutor<OauthAccountEntity> {

  OauthAccountEntity findByUserName(String username);

}
