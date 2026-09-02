package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProjectDto;
import org.example.entity.Project;
import org.example.mapper.ProjectMapper;
import org.example.repository.ProjectRepository;
import org.example.repository.ServerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ServerRepository serverRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProjectDto findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        ProjectDto dto = projectMapper.toDto(project);
        dto.setServers(serverRepository.findShortDtoServers(project.getId()));
        return dto;
    }

    @Transactional
    public ProjectDto findByName(String name) {
        Project project = projectRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Project not found with name: " + name));
        ProjectDto dto = projectMapper.toDto(project);
        dto.setServers(serverRepository.findShortDtoServers(project.getId()));
        return dto;
    }
}
