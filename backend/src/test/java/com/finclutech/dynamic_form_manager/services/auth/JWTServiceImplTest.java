package com.finclutech.dynamic_form_manager.services.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTServiceImplTest {

    @InjectMocks
    private JWTServiceImpl jwtService;

    @Mock
    private UserDetails userDetails;

    private String secretKey = "mysecretkeymysecretkeymysecretkeymysecretkey"; // Example secret key
    private int expirationTime = 1000 * 60 * 60; // 1 hour

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Use reflection to set the private fields
        Field secretKeyField = JWTServiceImpl.class.getDeclaredField("secretKey");
        secretKeyField.setAccessible(true);
        secretKeyField.set(jwtService, secretKey);

        Field expirationTimeField = JWTServiceImpl.class.getDeclaredField("expirationTime");
        expirationTimeField.setAccessible(true);
        expirationTimeField.set(jwtService, expirationTime);
    }



    @Test
    void generateToken_ShouldReturnValidToken() {

        when(userDetails.getUsername()).thenReturn("testUser");

        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertEquals("testUser", jwtService.extractUsername(token));
    }
}