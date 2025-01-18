package com.finclutech.dynamic_form_manager.entities.nosql;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@Document(collection = "user_forms") // MongoDB collection name
public class UserForm {

    @Id
    @Field(name = "form_id")
    private String formId;

    @Field(name = "service_id")
    private Long serviceId;

    @Field(name = "user_id")
    private String userId;

    @Field(name = "form_data")
    private Map<String, Object> formData;

    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    private LocalDateTime updatedAt;

}
