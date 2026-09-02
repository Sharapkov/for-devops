package org.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Сущность "Проект".
 * Представляет собой проект, в рамках которого находятся серверы и связности.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование проекта.
     * Фиксированный набор: ЕЦП, Сайт «Московские Кварталы», CRM «Московские Кварталы», Инфраструктура.
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /** Дата и время создания записи */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** Дата и время последнего обновления записи */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
