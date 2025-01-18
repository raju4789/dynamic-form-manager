package com.finclutech.dynamic_form_manager.dtos.response;

import com.finclutech.dynamic_form_manager.dtos.FieldValidationErrorMessageDTO;
import com.finclutech.dynamic_form_manager.dtos.LabelDTO;
import com.finclutech.dynamic_form_manager.dtos.PlaceHolderDTO;
import com.finclutech.dynamic_form_manager.entities.sql.FieldOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldsResponse {

    private Long fieldId;
    private String fieldName;
    private String fieldType;
    private String fieldValidation;
    private List<FieldValidationErrorMessageDTO> fieldValidationErrorMessages;
    private List<LabelDTO> labels;
    private List<PlaceHolderDTO> placeHolders;
    private Optional<Integer> maxLength = Optional.empty();
    private Optional<String> defaultValue = Optional.empty();
    private Optional<List<FieldOption>> fieldOptions = Optional.empty();

}
