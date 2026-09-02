package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ConnectivityDto;
import org.example.service.ConnectivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/connectivities")
@RequiredArgsConstructor
public class ConnectivityController {

    private final ConnectivityService connectivityService;

    @PostMapping
    public ResponseEntity<ConnectivityDto> create(@Valid @RequestBody ConnectivityDto dto) {
        try {
            ConnectivityDto created = connectivityService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ConnectivityDto>> findAll() {
        List<ConnectivityDto> connectivities = connectivityService.findAll();
        return ResponseEntity.ok(connectivities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectivityDto> findById(@PathVariable Long id) {
        try {
            ConnectivityDto dto = connectivityService.findById(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
