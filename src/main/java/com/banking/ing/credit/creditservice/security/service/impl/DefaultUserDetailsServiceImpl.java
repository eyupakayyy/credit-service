package com.banking.ing.credit.creditservice.security.service.impl;

import static java.lang.Boolean.TRUE;

import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import com.banking.ing.credit.creditservice.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  private static User toUser(final UserEntity entity) {
    return new User(
        entity.getUsername(),
        entity.getPassword(),
        List.of(new SimpleGrantedAuthority(entity.getRole().getCode()))
    );
  }

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    UserEntity user = fetchUser(username);
    if (TRUE.equals(user.getDeleted())) {
      throw new UsernameNotFoundException("User account is deleted");
    }
    return toUser(user);
  }

  private UserEntity fetchUser(final String username) {
    return userService.findByUsername(username);
  }
}
