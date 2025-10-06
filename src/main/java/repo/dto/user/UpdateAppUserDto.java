package repo.dto.user;

import jakarta.validation.constraints.*;

public record UpdateAppUserDto(
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
        Long departmentId
) {}
