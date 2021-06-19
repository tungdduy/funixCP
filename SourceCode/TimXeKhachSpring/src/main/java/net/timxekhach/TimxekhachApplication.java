package net.timxekhach;


import net.timxekhach.security.SecurityResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class TimxekhachApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimxekhachApplication.class, args);
    }

    public TimxekhachApplication(SecurityResource securityResource) {
        this.securityResource = securityResource;
    }

    private final SecurityResource securityResource;

    @Bean
    public CorsFilter corsFilter() {
        System.out.println("chac phai viet cai gi do");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(securityResource.getAllowedOrigins());
        corsConfiguration.setAllowedHeaders(securityResource.getAllowedHeaders());
        corsConfiguration.setExposedHeaders(securityResource.getExposedHeaders());
        corsConfiguration.setAllowedMethods(securityResource.getAllowedMethods());
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new org.springframework.web.filter.CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
