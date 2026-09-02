package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ConnectivityDto;
import org.example.entity.Connectivity;
import org.example.entity.enums.ConnectivityDirection;
import org.example.entity.enums.ConnectivityStatus;
import org.example.entity.enums.ConnectivityType;
import org.example.entity.enums.ValidityType;
import org.example.repository.ConnectivityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConnectivityService {

    private final ConnectivityRepository connectivityRepository;

    @Transactional
    public List<Connectivity> saveConnectivitiesFromDto(List<ConnectivityDto> connectivityDtos) {
        if (connectivityDtos == null || connectivityDtos.isEmpty()) {
            return List.of();
        }

        return connectivityDtos.stream()
                .map(dto -> {
                    if (dto.getId() != null) {
                        Connectivity existing = connectivityRepository.findById(dto.getId())
                                .orElseThrow(() -> new RuntimeException("Connectivity not found with id: " + dto.getId()));
                        updateConnectivity(dto, existing);
                        return connectivityRepository.saveAndFlush(existing);
                    } else {
                        Connectivity newConnectivity = new Connectivity();
                        mapConnectivity(dto, newConnectivity);
                        return connectivityRepository.saveAndFlush(newConnectivity);
                    }
                })
                .collect(Collectors.toList());
    }

    private void mapConnectivity(ConnectivityDto dto, Connectivity entity) {
        if (dto.getType() != null) entity.setType(ConnectivityType.valueOf(dto.getType()));
        entity.setService(dto.getService());
        if (dto.getDirection() != null) entity.setDirection(ConnectivityDirection.valueOf(dto.getDirection()));
        entity.setSubnetName(dto.getSubnetName());
        entity.setSourceIp(dto.getSourceIp());
        entity.setProtocolsAndPorts(dto.getProtocolsAndPorts());
        entity.setJustification(dto.getJustification());
        if (dto.getValidityType() != null) entity.setValidityType(ValidityType.valueOf(dto.getValidityType()));
        entity.setUntilDate(dto.getUntilDate());
        entity.setRequestNumber(dto.getRequestNumber());
        if (dto.getStatus() != null) entity.setStatus(ConnectivityStatus.valueOf(dto.getStatus()));
        entity.setNote(dto.getNote());
    }

    private void updateConnectivity(ConnectivityDto dto, Connectivity entity) {
        if (dto.getType() != null) entity.setType(ConnectivityType.valueOf(dto.getType()));
        entity.setService(dto.getService());
        if (dto.getDirection() != null) entity.setDirection(ConnectivityDirection.valueOf(dto.getDirection()));
        entity.setSubnetName(dto.getSubnetName());
        entity.setSourceIp(dto.getSourceIp());
        entity.setProtocolsAndPorts(dto.getProtocolsAndPorts());
        entity.setJustification(dto.getJustification());
        if (dto.getValidityType() != null) entity.setValidityType(ValidityType.valueOf(dto.getValidityType()));
        entity.setUntilDate(dto.getUntilDate());
        entity.setRequestNumber(dto.getRequestNumber());
        if (dto.getStatus() != null) entity.setStatus(ConnectivityStatus.valueOf(dto.getStatus()));
        entity.setNote(dto.getNote());
    }
}
