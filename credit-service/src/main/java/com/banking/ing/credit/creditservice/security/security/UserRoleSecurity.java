package com.banking.ing.credit.creditservice.security.security;

import static com.banking.ing.credit.creditservice.security.util.AuthenticationUtil.getCurrentUsername;
import static com.banking.ing.credit.creditservice.security.util.AuthenticationUtil.isCurrentUserAdmin;
import static java.lang.String.valueOf;

import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import com.banking.ing.credit.creditservice.user.exception.UserNotFoundException;
import com.banking.ing.credit.creditservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserRoleSecurity {

  private final UserService userService;

  public boolean isAdminOrSelf(final Long userId) {
    UserEntity user = userService.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(valueOf(userId)));
    return isCurrentAdminOrSelf(user.getUsername());
  }

  public boolean isAdminOrSelf(final String username) {
    return isCurrentAdminOrSelf(username);
  }

  private boolean isCurrentAdminOrSelf(final String user) {
    return isCurrentUserAdmin() || getCurrentUsername().equals(user);
  }
}