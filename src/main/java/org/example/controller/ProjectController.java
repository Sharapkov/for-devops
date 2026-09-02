package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProjectShortDto;
import org.example.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectShortDto> findAll() {
        return projectService.findAll();
    }

}
