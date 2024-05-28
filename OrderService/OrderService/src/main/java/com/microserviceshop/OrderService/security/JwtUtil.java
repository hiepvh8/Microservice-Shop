package com.microserviceshop.OrderService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class JwtUtil {

    public static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public static List<String> extractRoles(Claims claims) {
        return claims.get("roles", List.class);
    }

    public static List<String> extractPermissions(Claims claims) {
        return claims.get("permissions", List.class);
    }
}


