package com.vanda.oauth2.config.module.configuration.authentication;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanda.oauth2.config.module.model.ResponseCode;
import com.vanda.oauth2.config.module.model.ResponseModel;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType("application/json;charset=UTF-8");
    ResponseModel result = new ResponseModel();
    result.setResponseCode(ResponseCode._403);
    result.setErrorMsg(accessDeniedException.getMessage());
    result.setPath(request.getServletPath());
    result.setTimestemp(new Date().getTime());
    ObjectMapper mapper = new ObjectMapper();
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().write(mapper.writeValueAsString(result));
  }
}
