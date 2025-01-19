package com.finclutech.dynamic_form_manager.services;

import com.finclutech.dynamic_form_manager.dtos.requests.FormRequest;
import com.finclutech.dynamic_form_manager.dtos.response.FormDataResponse;
import com.finclutech.dynamic_form_manager.dtos.response.UserFormResponse;

import java.util.List;

public interface UserFormService {

    UserFormResponse saveUserForm(FormRequest formRequest);

    FormDataResponse getSubmittedForms(Long serviceId, String userId);
}
