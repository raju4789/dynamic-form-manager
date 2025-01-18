package com.finclutech.dynamic_form_manager.services;

import com.finclutech.dynamic_form_manager.dtos.*;
import com.finclutech.dynamic_form_manager.dtos.response.FieldsResponse;
import com.finclutech.dynamic_form_manager.exceptions.RecordNotFoundException;
import com.finclutech.dynamic_form_manager.repositories.sql.*;
import com.finclutech.dynamic_form_manager.entities.sql.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class FormManagerServiceImpl implements FormManagerService {

    private final ServiceRepository serviceRepository;
    private final FieldRepository fieldRepository;
    private final FieldTranslationRepository fieldTranslationRepository;
    private final FieldOptionRepository fieldOptionRepository;
    private final LanguageRepository languageRepository;

    @Override
    public List<AppServiceDTO> getAllServices() throws RecordNotFoundException {

        try {
            List<AppServiceDTO> services = serviceRepository.findAll().stream().map(
                    service -> AppServiceDTO.builder()
                            .serviceId(service.getServiceId())
                            .serviceName(service.getName())
                            .build()
            ).toList();

            return services;

        } catch (Exception e) {
            log.error("Error while fetching services", e);
            throw new RecordNotFoundException("No services found");
        }

    }

    @Override
    public List<LanguageDTO> getAllLanguages() throws RecordNotFoundException {

        try {
            List<LanguageDTO> languages = languageRepository.findAll().stream().map(
                    language -> LanguageDTO.builder()
                            .languageId(language.getLanguageId())
                            .languageCode(language.getLanguageCode())
                            .languageName(language.getLanguageName())
                            .build()
            ).toList();

            return languages;

        } catch (Exception e) {
            log.error("Error while fetching languages", e);
            throw new RecordNotFoundException("No languages found");
        }

    }

    @Override
    public List<FieldsResponse> getFieldsByServiceId(Long serviceId) {
        try {
            // Fetch fields for the given service ID
            List<Field> fields = fieldRepository.findByServiceId(serviceId)
                    .orElseThrow(() -> new RecordNotFoundException("No fields found for service id: " + serviceId));

            // Fetch all field translations and options in batch
            Map<Long, List<FieldTranslation>> fieldTranslationsMap = fetchFieldTranslations(fields);
            Map<Long, List<FieldOption>> fieldOptionsMap = fetchFieldOptions(fields);

            // Map fields to DTOs
            return fields.stream()
                    .map(field -> mapToFieldsDTO(field, fieldTranslationsMap, fieldOptionsMap))
                    .toList();

        } catch (RecordNotFoundException e) {
            log.error("Record not found for serviceId: {}", serviceId, e);
            throw e; // Re-throw the exception to propagate it to the caller
        } catch (Exception e) {
            log.error("Unexpected error while fetching fields for serviceId: {}", serviceId, e);
            throw new RuntimeException("An unexpected error occurred while fetching fields for service id: " + serviceId, e);
        }
    }

    private Map<Long, List<FieldTranslation>> fetchFieldTranslations(List<Field> fields) {
        try {
            return fieldTranslationRepository.findByFieldIdIn(
                    fields.stream().map(Field::getFieldId).toList()
            ).stream().collect(Collectors.groupingBy(FieldTranslation::getFieldId));
        } catch (Exception e) {
            log.error("Error while fetching field translations", e);
            throw new RuntimeException("Failed to fetch field translations", e);
        }
    }

    private Map<Long, List<FieldOption>> fetchFieldOptions(List<Field> fields) {
        try {
            return fieldOptionRepository.findByFieldIdIn(
                    fields.stream().map(Field::getFieldId).toList()
            ).stream().collect(Collectors.groupingBy(FieldOption::getFieldId));
        } catch (Exception e) {
            log.error("Error while fetching field options", e);
            throw new RuntimeException("Failed to fetch field options", e);
        }
    }

    private FieldsResponse mapToFieldsDTO(Field field, Map<Long, List<FieldTranslation>> fieldTranslationsMap, Map<Long, List<FieldOption>> fieldOptionsMap) {
        try {
            List<FieldTranslation> translations = fieldTranslationsMap.getOrDefault(field.getFieldId(), List.of());
            List<FieldOption> options = fieldOptionsMap.getOrDefault(field.getFieldId(), List.of());

            return FieldsResponse.builder()
                    .fieldId(field.getFieldId())
                    .fieldName(field.getName())
                    .fieldType(field.getType())
                    .fieldValidation(field.getValidation())
                    .maxLength(Optional.of(field.getMaxLength()))
                    .defaultValue(Optional.ofNullable(field.getDefaultValue()))
                    .labels(translations.stream().map(this::mapToLabelDTO).toList())
                    .placeHolders(translations.stream().map(this::mapToPlaceHolderDTO).toList())
                    .fieldValidationErrorMessages(translations.stream().map(this::mapToFieldValidationErrorMessageDTO).toList())
                    .fieldOptions(Optional.of(options))
                    .build();
        } catch (Exception e) {
            log.error("Error while mapping field to FieldsDTO for fieldId: {}", field.getFieldId(), e);
            throw new RuntimeException("Failed to map field to DTO for fieldId: " + field.getFieldId(), e);
        }
    }

    private LabelDTO mapToLabelDTO(FieldTranslation translation) {
        return LabelDTO.builder()
                .languageId(translation.getLanguageId())
                .labelName(translation.getLabel())
                .build();
    }

    private PlaceHolderDTO mapToPlaceHolderDTO(FieldTranslation translation) {
        return PlaceHolderDTO.builder()
                .languageId(translation.getLanguageId())
                .placeHolderName(translation.getPlaceholder())
                .build();
    }

    private FieldValidationErrorMessageDTO mapToFieldValidationErrorMessageDTO(FieldTranslation translation) {
        return FieldValidationErrorMessageDTO.builder()
                .languageId(translation.getLanguageId())
                .fieldValidationErrorMessage(translation.getValidationErrorMessage())
                .build();
    }

}
