package com.finclutech.dynamic_form_manager.repositories.sql;


import com.finclutech.dynamic_form_manager.entities.sql.FieldTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldTranslationRepository extends JpaRepository<FieldTranslation, Long> {

    List<FieldTranslation> findByFieldIdIn(List<Long> fieldIds);

}
