package com.vanda.oauth2.server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "author")
public class AuthorConfig {
  /**
   * 忽略api接口
   */
  private String[] ignoreApiUrls;

  public String[] getIgnoreApiUrls() {
    return ignoreApiUrls;
  }

  public void setIgnoreApiUrls(String[] ignoreApiUrls) {
    this.ignoreApiUrls = ignoreApiUrls;
  }
}
