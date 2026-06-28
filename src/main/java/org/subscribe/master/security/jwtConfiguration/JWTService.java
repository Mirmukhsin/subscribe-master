package org.subscribe.master.security.jwtConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.subscribe.master.entities.AuthUser;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(AuthUser user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 900000))
                .signWith(getSecretKey())
                .compact();
    }

    public String getEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long getUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    private SecretKey getSecretKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }


    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


}
