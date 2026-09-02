package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.DiskType;

import javax.persistence.*;

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

}
