package com.finclutech.dynamic_form_manager.dtos.requests.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppAuthenticationResponse {
   String fullName;
   String username;
   String token;
   String role;
}
