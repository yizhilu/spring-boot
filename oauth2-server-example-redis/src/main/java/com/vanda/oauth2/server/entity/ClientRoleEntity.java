/**
 * 角色.
 */
package com.vanda.oauth2.server.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户端用户角色
 * 
 * @author hc
 *
 */
@ApiModel(value = "ClientRoleEntity")
@Entity
@Table(name = "client_role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClientRoleEntity extends UuidEntity {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4750396018968101826L;

  /**
   * 角色名称
   **/
  @Column(name = "name", length = 64, nullable = false, unique = true)
  private String name = "";

  /**
   * 创建时间
   **/
  @Column(name = "create_date", nullable = false)
  private Date createDate = new Date();

  /**
   * 修改时间.
   **/
  @Column(name = "modify_date")
  private Date modifyDate;

  /**
   * 状态 1正常, 0禁用(枚举).
   **/
  @Column(name = "status", nullable = false)
  private Integer status = 1;


  /**
   * 备注.角色信息说明
   **/
  @Column(name = "comment", length = 64, nullable = true)
  private String comment;
  /**
   * 
   */
  @ApiModelProperty(hidden = true)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "role_oauth_account", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "oauth_account_id")})
  private Set<OauthAccountEntity> oauthAccounts;

  /**
   * 权限
   **/
  @ApiModelProperty(hidden = true)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "role_competences", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "competence_id")})
  private Set<CompetenceEntity> competences;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Set<CompetenceEntity> getCompetences() {
    return competences;
  }

  public void setCompetences(Set<CompetenceEntity> competences) {
    this.competences = competences;
  }

  public Set<OauthAccountEntity> getOauthAccounts() {
    return oauthAccounts;
  }

  public void setOauthAccounts(Set<OauthAccountEntity> oauthAccounts) {
    this.oauthAccounts = oauthAccounts;
  }
}
