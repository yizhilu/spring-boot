package com.vanda.oauth2.server.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vanda.oauth2.server.entity.ClientRoleEntity;

@Repository("clientRoleRepository")
public interface ClientRoleRepository
    extends
      JpaRepository<ClientRoleEntity, String>,
      JpaSpecificationExecutor<ClientRoleEntity> {

  /**
   * 查询符合角色状态的信息
   */
  @Query("from ClientRoleEntity r  where r.status = :useStatus")
  List<ClientRoleEntity> findByStatus(@Param("useStatus") Integer useStatus);

  /**
   * 查询所有的角色信息，包括角色的创建者信息
   */
  @Query("from ClientRoleEntity r ")
  List<ClientRoleEntity> findAll();

  /**
   * 查询指定的后台用户用户所绑定的角色信息
   *
   * @param userId 后台用户编号（数据层唯一编号）
   */
  @Query("from ClientRoleEntity r left join fetch r.oauthAccounts a where a.id = :userId")
  Set<ClientRoleEntity> findByUserId(@Param("userId") String userId);

  /**
   * 查询指定的功能描述所绑定的角色信息
   *
   * @param competenceId 功能描述信息
   */
  @Query("from ClientRoleEntity r left join fetch r.competences c where c.id = :competenceId")
  Set<ClientRoleEntity> findByCompetenceId(@Param("competenceId") String competenceId);

  /**
   * 按照角色名，查询指定的角色信息
   */
  ClientRoleEntity findByName(String name);
  
  /**
   * 给小区用户绑定角色ID为2的角色
   * @param userId
   */
  @Modifying
  @Query(value = "insert into role_oauth_account (role_id,oauth_account_id) values (2 , :userId)", nativeQuery = true)
  void bindRoleForUser(@Param("userId")String userId);

}
