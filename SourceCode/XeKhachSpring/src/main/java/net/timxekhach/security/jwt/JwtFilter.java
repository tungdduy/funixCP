package net.timxekhach.security.jwt;


import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.security.model.SecurityResource;
import net.timxekhach.utility.geo.GeoIPLocationService;
import org.apache.commons.lang3.StringUtils;
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
import java.util.concurrent.CompletableFuture;

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
    private final GeoIPLocationService geoIPLocationService;

    @Autowired
    public JwtFilter(SecurityResource securityResource, JwtTokenProvider jwtTokenProvider, GeoIPLocationService geoIPLocationService) {
        this.securityResource = securityResource;
        this.jwtTokenProvider = jwtTokenProvider;
        this.geoIPLocationService = geoIPLocationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(securityResource.getHttpMethodOption())) {
            response.setStatus(OK.value());
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader == null
                    || !authorizationHeader.startsWith(securityResource.getTokenPrefix())
                    || authorizationHeader.split("\\.").length < 3) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authorizationHeader.substring(securityResource.getTokenPrefix().length());
            String username = jwtTokenProvider.getSubject(token);
            if (jwtTokenProvider.isTokenValid(username, token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                loginNotification(username, request);
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    private void loginNotification(String username, HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null || StringUtils.isEmpty(ip)){
            ip = request.getRemoteAddr();
        }

        String finalIp = ip;
        CompletableFuture.runAsync(() -> {
            try {
                geoIPLocationService.verifyDevice(username, finalIp, userAgent);
            } catch (Exception e) {
                logger.error(String.format("An error occurred while verifying device or location [%s]", e.getMessage()));
            }
        });


    }

}
