package com.finclutech.dynamic_form_manager.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
        private int errorCode;
        private String errorMessage;

}
