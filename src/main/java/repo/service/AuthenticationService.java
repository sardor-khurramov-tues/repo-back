package repo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repo.configuration.SecretKeyProvider;
import repo.constant.ResponseType;
import repo.dto.ResponseDto;
import repo.dto.auth.AuthRequestDto;
import repo.dto.auth.AuthResponseDto;
import repo.entity.AppUserEntity;
import repo.exception.CustomBadRequestException;
import repo.repository.AppUserRepository;
import repo.util.JwtUtil;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecretKeyProvider secretKeyProvider;

    public ResponseDto<AuthResponseDto> authenticate(AuthRequestDto dto) {
        AppUserEntity user = getUserByUsername(dto.username());

        checkPassword(dto.password(), user.getPassword());
        checkUserIsNonBlocked(user);

        String accessToken = JwtUtil.generateAccessToken(
                secretKeyProvider.getSecretKey(),
                user.getId()
        );

        String refreshToken = JwtUtil.generateRefreshToken(
                secretKeyProvider.getSecretKey(),
                user.getId()
        );

        return ResponseDto.getSuccess(
                new AuthResponseDto(accessToken, refreshToken)
        );
    }

    public ResponseDto<AuthResponseDto> refresh(String token) {
        Long userId = JwtUtil.validateTokenAndGetUserId(
                secretKeyProvider.getSecretKey(),
                token
        );

        AppUserEntity user = getUserById(userId);
        checkUserIsNonBlocked(user);

        String accessToken = JwtUtil.generateAccessToken(
                secretKeyProvider.getSecretKey(),
                user.getId()
        );

        String refreshToken = JwtUtil.generateRefreshToken(
                secretKeyProvider.getSecretKey(),
                user.getId()
        );

        return ResponseDto.getSuccess(
                new AuthResponseDto(accessToken, refreshToken)
        );
    }



    public AppUserEntity getUserByUsername(String email) {
        try {
            return appUserRepository.findByUsername(email)
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new CustomBadRequestException(ResponseType.WRONG_CREDENTIALS);
        }
    }

    private void checkPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword))
            throw new CustomBadRequestException(ResponseType.WRONG_CREDENTIALS);
    }

    private void checkUserIsNonBlocked(AppUserEntity appUserEntity) {
        if (Boolean.TRUE.equals(appUserEntity.getIsBlocked()))
            throw new CustomBadRequestException(ResponseType.WRONG_CREDENTIALS);
    }

    public AppUserEntity getUserById(Long userId) {
        try {
            return appUserRepository.findById(userId)
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new CustomBadRequestException(ResponseType.WRONG_CREDENTIALS);
        }
    }

}
