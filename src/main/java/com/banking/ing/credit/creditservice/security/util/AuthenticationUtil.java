package com.banking.ing.credit.creditservice.security.util;

import static com.banking.ing.credit.creditservice.user.enums.UserRoleType.ADMIN;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.banking.ing.credit.creditservice.common.util.base.Utility;
import java.util.Collection;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public final class AuthenticationUtil extends Utility {

  public static Optional<String> getCurrentUsernameOptional() {
    String username = getCurrentUsername();
    if (username == null) {
      return Optional.empty();
    }
    return Optional.of(username);
  }

  public static String getCurrentUsername() {
    Authentication authentication = getContext().getAuthentication();
    return (authentication != null) ? authentication.getName() : null;
  }

  public static boolean isCurrentUserAdmin() {
    Authentication authentication = getContext().getAuthentication();
    if (authentication == null) {
      return false;
    }
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    return authorities.stream()
        .anyMatch(auth -> auth.getAuthority().equals(ADMIN.getCode()));
  }

  public static Authentication getAuthentication() {
    return getContext().getAuthentication();
  }
}
