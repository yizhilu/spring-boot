package com.hecheng.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 系统用户基本信息.
 * 
 * @version V1.0
 */
@Entity
@Table(name = "oauth_account")
public class OauthAccountEntity extends BaseUuidEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 2923947652076113330L;
  /**
   * 账户
   */
  @Column(name = "user_name")
  private String userName;
  /**
   * 密码
   */
  @Column(name = "password")
  private String password;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "oauth_account_authority", joinColumns = {
      @JoinColumn(name = "oauth_account_id")}, inverseJoinColumns = {
          @JoinColumn(name = "authority_id")})
  private List<ClientAuthorityEntity> authorities;

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

  public List<ClientAuthorityEntity> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(List<ClientAuthorityEntity> authorities) {
    this.authorities = authorities;
  }

}
