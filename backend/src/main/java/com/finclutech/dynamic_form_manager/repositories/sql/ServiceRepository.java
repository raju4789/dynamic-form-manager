package com.finclutech.dynamic_form_manager.repositories.sql;


import com.finclutech.dynamic_form_manager.entities.sql.AppService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<AppService, Long> {

}
