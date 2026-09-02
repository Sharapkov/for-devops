package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.DiskDto;
import org.example.dto.ServerDto;
import org.example.dto.ServerSummaryDto;
import org.example.entity.Disk;
import org.example.entity.Project;
import org.example.entity.Server;
import org.example.entity.enums.DiskType;
import org.example.mapper.ServerMapper;
import org.example.repository.ProjectRepository;
import org.example.repository.ServerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;
    private final ProjectRepository projectRepository;
    private final ServerMapper serverMapper;

    public List<ServerDto> findAll() {
        return serverRepository.findAll().stream()
                .map(serverMapper::toDto)
                .collect(Collectors.toList());
    }

    public ServerDto findById(Long id) {
        Server server = serverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Server not found with id: " + id));
        return serverMapper.toDto(server);
    }

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
        server = serverRepository.save(server);
        return serverMapper.toDto(server);
    }

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
        server = serverRepository.save(server);
        return serverMapper.toDto(server);
    }

    @Transactional
    public void delete(Long id) {
        if (!serverRepository.existsById(id)) {
            throw new RuntimeException("Server not found with id: " + id);
        }
        serverRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ServerSummaryDto> findByProjectId(Long projectId, Pageable pageable) {
        return serverRepository.findShortDtoServers(projectId, pageable);
    }

    private void mapBusinessFields(ServerDto dto, Server server) {
        // Handle project
        if (dto.getProjectName() != null && !dto.getProjectName().isEmpty()) {
            Project project = projectRepository.findByName(dto.getProjectName())
                    .orElseThrow(() -> new RuntimeException("Project not found with name: " + dto.getProjectName()));
            server.setProject(project);
        }

        // Handle disks
        server.getDisks().clear();
        if (dto.getDisks() != null) {
            for (DiskDto diskDto : dto.getDisks()) {
                Disk disk = new Disk();
                disk.setType(DiskType.valueOf(diskDto.getType()));
                disk.setSizeGb(diskDto.getSizeGb());
                server.addDisk(disk);
            }
        }
    }
}
