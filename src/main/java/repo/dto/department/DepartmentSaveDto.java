package repo.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentSaveDto(
        @NotBlank
        @Size(max = 63)
        String nameUz,
        @NotBlank
        @Size(max = 63)
        String nameEn,
        @NotBlank
        @Size(max = 63)
        String nameRu,
        @NotNull
        Boolean isBlocked
) {}
