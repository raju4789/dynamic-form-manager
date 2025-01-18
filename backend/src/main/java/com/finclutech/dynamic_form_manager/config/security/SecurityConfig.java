package com.finclutech.dynamic_form_manager.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finclutech.dynamic_form_manager.advices.GlobalExceptionHandler;
import com.finclutech.dynamic_form_manager.dtos.common.CommonApiResponse;
import com.finclutech.dynamic_form_manager.exceptions.RecordNotFoundException;
import com.finclutech.dynamic_form_manager.repositories.sql.auth.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * SecurityConfig configures authentication and authorization components for the application.
 * It includes beans for user details service, authentication provider, password encoder,
 * and custom handlers for authentication and access denial.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final AppUserRepository appUserRepository;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final ObjectMapper objectMapper;

    /**
     * Configures the UserDetailsService to load user details from the database.
     *
     * @return A UserDetailsService implementation.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            log.info("Loading user details for username: {}", username);
            return appUserRepository.findById(username)
                    .orElseThrow(() -> {
                        log.error("User not found with username: {}", username);
                        return new RecordNotFoundException("User not found with username: " + username);
                    });
        };
    }

    /**
     * Configures the AuthenticationProvider to use the custom UserDetailsService and password encoder.
     *
     * @param userDetailsService The UserDetailsService implementation.
     * @return A configured AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        log.info("Configuring AuthenticationProvider...");
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        log.info("AuthenticationProvider configured successfully.");
        return daoAuthenticationProvider;
    }

    /**
     * Configures the AuthenticationManager to use the default authentication configuration.
     *
     * @param configuration The AuthenticationConfiguration.
     * @return A configured AuthenticationManager.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        log.info("Configuring AuthenticationManager...");
        return configuration.getAuthenticationManager();
    }

    /**
     * Configures the PasswordEncoder to use BCrypt for password hashing.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Configuring PasswordEncoder with BCrypt...");
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures a custom AuthenticationEntryPoint to handle unauthenticated requests.
     *
     * @return A custom AuthenticationEntryPoint.
     */
    @Bean
    public AuthenticationEntryPoint appAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            log.error("User not authenticated. Error: {}", authException.getMessage());
            ResponseEntity<CommonApiResponse<String>> errorResponse =
                    globalExceptionHandler.handleUnAuthenticatedException(authException);

            response.setStatus(errorResponse.getStatusCode().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            String jsonResponse = objectMapper.writeValueAsString(errorResponse.getBody());
            response.getWriter().write(jsonResponse);
        };
    }

    /**
     * Configures a custom AccessDeniedHandler to handle unauthorized access attempts.
     *
     * @return A custom AccessDeniedHandler.
     */
    @Bean
    public AccessDeniedHandler appAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            log.error("User not authorized. Error: {}", accessDeniedException.getMessage());
            ResponseEntity<CommonApiResponse<String>> errorResponse =
                    globalExceptionHandler.handleAccessDeniedException(accessDeniedException);

            response.setStatus(errorResponse.getStatusCode().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            String jsonResponse = objectMapper.writeValueAsString(errorResponse.getBody());
            response.getWriter().write(jsonResponse);
        };
    }
}