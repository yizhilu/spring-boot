package com.vanda.oauth2.server.service.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.vanda.oauth2.server.entity.ClientRoleEntity;
import com.vanda.oauth2.server.repository.ClientRoleRepository;
import com.vanda.oauth2.server.service.RoleService;

/**
 * TODO 角色信息变化不大，且经常需要查询，所以需要将查询性质的操作放入到缓存中
 *
 */
@Service("roleServiceImpl")
public class RoleServiceImpl implements RoleService {

  /**
   * 角色repository自动注入.
   */
  @Autowired
  private ClientRoleRepository roleRepository;

  /**
   * 配置的那些不允许被删除被作废的角色
   */
  @Value("${roles.deleteDeny}")
  private String[] deleteDenys;

  @Override
  public List<ClientRoleEntity> findByUserId(String userId) {
    Validate.notEmpty(userId, "用户id不能为空!");
    Set<ClientRoleEntity> roles = this.roleRepository.findByUserId(userId);
    if (roles == null || roles.isEmpty()) {
      return Collections.emptyList();
    }

    List<ClientRoleEntity> rolesList = new ArrayList<>(roles);
    return rolesList;
  }

  @Override
  public List<ClientRoleEntity> findByCompetenceId(String competenceId) {
    Validate.notEmpty(competenceId, "权限id不能为空!");
    Set<ClientRoleEntity> roles = this.roleRepository.findByCompetenceId(competenceId);
    if (roles == null || roles.isEmpty()) {
      return Collections.emptyList();
    }

    List<ClientRoleEntity> rolesList = new ArrayList<ClientRoleEntity>(roles);
    return rolesList;
  }

  @Override
  public List<ClientRoleEntity> findByStatus(Integer useStatus) {
    Validate.notNull(useStatus, "角色状态不能为空!");
    List<ClientRoleEntity> rolesList = this.roleRepository.findByStatus(useStatus);
    if (rolesList == null || rolesList.size() == 0) {
      return Collections.emptyList();
    }

    return rolesList;
  }

  @Override
  public ClientRoleEntity findRoleById(String roleId) {
    Validate.notNull(roleId, "角色id不能为空!");
    ClientRoleEntity currentRole = this.roleRepository.findOne(roleId);
    if (currentRole == null) {
      throw new IllegalArgumentException("没有该角色!");
    }

    return currentRole;
  }

  @Override
  public List<ClientRoleEntity> findAll() {
    List<ClientRoleEntity> rolesList = this.roleRepository.findAll();
    // 如果条件成立说明系统该数据异常，这是直接抛出错误信息
    if (rolesList == null || rolesList.size() == 0) {
      throw new IllegalArgumentException("role infos error!!");
    }
    return rolesList;
  }

  @Override
  public Page<ClientRoleEntity> getByCondition(Map<String, Object> params, Pageable pageable) {
    // TODO
    return null;
  }

  @Override
  public Boolean findByRoleName(String roleName) {
    Validate.notBlank(roleName, "参数异常");
    if (roleName.length() < 4 || roleName.length() > 20) {
      throw new IllegalArgumentException("角色名长度为4-20字符");
    }
    ClientRoleEntity roleEntity = roleRepository.findByName(roleName);
    return null != roleEntity ? true : false;
  }

  @Override
  @Transactional
  public ClientRoleEntity addRole(ClientRoleEntity role) {
    // 进入传入信息的检查
    this.validateRoleBeforeAdd(role);
    // 开始插入
    ClientRoleEntity currentRole = this.roleRepository.save(role);
    return currentRole;
  }

  @Override
  public ClientRoleEntity updateRole(ClientRoleEntity role) {
    /*
     * 修改角色的过程如下： 1、首先确定当前role是可以进行修改的role 2、只进行comment的修改，其它即使传递了也不进行修改
     */
    Validate.notNull(role, "role mssage not null");
    String roleId = role.getId();
    String updateComment = role.getComment();
    Validate.notEmpty(roleId, "role id not empty!");
    Validate.notEmpty(updateComment, "role comment not empty!");
    ClientRoleEntity currentRole = this.roleRepository.getOne(roleId);
    Validate.notNull(currentRole, "role not found");

    // 1、========
    String currentName = currentRole.getName();
    // 如果条件成立，说明这个角色信息不能被修改
    for (String deleteDeny : deleteDenys) {
      if (StringUtils.equals(currentName, deleteDeny)) {
        throw new AccessDeniedException("the role not allow be disable！");
      }
    }

    // 2、========
    currentRole.setModifyDate(new Date());
    currentRole.setComment(updateComment);
    this.roleRepository.save(currentRole);
    return currentRole;
  }

  @Override
  @Transactional
  public ClientRoleEntity disableRole(String roleId) {
    /*
     * 注意：在配置文件中已经设定的那些不能操作的角色信息，是不允许禁用的
     */
    Validate.notEmpty(roleId, "role id not be found!");
    ClientRoleEntity currentRole = this.roleRepository.findOne(roleId);
    // 如果条件成立，说明这个角色信息不能被删除（或者说作废）
    for (String deleteDeny : deleteDenys) {
      if (StringUtils.equals(currentRole.getName(), deleteDeny)) {
        throw new AccessDeniedException("the role not allow be disable！");
      }
    }

    // 禁用角色
    currentRole.setStatus(0);
    currentRole.setModifyDate(new Date());
    this.roleRepository.save(currentRole);
    return currentRole;
  }

  @Override
  @Transactional
  public ClientRoleEntity enableRole(String roleId) {
    Validate.notEmpty(roleId, "role id not be found!");
    ClientRoleEntity currentRole = this.roleRepository.findOne(roleId);

    // 启用角色
    currentRole.setStatus(1);
    currentRole.setModifyDate(new Date());
    this.roleRepository.save(currentRole);
    return currentRole;
  }

  /**
   * 该私有方法在新增一个role信息前，检查传入信息的证确定
   */
  private void validateRoleBeforeAdd(ClientRoleEntity role) {
    Validate.notNull(role, "role input object not be null!");
    Validate.isTrue(StringUtils.isEmpty(role.getId()), "新增role时，其中的id属性不能设定任何值！");
    // 开始验证
    Validate.notEmpty(role.getName(), "role name not be null!");
    // 必须是大写
    role.setName(role.getName().toUpperCase());
    ClientRoleEntity oldRole = this.roleRepository.findByName(role.getName());
    Validate.isTrue(oldRole == null, "当前设定的角色名称（role name）已经被使用，请更换!");
    // 当前的创建时间和修改时间要一起写入
    Date currentTime = new Date();
    role.setCreateDate(currentTime);
    role.setModifyDate(currentTime);
    // 说明性信息
    Validate.notEmpty(role.getComment(), "角色中文说明一定要填写(comment)");
    // 当前角色必须是状态正常的
    role.setStatus(1);
  }

  @Override
  public ClientRoleEntity findByName(String name) {
    ClientRoleEntity oldRole = this.roleRepository.findByName(name);
    return oldRole;
  }
  
  @Override
  @Transactional
  public void bindRoleForUser(String userId) {
    Validate.notBlank(userId, "小区用户ID不能为空！");
    roleRepository.bindRoleForUser(userId);
  }
}
