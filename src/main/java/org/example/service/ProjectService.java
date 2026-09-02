package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProjectShortDto;
import org.example.mapper.ProjectMapper;
import org.example.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectShortDto> findAll() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toShortDto)
                .collect(Collectors.toList());
    }

}
