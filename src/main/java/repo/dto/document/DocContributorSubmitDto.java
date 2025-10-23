package repo.dto.document;

import jakarta.validation.constraints.NotNull;
import repo.entity.enums.DocRole;

public record DocContributorSubmitDto(
        @NotNull
        Long appUserId,
        @NotNull
        DocRole docRole
) {}
