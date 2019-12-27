package com.hecheng.curator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author hc
 */
@Entity
@Table(name = "goods")
public class GoodsEntity extends BaseUuidEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * 昵称
   */
  @Column(name = "name", length = 64)
  private String name = "";
  /**
   * 库存
   */
  @Column(name = "stock")
  private int stock = 0;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }


}
