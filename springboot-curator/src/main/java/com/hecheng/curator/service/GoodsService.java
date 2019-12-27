package com.hecheng.curator.service;

public interface GoodsService {
  boolean reduce(int num);

  boolean dbReduce(int num);

  boolean redisReduce(int num);

  boolean zkReduce(int num);

  boolean redissonReduce(int num);


}
