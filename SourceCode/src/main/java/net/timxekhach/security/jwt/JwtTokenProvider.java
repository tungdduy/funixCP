package net.timxekhach.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import net.timxekhach.operation.entity.User;
import net.timxekhach.security.SecurityResource;
import net.timxekhach.security.UserDetailsImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.util.Arrays.stream;

@Component
public class JwtTokenProvider {

    private final SecurityResource securityResource;

    @Autowired
    public JwtTokenProvider(SecurityResource securityResource) {
        this.securityResource = securityResource;
    }

    public String generateJwtToken(UserDetails user) {
        String[] claims = getClaimsFromUser(user);
        return JWT.create().withIssuer(securityResource.getJwtIssuer()).withAudience(securityResource.getJwtAudience())
                .withIssuedAt(new Date()).withSubject(user.getUsername())
                .withArrayClaim(securityResource.getAuthorities(), claims).withExpiresAt(new Date(System.currentTimeMillis() + securityResource.getExpirationTime()))
                .sign(HMAC512(securityResource.getJwtSecret().getBytes()));
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken userPasswordAuthToken = new
                UsernamePasswordAuthenticationToken(username, null, authorities);
        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPasswordAuthToken;
    }

    public boolean isTokenValid(String username, String token) {
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
    }

    public String getSubject(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(securityResource.getAuthorities()).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        return JWT.require(HMAC512(securityResource.getJwtSecret()))
                .withIssuer(securityResource.getJwtIssuer())
                .build();
    }

    private String[] getClaimsFromUser(UserDetails user) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

    public HttpHeaders getJwtHeader(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add(securityResource.getTokenHeader(), this.generateJwtToken(userDetails));
        return headers;
    }
}
