package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

    @NotBlank(message = "IP address is required")
    @Pattern(regexp = "^(\\d{1,3}\\.){3}\\d{1,3}(/(\\d{1,2}))?$", message = "Invalid IP address format")
    private String ipAddress;

    private String role;
    private String environment;
    private String projectName;
    private String os;

    private List<DiskDto> disks;
}
