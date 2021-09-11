package net.timxekhach.utility.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Log4j2
public class LoggingService {
  private static final String REQUEST_ID = "request_id";

  public void logRequest(HttpServletRequest request, Object body) {
    if (request.getRequestURI().contains("medias")) {
      return;
    }
    Object requestId = request.getAttribute(REQUEST_ID);
    StringBuilder data = new StringBuilder("\n");
    data.append("LOGGING REQUEST BODY\n")
        .append("\t[Remote-IP-Address]: ").append(request.getRemoteAddr()).append("\n")
        .append("\t[X-FORWARD-FOR]: ").append(request.getHeader("X-Forwarded-For")).append("\n")
        .append("\t[USER-INFO]: ").append(getUsername()).append("\n")
        .append("\t[REQUEST-SERVICE]: ").append(request.getRequestURI().substring(request.getContextPath().length())).append("\n")
        .append("\t[REQUEST-METHOD]: ").append(request.getMethod()).append("\n")
        .append("\t[BODY REQUEST]: ").append("\n\n")
        .append("\t").append(parseObjectToString(body))
        .append("\n\n")
        .append("\tLOGGING REQUEST BODY\n");

    log.info(data.toString());
  }

  public void logResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
    if (request.getRequestURI().contains("medias")) {
      return;
    }
    Object requestId = request.getAttribute(REQUEST_ID);
    StringBuilder data = new StringBuilder("\n");
    data.append("\tLOGGING RESPONSE\n")
        .append("\t[Remote-IP-Address]: ").append(request.getRemoteAddr()).append("\n")
        .append("\t[X-FORWARD-FOR]: ").append(request.getHeader("X-Forwarded-For")).append("\n")
        .append("\t[USER-INFO]: ").append(getUsername()).append("\n")
        .append("\t[REQUEST-SERVICE]: ").append(request.getRequestURI().substring(request.getContextPath().length())).append("\n")
        .append("\t[REQUEST-METHOD]: ").append(request.getMethod()).append("\n")
        .append("\t[BODY RESPONSE]: ").append("\n\n")
        .append("\t").append(parseObjectToString(body))
        .append("\n\n")
        .append("\tLOGGING RESPONSE\n");

    log.info(data.toString());
  }

  private String getUsername() {
    try {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      StringBuilder username = new StringBuilder();
      if (principal instanceof UserDetails) {
        username.append(((UserDetails)principal).getUsername());
      } else {
        username.append(principal.toString());
      }
      return username.toString();
    }catch (Exception e){
      return StringUtils.EMPTY;
    }
  }

  private String parseObjectToString(Object object) {

    if (object == null)
      return null;

    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }

    return null;
  }
}
