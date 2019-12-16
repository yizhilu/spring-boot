package com.vanda.oauth2.server.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vanda.oauth2.server.entity.CompetenceEntity;

/**
 * 权限service接口.
 *
 * @author ly yinwenjie
 * @version V1.0
 * @date 2017年8月18日 下午3:19:03
 */
public interface CompetenceService {

  /**
   * 查询符合权限状态的信息.
   *
   * @param useStatus 状态（正常/禁用）。1：正常；0：禁用
   * @return List<CompetenceEntity>
   */
  List<CompetenceEntity> findByStatus(Integer useStatus);

  /**
   * 查询为当前URL设置的功能信息，注意，这里没有通过method进行过滤.
   *
   * @param resource 权限串
   * @return List<CompetenceEntity>
   */
  List<CompetenceEntity> findByResource(String resource);

  /**
   * 查询指定的角色已绑定的功能信息，无论这些功能是否可用（状态是否正常）.
   *
   * @param roleId 角色id
   * @return List<CompetenceEntity>
   */
  List<CompetenceEntity> findByRoleId(String roleId);

  /**
   * 查询指定的角色已绑定的功能信息，且这些功能状态符合查询的要求.
   *
   * @param roleId 角色id
   * @param status 状态（正常/禁用）。1：正常；0：禁用
   * @return List<CompetenceEntity>
   */
  List<CompetenceEntity> findByRoleId(String roleId, Integer status);

  /**
   * 新增权限.
   *
   * @param comp 权限对象
   * @return CompetenceEntity
   */
  CompetenceEntity addCompetence(CompetenceEntity comp);

  /**
   * 修改权限.
   *
   * @param comp 权限对象
   * @return CompetenceEntity
   */
  CompetenceEntity updateCompetence(CompetenceEntity comp);

  /**
   * 启用/禁用.
   *
   * @param id 权限id
   * @param flag 标识（true：启用操作；false：禁用操作）
   * @return CompetenceEntity
   */
  CompetenceEntity diableOrUndisable(String id, Boolean flag);

  /**
   * 根据id获取权限详情（单条）.
   *
   * @param id 权限id
   * @return CompetenceEntity
   */
  CompetenceEntity getById(String id);

  /**
   * 条件分页查询.
   *
   * @param params 条件
   * @param pageable 分页
   * @return Page<CompetenceEntity>
   */
  Page<CompetenceEntity> getByCondition(Map<String, Object> params, Pageable pageable);

  /**
   * 根据状态查询该状态下的所有权限信息.
   *
   * @param status 状态
   * @return List<CompetenceEntity>
   */
  List<CompetenceEntity> getAll(Integer status);

  /**
   * 根据methods和resource查找是否存在权限对象.
   *
   * @param methods 方法
   * @param resource 权限串
   * @param id 权限id
   * @return boolean
   */
  boolean isExit(String methods, String resource, String id);

  /**
   *
   * @param name
   * @param url
   * @param methods
   * @return
   */
  Boolean findCompetencePermissionByUrl(String name, String url, String methods);

  /**
   *
   * @param account
   * @return
   */
  Set<CompetenceEntity> queryAllCompetencesByAccount(String account);
}
