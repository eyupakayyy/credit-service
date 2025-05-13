package com.banking.ing.credit.creditservice.security.service.impl;

import static com.banking.ing.credit.creditservice.security.util.JwtUtil.generateToken;

import com.banking.ing.credit.creditservice.security.config.SecurityCredentials;
import com.banking.ing.credit.creditservice.security.modal.AuthRequest;
import com.banking.ing.credit.creditservice.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final SecurityCredentials credentials;

  @Override
  public String login(AuthRequest authRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
    );
    final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
    return generateToken(authentication, userDetails, credentials.getSecretKey());
  }

}
