package repo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.auth.AuthRequestDto;
import repo.dto.auth.AuthResponseDto;
import repo.service.AuthenticationService;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Value("${app.cookie.secure}")
    private boolean cookieSecure;

    public static final long ACCESS_TOKEN_VALIDITY = 24L * 60L * 60L; // 1 day in seconds
    public static final long REFRESH_TOKEN_VALIDITY = 12L * 24L * 60L * 60L; // 12 days in seconds

    public static final String ACCESS_TOKEN_NAME = "access-token";
    public static final String REFRESH_TOKEN_NAME = "refresh-token";

    public static final String SAME_SITE_RESTRICTION = "Strict";

    public static final String ACCESS_TOKEN_PATH = "/";
    public static final String REFRESH_TOKEN_PATH = "/repo/refresh-token";

    @PostMapping(path = Endpoint.AUTHENTICATE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<AuthResponseDto>> authentication(
            @RequestBody @Valid AuthRequestDto dto,
            HttpServletResponse response
    ) {
        ResponseDto<AuthResponseDto> authResponse = authenticationService.authenticate(dto);
        String accessToken = authResponse.payload().accessToken();
        String refreshToken = authResponse.payload().refreshToken();

        ResponseCookie accessTokenCookie = ResponseCookie.from(ACCESS_TOKEN_NAME, accessToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .path(ACCESS_TOKEN_PATH)
                .maxAge(ACCESS_TOKEN_VALIDITY)
                .sameSite(SAME_SITE_RESTRICTION)
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN_NAME, refreshToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .path(REFRESH_TOKEN_PATH)
                .maxAge(REFRESH_TOKEN_VALIDITY)
                .sameSite(SAME_SITE_RESTRICTION)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(path = Endpoint.REFRESH_TOKEN, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<AuthResponseDto>> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Cookie cookie = WebUtils.getCookie(request, REFRESH_TOKEN_NAME);

        String oldRefreshToken = null;
        if (Objects.nonNull(cookie)) {
            oldRefreshToken = cookie.getValue();
        }

        if (Objects.isNull(oldRefreshToken))
            return ResponseEntity.badRequest().build();

        ResponseDto<AuthResponseDto> authRefresh = authenticationService.refresh(oldRefreshToken);

        String accessToken = authRefresh.payload().accessToken();
        String refreshToken = authRefresh.payload().refreshToken();

        ResponseCookie accessTokenCookie = ResponseCookie.from(ACCESS_TOKEN_NAME, accessToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .path(ACCESS_TOKEN_PATH)
                .maxAge(ACCESS_TOKEN_VALIDITY)
                .sameSite(SAME_SITE_RESTRICTION)
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN_NAME, refreshToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .path(REFRESH_TOKEN_PATH)
                .maxAge(REFRESH_TOKEN_VALIDITY)
                .sameSite(SAME_SITE_RESTRICTION)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.ok(authRefresh);
    }

    @PostMapping(path = Endpoint.SIGN_OUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Objects>> signOut(
            HttpServletResponse response
    ) {
        ResponseCookie accessTokenCookie = ResponseCookie.from(ACCESS_TOKEN_NAME)
                .httpOnly(true)
                .secure(cookieSecure)
                .path(ACCESS_TOKEN_PATH)
                .maxAge(0)
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN_NAME)
                .httpOnly(true)
                .secure(cookieSecure)
                .path(REFRESH_TOKEN_PATH)
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.ok(null);
    }

}
