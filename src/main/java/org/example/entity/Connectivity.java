package org.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.example.entity.enums.ConnectivityDirection;
import org.example.entity.enums.ConnectivityStatus;
import org.example.entity.enums.ConnectivityType;
import org.example.entity.enums.ValidityType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность "Связность".
 * Представляет карточку сетевой связности, привязанную к одному или нескольким серверам.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "connectivities")
public class Connectivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип связности.
     * INTERNAL — между серверами в текущем реестре.
     * EXTERNAL — с внешними ресурсами (другие серверы ФР, внешние зависимости).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ConnectivityType type;

    /** Сервис, использующий данную связность (информационное поле) */
    @Column(name = "service")
    private String service;

    /**
     * Направление соединения.
     * INCOMING — входящее.
     * OUTGOING — исходящее.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "direction")
    private ConnectivityDirection direction;

    /** Название подсети (информационное поле) */
    @Column(name = "subnet_name")
    private String subnetName;

    /** IP или подсеть источника (IP/маска, например: 192.168.10.0/24) */
    @Column(name = "source_ip")
    private String sourceIp;

    /** IP или подсеть назначения (IP/маска, например: 192.168.10.0/24) */
    @Column(name = "destination_ip")
    private String destinationIp;

    /** Протоколы и порты доступа (многострочное значение) */
    @Lob
    @Column(name = "protocols_and_ports", columnDefinition = "TEXT")
    private String protocolsAndPorts;

    /** Обоснование доступа (текстовое, в том числе многострочное) */
    @Lob
    @Column(name = "justification", columnDefinition = "TEXT")
    private String justification;

    /**
     * Срок действия доступы.
     * PERMANENT — постоянно.
     * TEMPORARY — временно.
     * UNTIL_DATE — до определённой даты (untilDate).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "validity_type")
    private ValidityType validityType;

    /** Конечная дата действия (заполняется, если validityType = UNTIL_DATE) */
    @Column(name = "until_date")
    private LocalDate untilDate;

    /** Номер заявки по которому запрашивался доступ */
    @Column(name = "request_number")
    private String requestNumber;

    /**
     * Статус связности.
     * NEW — новый (черновик).
     * REQUESTED — запрошен (заявка отправлена, но не выполнена).
     * ACTIVE — действующий (доступ предоставлен и проверен).
     * REWORK — доработка (заявку выполнили, но доступ не работает).
     * REVOKED — отозван / прекратил действие.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ConnectivityStatus status;

    /** Примечание (комментарий) */
    @Lob
    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    /** Серверы, связанные с данной связностью (один или несколько) */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "connectivity_servers",
            joinColumns = @JoinColumn(name = "connectivity_id"),
            inverseJoinColumns = @JoinColumn(name = "server_id")
    )
    private List<Server> servers = new ArrayList<>();

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
        if (status == null) {
            status = ConnectivityStatus.NEW;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
