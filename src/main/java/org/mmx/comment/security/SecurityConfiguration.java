package org.mmx.comment.security;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

/**
 * Security configurations
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    /**
     * The user details service
     */
    private UserDetailsService userDetailsService;
    /**
     * The BCrypt password encoder
     */
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(AppUserDetailsServiceImpl appUserDetailsService) {
        this.userDetailsService = new CachingUserDetailsService(appUserDetailsService);
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Enable stateless session, HTTP BASIC authentication
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("Setup security filter chain");

        http
            // disable csrf for easier testing
            .csrf(csrf -> csrf.disable())
            //.csrf(Customizer.withDefaults())
            .cors(Customizer.withDefaults())
            // allow h2-console
            .headers(headers -> headers.frameOptions(frameOperations -> frameOperations.sameOrigin()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                                   // allow h2-console without authentication
                                   .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                                   .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            ;

        return http.build();
    }

    /**
     * Create the new authentication provider with BCrypt password encoder and
     * user details service based on database
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        log.debug("Setup authentication provider");

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }

    /*
    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ADMIN > USER");

        return roleHierarchy;
    }
    */
}
