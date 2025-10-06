package repo.dto.department;

public record DepartmentDto(
        Long id,
        String nameUz,
        String nameEn,
        String nameRu,
        Boolean isBlocked
) {}
