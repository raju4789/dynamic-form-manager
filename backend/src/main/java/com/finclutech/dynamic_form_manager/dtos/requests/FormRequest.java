package com.finclutech.dynamic_form_manager.dtos.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormRequest {

    @NotBlank(message = "formId is mandatory")
    private String formId;

    @NotBlank(message = "serviceId is mandatory")
    private Long serviceId;

    @NotBlank(message = "userId is mandatory")
    private String userId;

    @NotNull(message = "submissionData is mandatory")
    private Map<String, Object> formData;
}
