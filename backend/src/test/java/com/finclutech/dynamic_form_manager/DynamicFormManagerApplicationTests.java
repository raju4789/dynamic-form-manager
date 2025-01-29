package com.finclutech.dynamic_form_manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
class DynamicFormManagerApplicationTests {
    @Test
    public void contextLoads() {
        // This test will fail if the application context cannot be loaded
    }

}