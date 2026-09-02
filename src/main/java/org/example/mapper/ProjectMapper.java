package org.example.mapper;

import org.example.dto.ProjectDto;
import org.example.dto.ProjectShortDto;
import org.example.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "servers", ignore = true)
    ProjectDto toDto(Project project);

    ProjectShortDto toShortDto(Project project);
}
