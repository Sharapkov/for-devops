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

//    @GetMapping
//    public List<ConnectivityDto> findAll() {
//        return connectivityService.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ConnectivityDto> findById(@PathVariable Long id) {
//        try {
//            ConnectivityDto dto = connectivityService.findById(id);
//            return ResponseEntity.ok(dto);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<ConnectivityDto> create(@Valid @RequestBody ConnectivityDto dto) {
//        ConnectivityDto created = connectivityService.create(dto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ConnectivityDto> update(@PathVariable Long id, @Valid @RequestBody ConnectivityDto dto) {
//        try {
//            ConnectivityDto updated = connectivityService.update(id, dto);
//            return ResponseEntity.ok(updated);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        try {
//            connectivityService.delete(id);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
