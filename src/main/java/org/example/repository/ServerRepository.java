package org.example.repository;

import org.example.dto.ServerSummaryDto;
import org.example.entity.Server;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {

    boolean existsByHostname(String hostname);

    boolean existsByIpAddress(String ipAddress);

    Optional<Server> findByIpAddress(String ipAddress);

    @Query("SELECT new org.example.dto.ServerSummaryDto(s.id, s.hostname, s.ipAddress, s.role) " +
            "FROM Server s WHERE s.project.id = ?1")
    Page<ServerSummaryDto> findShortDtoServersByProjectId(Long projectId, Pageable pageable);
}
