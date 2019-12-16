/**
 * 功能.
 */
package com.vanda.oauth2.server.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统“功能”URL相关路径
 *
 * @author yinwenjie
 * @version V1.0
 * @date 2017年8月8日 下午2:22:32
 */
@ApiModel(value = "CompetenceEntity")
@Entity
@Table(name = "competence")
public class CompetenceEntity extends UuidEntity {

  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = -7742962048681654604L;

  /**
   * 权限URL串.
   **/
  @Column(name = "resource", nullable = false)
  private String resource = "";

  /**
   * 涉及的方法描述<br>
   * 例如：POST或者POST|GET|DELETE|PATCH 等等
   */
  @Column(name = "methods", nullable = false)
  private String methods = "";

  /**
   * 创建时间.
   **/
  @Column(name = "create_date", nullable = false)
  private Date createDate = new Date();

  /**
   * 修改时间.
   **/
  @Column(name = "modify_date")
  private Date modifyDate;

  /**
   * 状态 1正常, 0禁用（枚举）.
   **/
  @Column(name = "status", nullable = false)
  private Integer status = 1;


  /**
   * 权限对应的角色信息
   */
  @ApiModelProperty(hidden = true)
  @ManyToMany(mappedBy = "competences")
  private Set<ClientRoleEntity> roles;

  /**
   * 备注.
   **/
  @Column(name = "comment", nullable = false)
  private String comment = "";

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public String getMethods() {
    return methods;
  }

  public void setMethods(String methods) {
    this.methods = methods;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Set<ClientRoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<ClientRoleEntity> roles) {
    this.roles = roles;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

}
