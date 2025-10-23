package repo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterStaffDto(
        @NotBlank
        @Pattern(regexp = "^\\S*$")
        @Size(max = 127)
        String username,
        @NotBlank
        @Pattern(regexp = "^\\S*$")
        @Size(min = 8, max = 64)
        String password,
        @NotBlank
        @Size(max = 63)
        String firstName,
        @NotBlank
        @Size(max = 63)
        String lastName,
        @Size(max = 63)
        String middleName,
        @NotNull
        Long departmentId
) {}
