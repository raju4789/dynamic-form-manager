package com.finclutech.dynamic_form_manager.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SecurityFilterConfig configures the security filter chain for the application.
 * It defines the authentication and authorization rules, session management, and JWT filter integration.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityFilterConfig {


    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configures the security filter chain for the application.
     *
     * @param httpSecurity The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("Configuring security filter chain...");

        httpSecurity
                // Disable CSRF as we are using stateless JWT authentication
                .csrf(AbstractHttpConfigurer::disable)

                // Define authorization rules for endpoints
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    log.info("Defining authorization rules...");
                    authorizeHttpRequests
                            .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll() // Public endpoints
                            .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll() // Swagger UI
                            .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll() // Swagger UI
                            .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll() // Swagger API docs
                            .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll() // Allow all Actuator endpoints
                            .requestMatchers(new AntPathRequestMatcher("/api/v2/spans")).permitAll() // Jaeger endpoint
                            .anyRequest().authenticated(); // All other endpoints require authentication
                })

                // Configure session management to be stateless
                .sessionManagement(sessionManagement -> {
                    log.info("Configuring session management as stateless...");
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })

                // Set the custom authentication provider
                .authenticationProvider(authenticationProvider)

                // Add the JWT authentication filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        log.info("Security filter chain configured successfully.");
        return httpSecurity.build();
    }
}