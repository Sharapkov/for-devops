package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerDto {

    private Long id;

    @NotBlank(message = "Hostname is required")
    private String hostname;

    // todo проверить валидацию
    @NotBlank(message = "IP address is required")
    @Pattern(regexp = "^(\\d{1,3}\\.){3}\\d{1,3}(/(\\d{1,2}))?$", message = "Invalid IP address format")
    private String ipAddress;

    private String role;
    private String environment;
    private ProjectShortDto project;
    private String os;

    /** Количество ядер ЦП */
    private Integer vCpu;

    /** Объем ОЗУ в ГБ */
    private Integer ramGb;

    /** Общий объем дисков типа HDD */
    private Integer totalHddGb;

    /** Общий объем дисков типа SSD */
    private Integer totalSsdGb;

    /** Общий объем дисков всех типов */
    private Integer totalAllGb;

    private List<DiskDto> disks;

    /** Связи сервера (только для чтения, управление через /api/connectivities) */
    private List<ConnectivityDto> connectivities;

    /** Метод для вычисления суммарных объемов дисков */
    public void computeDiskTotals() {
        if (disks == null || disks.isEmpty()) {
            this.totalHddGb = 0;
            this.totalSsdGb = 0;
            this.totalAllGb = 0;
            return;
        }
        int hdd = 0, ssd = 0;
        for (DiskDto disk : disks) {
            int size = disk.getSizeGb() != null ? disk.getSizeGb() : 0;
            String type = disk.getType();
            if (type == null) continue;
            switch (type) {
                case "HDD":
                    hdd += size;
                    break;
                case "SSD":
                    ssd += size;
                    break;
                default:
                    hdd += size;
                    ssd += size;
                    break;
            }
        }
        this.totalHddGb = hdd;
        this.totalSsdGb = ssd;
        this.totalAllGb = hdd + ssd;
    }
}
