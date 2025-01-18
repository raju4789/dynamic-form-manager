package com.finclutech.dynamic_form_manager.controllers;


import com.finclutech.dynamic_form_manager.dtos.common.CommonApiResponse;
import com.finclutech.dynamic_form_manager.dtos.requests.auth.AppAuthenticationRequest;
import com.finclutech.dynamic_form_manager.dtos.requests.auth.AppAuthenticationResponse;
import com.finclutech.dynamic_form_manager.dtos.requests.auth.AppTokenValidationRequest;
import com.finclutech.dynamic_form_manager.dtos.requests.auth.AppTokenValidationResponse;
import com.finclutech.dynamic_form_manager.services.auth.AppAuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Authentication controller")
public class AppAuthenticationController {

    private final AppAuthenticationService appAuthenticationService;


    @PostMapping("/authenticate")
    public ResponseEntity<CommonApiResponse<AppAuthenticationResponse>> authenticate(@Valid @RequestBody AppAuthenticationRequest appAuthenticationRequest) {

        AppAuthenticationResponse appAuthenticationResponse = appAuthenticationService.authenticate(appAuthenticationRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CommonApiResponse<>(appAuthenticationResponse));
    }


    @PostMapping("/validateToken")
    public ResponseEntity<CommonApiResponse<AppTokenValidationResponse>> validateToken(@Valid @RequestBody AppTokenValidationRequest appTokenValidationRequest) {

        AppTokenValidationResponse appTokenValidationResponse = appAuthenticationService.validateToken(appTokenValidationRequest.getToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CommonApiResponse<>(appTokenValidationResponse));
    }
}
