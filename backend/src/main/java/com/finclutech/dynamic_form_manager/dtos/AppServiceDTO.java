package com.finclutech.dynamic_form_manager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppServiceDTO {

    private Long serviceId;
    private String serviceName;
}
