package org.example.mapper;

import org.example.dto.ConnectivityDto;
import org.example.entity.Connectivity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ConnectivityMapper {

    ConnectivityDto toDto(Connectivity connectivity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "direction", ignore = true)
    @Mapping(target = "validityType", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "servers", ignore = true)
    void updateFromDto(ConnectivityDto dto, @MappingTarget Connectivity connectivity);
}
