package com.finclutech.dynamic_form_manager.repositories.sql;

import com.finclutech.dynamic_form_manager.entities.sql.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Language findByLanguageCode(String languageCode);
}
