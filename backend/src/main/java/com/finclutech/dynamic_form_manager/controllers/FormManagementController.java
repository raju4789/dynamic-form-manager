package com.finclutech.dynamic_form_manager.controllers;

import com.finclutech.dynamic_form_manager.dtos.AppServiceDTO;
import com.finclutech.dynamic_form_manager.dtos.response.FieldsResponse;
import com.finclutech.dynamic_form_manager.dtos.LanguageDTO;
import com.finclutech.dynamic_form_manager.dtos.common.CommonApiResponse;
import com.finclutech.dynamic_form_manager.services.FormManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manage")
@RequiredArgsConstructor
@Tag(name = "Service Form Manager", description = "Service Form Manager APIs")
public class FormManagementController {

    private final FormManagerService formManagerService;

    @Operation(
            description = "Get all services",
            summary = "Get all services",

            responses = {
                    @ApiResponse(responseCode = "200", description = "Services retrieved successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
            })
    @GetMapping("/service")
    public ResponseEntity<CommonApiResponse<List<AppServiceDTO>>> getAllServices() {
        List<AppServiceDTO> services = formManagerService.getAllServices();
        CommonApiResponse<List<AppServiceDTO>> servicesResponse = new CommonApiResponse<>(services);
        return ResponseEntity.ok(servicesResponse);
    }

    @Operation(
            description = "Get all languages",
            summary = "Get all languages",

            responses = {
                    @ApiResponse(responseCode = "200", description = "Languages retrieved successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
            })
    @GetMapping("/language")
    public ResponseEntity<CommonApiResponse<List<LanguageDTO>>> getAllLanguages() {
        List<LanguageDTO> languages = formManagerService.getAllLanguages();
        CommonApiResponse<List<LanguageDTO>> languagesResponse = new CommonApiResponse<>(languages);
        return ResponseEntity.ok(languagesResponse);
    }

    @Operation(
            description = "Get fields by service id",
            summary = "Get fields by service id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fields retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
            })
    @GetMapping("/service/fields/{id}")
    public ResponseEntity<CommonApiResponse<List<FieldsResponse>>> getFieldsByServiceId(@PathVariable(name = "id") Long serviceId) {
        List<FieldsResponse> fields = formManagerService.getFieldsByServiceId(serviceId);
        CommonApiResponse<List<FieldsResponse>> fieldsResponse = new CommonApiResponse<>(fields);
        return ResponseEntity.ok(fieldsResponse);
    }
}
