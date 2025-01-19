package com.finclutech.dynamic_form_manager.services;

import com.finclutech.dynamic_form_manager.dtos.requests.FormRequest;
import com.finclutech.dynamic_form_manager.dtos.response.FormDataResponse;
import com.finclutech.dynamic_form_manager.dtos.response.UserFormResponse;
import com.finclutech.dynamic_form_manager.entities.nosql.UserForm;
import com.finclutech.dynamic_form_manager.repositories.nosql.UserFormRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserFormServiceImplTest {

    @InjectMocks
    private UserFormServiceImpl userFormService;

    @Mock
    private UserFormRepository userFormRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests for saveUserForm
    @Test
    void saveUserForm_ShouldReturnSuccessResponse_WhenFormIsSavedSuccessfully() {
        // Arrange
        FormRequest formRequest = FormRequest.builder()
                .serviceId(100L)
                .userId("user123")
                .formData(Map.of("key1", "value1", "key2", "value2"))
                .build();

        UserForm userForm = UserForm.builder()
                .serviceId(100L)
                .userId("user123")
                .formData(Map.of("key1", "value1", "key2", "value2"))
                .createdAt(LocalDateTime.now())
                .build();

        when(userFormRepository.save(any(UserForm.class))).thenReturn(userForm);

        // Act
        UserFormResponse response = userFormService.saveUserForm(formRequest);

        // Assert
        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals(userForm.getFormData(), response.getFormData());
        verify(userFormRepository, times(1)).save(any(UserForm.class));
    }

    @Test
    void saveUserForm_ShouldReturnFailureResponse_WhenExceptionOccurs() {
        // Arrange
        FormRequest formRequest = FormRequest.builder()
                .serviceId(100L)
                .userId("user123")
                .formData(Map.of("key1", "value1", "key2", "value2"))
                .build();

        when(userFormRepository.save(any(UserForm.class))).thenThrow(new RuntimeException("Database error"));

        // Act
        UserFormResponse response = userFormService.saveUserForm(formRequest);

        // Assert
        assertNotNull(response);
        assertFalse(response.isStatus());
        assertNull(response.getFormData());
        verify(userFormRepository, times(1)).save(any(UserForm.class));
    }

    // Tests for getSubmittedForms
    @Test
    void getSubmittedForms_ShouldReturnFormDataResponses_WhenFormsExist() {
        // Arrange
        Long serviceId = 100L;
        String userId = "user123";

        UserForm userForm1 = UserForm.builder()
                .serviceId(serviceId)
                .userId(userId)
                .formData(Map.of("key1", "value1"))
                .createdAt(LocalDateTime.now())
                .build();

        UserForm userForm2 = UserForm.builder()
                .serviceId(serviceId)
                .userId(userId)
                .formData(Map.of("key2", "value2"))
                .createdAt(LocalDateTime.now())
                .build();

        when(userFormRepository.findByServiceIdAndUserId(serviceId, userId)).thenReturn(List.of(userForm1, userForm2));

        // Act
        FormDataResponse responses = userFormService.getSubmittedForms(serviceId, userId);

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("value1", responses.get(0).getFormData().get(0).get("key1"));
        assertEquals("value2", responses.get(1).getFormData().get(0).get("key2"));
        verify(userFormRepository, times(1)).findByServiceIdAndUserId(serviceId, userId);
    }

    @Test
    void getSubmittedForms_ShouldReturnEmptyList_WhenNoFormsExist() {
        // Arrange
        Long serviceId = 100L;
        String userId = "user123";

        when(userFormRepository.findByServiceIdAndUserId(serviceId, userId)).thenReturn(List.of());

        // Act
        List<FormDataResponse> responses = userFormService.getSubmittedForms(serviceId, userId);

        // Assert
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
        verify(userFormRepository, times(1)).findByServiceIdAndUserId(serviceId, userId);
    }

    @Test
    void getSubmittedForms_ShouldReturnEmptyList_WhenExceptionOccurs() {
        // Arrange
        Long serviceId = 100L;
        String userId = "user123";

        when(userFormRepository.findByServiceIdAndUserId(serviceId, userId)).thenThrow(new RuntimeException("Database error"));

        // Act
        List<FormDataResponse> responses = userFormService.getSubmittedForms(serviceId, userId);

        // Assert
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
        verify(userFormRepository, times(1)).findByServiceIdAndUserId(serviceId, userId);
    }
}
