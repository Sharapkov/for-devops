package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ConnectivityDto;
import org.example.entity.Connectivity;
import org.example.entity.Server;
import org.example.entity.enums.ConnectivityDirection;
import org.example.entity.enums.ConnectivityType;
import org.example.mapper.ConnectivityMapper;
import org.example.repository.ConnectivityRepository;
import org.example.repository.ServerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConnectivityService {

    private final ConnectivityRepository connectivityRepository;
    private final ServerRepository serverRepository;
    private final ConnectivityMapper connectivityMapper;

    @Transactional
    public ConnectivityDto create(ConnectivityDto dto) {
        Connectivity connectivity = connectivityMapper.toEntity(dto);

        // Автопоиск серверов для INTERNAL связей
        if (ConnectivityType.INTERNAL.name().equals(dto.getType())) {
            linkServersToConnectivity(dto, connectivity);
        }

        connectivity = connectivityRepository.save(connectivity);
        return connectivityMapper.toDto(connectivity);
    }

    private void linkServersToConnectivity(ConnectivityDto dto, Connectivity connectivity) {
        List<Server> linkedServers = new ArrayList<>();

        // Проверяем sourceIp
        Optional<Server> sourceServer = serverRepository.findByIpAddress(dto.getSourceIp());
        sourceServer.ifPresent(server -> {
            linkedServers.add(server);
            // Если sourceIp совпадает с IP сервера — направление OUTGOING (сервер отправляет)
            if (connectivity.getDirection() == null) {
                connectivity.setDirection(ConnectivityDirection.OUTGOING);
            }
        });

        // Проверяем destinationIp
        Optional<Server> destServer = serverRepository.findByIpAddress(dto.getDestinationIp());
        destServer.ifPresent(server -> {
            linkedServers.add(server);
            // Если destinationIp совпадает с IP сервера — направление INCOMING (сервер получает)
            if (connectivity.getDirection() == null) {
                connectivity.setDirection(ConnectivityDirection.INCOMING);
            }
        });

        // Привязываем найденные серверы к связи
        for (Server server : linkedServers) {
            server.getConnectivities().add(connectivity);
        }
    }

    @Transactional(readOnly = true)
    public List<ConnectivityDto> findAll() {
        return connectivityRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(connectivityMapper::toDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConnectivityDto findById(Long id) {
        Connectivity connectivity = connectivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Connectivity not found with id: " + id));
        return connectivityMapper.toDto(connectivity);
    }
}
