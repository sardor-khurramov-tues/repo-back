package repo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SetPasswordDto(
        @NotBlank
        @Pattern(regexp = "^\\S*$")
        @Size(min = 8, max = 64)
        String newPassword
) {}
