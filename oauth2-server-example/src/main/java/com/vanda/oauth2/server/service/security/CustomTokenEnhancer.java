package com.vanda.oauth2.server.service.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class CustomTokenEnhancer implements TokenEnhancer {

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    OAuth2Request oAuth2Request = authentication.getOAuth2Request();
    Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
    String grant_type = requestParameters.get("grant_type");
    final Map<String, Object> additionalInfo = new HashMap<>();
    // 注意添加的额外信息，最好不要和已有的json对象中的key重名，容易出现错误
    additionalInfo.put("authorities", authentication.getAuthorities());
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    return accessToken;
  }
}
