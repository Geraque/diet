package com.example.demo.security;

import com.example.demo.entity.UserItem;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {
    public static final Logger LOG = LoggerFactory.getLogger(JWTTokenProvider.class);

    public String generateToken(Authentication authentication) {
        UserItem userModel = (UserItem) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);

        String userId = Long.toString(userModel.getUserId());

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("userId", userId);
        claimsMap.put("username", userModel.getEmail());
        claimsMap.put("firstname", userModel.getName());
        claimsMap.put("lastname", userModel.getLastname());

        return Jwts.builder()
            .setSubject(userId)
            .addClaims(claimsMap)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
            .compact();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token);
            return true;
        } catch (SignatureException |
                 MalformedJwtException |
                 ExpiredJwtException |
                 UnsupportedJwtException |
                 IllegalArgumentException ex) {
            LOG.error(ex.getMessage());
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(SecurityConstants.SECRET)
            .parseClaimsJws(token)
            .getBody();
        String userId = (String) claims.get("userId");
        return Long.parseLong(userId);
    }

}
