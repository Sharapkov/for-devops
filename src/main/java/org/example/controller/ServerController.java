package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ServerDto;
import org.example.dto.ServerSummaryDto;
import org.example.service.ServerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/servers")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @GetMapping("/{id}")
    public ResponseEntity<ServerDto> findById(@PathVariable Long id) {
        try {
            ServerDto dto = serverService.findById(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ServerDto> create(@Valid @RequestBody ServerDto dto) {
        try {
            ServerDto created = serverService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServerDto> update(@PathVariable Long id, @Valid @RequestBody ServerDto dto) {
        try {
            ServerDto updated = serverService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            serverService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/project")
    public ResponseEntity<Page<ServerSummaryDto>> findByProjectId(
            @RequestParam Long projectId,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<ServerSummaryDto> servers = serverService.findByProjectId(projectId, pageable);
            return ResponseEntity.ok(servers);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
