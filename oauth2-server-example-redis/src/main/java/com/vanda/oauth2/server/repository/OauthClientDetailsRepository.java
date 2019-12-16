package com.vanda.oauth2.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.vanda.oauth2.server.entity.OauthAccountEntity;
import com.vanda.oauth2.server.entity.OauthClientDetailsEntity;

@Repository("oauthClientDetailsRepository")
public interface OauthClientDetailsRepository
    extends
      JpaRepository<OauthClientDetailsEntity, String>,
      JpaSpecificationExecutor<OauthAccountEntity> {

  /**
   * 根据小区ID查询oauthClient
   * 
   * @param communityId
   * @return
   */
  OauthClientDetailsEntity findByCommunityId(String communityId);
}
