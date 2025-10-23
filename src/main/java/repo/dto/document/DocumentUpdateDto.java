package repo.dto.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import repo.entity.enums.DocType;

public record DocumentUpdateDto(
        @NotNull
        Long departmentId,
        @NotNull
        DocType docType,
        @NotBlank
        @Size(max = 255)
        String title,
        @Size(max = 255)
        String subtitle,
        @Size(max = 5000)
        String docAbstract
) {}
