package repo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequestDto(
        @NotBlank
        @Size(max = 127)
        String username,
        @NotBlank
        @Size(min = 8, max = 64)
        String password
) {}
