package org.example.repository;

import org.example.entity.Connectivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectivityRepository extends JpaRepository<Connectivity, Long> {

    List<Connectivity> findAllByOrderByCreatedAtDesc();

}
