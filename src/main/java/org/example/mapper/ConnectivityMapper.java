package org.example.mapper;

import org.example.dto.ConnectivityDto;
import org.example.entity.Connectivity;
import org.example.entity.enums.ConnectivityDirection;
import org.example.entity.enums.ConnectivityStatus;
import org.example.entity.enums.ConnectivityType;
import org.example.entity.enums.ValidityType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ConnectivityMapper {

    @Mapping(target = "type", source = "type", qualifiedByName = "connectivityTypeToString")
    @Mapping(target = "direction", source = "direction", qualifiedByName = "connectivityDirectionToString")
    @Mapping(target = "validityType", source = "validityType", qualifiedByName = "validityTypeToString")
    @Mapping(target = "status", source = "status", qualifiedByName = "connectivityStatusToString")
    ConnectivityDto toDto(Connectivity connectivity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "type", source = "type", qualifiedByName = "connectivityTypeFromValue")
    @Mapping(target = "direction", source = "direction", qualifiedByName = "connectivityDirectionFromValue")
    @Mapping(target = "validityType", source = "validityType", qualifiedByName = "validityTypeFromValue")
    @Mapping(target = "status", source = "status", qualifiedByName = "connectivityStatusFromValue")
    Connectivity toEntity(ConnectivityDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "type", source = "type", qualifiedByName = "connectivityTypeFromValue")
    @Mapping(target = "direction", source = "direction", qualifiedByName = "connectivityDirectionFromValue")
    @Mapping(target = "validityType", source = "validityType", qualifiedByName = "validityTypeFromValue")
    @Mapping(target = "status", source = "status", qualifiedByName = "connectivityStatusFromValue")
    void updateFromDto(ConnectivityDto dto, @MappingTarget Connectivity connectivity);

    @Named("connectivityTypeToString")
    default String connectivityTypeToString(ConnectivityType type) {
        return type != null ? type.name() : null;
    }

    @Named("connectivityTypeFromValue")
    default ConnectivityType connectivityTypeFromValue(String value) {
        return value != null ? ConnectivityType.valueOf(value) : null;
    }

    @Named("connectivityDirectionToString")
    default String connectivityDirectionToString(ConnectivityDirection direction) {
        return direction != null ? direction.name() : null;
    }

    @Named("connectivityDirectionFromValue")
    default ConnectivityDirection connectivityDirectionFromValue(String value) {
        return value != null ? ConnectivityDirection.valueOf(value) : null;
    }

    @Named("validityTypeToString")
    default String validityTypeToString(ValidityType type) {
        return type != null ? type.name() : null;
    }

    @Named("validityTypeFromValue")
    default ValidityType validityTypeFromValue(String value) {
        return value != null ? ValidityType.valueOf(value) : null;
    }

    @Named("connectivityStatusToString")
    default String connectivityStatusToString(ConnectivityStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("connectivityStatusFromValue")
    default ConnectivityStatus connectivityStatusFromValue(String value) {
        return value != null ? ConnectivityStatus.valueOf(value) : null;
    }
}
