package repo.dto.user;

import repo.entity.enums.UserRole;

public record AppUserResponseDto(
        Long id,
        String username,
        UserRole userRole,
        String hemisId,
        String firstName,
        String lastName,
        String middleName,
        String orcid,
        String ror,
        String imageName,
        Boolean isBlocked
) {}
