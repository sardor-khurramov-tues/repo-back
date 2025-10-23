package repo.dto.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import repo.entity.enums.DocType;

import java.util.List;

public record DocumentSubmitDto(
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
        String docAbstract,
        List<DocContributorSubmitDto> docContributorList
) {}
