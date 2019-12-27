package com.hecheng.curator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hecheng.curator.entity.GoodsEntity;


@Repository("goodsRepository")
public interface GoodsRepository
    extends
      JpaRepository<GoodsEntity, String>,
      JpaSpecificationExecutor<GoodsEntity> {
  @Modifying
  @Query(value = "update goods set stock=stock-1", nativeQuery = true)
  void dbReduce();

}
