package com.finclutech.dynamic_form_manager.services.auth;

import com.finclutech.dynamic_form_manager.dtos.requests.auth.AppAuthenticationRequest;
import com.finclutech.dynamic_form_manager.dtos.requests.auth.AppAuthenticationResponse;
import com.finclutech.dynamic_form_manager.dtos.requests.auth.AppTokenValidationResponse;
import com.finclutech.dynamic_form_manager.entities.sql.auth.AppUser;
import com.finclutech.dynamic_form_manager.exceptions.RecordNotFoundException;
import com.finclutech.dynamic_form_manager.repositories.sql.auth.AppUserRepository;
import com.finclutech.dynamic_form_manager.utils.ApplicationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppAuthenticationServiceImplTest {

    @InjectMocks
    private AppAuthenticationServiceImpl appAuthenticationService;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private JWTServiceImpl jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateToken_ShouldReturnValidResponse_WhenTokenIsValid() {
        // Arrange
        String token = "validToken";
        String username = "testUser";
        AppUser user = new AppUser();
        user.setUsername(username);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(appUserRepository.findById(username)).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(token, user)).thenReturn(true);


        AppTokenValidationResponse response = appAuthenticationService.validateToken(token);

        assertNotNull(response);
        assertTrue(response.isValid());
        verify(jwtService, times(1)).extractUsername(token);
        verify(appUserRepository, times(1)).findById(username);
        verify(jwtService, times(1)).isTokenValid(token, user);
    }

    @Test
    void validateToken_ShouldThrowRecordNotFoundException_WhenUserNotFound() {

        String token = "invalidToken";
        String username = "nonExistentUser";

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(appUserRepository.findById(username)).thenReturn(Optional.empty());


        assertThrows(RecordNotFoundException.class, () -> appAuthenticationService.validateToken(token));
        verify(jwtService, times(1)).extractUsername(token);
        verify(appUserRepository, times(1)).findById(username);
    }

    @Test
    void authenticate_ShouldReturnAuthenticationResponse_WhenCredentialsAreValid() {

        AppAuthenticationRequest request = new AppAuthenticationRequest();
        request.setUsername("testUser");
        request.setPassword("password");

        AppUser user = new AppUser();
        user.setUsername("testUser");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole(ApplicationConstants.AppUserRole.USER);

        String jwtToken = "generatedToken";

        when(appUserRepository.findById(request.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(jwtToken);


        AppAuthenticationResponse response = appAuthenticationService.authenticate(request);


        assertNotNull(response);
        assertEquals("John Doe", response.getFullName());
        assertEquals("testUser", response.getUsername());
        assertEquals("USER", response.getRole());
        assertEquals(jwtToken, response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(appUserRepository, times(1)).findById(request.getUsername());
        verify(jwtService, times(1)).generateToken(user);
    }

    @Test
    void authenticate_ShouldThrowRecordNotFoundException_WhenUserNotFound() {

        AppAuthenticationRequest request = new AppAuthenticationRequest();
        request.setUsername("nonExistentUser");
        request.setPassword("password");

        when(appUserRepository.findById(request.getUsername())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> appAuthenticationService.authenticate(request));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(appUserRepository, times(1)).findById(request.getUsername());
    }

    @Test
    void authenticate_ShouldThrowUserUnAuthorizedException_WhenAuthenticationFails() {

        AppAuthenticationRequest request = new AppAuthenticationRequest();
        request.setUsername("testUser");
        request.setPassword("wrongPassword");

        doThrow(RuntimeException.class).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(RuntimeException.class, () -> appAuthenticationService.authenticate(request));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(appUserRepository, never()).findById(anyString());
    }
}
