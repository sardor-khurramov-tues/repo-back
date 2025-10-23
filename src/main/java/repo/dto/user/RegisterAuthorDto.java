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
        @Size(max = 31)
        String hemisId,
        @NotBlank
        @Size(max = 63)
        String firstName,
        @NotBlank
        @Size(max = 63)
        String lastName,
        @Size(max = 63)
        String middleName,
        @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{3}[0-9X]$")
        @Size(max = 19)
        String orcid,
        @Pattern(regexp = "^0[a-hj-km-np-tv-z0-9]{6}\\d{2}$")
        @Size(max = 9)
        String ror,
        Long departmentId
) {}
