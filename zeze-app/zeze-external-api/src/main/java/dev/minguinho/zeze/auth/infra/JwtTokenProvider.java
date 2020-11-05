package dev.minguinho.zeze.auth.infra;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import dev.minguinho.zeze.auth.exception.InvalidTokenException;
import dev.minguinho.zeze.auth.model.Authority;

@Component
public class JwtTokenProvider {
    private static final String USER_ID_KEY = "userId";
    private static final String ROLES_KEY = "authorities";
    private static final String ROLE_DELIMITER = ",";

    private final String secretKey;
    private final long validityInMilliseconds;

    public JwtTokenProvider(
        @Value("${security.jwt.token.secret-key}") String secretKey,
        @Value("${security.jwt.token.expire-length}") long validityInMilliseconds
    ) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(Long userId, Set<Authority> authorities) {
        String roles = authorities.stream()
            .map(Authority::getRole)
            .map(Authority.Role::name)
            .collect(Collectors.joining(ROLE_DELIMITER));
        Date now = new Date();
        Date validity = new Date(now.getTime()
            + validityInMilliseconds);

        return Jwts.builder()
            .claim(USER_ID_KEY, userId)
            .claim(ROLES_KEY, roles)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public Long getUserId(String token) {
        return getClaims(token).get(USER_ID_KEY, Long.class);
    }

    public Set<Authority.Role> getAuthorities(String token) {
        String roles = getClaims(token).get(ROLES_KEY, String.class);
        return Arrays.stream(roles.split(ROLE_DELIMITER))
            .map(Authority.Role::valueOf)
            .collect(Collectors.toSet());
    }

    public Claims getClaims(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return claims.getBody();
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("invalid token. please login again.");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("expired token. please login again.");
        }
    }
}
