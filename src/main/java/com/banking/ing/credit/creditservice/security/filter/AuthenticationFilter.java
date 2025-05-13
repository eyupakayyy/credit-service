package com.banking.ing.credit.creditservice.security.filter;

import static com.banking.ing.credit.creditservice.security.constants.SecurityConstants.AUTHENTICATION_WHITE_LIST;
import static com.banking.ing.credit.creditservice.security.util.JwtUtil.extractUsername;
import static com.banking.ing.credit.creditservice.security.util.JwtUtil.resolveToken;
import static com.banking.ing.credit.creditservice.security.util.JwtUtil.validateToken;

import com.banking.ing.credit.creditservice.security.config.SecurityCredentials;
import com.banking.ing.credit.creditservice.security.security.JwtAuthenticationEntryPoint;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

  private final JwtAuthenticationEntryPoint entryPoint;
  private final UserDetailsService detailsService;
  private final SecurityCredentials credentials;

  @Override
  protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String token = resolveToken(request);
      if (token != null) {
        String username = extractUsername(token, credentials.getSecretKey());
        if (username != null) {
          UserDetails userDetails = detailsService.loadUserByUsername(username);
          if (validateToken(token, credentials.getSecretKey(), userDetails)) {
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }
      }
      filterChain.doFilter(request, response);
    } catch (JwtException ex) {
      entryPoint.commence(request, response, new AuthenticationServiceException("Invalid or expired JWT token", ex));
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();
    AntPathMatcher matcher = new AntPathMatcher();
    return Arrays.stream(AUTHENTICATION_WHITE_LIST)
        .anyMatch(whitelisted -> matcher.match(whitelisted, path));
  }
}
