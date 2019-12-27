package com.hecheng.curator.service.internal;

import javax.transaction.Transactional;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.hecheng.curator.entity.GoodsEntity;
import com.hecheng.curator.repository.GoodsRepository;
import com.hecheng.curator.service.GoodsService;
import com.snowalker.lock.redisson.RedissonLock;

@Service
public class GoodsServiceImpl implements GoodsService {
  private static final Logger LOG = LoggerFactory.getLogger(GoodsServiceImpl.class);

  @Autowired
  private GoodsRepository goodsRepository;

  @Override
  @Transactional
  public boolean reduce(int num) {
    GoodsEntity goods = goodsRepository.findOne("1");
    goods.setStock(goods.getStock() - 1);
    goodsRepository.saveAndFlush(goods);
    return true;
  }

  @Override
  @Transactional
  public boolean dbReduce(int num) {
    goodsRepository.dbReduce();
    return false;
  }

  @Autowired
  RedissonLock redissonLock;

  @Override
  @Transactional
  public boolean redisReduce(int num) {
    if (redissonLock.lock("redisson", 1)) {
      long t1=System.currentTimeMillis();
      LOG.info("redisReduce获得了锁, 进行业务流程====>");
      GoodsEntity goods = goodsRepository.findOne("1");
      goods.setStock(goods.getStock() - 1);
      goodsRepository.saveAndFlush(goods);
      long t2=System.currentTimeMillis();
      LOG.info((t2-t1)/1000+"");
      LOG.info("redisReduce, 进行业务流程库存减1");
      redissonLock.release("redisson");
    } else {
      LOG.info("[ExecutorRedisson]获取锁失败");
    }
    return false;
  }

  @Autowired
  private CuratorFramework curatorFramework;

  @Override
  @Transactional
  public boolean zkReduce(int num) {
    // 创建分布式锁, 锁空间的根节点路径为/curator/lock
    InterProcessMutex mutex = new InterProcessMutex(curatorFramework, "/goods/lock");
    try {
      // 获得了锁, 进行业务流程
      mutex.acquire();
      LOG.info("zk获得了锁, 进行业务流程====>");
      GoodsEntity goods = goodsRepository.findOne("1");
      goods.setStock(goods.getStock() - 1);
      goodsRepository.saveAndFlush(goods);
      LOG.info("zk, 进行业务流程库存减1");
    } catch (Exception e) {
      e.printStackTrace();
      LOG.info("zk获得锁失败");
    } finally {
      // 完成业务流程, 释放锁
      try {
        mutex.release();
        LOG.info("<====完成业务流程, 释放锁zk");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  @Override
  @Transactional
  public boolean redissonReduce(int num) {
    return false;
  }

}
