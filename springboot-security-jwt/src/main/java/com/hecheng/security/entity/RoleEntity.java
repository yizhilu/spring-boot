/**
 * 角色.
 */
package com.hecheng.security.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.hecheng.security.entity.enums.EnumType;
import com.hecheng.security.entity.enums.StatusType;


/**
 * 角色信息
 * 
 * @version V1.0
 */
@Entity
@Table(name = "role")
@TypeDefs({@TypeDef(name = "status", typeClass = EnumType.class, parameters = {
    @Parameter(name = "class", value = "com.hecheng.security.entity.enums.StatusType")})})
public class RoleEntity extends BaseUuidEntity {

  private static final long serialVersionUID = -4750396018968101826L;

  /** 角色名称. **/
  @Column(name = "name", length = 64, nullable = false, unique = true)
  private String name = "";

  /** 创建时间. **/
  @Column(name = "create_date", nullable = false)
  private Date createDate = new Date();

  /** 修改时间. **/
  @Column(name = "modify_date")
  private Date modifyDate;

  /** 状态 1正常, 0禁用(枚举). **/
  @Type(type = "status")
  @Column(name = "status", nullable = false)
  private StatusType status = StatusType.STATUS_NORMAL;

  /** 创建人. **/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id")
  private OperatorEntity creator;

  /** 修改人. **/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modifier_id")
  private OperatorEntity modifier;

  /** 备注.角色信息说明 **/
  @Column(name = "comment", length = 64, nullable = true)
  private String comment = "";

  /**
   * 这个角色对应的后台管理人员信息.
   */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "role_operator", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "operator_id")})
  private Set<OperatorEntity> operators;

  /** 权限. **/
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "role_competence", joinColumns = {
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

  public StatusType getStatus() {
    return status;
  }

  public void setStatus(StatusType status) {
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

  public OperatorEntity getCreator() {
    return creator;
  }

  public void setCreator(OperatorEntity creator) {
    this.creator = creator;
  }

  public OperatorEntity getModifier() {
    return modifier;
  }

  public void setModifier(OperatorEntity modifier) {
    this.modifier = modifier;
  }

  public Set<OperatorEntity> getOperators() {
    return operators;
  }

  public void setOperators(Set<OperatorEntity> operators) {
    this.operators = operators;
  }
}
