package com.finclutech.dynamic_form_manager.services;

import com.finclutech.dynamic_form_manager.dtos.*;
import com.finclutech.dynamic_form_manager.dtos.response.FieldsResponse;
import com.finclutech.dynamic_form_manager.entities.sql.*;
import com.finclutech.dynamic_form_manager.exceptions.RecordNotFoundException;
import com.finclutech.dynamic_form_manager.repositories.sql.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FormManagerServiceImplTest {

    @InjectMocks
    private FormManagerServiceImpl formManagerService;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FieldTranslationRepository fieldTranslationRepository;

    @Mock
    private FieldOptionRepository fieldOptionRepository;

    @Mock
    private LanguageRepository languageRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test: getAllServices - Success Scenario
    @Test
    void getAllServices_ShouldReturnServiceDTOs_WhenServicesExist() {
        // Arrange
        List<AppService> services = List.of(
                new AppService(1L, "Service A", LocalDateTime.now(), LocalDateTime.now()),
                new AppService(2L, "Service B", LocalDateTime.now(), LocalDateTime.now())
        );

        when(serviceRepository.findAll()).thenReturn(services);

        // Act
        List<AppServiceDTO> result = formManagerService.getAllServices();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Service A", result.get(0).getServiceName());
        assertEquals("Service B", result.get(1).getServiceName());
        verify(serviceRepository, times(1)).findAll();
    }

    // Test: getAllServices - Exception Handling
    @Test
    void getAllServices_ShouldThrowRecordNotFoundException_WhenUnexpectedErrorOccurs() {
        // Arrange
        when(serviceRepository.findAll()).thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> formManagerService.getAllServices());
        assertEquals("No services found", exception.getMessage());
        verify(serviceRepository, times(1)).findAll();
    }

    // Test: getAllLanguages - Success Scenario
    @Test
    void getAllLanguages_ShouldReturnLanguageDTOs_WhenLanguagesExist() {
        // Arrange
        List<Language> languages = List.of(
                new Language(1L, "en", "English", LocalDateTime.now(), LocalDateTime.now()),
                new Language(2L, "fr", "French", LocalDateTime.now(), LocalDateTime.now())
        );

        when(languageRepository.findAll()).thenReturn(languages);

        // Act
        List<LanguageDTO> result = formManagerService.getAllLanguages();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("English", result.get(0).getLanguageName());
        assertEquals("French", result.get(1).getLanguageName());
        verify(languageRepository, times(1)).findAll();
    }

    // Test: getAllLanguages - Exception Handling
    @Test
    void getAllLanguages_ShouldThrowRecordNotFoundException_WhenUnexpectedErrorOccurs() {
        // Arrange
        when(languageRepository.findAll()).thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> formManagerService.getAllLanguages());
        assertEquals("No languages found", exception.getMessage());
        verify(languageRepository, times(1)).findAll();
    }

    // Test: getFieldsByServiceId - Success Scenario
    @Test
    void getFieldsByServiceId_ShouldReturnFieldsResponse_WhenFieldsExist() {
        // Arrange
        Long serviceId = 1L;
        List<Field> fields = List.of(
                new Field(1L, serviceId, "Field A", "text", "required", 50, null, true, LocalDateTime.now(), LocalDateTime.now()),
                new Field(2L, serviceId, "Field B", "number", "optional", 10, null, false, LocalDateTime.now(), LocalDateTime.now())
        );

        List<FieldTranslation> translations = List.of(
                new FieldTranslation(1L, 1L, 1L, "Label A", "Placeholder A", "Validation Error A", LocalDateTime.now(), LocalDateTime.now()),
                new FieldTranslation(2L, 2L, 1L, "Label B", "Placeholder B", "Validation Error B", LocalDateTime.now(), LocalDateTime.now())
        );

        List<FieldOption> options = List.of(
                new FieldOption(1L, 2L, "Option 1", "Label 1", LocalDateTime.now(), LocalDateTime.now()),
                new FieldOption(2L, 2L, "Option 2", "Label 2", LocalDateTime.now(), LocalDateTime.now())
        );

        when(fieldRepository.findByServiceId(serviceId)).thenReturn(Optional.of(fields));
        when(fieldTranslationRepository.findByFieldIdIn(List.of(1L, 2L))).thenReturn(translations);
        when(fieldOptionRepository.findByFieldIdIn(List.of(1L, 2L))).thenReturn(options);

        // Act
        List<FieldsResponse> result = formManagerService.getFieldsByServiceId(serviceId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Field A", result.get(0).getFieldName());
        assertEquals("Field B", result.get(1).getFieldName());
        verify(fieldRepository, times(1)).findByServiceId(serviceId);
        verify(fieldTranslationRepository, times(1)).findByFieldIdIn(List.of(1L, 2L));
        verify(fieldOptionRepository, times(1)).findByFieldIdIn(List.of(1L, 2L));
    }

    // Test: getFieldsByServiceId - No Fields Found
    @Test
    void getFieldsByServiceId_ShouldThrowRecordNotFoundException_WhenNoFieldsExist() {
        // Arrange
        Long serviceId = 1L;
        when(fieldRepository.findByServiceId(serviceId)).thenReturn(Optional.empty());

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> formManagerService.getFieldsByServiceId(serviceId));
        assertEquals("No fields found for service id: " + serviceId, exception.getMessage());
        verify(fieldRepository, times(1)).findByServiceId(serviceId);
        verifyNoInteractions(fieldTranslationRepository, fieldOptionRepository);
    }

    // Test: getFieldsByServiceId - Exception Handling
    @Test
    void getFieldsByServiceId_ShouldThrowRuntimeException_WhenUnexpectedErrorOccurs() {
        // Arrange
        Long serviceId = 1L;
        when(fieldRepository.findByServiceId(serviceId)).thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> formManagerService.getFieldsByServiceId(serviceId));
        assertEquals("An unexpected error occurred while fetching fields for service id: " + serviceId, exception.getMessage());
        verify(fieldRepository, times(1)).findByServiceId(serviceId);
        verifyNoInteractions(fieldTranslationRepository, fieldOptionRepository);
    }
}