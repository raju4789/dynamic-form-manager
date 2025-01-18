package com.finclutech.dynamic_form_manager.services;



import com.finclutech.dynamic_form_manager.dtos.AppServiceDTO;
import com.finclutech.dynamic_form_manager.dtos.response.FieldsResponse;
import com.finclutech.dynamic_form_manager.dtos.LanguageDTO;

import java.util.List;

public interface FormManagerService {

    List<AppServiceDTO> getAllServices();

    List<FieldsResponse> getFieldsByServiceId(Long serviceId);

    List<LanguageDTO> getAllLanguages();

}
