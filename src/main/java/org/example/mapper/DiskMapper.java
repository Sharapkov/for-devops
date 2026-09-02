package org.example.mapper;

import org.example.dto.DiskDto;
import org.example.entity.Disk;
import org.example.entity.enums.DiskType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DiskMapper {

    DiskDto toDto(Disk disk);

    void updateFromDto(DiskDto dto, @MappingTarget Disk disk);

    @Named("diskTypeToString")
    default String diskTypeToString(DiskType type) {
        return type != null ? type.name() : null;
    }

    @Named("stringToDiskType")
    default DiskType stringToDiskType(String type) {
        return type != null ? DiskType.valueOf(type) : null;
    }
}
