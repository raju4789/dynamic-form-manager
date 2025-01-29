package com.finclutech.dynamic_form_manager.dtos.response.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppTokenValidationResponse {
    private boolean isValid;
}
