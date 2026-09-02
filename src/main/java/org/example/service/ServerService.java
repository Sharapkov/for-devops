package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.DiskDto;
import org.example.dto.ServerDto;
import org.example.dto.ServerSummaryDto;
import org.example.entity.Connectivity;
import org.example.entity.Disk;
import org.example.entity.Server;
import org.example.entity.enums.DiskType;
import org.example.mapper.ServerMapper;
import org.example.repository.ServerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;
    private final ConnectivityService connectivityService;
    private final ServerMapper serverMapper;

    // ok
    public ServerDto findById(Long id) {
        Server server = serverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Server not found with id: " + id));
        return serverMapper.toDto(server);
    }

    // ok
    @Transactional
    public ServerDto create(ServerDto dto) {
        if (serverRepository.existsByHostname(dto.getHostname())) {
            throw new RuntimeException("Server with hostname '" + dto.getHostname() + "' already exists");
        }
        if (serverRepository.existsByIpAddress(dto.getIpAddress())) {
            throw new RuntimeException("Server with IP address '" + dto.getIpAddress() + "' already exists");
        }

        Server server = new Server();
        serverMapper.updateFromDto(dto, server);
        mapBusinessFields(dto, server);
        server = serverRepository.saveAndFlush(server);
        return serverMapper.toDto(server);
    }


    // ok
    @Transactional
    public ServerDto update(Long id, ServerDto dto) {
        Server server = serverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Server not found with id: " + id));

        // Check uniqueness if hostname or IP changed
        if (!server.getHostname().equals(dto.getHostname()) && serverRepository.existsByHostname(dto.getHostname())) {
            throw new RuntimeException("Server with hostname '" + dto.getHostname() + "' already exists");
        }
        if (!server.getIpAddress().equals(dto.getIpAddress()) && serverRepository.existsByIpAddress(dto.getIpAddress())) {
            throw new RuntimeException("Server with IP address '" + dto.getIpAddress() + "' already exists");
        }

        serverMapper.updateFromDto(dto, server);
        mapBusinessFields(dto, server);
        server = serverRepository.saveAndFlush(server);
        return serverMapper.toDto(server);
    }

    // ok
    @Transactional
    public void delete(Long id) {
        if (!serverRepository.existsById(id)) {
            throw new RuntimeException("Server not found with id: " + id);
        }
        serverRepository.deleteById(id);
    }

    // ok
    @Transactional(readOnly = true)
    public Page<ServerSummaryDto> findByProjectId(Long projectId, Pageable pageable) {
        return serverRepository.findShortDtoServersByProjectId(projectId, pageable);
    }


    private void mapBusinessFields(ServerDto dto, Server server) {
        server.getDisks().clear();
        if (dto.getDisks() != null) {
            for (DiskDto diskDto : dto.getDisks()) {
                Disk disk = new Disk();
                disk.setType(DiskType.valueOf(diskDto.getType()));
                disk.setSizeGb(diskDto.getSizeGb());
                server.addDisk(disk);
            }
        }

        server.getConnectivities().clear();
        if (dto.getConnectivities() != null && !dto.getConnectivities().isEmpty()) {
            List<Connectivity> connectivities = connectivityService.saveConnectivitiesFromDto(dto.getConnectivities());
            server.getConnectivities().addAll(connectivities);
        }
    }
}
