package repo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterAuthorDto(
        @NotBlank
        @Email
        @Size(max = 127)
        String email,
        @Pattern(regexp = "^\\S*$")
        @Size(max = 31)
        String hemisId,
        @NotBlank
        @Pattern(regexp = "^\\S*$")
        @Size(max = 63)
        String firstName,
        @NotBlank
        @Pattern(regexp = "^\\S*$")
        @Size(max = 63)
        String lastName,
        @Pattern(regexp = "^\\S*$")
        @Size(max = 63)
        String middleName,
        Long departmentId
) {}
