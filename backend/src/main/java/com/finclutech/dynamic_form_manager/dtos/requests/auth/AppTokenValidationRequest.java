package com.finclutech.dynamic_form_manager.dtos.requests.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppTokenValidationRequest {

    @NotBlank(message = "Token is mandatory")
    private String token;
}