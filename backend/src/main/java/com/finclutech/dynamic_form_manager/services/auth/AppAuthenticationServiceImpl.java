package com.finclutech.dynamic_form_manager.services.auth;

import com.finclutech.dynamic_form_manager.dtos.requests.auth.AppAuthenticationRequest;
import com.finclutech.dynamic_form_manager.dtos.requests.auth.AppAuthenticationResponse;
import com.finclutech.dynamic_form_manager.dtos.requests.auth.AppTokenValidationResponse;
import com.finclutech.dynamic_form_manager.entities.sql.auth.AppUser;
import com.finclutech.dynamic_form_manager.exceptions.RecordNotFoundException;
import com.finclutech.dynamic_form_manager.exceptions.UserUnAuthorizedException;
import com.finclutech.dynamic_form_manager.repositories.sql.auth.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppAuthenticationServiceImpl implements AppAuthenticationService {

    private final AppUserRepository appUserRepository;
    private final JWTServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private static final Logger log = LoggerFactory.getLogger(AppAuthenticationServiceImpl.class);

    @Override
    public AppTokenValidationResponse validateToken(String token) {
        try {
            log.info("Validating token");
            String username = jwtService.extractUsername(token);

            AppUser user = appUserRepository.findById(username)
                    .orElseThrow(() -> new RecordNotFoundException("User not found with username: " + username));


            boolean isValid = jwtService.isTokenValid(token, user);
            return AppTokenValidationResponse.builder()
                    .isValid(isValid)
                    .build();
        } catch (RecordNotFoundException e) {
            log.error(e.getMessage());
            throw new RecordNotFoundException(e.getMessage());
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            throw new UserUnAuthorizedException(e.getMessage());
        } catch (Exception e) {
            log.error("unknown exception occurred validating token with error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public AppAuthenticationResponse authenticate(AppAuthenticationRequest appAuthenticationRequest) {
        try {
            log.info("Authenticating user");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(appAuthenticationRequest.getUsername(), appAuthenticationRequest.getPassword())
            );

            AppUser user = appUserRepository.findById(appAuthenticationRequest.getUsername())
                    .orElseThrow(() -> new RecordNotFoundException("User not found with username: " + appAuthenticationRequest.getUsername()));


            String JWTToken = jwtService.generateToken(user);

            return AppAuthenticationResponse.builder()
                    .fullName(user.getFirstName() + " " + user.getLastName())
                    .username(user.getUsername())
                    .role(user.getRole().toString())
                    .token(JWTToken)
                    .build();
        } catch (RecordNotFoundException e) {
            log.error(e.getMessage());
            throw new RecordNotFoundException(e.getMessage());
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            throw new UserUnAuthorizedException(e.getMessage());
        } catch (Exception e) {
            log.error("unknown exception occurred authenticating user with error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
