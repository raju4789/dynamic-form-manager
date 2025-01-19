package com.finclutech.dynamic_form_manager.services;

import com.finclutech.dynamic_form_manager.dtos.requests.FormRequest;
import com.finclutech.dynamic_form_manager.dtos.response.FormDataResponse;
import com.finclutech.dynamic_form_manager.dtos.response.UserFormResponse;
import com.finclutech.dynamic_form_manager.entities.nosql.UserForm;
import com.finclutech.dynamic_form_manager.repositories.nosql.UserFormRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFormServiceImpl implements UserFormService {

    private final UserFormRepository userFormRepository;

    @Override
    public UserFormResponse saveUserForm(FormRequest formRequest) {
        log.info("Attempting to save user form for userId: {}, formId: {}, serviceId: {}",
                formRequest.getUserId(), formRequest.getFormId(), formRequest.getServiceId());

        try {
            // Map FormRequest to UserForm entity
            UserForm userForm = mapToUserForm(formRequest);

            // Save the form to the repository
            UserForm savedUserForm = userFormRepository.save(userForm);

            log.info("Successfully saved user form for userId: {}, formId: {}, serviceId: {}",
                    formRequest.getUserId(), formRequest.getFormId(), formRequest.getServiceId());

            // Return success response with form data
            return UserFormResponse.builder()
                    .status(true)
                    .formData(savedUserForm.getFormData())
                    .build();
        } catch (Exception e) {
            log.error("Failed to save user form for userId: {}, formId: {}, serviceId: {}. Error: {}",
                    formRequest.getUserId(), formRequest.getFormId(), formRequest.getServiceId(), e.getMessage(), e);

            // Return failure response with empty form data
            return UserFormResponse.builder()
                    .status(false)
                    .formData(null)
                    .build();
        }
    }


    /**
     * Fetches all submitted forms for a given serviceId and userId.
     *
     * @param serviceId The service ID to filter by.
     * @param userId    The user ID to filter by.
     * @return A list of FormDataResponse DTOs containing the form data and creation timestamps.
     */
    @Override
    public FormDataResponse getSubmittedForms(Long serviceId, String userId) {
        log.info("Attempting to get all submitted forms for serviceId: {}, userId: {}", serviceId, userId);

        try {
            // Fetch all submitted forms by serviceId and userId
            List<UserForm> userForms = userFormRepository.findByServiceIdAndUserId(serviceId, userId);

            if (userForms.isEmpty()) {
                log.info("No submitted forms found for serviceId: {}, userId: {}", serviceId, userId);
                return new FormDataResponse(List.of()); // Return an empty FormDataResponse
            }

            // Map UserForm entities to a single FormDataResponse DTO
            List<Map<String, Object>> formDataList = userForms.stream()
                    .map(UserForm::getFormData) // Extract the formData map from each UserForm
                    .collect(Collectors.toList());

            FormDataResponse formDataResponse = new FormDataResponse(formDataList);

            log.info("Successfully fetched {} submitted forms for serviceId: {}, userId: {}",
                    formDataList.size(), serviceId, userId);

            return formDataResponse;
        } catch (Exception e) {
            log.error("Failed to fetch submitted forms for serviceId: {}, userId: {}. Error: {}",
                    serviceId, userId, e.getMessage(), e);

            // Return an empty FormDataResponse in case of an error
            return new FormDataResponse(List.of());
        }
    }
    /**
     * Maps a FormRequest DTO to a UserForm entity.
     *
     * @param formRequest the form request DTO
     * @return the UserForm entity
     */
    private UserForm mapToUserForm(FormRequest formRequest) {
        return UserForm.builder()
                .formId(formRequest.getFormId())
                .serviceId(formRequest.getServiceId())
                .userId(formRequest.getUserId())
                .formData(formRequest.getFormData())
                .createdAt(LocalDateTime.now())
                .build();
    }
}