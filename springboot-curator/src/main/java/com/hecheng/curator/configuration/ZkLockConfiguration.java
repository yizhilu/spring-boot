package com.hecheng.curator.configuration;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZkLockConfiguration {
  @Autowired
  private ZkLockConfig zkLockConfig;

  @Bean(initMethod = "start")
  public CuratorFramework curatorFramework() {
    return CuratorFrameworkFactory.newClient(zkLockConfig.getConnectString(),
        zkLockConfig.getSessionTimeoutMs(), zkLockConfig.getConnectionTimeoutMs(),
        new RetryNTimes(zkLockConfig.getRetryCount(), zkLockConfig.getElapsedTimeMs()));
  }
}
