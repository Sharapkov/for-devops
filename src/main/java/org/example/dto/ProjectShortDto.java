package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectShortDto {

    private Long id;

    @NotBlank(message = "Project name is required")
    private String name;

}
