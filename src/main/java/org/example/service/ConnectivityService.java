package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ConnectivityDto;
import org.example.entity.Connectivity;
import org.example.entity.Server;
import org.example.entity.enums.ConnectivityDirection;
import org.example.entity.enums.ConnectivityStatus;
import org.example.entity.enums.ConnectivityType;
import org.example.entity.enums.ValidityType;
import org.example.mapper.ConnectivityMapper;
import org.example.repository.ConnectivityRepository;
import org.example.repository.ServerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConnectivityService {

    private final ConnectivityRepository connectivityRepository;
    private final ServerRepository serverRepository;
    private final ConnectivityMapper connectivityMapper;

}
