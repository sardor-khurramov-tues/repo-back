package repo.dto.user;

import jakarta.validation.constraints.NotNull;

public record SetBlockDto(
        @NotNull
        Boolean isBlocked
) {}
