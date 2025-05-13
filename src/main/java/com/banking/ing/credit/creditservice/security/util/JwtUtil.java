package com.banking.ing.credit.creditservice.security.util;

import com.banking.ing.credit.creditservice.common.util.base.Utility;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public final class JwtUtil extends Utility {

  public static String generateToken(final Authentication authentication, final UserDetails userDetails, final String secretKey) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(Date.from(LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(getSignKey(secretKey), SignatureAlgorithm.HS256)
        .compact();
  }

  public static boolean validateToken(final String token, final String secretKey, final UserDetails userDetails) {
    final String username = extractUsername(token, secretKey);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token, secretKey);
  }

  public static String extractUsername(final String token, final String secretKey) {
    return extractClaims(token, secretKey).getSubject();
  }

  public static Claims extractClaims(final String token, final String secretKey) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignKey(secretKey))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private static boolean isTokenExpired(final String token, final String secretKey) {
    return extractClaims(token, secretKey).getExpiration().before(new Date());
  }

  private static Key getSignKey(final String secretKey) {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public static String resolveToken(final HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    String customToken = request.getHeader("X-Auth-Token");
    if (customToken != null && !customToken.isBlank()) {
      return customToken;
    }

    return null;
  }
}
