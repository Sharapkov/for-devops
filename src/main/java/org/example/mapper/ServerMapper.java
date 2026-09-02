package org.example.mapper;

import org.example.dto.ConnectivityDto;
import org.example.dto.DiskDto;
import org.example.dto.ServerDto;
import org.example.entity.Connectivity;
import org.example.entity.Disk;
import org.example.entity.Server;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {DiskMapper.class, ConnectivityMapper.class})
public interface ServerMapper {

    @Mapping(target = "connectivities", ignore = true)
    ServerDto toDto(Server server);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "disks", ignore = true)
    @Mapping(target = "connectivities", ignore = true)
    void updateFromDto(ServerDto dto, @MappingTarget Server server);

    @AfterMapping
    default void computeDiskTotals(Server server, @MappingTarget ServerDto dto) {
        dto.computeDiskTotals();
    }

    @AfterMapping
    default void mapDisks(@MappingTarget Server server, ServerDto dto) {
        server.getDisks().clear();
        if (dto.getDisks() != null) {
            for (DiskDto diskDto : dto.getDisks()) {
                Disk disk = toDisk(diskDto);
                server.addDisk(disk);
            }
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "server", ignore = true)
    Disk toDisk(DiskDto dto);

    @AfterMapping
    default void mapConnectivities(Server server, @MappingTarget ServerDto dto) {
        if (server.getConnectivities() != null && !server.getConnectivities().isEmpty()) {
            dto.setConnectivities(server.getConnectivities().stream()
                    .map(this::toConnectivityDto)
                    .collect(Collectors.toList()));
        }
    }

    private ConnectivityDto toConnectivityDto(Connectivity connectivity) {
        ConnectivityDto dto = new ConnectivityDto();
        dto.setId(connectivity.getId());
        if (connectivity.getType() != null) dto.setType(connectivity.getType().name());
        dto.setService(connectivity.getService());
        if (connectivity.getDirection() != null) dto.setDirection(connectivity.getDirection().name());
        dto.setSubnetName(connectivity.getSubnetName());
        dto.setSourceIp(connectivity.getSourceIp());
        dto.setProtocolsAndPorts(connectivity.getProtocolsAndPorts());
        dto.setJustification(connectivity.getJustification());
        if (connectivity.getValidityType() != null) dto.setValidityType(connectivity.getValidityType().name());
        dto.setUntilDate(connectivity.getUntilDate());
        dto.setRequestNumber(connectivity.getRequestNumber());
        if (connectivity.getStatus() != null) dto.setStatus(connectivity.getStatus().name());
        dto.setNote(connectivity.getNote());
        return dto;
    }

}
