package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiskDto {

    private Long id;

    @NotBlank(message = "Disk type is required")
    private String type;

    @NotBlank(message = "Disk size is required")
    private Integer sizeGb;
}
