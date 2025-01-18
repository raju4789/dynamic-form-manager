package com.finclutech.dynamic_form_manager.repositories.nosql;

import com.finclutech.dynamic_form_manager.entities.nosql.UserForm;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserFormRepository extends MongoRepository<UserForm, String> {
    List<UserForm> findByServiceIdAndUserId(Long serviceId, String userId);
}
