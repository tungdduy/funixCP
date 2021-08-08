package net.timxekhach.security.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@ConfigurationProperties(prefix = "xekhach.security")
@Component
@Getter
@Setter
public class SecurityResource {

    SecurityResource() {
        _instance = this;
    }
    private static SecurityResource _instance;
    public static final Supplier<SecurityResource> instance = () -> _instance;

    private String jwtSecret;
    private String tokenPrefix;
    private String tokenHeader;
    private long expirationTime;
    private String authorities;
    private String httpMethodOption;
    private String[] publicUrls;
    private int maximumNumberOfLoginAttempts;
    private String jwtIssuer;
    private String jwtAudience;
    private List<String> allowedOrigins;
    private List<String> allowedHeaders;
    private List<String> exposedHeaders;
    private List<String> allowedMethods;
    private String resourcePath, resourceUrl;
    private String apiUrl;
}
