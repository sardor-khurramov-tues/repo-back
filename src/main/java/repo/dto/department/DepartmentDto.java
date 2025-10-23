package repo.dto.department;

import repo.entity.enums.DepType;

public record DepartmentDto(
        Long id,
        DepType depType,
        String nameUz,
        String nameEn,
        String nameRu,
        Boolean isBlocked
) {}
