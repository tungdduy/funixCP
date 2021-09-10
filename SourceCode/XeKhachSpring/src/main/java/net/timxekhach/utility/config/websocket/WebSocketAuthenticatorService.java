package net.timxekhach.utility.config.websocket;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.timxekhach.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class WebSocketAuthenticatorService {

  private final JwtTokenProvider jwtTokenProvider;

  // This method MUST return a UsernamePasswordAuthenticationToken instance, the spring security chain is testing it with 'instanceof' later on. So don't use a subclass of it or any other class
  public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(String token) throws AuthenticationException {
    String username = jwtTokenProvider.getSubject(token);
    if (jwtTokenProvider.isTokenValid(username, token)) {
      List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
      return new UsernamePasswordAuthenticationToken(
          username,
          null,
          authorities // MUST provide at least one role
      );
    } else
      throw new AuthenticationCredentialsNotFoundException("Bad credentials");
  }
}
