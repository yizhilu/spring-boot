package com.hecheng.curator.configuration;

import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.shaded.com.google.common.base.Objects;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistributedLockByZookeeper implements InitializingBean {
  private static final Logger log = LoggerFactory.getLogger(DistributedLockByZookeeper.class);
  @Autowired
  private ZkLockConfig zkLockConfig;

  private CountDownLatch countDownLatch = new CountDownLatch(1);

  @Autowired
  private CuratorFramework curatorFramework;

  /**
   * 获取分布式锁
   */
  public void acquireDistributedLock(String path) {
    String keyPath = zkLockConfig.getLockRoot() + "/" + path;
    while (true) {
      try {
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
            .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(keyPath);
        log.info("success to acquire lock for path:{}", keyPath);
        break;
      } catch (Exception e) {
        log.info("failed to acquire lock for path:{}", keyPath);
        log.info("while try again .......");
        try {
          if (countDownLatch.getCount() <= 0) {
            countDownLatch = new CountDownLatch(1);
          }
          countDownLatch.await();
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  /**
   * 释放分布式锁
   */
  public boolean releaseDistributedLock(String path) {
    try {
      String keyPath = zkLockConfig.getLockRoot() + "/" + path;
      if (curatorFramework.checkExists().forPath(keyPath) != null) {
        curatorFramework.delete().forPath(keyPath);
      }
    } catch (Exception e) {
      log.error("failed to release lock");
      return false;
    }
    return true;
  }

  /**
   * 创建 watcher 事件
   */
  private void addWatcher(String path) throws Exception {
    String keyPath;
    if (Objects.equal(path, zkLockConfig.getLockRoot())) {
      keyPath = path;
    } else {
      keyPath = zkLockConfig.getLockRoot() + "/" + path;
    }
    final PathChildrenCache cache = new PathChildrenCache(curatorFramework, keyPath, false);
    cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
    cache.getListenable().addListener((client, event) -> {
      if (Objects.equal(event.getType(), PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
        String oldPath = event.getData().getPath();
        log.info("上一个节点 " + oldPath + " 已经被断开");
        if (oldPath.contains(path)) {
          // 释放计数器，让当前的请求获取锁
          countDownLatch.countDown();
        }
      }
    });
  }

  // 创建父节点，并创建永久节点
  @Override
  public void afterPropertiesSet() {
    curatorFramework = curatorFramework.usingNamespace("lock-namespace");
    String path = zkLockConfig.getLockRoot();
    try {
      if (curatorFramework.checkExists().forPath(path) == null) {
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
            .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path);
      }
      addWatcher(zkLockConfig.getLockRoot());
      log.info("root path 的 watcher 事件创建成功");
    } catch (Exception e) {
      log.error("connect zookeeper fail，please check the log >> {}", e.getMessage(), e);
    }
  }
}
