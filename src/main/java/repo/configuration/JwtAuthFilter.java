package repo.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import repo.entity.AppUserEntity;
import repo.repository.AppUserRepository;
import repo.util.JwtUtil;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String AUTH_HEADER_VALUE_STARTS_WITH = "Bearer ";
    private static final int AUTH_HEADER_SUBSTRING_BEGIN_INDEX = 7;

    private final SecretKeyProvider secretKeyProvider;
    private final AppUserRepository appUserRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
//        final String authHeader = request.getHeader(AUTH_HEADER_NAME);
//
//        if (authHeader == null || !authHeader.startsWith(AUTH_HEADER_VALUE_STARTS_WITH)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String jwt = authHeader.substring(AUTH_HEADER_SUBSTRING_BEGIN_INDEX);

        Cookie accessTokenCookie = WebUtils.getCookie(request, "access-token");
        String accessToken = null;

        if (accessTokenCookie != null) {
            accessToken = accessTokenCookie.getValue();
        }

        if (Objects.isNull(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = accessToken;

        try {
            Long userId = JwtUtil.validateTokenAndGetUserId(secretKeyProvider.getSecretKey(), jwt);

            AppUserEntity user = appUserRepository
                    .findByIdAndIsBlockedFalse(userId)
                    .orElseThrow();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
