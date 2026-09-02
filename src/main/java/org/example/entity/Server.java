package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность "Сервер".
 * Представляет собой сервер в реестре с его характеристиками, привязкой к проекту и сетевыми связностями.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servers")
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Имя сервера (hostname) */
    @Column(name = "hostname", nullable = false, unique = true)
    private String hostname;

    /** IP-адрес сервера */
    @Column(name = "ip_address", nullable = false, unique = true)
    private String ipAddress;

    /** Роль (назначение) сервера */
    @Column(name = "role")
    private String role;

    /** Среда эксплуатации (dev, test, staging, prod и т.д.) */
    @Column(name = "environment")
    private String environment;

    /** Проект, к которому относится сервер */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    /** Операционная система сервера */
    @Column(name = "os")
    private String os;

    /** Количество ядер ЦП */
    @Column(name = "vcpu")
    private Integer vCpu;

    /** Объем ОЗУ в ГБ */
    @Column(name = "ram_gb")
    private Integer ramGb;

    /** Характеристики дисков сервера */
    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Disk> disks = new ArrayList<>();

    /** Сетевые связности сервера */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "connectivity_servers",
            joinColumns = @JoinColumn(name = "server_id"),
            inverseJoinColumns = @JoinColumn(name = "connectivity_id")
    )
    private List<Connectivity> connectivities = new ArrayList<>();

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

    /** Добавляет диск в список дисков сервера (устанавливает обратную связь) */
    public void addDisk(Disk disk) {
        disks.add(disk);
        disk.setServer(this);
    }

    /** Добавляет связность к серверу (устанавливает обратную связь) */
    public void addConnectivity(Connectivity connectivity) {
        connectivities.add(connectivity);
    }

}
