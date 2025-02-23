package com.finclutech.dynamic_form_manager.services.auth;


import com.finclutech.dynamic_form_manager.dtos.requests.auth.AppAuthenticationRequest;
import com.finclutech.dynamic_form_manager.dtos.response.auth.AppAuthenticationResponse;
import com.finclutech.dynamic_form_manager.dtos.response.auth.AppTokenValidationResponse;

public interface AppAuthenticationService {

    AppTokenValidationResponse validateToken(String token);
    AppAuthenticationResponse authenticate(AppAuthenticationRequest appAuthenticationRequest);
}
