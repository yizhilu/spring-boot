package com.hecheng.curator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "curator")
public class ZkLockConfig {
  /**
   * 重试次数
   */
  private int retryCount;
  /**
   * 重试间隔时间
   */
  private int elapsedTimeMs;
  /**
   * zookeeper 地址
   */
  private String connectString;
  /**
   * session超时时间
   */
  private int sessionTimeoutMs;
  /**
   * 连接超时时间
   */
  private int connectionTimeoutMs;
  /**
   * 锁的根节点
   */
  private String lockRoot = "/lock/root";

  public int getRetryCount() {
    return retryCount;
  }

  public void setRetryCount(int retryCount) {
    this.retryCount = retryCount;
  }

  public int getElapsedTimeMs() {
    return elapsedTimeMs;
  }

  public void setElapsedTimeMs(int elapsedTimeMs) {
    this.elapsedTimeMs = elapsedTimeMs;
  }

  public String getConnectString() {
    return connectString;
  }

  public void setConnectString(String connectString) {
    this.connectString = connectString;
  }

  public int getSessionTimeoutMs() {
    return sessionTimeoutMs;
  }

  public void setSessionTimeoutMs(int sessionTimeoutMs) {
    this.sessionTimeoutMs = sessionTimeoutMs;
  }

  public int getConnectionTimeoutMs() {
    return connectionTimeoutMs;
  }

  public void setConnectionTimeoutMs(int connectionTimeoutMs) {
    this.connectionTimeoutMs = connectionTimeoutMs;
  }

  public String getLockRoot() {
    return lockRoot;
  }

  public void setLockRoot(String lockRoot) {
    this.lockRoot = lockRoot;
  }

}
