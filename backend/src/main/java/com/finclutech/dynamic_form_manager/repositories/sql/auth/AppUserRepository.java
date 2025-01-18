package com.finclutech.dynamic_form_manager.repositories.sql.auth;

import com.finclutech.dynamic_form_manager.entities.sql.auth.AppUser;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Observed
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
}
