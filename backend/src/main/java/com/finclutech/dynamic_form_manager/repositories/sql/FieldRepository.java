package com.finclutech.dynamic_form_manager.repositories.sql;


import com.finclutech.dynamic_form_manager.entities.sql.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    // Find all fields for a specific service
    Optional<List<Field>> findByServiceId(Long serviceId);
}
