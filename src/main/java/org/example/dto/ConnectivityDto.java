package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectivityDto {

    private Long id;

    @NotBlank(message = "Type is required")
    private String type;

    private String service;
    private String direction;
    private String subnetName;
    private String sourceIp;
    private String destinationIp;
    private String protocolsAndPorts;
    private String justification;
    private String validityType;
    private LocalDate untilDate;
    private String requestNumber;

    @NotBlank(message = "Status is required")
    private String status;

    private String note;
    private List<Long> serverIds;
}
