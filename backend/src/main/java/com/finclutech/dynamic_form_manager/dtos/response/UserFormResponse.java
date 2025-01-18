package com.finclutech.dynamic_form_manager.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFormResponse {

    private boolean status;

    private Map<String, Object> formData;
}
