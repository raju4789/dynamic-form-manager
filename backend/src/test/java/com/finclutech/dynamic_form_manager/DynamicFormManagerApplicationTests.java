package com.finclutech.dynamic_form_manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test") // Use a test profile to avoid loading production configurations
class DynamicFormManagerApplicationTests {

	@Test
	void applicationStartsSuccessfully() {
		// This test ensures that the main method runs without exceptions
		assertDoesNotThrow(() -> DynamicFormManagerApplication.main(new String[]{}));
	}
}