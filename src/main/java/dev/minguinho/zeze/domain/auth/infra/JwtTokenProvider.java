package dev.minguinho.zeze.domain.auth.infra;

import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import dev.minguinho.zeze.domain.auth.model.Authority;

@Component
public class JwtTokenProvider {
    private static final String USER_ID_KEY = "userId";
    private static final String ROLES_KEY = "authorities";
    private String secretKey;
    private long validityInMilliseconds;

    public JwtTokenProvider(
        @Value("${security.jwt.token.secret-key}") String secretKey,
        @Value("${security.jwt.token.expire-length}") long validityInMilliseconds
    ) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(Long userId, Set<Authority> authorities) {
        Set<Authority.Role> roles = authorities.stream()
            .map(Authority::getRole)
            .collect(Collectors.toSet());
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
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .get(USER_ID_KEY, Long.class);
    }

    public Set<Authority.Role> getAuthorities(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .get(ROLES_KEY, Set.class);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return claims.getBody().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
