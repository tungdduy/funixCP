package net.timxekhach.security.jwt;


import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.security.model.SecurityResource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Bean
    public AccessDeniedHandler deniedHandler(){
        return (httpServletRequest, response, e) -> ErrorCode.ACCESS_DENIED.generateResponse(response, HttpStatus.FORBIDDEN);
    }

    @Bean
    public Http403ForbiddenEntryPoint forbiddenEntryPoint(){
        return new Http403ForbiddenEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
                ErrorCode.DO_NOT_HAVE_PERMISSION.generateResponse(response, HttpStatus.UNAUTHORIZED);
            }
        };
    }

    private final SecurityResource securityResource;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtFilter(SecurityResource securityResource, JwtTokenProvider jwtTokenProvider) {
        this.securityResource = securityResource;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(securityResource.getHttpMethodOption())) {
            response.setStatus(OK.value());
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith(securityResource.getTokenPrefix())) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authorizationHeader.substring(securityResource.getTokenPrefix().length());
            String username = jwtTokenProvider.getSubject(token);
            if (jwtTokenProvider.isTokenValid(username, token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}
