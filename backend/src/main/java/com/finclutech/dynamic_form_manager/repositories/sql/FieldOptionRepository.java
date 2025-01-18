package com.finclutech.dynamic_form_manager.repositories.sql;

import com.finclutech.dynamic_form_manager.entities.sql.FieldOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldOptionRepository extends JpaRepository<FieldOption, Long> {

    List<FieldOption> findByFieldIdIn(List<Long> fieldIds);

}
