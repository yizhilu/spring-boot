package com.vanda.oauth2.server.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * 系统用户基本信息.
 * 
 * @version V1.0
 */
@Entity
@Table(name = "oauth_account")
public class OauthAccountEntity extends UuidEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 2923947652076113330L;
  /**
   * 账户
   */
  @Column(name = "user_name",unique=true)
  private String userName;
  /**
   * 密码
   */
  @Column(name = "password")
  private String password;
  /**
   * 角色——和用户相关的.
   **/
  @ApiModelProperty(hidden = true)
  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "oauthAccounts")
  private Set<ClientRoleEntity> roles;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<ClientRoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<ClientRoleEntity> roles) {
    this.roles = roles;
  }
}
