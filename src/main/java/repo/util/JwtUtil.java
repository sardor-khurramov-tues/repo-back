package repo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.experimental.UtilityClass;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class JwtUtil implements Serializable {

    private static final String CLAIM_ID = "id";
    public static final long ACCESS_TOKEN_VALIDITY = 24L * 60L * 60L * 1000L; // 1 day in millis
//    public static final long ACCESS_TOKEN_VALIDITY = 10L * 1000L; // 10 seconds in millis
    public static final long REFRESH_TOKEN_VALIDITY = 12L * 24L * 60L * 60L * 1000L; // 12 days in millis
//    public static final long REFRESH_TOKEN_VALIDITY = 60L * 1000L; // 1 minute in millis

    public static String generateAccessToken(Key secretKey, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_ID, userId);

        long currentTimeMillis = System.currentTimeMillis();
        Date issuedAt = new Date(currentTimeMillis);
        Date expiration = new Date(currentTimeMillis + ACCESS_TOKEN_VALIDITY);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public static String generateRefreshToken(Key secretKey, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_ID, userId);

        long currentTimeMillis = System.currentTimeMillis();
        Date issuedAt = new Date(currentTimeMillis);
        Date expiration = new Date(currentTimeMillis + REFRESH_TOKEN_VALIDITY);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public static Long validateTokenAndGetUserId(Key secretKey, String token)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException,
            SignatureException, IllegalArgumentException
    {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get(CLAIM_ID, Long.class);
    }

}
