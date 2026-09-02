package org.example.mapper;

import org.example.dto.ServerDto;
import org.example.entity.Server;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.AfterMapping;

@Mapper(componentModel = "spring", uses = {DiskMapper.class})
public interface ServerMapper {

    ServerDto toDto(Server server);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "disks", ignore = true)
    void updateFromDto(ServerDto dto, @MappingTarget Server server);

    @AfterMapping
    default void computeDiskTotals(Server server, @MappingTarget ServerDto dto) {
        dto.computeDiskTotals();
    }

}
