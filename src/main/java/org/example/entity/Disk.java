package org.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.example.entity.enums.DiskType;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Сущность "Диск".
 * Представляет характеристику диска сервера (тип и объём).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "disks")
public class Disk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Сервер, которому принадлежит диск */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    /** Тип диска (SSD / HDD) */
    @Enumerated(EnumType.STRING)
    @Column(name = "disk_type", nullable = false)
    private DiskType type;

    /** Объём диска в ГБ */
    @Column(name = "size_gb", nullable = false)
    private Integer sizeGb;

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
