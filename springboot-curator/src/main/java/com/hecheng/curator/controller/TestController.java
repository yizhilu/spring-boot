package com.hecheng.curator.controller;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hecheng.curator.configuration.DistributedLockByZookeeper;
import com.hecheng.curator.service.GoodsService;

/**
 * 和权限安全相关的接口，在这里暴露<br>
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

  @Autowired
  private DistributedLockByZookeeper distributedLockByZookeeper;
  @Autowired
  private GoodsService goodsService;
  private final static String PATH = "test";

  @GetMapping("/lock1")
  public Boolean getLock1() {
    Boolean flag = false;
    distributedLockByZookeeper.acquireDistributedLock(PATH);
    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      e.printStackTrace();
      flag = distributedLockByZookeeper.releaseDistributedLock(PATH);
    }
    flag = distributedLockByZookeeper.releaseDistributedLock(PATH);
    return flag;
  }

  @GetMapping("/lock2")
  public Boolean getLock2() {
    Boolean flag = false;
    distributedLockByZookeeper.acquireDistributedLock(PATH);
    try {
      Thread.sleep(15000);
    } catch (InterruptedException e) {
      e.printStackTrace();
      flag = distributedLockByZookeeper.releaseDistributedLock(PATH);
    }
    flag = distributedLockByZookeeper.releaseDistributedLock(PATH);
    return flag;
  }

  @GetMapping("/1")
  public Boolean test1() {
    Boolean flag = false;
    goodsService.reduce(1);
    return flag;
  }

  @GetMapping("/2")
  public Boolean test2() {
    Boolean flag = false;
    goodsService.dbReduce(1);
    return flag;
  }

  @GetMapping("/3")
  public Boolean test3() {
    Boolean flag = false;
    goodsService.redisReduce(1);
    return flag;
  }

  @GetMapping("/4")
  public Boolean test4() {
    Boolean flag = false;
    goodsService.zkReduce(1);
    return flag;
  }

  @GetMapping("/5")
  public Boolean test5() {
    Boolean flag = false;
    goodsService.redissonReduce(1);
    return flag;
  }

  public static void main(String[] args) throws Exception {
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.0.116:2181", retryPolicy);
    client.start();
    // 创建分布式锁, 锁空间的根节点路径为/curator/lock
    InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");
    mutex.acquire();
    // 获得了锁, 进行业务流程
    System.out.println("Enter mutex");
    // 完成业务流程, 释放锁
    mutex.release();
    // 关闭客户端
    client.close();
  }
}
