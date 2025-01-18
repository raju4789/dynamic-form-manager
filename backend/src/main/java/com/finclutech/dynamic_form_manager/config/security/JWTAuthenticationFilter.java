package com.finclutech.dynamic_form_manager.config.security;

import com.finclutech.dynamic_form_manager.exceptions.InvalidRequestException;
import com.finclutech.dynamic_form_manager.services.auth.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * JWTAuthenticationFilter is a custom filter that validates JWT tokens for incoming requests.
 * It ensures that only authenticated users can access secured endpoints.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.authorizationHeaderString}")
    private String authorizationHeaderString;

    // Define whitelisted endpoints that do not require authentication
    private final RequestMatcher requestMatcher = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/v1/auth/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/swagger-ui/**")
    );

    /**
     * Filters incoming requests to validate JWT tokens.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain.
     * @throws ServletException If an error occurs during filtering.
     * @throws IOException      If an I/O error occurs during filtering.
     */
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) {

        try {
            // Skip authentication for whitelisted endpoints
            if (requestMatcher.matches(request)) {
                log.info("Skipping authentication for whitelisted endpoint: {}", request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }

            // Extract the Authorization header
            final String authorizationHeader = request.getHeader(authorizationHeaderString);
            if (authorizationHeader == null || !authorizationHeader.startsWith(tokenPrefix)) {
                log.warn("No JWT token found in request headers for URI: {}", request.getRequestURI());
                throw new InvalidRequestException("No JWT token found in request headers");
            }

            // Extract the JWT token from the Authorization header
            final String jwtToken = authorizationHeader.substring(tokenPrefix.length()).trim();
            final String username = jwtService.extractUsername(jwtToken);

            // Validate the token and set the authentication context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.info("Validating JWT token for username: {}", username);

                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwtToken, userDetails)) {
                    log.info("JWT token is valid for username: {}", username);

                    // Create an authentication token and set it in the security context
                    final UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    log.warn("Invalid JWT token for username: {}", username);
                }
            }

            // Continue with the filter chain
            filterChain.doFilter(request, response);
        } catch (InvalidRequestException | UsernameNotFoundException e) {
            log.error("Authentication error: {}", e.getMessage(), e);
            resolver.resolveException(request, response, null, e);
        } catch (Exception e) {
            log.error("Unexpected error during authentication: {}", e.getMessage(), e);
            resolver.resolveException(request, response, null, e);
        }
    }
}