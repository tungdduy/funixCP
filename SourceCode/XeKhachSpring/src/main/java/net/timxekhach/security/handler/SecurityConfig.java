// ____________________ ::BEFORE_AUTHORIZATION_SEPARATOR:: ____________________ //
package net.timxekhach.security.handler;

import lombok.RequiredArgsConstructor;
import net.timxekhach.security.jwt.JwtFilter;
import net.timxekhach.security.model.SecurityResource;
import net.timxekhach.security.user.UserDetailsServiceImpl;
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

    private static BCryptPasswordEncoder passwordEncoder;
    public static BCryptPasswordEncoder getPasswordEncoder(){
        if(passwordEncoder == null) {
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder;
    }


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
                .antMatchers(securityResource.getPublicUrls()).permitAll()
// ____________________ ::BEFORE_AUTHORIZATION_SEPARATOR:: ____________________ //
            .antMatchers("/socket/**").permitAll()
                .antMatchers("/webSocket/send").permitAll()
                .antMatchers("/user/login/**").permitAll()
                .antMatchers("/user/register/**").permitAll()
                .antMatchers("/user/forgot-password/**").permitAll()
                .antMatchers("/user/forgot-password-secret-key/**").permitAll()
                .antMatchers("/user/change-password/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/user/update-password/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/trip/search-location/**").permitAll()
                .antMatchers("/trip/find-buss-schedules/**").permitAll()
                .antMatchers("/trip/find-scheduled-locations/**").permitAll()
                .antMatchers("/trip/get-trip-users/**").permitAll()
                .antMatchers("/trip/get-trip-by-company-id/**").hasAnyAuthority("ROLE_BUSS_STAFF", "ROLE_CALLER_STAFF")
                .antMatchers("/trip/get-buss-schedules-by-company-id/**").hasAnyAuthority("ROLE_BUSS_STAFF", "ROLE_CALLER_STAFF")
                .antMatchers("/trip/find-pending-trip-users/**").hasAnyAuthority("ROLE_BUSS_STAFF", "ROLE_CALLER_STAFF")
                .antMatchers("/common-update/Trip/**").hasAnyAuthority("ROLE_CALLER_STAFF", "ROLE_BUSS_STAFF")
                .antMatchers("/common-update/TripUser").permitAll()
                .antMatchers("/common-update/TripUser/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/common-update/User/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/common-update/Buss/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/common-update/BussSchedule/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/common-update/BussSchedulePoint/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/common-update/BussType/**").hasAnyAuthority("ROLE_SYS_ADMIN")
                .antMatchers("/common-update/Company/**").hasAnyAuthority("ROLE_BUSS_ADMIN")
                .antMatchers("/common-update/Employee/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/common-update/Location/**").hasAnyAuthority("ROLE_SYS_ADMIN")
                .antMatchers("/common-update/Path/**").hasAnyAuthority("ROLE_BUSS_ADMIN")
                .antMatchers("/common-update/PathPoint/**").hasAnyAuthority("ROLE_BUSS_ADMIN")
                .antMatchers("/common-update/SeatGroup/**").hasAnyAuthority("ROLE_SYS_ADMIN")

// ____________________ ::AFTER_AUTHORIZATION_SEPARATOR:: ____________________ //
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                    .accessDeniedHandler(jwtFilter.deniedHandler())
                    .authenticationEntryPoint(jwtFilter.forbiddenEntryPoint())
            .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
// ____________________ ::AFTER_AUTHORIZATION_SEPARATOR:: ____________________ //
