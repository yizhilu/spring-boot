package com.vanda.oauth2.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.vanda.oauth2.server.entity.OauthAccountEntity;


@Repository("oauthAccountRepository")
public interface OauthAccountRepository
    extends
      JpaRepository<OauthAccountEntity, String>,
      JpaSpecificationExecutor<OauthAccountEntity> {

  OauthAccountEntity findByUserName(String username);

}
