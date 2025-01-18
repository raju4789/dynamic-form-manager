package com.finclutech.dynamic_form_manager.entities.sql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "field_translations")
public class FieldTranslation {
    @Id
    @Column(name = "field_translation_id")
    private Long fieldTranslationId;

    @Column(name = "field_id", nullable = false)
    private Long fieldId; // Foreign key to Field (managed manually)

    @Column(name = "language_id", nullable = false)
    private Long languageId; // Foreign key to Language (managed manually)

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "placeholder")
    private String placeholder;

    @Column(name = "validation_error_message")
    private String validationErrorMessage;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}