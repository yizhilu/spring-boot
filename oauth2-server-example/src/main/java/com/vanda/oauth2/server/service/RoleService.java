package com.vanda.oauth2.server.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vanda.oauth2.server.entity.ClientRoleEntity;

public interface RoleService {

  /**
   * 查询指定的后台用户所绑定的角色信息(只包括角色基本信息)
   *
   * @param userId 后台用户编号（数据层唯一编号）
   */
  List<ClientRoleEntity> findByUserId(String userId);

  /**
   * 查询指定的角色信息，按照角色的数据层编号查询
   *
   * @param roleId 角色编号
   */
  ClientRoleEntity findRoleById(String roleId);

  /**
   * 查询指定的功能描述所绑定的角色信息(只包括角色基本信息)
   *
   * @param competenceId 功能描述信息
   */
  List<ClientRoleEntity> findByCompetenceId(String competenceId);

  /**
   * 查询符合角色状态的信息
   *
   * @param useStatus 目前只有两种状态。1：正常；0：异常
   */
  List<ClientRoleEntity> findByStatus(Integer useStatus);

  /**
   * 查询目前系统中所有的角色信息，无论这些角色信息是否可用（但是只包括角色的基本信息）
   */
  List<ClientRoleEntity> findAll();

  /**
   * 该方法用于添加一个角色信息，这个角色信息的名字必须是唯一的
   *
   * @param role 新的角色信息
   */
  ClientRoleEntity addRole(ClientRoleEntity role);

  /**
   * 修改一个指定的角色信息，注意配置在roles.deleteDeny属性的信息不能进行修改操作<br>
   * 且指定的一个角色只能修改角色的comment信息
   *
   * @param role 指定的修改信息
   */
  ClientRoleEntity updateRole(ClientRoleEntity role);

  /**
   * 禁用某一个指定的角色信息（相当于删除）<br>
   * 只是系统中不能真正的删除某一个角色，只能是将这个角色作废掉或者恢复正常状态
   */
  ClientRoleEntity disableRole(String roleId);

  /**
   * 重新启用某一个指定的角色信息
   */
  ClientRoleEntity enableRole(String roleId);

  /**
   * 条件分页查询.
   *
   * @param params 条件
   * @param pageable 分页
   * @return age<RoleEntity>
   */
  Page<ClientRoleEntity> getByCondition(Map<String, Object> params, Pageable pageable);

  /**
   * 验证用户角色是否已经存在
   *
   * @return true 存在 / false 不存在
   */
  Boolean findByRoleName(String roleName);

  /**
   * 按角色查询
   * 
   * @param string
   * @return
   */
  ClientRoleEntity findByName(String string);
  
  /**
   * 给用户绑定指定的角色
   * @param userId
   */
  void bindRoleForUser(String userId);
}
