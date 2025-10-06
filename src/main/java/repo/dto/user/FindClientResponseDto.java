package repo.dto.user;

import java.util.List;

public record FindClientResponseDto(
        Long totalCount,
        Long pageCount,
        List<AppUserResponseDto> clientList
) {}
