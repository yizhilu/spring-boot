package com.vanda.oauth2.server.service.internal;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vanda.oauth2.server.entity.CompetenceEntity;
import com.vanda.oauth2.server.repository.CompetenceRepository;
import com.vanda.oauth2.server.service.CompetenceService;
import com.vanda.oauth2.server.service.RoleService;

/**
 * 功能信息变化不大，且经常需要查询，所以需要将查询性质的操作放入到缓存中<br>
 * 功能在服务中的描述信息，主要为了和角色进行绑定产生权限控制
 *
 * @author yinwenjie ly
 */
@Service("CompetenceServiceImpl")
public class CompetenceServiceImpl implements CompetenceService {

  /**
   * 权限repository自动注入.
   */
  @Autowired
  private CompetenceRepository competenceRepository;

  @Autowired
  private RoleService roleService;

  @Override
  public List<CompetenceEntity> findByStatus(Integer useStatus) {
    Validate.notNull(useStatus, "useStatus must not be null!");
    return this.competenceRepository.findByStatus(useStatus);
  }

  @Override
  public List<CompetenceEntity> findByResource(String resource) {
    Validate.notNull(resource, "resource must not be null!");
    return this.competenceRepository.findByResource(resource);
  }

  @Override
  public List<CompetenceEntity> findByRoleId(String roleId) {
    Validate.notNull(roleId, "roleId must not be null!");
    return this.competenceRepository.findByRoleId(roleId);
  }

  @Override
  public List<CompetenceEntity> findByRoleId(String roleId, Integer status) {
    Validate.notNull(roleId, "roleId must not be null!");
    Validate.notNull(status, "status must not be null!");
    return this.competenceRepository.findByRoleId(roleId, status);
  }

  @Override
  public CompetenceEntity getById(String id) {
    Validate.notEmpty(id, "参数错误！");
    CompetenceEntity competence = competenceRepository.findOne(id);
    return competence;
  }

  @Override
  public boolean isExit(String methods, String resource, String id) {
    int count = 0;
    if (StringUtils.isBlank(id)) {
      count = competenceRepository.findByMethodsAndResource(methods, resource);
    } else {
      count = competenceRepository.findByMethodsAndResourceAndId(methods, resource, id);
    }
    if (count > 0) {
      return true;
    }
    return false;
  }

  @Override
  public Page<CompetenceEntity> getByCondition(Map<String, Object> params, Pageable pageable) {
    // TODO
    return null;
  }

  @Override
  public List<CompetenceEntity> getAll(Integer status) {
    Validate.notNull(status, "状态参数不能为空！");
    List<CompetenceEntity> list = competenceRepository.getAll(status);
    return list;
  }

  @Override
  @Transactional
  public CompetenceEntity addCompetence(CompetenceEntity comp) {
    // 验证
    validCompetence(comp);
    // 验证是否已经存在
    Validate.isTrue(!isExit(comp.getMethods().toUpperCase(), comp.getResource(), null), "权限对象已存在！");
    comp.setMethods(comp.getMethods().toUpperCase());
    comp.setStatus(1);
    comp.setCreateDate(new Date());
    CompetenceEntity competenceEntity = competenceRepository.saveAndFlush(comp);
    return competenceEntity;
  }

  @Override
  @Transactional
  public CompetenceEntity updateCompetence(CompetenceEntity comp) {
    CompetenceEntity currentComp = getById(comp.getId());
    // 验证对象
    Validate.notNull(currentComp, "id为" + comp.getId() + "的对象不存在！");
    currentComp.setResource(comp.getResource());
    currentComp.setMethods(comp.getMethods());
    currentComp.setComment(comp.getComment());
    currentComp.setModifyDate(new Date());
    // 验证
    validCompetence(currentComp);
    // 验证是否已经存在
    Validate.isTrue(!isExit(currentComp.getMethods().toUpperCase(), currentComp.getResource(),
        currentComp.getId()), "权限对象已存在！");
    // 验证通过后methods统一大写存储
    currentComp.setMethods(currentComp.getMethods().toUpperCase());
    competenceRepository.saveAndFlush(currentComp);
    return currentComp;
  }

  @Override
  @Transactional
  public CompetenceEntity diableOrUndisable(String id, Boolean flag) {
    if (StringUtils.isEmpty(id) || flag == null) {
      throw new IllegalArgumentException("参数错误！");
    }
    CompetenceEntity competenceEntity = getById(id);
    Validate.notNull(competenceEntity, "对象不存在！");
    if (flag == true) {
      // 启用
      competenceEntity.setStatus(1);
    } else if (flag == false) {
      // 禁用
      competenceEntity.setStatus(0);
    }
    competenceEntity.setModifyDate(new Date());
    competenceRepository.saveAndFlush(competenceEntity);
    return competenceEntity;
  }

  @Override
  public Boolean findCompetencePermissionByUrl(String opUserAccount, String url, String methods) {
    // Validate.notBlank(opUserAccount, "opUserAccount must be input!");
    // Validate.notBlank(url, "url must be input!");
    // Validate.notBlank(methods, "methods must be input!");
    // // 如果当前用户拥有管理员权限，则都返回true
    // // TODO
    // if (currentOp == null || currentOp.getStatus() == 0) {
    // throw new IllegalArgumentException("用户状态不正确!");
    // }
    // List<ClientRoleEntity> roles = this.roleService.findByUserId(currentOp.getId());
    // if (roles == null || roles.isEmpty()) {
    // return false;
    // }
    // for (ClientRoleEntity role : roles) {
    // if (StringUtils.equals(role.getName(), "ADMIN")) {
    // return true;
    // }
    // }
    //
    // String currentMethod = methods.toUpperCase();
    // Set<CompetenceEntity> allCompetences = this.queryAllCompetencesByAccount(opUserAccount);
    // // 看看集合中是不是有这个指定的功能
    // if (allCompetences.isEmpty()) {
    // return false;
    // }
    // for (CompetenceEntity item : allCompetences) {
    // if (StringUtils.equals(item.getResource(), url)
    // && StringUtils.indexOf(item.getMethods(), currentMethod) != -1) {
    // return true;
    // }
    // }
    return false;
  }

  @Override
  public Set<CompetenceEntity> queryAllCompetencesByAccount(String opUserAccount) {
    // if (operator == null) {
    // return Collections.emptySet();
    // }
    // Set<RoleEntity> roles = operator.getRoles();
    // if (roles == null || roles.isEmpty()) {
    // return Collections.emptySet();
    // }
    //
    // // 集成查询当前用户所有的功能
    // Set<CompetenceEntity> allCompetences = new HashSet<>();
    // for (RoleEntity role : roles) {
    // // 这里带有缓存
    // List<CompetenceEntity> competences = this.findByRoleId(role.getId());
    // if (competences == null || competences.isEmpty()) {
    // continue;
    // }
    // if (competences != null) {
    // allCompetences.addAll(competences);
    // }
    // }
    // return allCompetences;
    return null;
  }


  /**
   * 验证权限对象和字段.
   *
   * @param comp 权限对象
   */
  private void validCompetence(CompetenceEntity comp) {
    // 验证对象是否存在
    Validate.notNull(comp, "权限对象不能为空！");
    // 权限串
    String resource = comp.getResource();
    Validate.notBlank(resource, "权限串不能为空！");
    // 涉及的方法描述（POST/GET……）
    String method = comp.getMethods();
    Validate.notBlank(method, "方法描述不能为空！");
    // 备注
    String comment = comp.getComment();
    Validate.notBlank(comment, "权限备注不能为空！");
  }

}
