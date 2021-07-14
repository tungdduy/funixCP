package net.timxekhach.security.handler;

import lombok.RequiredArgsConstructor;
import net.timxekhach.security.jwt.JwtFilter;
import net.timxekhach.security.model.SecurityResource;
import net.timxekhach.security.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;
    private final SecurityResource securityResource;
    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Bean
    public CorsFilter corsFilter() {
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
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable().cors()
            .and()
                .sessionManagement().sessionCreationPolicy((SessionCreationPolicy.STATELESS))
            .and()
                .authorizeRequests()
                .antMatchers(securityResource.getPublicUrls()).hasAnyAuthority()
            // ------ START OF IMPORT ROLES ------------------
            // ____________________ ::AUTHORIZATION_SEPARATOR:: ____________________ //
                .antMatchers("user").hasAnyRole("USER")
                .antMatchers("user/login").hasAnyAuthority("ROLE_BUSS_STAFF", "ADMIN_READ", "USER_READ")
                .antMatchers("user/register").hasAnyRole()
                .antMatchers("user/forgot-password").hasAnyAuthority("ADMIN_WRITE")
                .antMatchers("admin").hasAnyRole()
                .antMatchers("admin/list").hasAnyRole()
                .antMatchers("caller-staff").hasAnyRole()
                .antMatchers("buss-staff").hasAnyRole()
            // ____________________ ::AUTHORIZATION_SEPARATOR:: ____________________ //
            // ------ END OF IMPORT ROLES ------------------
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                    .accessDeniedHandler(jwtFilter.deniedHandler())
                    .authenticationEntryPoint(jwtFilter.forbiddenEntryPoint())
            .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
