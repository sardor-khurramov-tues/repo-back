package repo.dto.auth;

public record AuthResponseDto(
        String accessToken,
        String refreshToken
) {}
