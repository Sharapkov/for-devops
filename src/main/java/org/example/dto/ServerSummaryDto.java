package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerSummaryDto {

    private Long id;
    private String hostname;
    private String ipAddress;
    private String role;
}
