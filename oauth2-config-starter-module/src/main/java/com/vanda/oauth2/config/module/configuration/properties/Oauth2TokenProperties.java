package com.vanda.oauth2.config.module.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oauth2Token")
public class Oauth2TokenProperties {

  private int refreshTokenValiditySeconds = 60 * 60 * 24 * 30; // default 30 days.

  private int accessTokenValiditySeconds = 60 * 60 * 12; // default 12 hours.

  private boolean supportRefreshToken = false;

  private boolean reuseRefreshToken = true;

  public int getRefreshTokenValiditySeconds() {
    return refreshTokenValiditySeconds;
  }

  public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
    this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
  }

  public int getAccessTokenValiditySeconds() {
    return accessTokenValiditySeconds;
  }

  public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
    this.accessTokenValiditySeconds = accessTokenValiditySeconds;
  }

  public boolean isSupportRefreshToken() {
    return supportRefreshToken;
  }

  public void setSupportRefreshToken(boolean supportRefreshToken) {
    this.supportRefreshToken = supportRefreshToken;
  }

  public boolean isReuseRefreshToken() {
    return reuseRefreshToken;
  }

  public void setReuseRefreshToken(boolean reuseRefreshToken) {
    this.reuseRefreshToken = reuseRefreshToken;
  }

}
