package com.vanda.oauth2.config.module.configuration.authentication;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanda.oauth2.config.module.model.ResponseCode;
import com.vanda.oauth2.config.module.model.ResponseModel;

public class CustomAuthExceptionEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws ServletException {
    ResponseModel result = new ResponseModel();
    Throwable cause = authException.getCause();
    if (cause instanceof InvalidTokenException) {
      result.setResponseCode(ResponseCode._401);
      result.setErrorMsg(authException.getMessage());
    } else {
      result.setResponseCode(ResponseCode._401);
      result.setErrorMsg(authException.getMessage());
    }
    result.setPath(request.getServletPath());
    result.setTimestemp(new Date().getTime());
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_OK);
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(response.getOutputStream(), result);
    } catch (Exception e) {
      throw new ServletException();
    }
  }
}
