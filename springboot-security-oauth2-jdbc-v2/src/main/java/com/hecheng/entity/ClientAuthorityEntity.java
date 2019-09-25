package com.hecheng.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "client_authority")
public class ClientAuthorityEntity extends BaseUuidEntity implements GrantedAuthority {
  private static final long serialVersionUID = 1L;

  @Column(name = "authority")
  private String authority;


  @Override
  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
