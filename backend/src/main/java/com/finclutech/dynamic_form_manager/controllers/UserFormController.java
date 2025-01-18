package com.finclutech.dynamic_form_manager.controllers;

import com.finclutech.dynamic_form_manager.dtos.common.CommonApiResponse;
import com.finclutech.dynamic_form_manager.dtos.common.ErrorDetails;
import com.finclutech.dynamic_form_manager.dtos.requests.FormRequest;
import com.finclutech.dynamic_form_manager.dtos.response.FormDataResponse;
import com.finclutech.dynamic_form_manager.dtos.response.UserFormResponse;
import com.finclutech.dynamic_form_manager.services.UserFormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/form")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Form Manager", description = "User Form Manager APIs")
public class UserFormController {


    private final UserFormService userFormService;

    @Operation(
            description = "Submit user form",
            summary = "Submit user form",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User form submitted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
            })
    @PostMapping("/submit")
    public ResponseEntity<CommonApiResponse<UserFormResponse>> saveUserForm(@RequestBody @Valid FormRequest formRequest) {
        try {
            // Call the service to save the user form
            UserFormResponse userFormResponse = userFormService.saveUserForm(formRequest);

            if (userFormResponse.isStatus()) {
                // Create a success response
                CommonApiResponse<UserFormResponse> response = new CommonApiResponse<>(userFormResponse);
                return ResponseEntity.ok(response);
            } else {
                // Create an error response with appropriate error details using the builder pattern
                ErrorDetails errorDetails = ErrorDetails.builder()
                        .errorCode(400)
                        .errorMessage("Failed to save user form")
                        .build();

                CommonApiResponse<UserFormResponse> response = new CommonApiResponse<>(errorDetails);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            // Log the exception and return an internal server error response
            log.error("An unexpected error occurred while saving the user form: {}", e.getMessage(), e);

            // Create an error response with appropriate error details using the builder pattern
            ErrorDetails errorDetails = ErrorDetails.builder()
                    .errorCode(500)
                    .errorMessage("An unexpected error occurred. Please try again later.")
                    .build();

            CommonApiResponse<UserFormResponse> response = new CommonApiResponse<>(errorDetails);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // get form data by service id and userId
    @Operation(
            description = "Get form data by service id and userId",
            summary = "Get form data by service id and userId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Form data retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
            })
    @GetMapping("/getFormData")
    public ResponseEntity<CommonApiResponse<List<FormDataResponse>>> getFormData(@RequestParam Long serviceId, @RequestParam String userId) {
        try {
            // Call the service to get the form data
            List<FormDataResponse> formDataResponse = userFormService.getSubmittedForms(serviceId, userId);

            // Create a success response
            CommonApiResponse<List<FormDataResponse>> response = new CommonApiResponse<>(formDataResponse);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Log the exception and return an internal server error response
            log.error("An unexpected error occurred while fetching the form data: {}", e.getMessage(), e);

            // Create an error response with appropriate error details using the builder pattern
            ErrorDetails errorDetails = ErrorDetails.builder()
                    .errorCode(500)
                    .errorMessage("An unexpected error occurred. Please try again later.")
                    .build();

            CommonApiResponse<List<FormDataResponse>> response = new CommonApiResponse<>(errorDetails);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
